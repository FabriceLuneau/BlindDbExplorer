package accesBd;

	import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author Luneau

 *reviwed on
 *
 *The object DbManager provide all transaction with database
 * 
 */
/**
 * @author Luneau
 *
 */
/**
 * @author Luneau
 *
 */
/**
 * @author Luneau
 *
 */
/**
 * @author Luneau
 *
 */
public final class DbManager {
	private String name;
	private String url;
	private String user;
	private String password;

	private Connection connection = null;

	public DbManager() {}

	public DbManager(String name,String url, String user, String password) {
		this.name = name;
		this.url = url;
		this.user = user;
		this.password = password;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

//ToDo change for a better solution
	public boolean loadDriver(String pathDriver) {
		boolean success = false;
		File file = new File(pathDriver);

		try {
			URLClassLoader systemClassLoader = (URLClassLoader) ClassLoader
					.getSystemClassLoader();
			URL url = file.toURI().toURL();
			Method addURL = URLClassLoader.class.getDeclaredMethod("addURL",
					new Class<?>[] { URL.class });
			addURL.setAccessible(true);
			addURL.invoke(systemClassLoader, new Object[] { url });

			success = true;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return success;
	}

	/**
	 * Open a JDBC connection for DbManager
	 * 
	 * @return true if the connection has been opend
	 */
	public boolean openConnection() {
		boolean success = false;

//if a connection is already opend, we return it 
		if (connection != null) {
			success = true;
			return success;
		}
		
		//if there no connectio opened, we creat one
		try {
			connection = DriverManager.getConnection(url, user, password);

			success = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return success;
	}

	/**
	 *Do the database shutdown correctly, and Close the connection 
	 */
	public void closeConnection() {
		try {
			generateStatement().execute("SHUTDOWN");
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return Statement
	 * 
	 * create a statement for internal use
	 */
	private Statement generateStatement() {
		Statement statement = null;

		try {
			statement = statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return statement;
	}

	/**
	 * @param requete
	 * 
	 * Executre SQL SELECT orders
	 */
	public void executeSelectRequest(Request requete) {
		Statement statement;
		openConnection();

		// To chechk
		int type = ResultSet.TYPE_SCROLL_INSENSITIVE;

		// To check
		int mode = ResultSet.CONCUR_UPDATABLE;

		try {
			statement = generateStatement();

			if (statement.execute(requete.getSqlOrders())) {
				ResultSet resultSet = statement.getResultSet();

				requete.setColumns(getColumnsForRequest(resultSet));

				ArrayList<ArrayList> rows = new ArrayList<ArrayList>();

				ResultSetMetaData metaDatas = resultSet.getMetaData();

				while (resultSet.next()) {
					ArrayList<String> row = new ArrayList<String>();

					for (int i = 1; i <= metaDatas.getColumnCount(); i++) {
						row.add(resultSet.getString(i));
					}

					rows.add(row);
				}

				requete.setRows(rows);
			} else {
				requete.setColumns(new ArrayList<String>());

				requete.setRows(new ArrayList());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param resultSet
	 * @return colunms name for a request
	 */
	private ArrayList<String> getColumnsForRequest(ResultSet resultSet) {
		ArrayList columns = new ArrayList<String>();

		try {
			ResultSetMetaData metaDatas = resultSet.getMetaData();

			for (int i = 1; i <= metaDatas.getColumnCount(); i++) {
				columns.add(metaDatas.getColumnName(i).toString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return columns;
	}

	/**
	 * @param requete
	 * @return number of affected rows
	 * 
	 * Execute a SQL orders, witch is not a SELECT
	 */
	public int executeChangeRequest(Request requete) {
		Statement statement = generateStatement();

		int numOfChange = 0;
		openConnection();

		try {
			numOfChange = statement.executeUpdate(requete.getSqlOrders());

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return numOfChange;
	}

	/**
	 * @return
	 * 
	 * To check
	 * return the connection  for internal use ???
	 */
	public Connection getConnection() {
		openConnection();

		return connection;
	}

	/**
	 * @return name of tables
	 * 
	 * Return the names of tables of the dattabase
	 */
	public ArrayList<String> getTableList() {
		ArrayList<String> tableList = new ArrayList<String>();

		try {
			DatabaseMetaData dmd = getConnection().getMetaData();

			ResultSet tables = dmd.getTables(connection.getCatalog(), null,
					null, null);

			while (tables.next()) {
				if (tables.getString("TABLE_TYPE").equals("TABLE"))
					tableList.add(tables.getString("TABLE_NAME"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tableList;
	}

	/**
	 * @param table
	 * @return columns name of the tables 
	 */
	public ArrayList<String> getColumnsForTable(String table) {
		ArrayList<String> lcolonnList = new ArrayList<String>();

		try {
			DatabaseMetaData dmd = connection.getMetaData();

			ResultSet resultat = dmd.getColumns(connection.getCatalog(), null,
					table, "%");

			ResultSetMetaData rsmd = resultat.getMetaData();
			while (resultat.next()) {
				String val = resultat.getString("COLUMN_NAME");

				lcolonnList.add(val);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lcolonnList;
	}

	public ArrayList<String> getColumnDetailForTable(String table) {
		ArrayList<String> colonneList = new ArrayList<String>();

		try {
			ArrayList<String> keyList = getListePrimaryKeyForTable(table);

			DatabaseMetaData dmd = connection.getMetaData();

			ResultSet resultat = dmd.getColumns(connection.getCatalog(), null,
					table, "%");

			ResultSetMetaData rsmd = resultat.getMetaData();
			while (resultat.next()) {
				String val = resultat.getString("COLUMN_NAME") + " "
						+ resultat.getString("TYPE_NAME") + " "
						+ resultat.getString("COLUMN_SIZE");

				for (String clef : keyList) {
					if (clef.equals(resultat.getString("COLUMN_NAME")))
						val += "PK";
				}
				colonneList.add(val);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return colonneList;
	}

	/**
	 * @param table
	 * @return the list of primary key for the table
	 */
	public ArrayList<String> getListePrimaryKeyForTable(String table) {
		ArrayList<String> listeClefs = new ArrayList<String>();

		try {
			listeClefs = new ArrayList();

			DatabaseMetaData metadata = connection.getMetaData();

			ResultSet clefs = metadata.getPrimaryKeys(connection.getCatalog(),
					null, table);

			while (clefs.next()) {
				listeClefs.add(clefs.getString("COLUMN_NAME"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listeClefs;
	}

	/**
	 * @param tableName
	 * @return the num of fields for the table
	 */
	public int getNumOfFieldsForTable(String tableName) {
		Statement statement;

		int num = 0;

		try {
			openConnection();
			statement = connection.createStatement();

			String requete = ("SELECT count(*) FROM " + tableName);

			ResultSet rs = statement.executeQuery(requete);

			rs.next();

			num = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return num;
	}
		
/* (non-Javadoc)
 * @see java.lang.Object#toString()
 * 
 * return a description of state of DbManager
 */
@Override		
		public String toString() {
	String text = 
			"\nUSER : " + user +
					"\nURL : " + url+
					"\nPassword: " + (password!=null?"*":"null") +
	"\nConnection: "
+ (connection==null?"Closed":"opened");
	
	return text;
}
	}