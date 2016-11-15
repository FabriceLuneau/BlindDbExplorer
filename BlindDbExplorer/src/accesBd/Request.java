package accesBd;

import java.util.ArrayList;

/**
 * @author Luneau

 *Provide SQL request from a DbManager 
 */
public class Request {
	private DbManager dbManager = null;
	private String sqlOrders;
	
	private ArrayList<String> columns = new ArrayList<String>();
	private ArrayList<ArrayList> rows = new ArrayList<ArrayList>();

	public Request(DbManager dbManager, String sqlOrders) {
		this.dbManager = dbManager;
		this.sqlOrders = sqlOrders;

		execute();
	}

	public void setDbManager(DbManager dbManager) {
		this.dbManager = dbManager;
	}

	public String getSqlOrders() {
		return sqlOrders;
	}

	public void setSqlOrders(String sqlOrders) {
		this.sqlOrders = sqlOrders;
	}

	public void setColumns(ArrayList<String> columns) {
		this.columns = columns;
	}

	public void setRows(ArrayList<ArrayList> rows) {
		this.rows = rows;
	}

	public void execute() {
		dbManager.executeSelectRequest(this);
	}

	public String getValueAt(int y, int x) {
		ArrayList<ArrayList> row = rows.get(y);
		Object chaine = row.get(x);
		return chaine.toString();
	}

	public String getColumnName(int columnNum) {
		return columns.get(columnNum);
	}

	public int getColumnCount() {
		return columns.size();
	}

	public int getRowCount() {
		return rows.size();
	}

	@Override
	public String toString() {
		StringBuffer chaine = new StringBuffer();

		for (String column : columns) {
			chaine.append(column + " ");
		}
		chaine.append("\n");

		for (ArrayList<String> row : rows) {
			for (String value : row) {
				chaine.append(value);
			}
			chaine.append("\n");
		}

		return chaine.toString();
	}

	/**
	 * @return the result of request to HTML format 
	 */
	public String toHTML() {
		StringBuffer chaine = new StringBuffer();

		// chaine.append("text/html\n");
		// chaine.append("<html>\n");
		chaine.append("<table>\n");
		chaine.append("<tr>\n");

		for (String column : columns) {
			chaine.append("<th>" + column + "</th>");
		}

		chaine.append("</tr>\n");

		for (ArrayList<String> row : rows) {
			chaine.append("</tr>");

			for (String value : row) {
				chaine.append("<td>" + value + "</td>");
			}
			chaine.append("</tr>\n");
		}
		chaine.append("</table>\n");
		// chaine.append("</html>\n");

		return chaine.toString();
	}

	// Prend en argument un script sql
	// et Retourne un tableau où chaques entrées est une instruction SQL
	public static String[] splitRequte(String requete) {
		String[] tableauDesRequetes = requete.split(";");
		;

		return tableauDesRequetes;
	}

	public static boolean isSelect(String requete) {
		requete = requete.trim();
		requete = requete.toUpperCase();

		if (requete.matches("SELECT.*"))
			return true;
		else
			return false;
	}
}