package test;

import accesBd.DbManager;
import accesBd.Request;

public class RequeteTest {
	public static void main(String args[]) {
		DbManager dbManager = new DbManager();
		dbManager.setName("none");
		dbManager.setUrl("jdbc:hsqldb:file:user/ABC.hsql");
		dbManager.setUser("sa");
		dbManager.setPassword("");
		
		dbManager.loadDriver("lib/hsqldb.jar");

		dbManager.openConnection();

		Request requete;

		requete = new Request(dbManager, "select * from compte");
		requete.execute();

		System.out.println(requete);

		System.out.println(requete.getColumnCount());
		System.out.println(requete.getRowCount());
	}
}