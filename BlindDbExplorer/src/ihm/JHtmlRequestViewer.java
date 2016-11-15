package ihm;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.text.html.HTMLEditorKit;

import accesBd.DbManager;
import accesBd.Request;

public class JHtmlRequestViewer extends JEditorPane {
	private Request requete;

	public JHtmlRequestViewer(Request requete) {
		this.requete = requete;

		setEditorKit(new HTMLEditorKit());

		if (requete != null) {
			setText(requete.toHTML());
		} else {
			setText("Empty");
		}

		// try {
		// URL page = new URL(requete.toHTML());

		// setPage(page);
		// }catch(MalformedURLException e) {
		// e.printStackTrace();
		// }catch( e) {
		// e.printStackTrace();
		// }

		setEditable(false);

		setVisible(true);

		// requet//requestFocusInWindow();
	}

	public Request getRequete() {
		return requete;
	}

	public void setRequete(Request requete) {
		this.requete = requete;
					setText(requete.toHTML());
	}

	public static void main(String[] args) {
		DbManager connection = new DbManager("none", "jdbc:hsqldb:file:user/ABC.hsql", "sa", "");

		connection.loadDriver("lib/hsqldb.jar");
		connection.openConnection();
		
		new JMainWindow(connection);
		
		/*
		Requete requete = null;

		if(requete != null) {
		requete = new Requete(connection, "select * from compte");
		requete.execute();

		}
			
		JFrame frame = new JFrame("Teste");
		frame.setSize(500, 500);

		JRequeteViewer requeteViewer = new JRequeteViewer(requete);
		JTableRequete tableRequete = new JTableRequete(requete);

		JTabbedPane tabbedPane = new JTabbedPane();

		frame.setContentPane(tabbedPane);

		JScrollPane scrollPane;

		scrollPane = new JScrollPane();
		scrollPane.setViewportView(requeteViewer);
		scrollPane.setName("HTML");
		tabbedPane.add(scrollPane, 0);

		scrollPane = new JScrollPane();
		scrollPane.setViewportView(tableRequete);
		scrollPane.setName("Jtable");
		tabbedPane.add(scrollPane, 1);

		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		*/
		
		System.out.println(connection);

		//connection.closeConnection();
	}
}