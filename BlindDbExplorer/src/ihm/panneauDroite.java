package ihm;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import accesBd.DbManager;
import accesBd.Request;

public final class panneauDroite extends JPanel {
	protected JTabbedPane tabbedPane= new JTabbedPane();
	//protected JScrollPane scrollPaneTableau = new JScrollPane();
	protected JScrollPane scrollPaneConsole = new JScrollPane();

	protected JLabel labelTableau = new JLabel("Tableau des résultats");
	protected JTableRequestViewer tableRequete = new JTableRequestViewer(null);
	protected JHtmlRequestViewer requeteViewer = new JHtmlRequestViewer (null);
	
	protected JLabel labelConsole = new JLabel("Console SQL");
	protected JTextArea
	consoleSql = new JTextArea(10,80);
	
	protected JButton boutonExecuter = new JButton("Executer");

	protected DbManager connection;

	public panneauDroite(DbManager connection ) {
		this.connection = connection;
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		Request requete = null;
		
		JScrollPane scrollPane;
		
		scrollPane = new JScrollPane();
				scrollPane.setViewportView(requeteViewer);
				scrollPane.setName("HTML");
tabbedPane.add(scrollPane,0);

scrollPane = new JScrollPane();
scrollPane.setViewportView(tableRequete);
scrollPane.setName("Jtable");;
tabbedPane.add(scrollPane, 1);

scrollPane = new JScrollPane();
//scrollPane.setViewportView(console);
scrollPane.setName("Console");;
tabbedPane.add(scrollPane, 1);

scrollPane = new JScrollPane();
//scrollPane.setViewportView(history);
scrollPane.setName("History");;
tabbedPane.add(scrollPane, 1);

add(tabbedPane);
		
		//add(labelTableau);
//labelTableau.setDisplayedMnemonic(KeyEvent.VK_T);
		//labelTableau.setLabelFor(tableRequete);
		
		add(labelConsole);
		scrollPaneConsole.setViewportView(consoleSql);
		add(scrollPaneConsole);
		
		labelConsole.setDisplayedMnemonic(KeyEvent.VK_C)	;
		labelConsole.setLabelFor(consoleSql);
		
		add(boutonExecuter);
		
		boutonExecuter.setMnemonic('X');
		
		//pack();

		boutonExecuter.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				executerScript();
				//PanneauGauche.getDataBaseExplorer().refresh();
				}});
		}

	private void executerScript() {
		String chaineRequete = consoleSql.getText();
		Request requete = new Request(connection, chaineRequete);
		tableRequete.setRequete(requete);
		requeteViewer.setRequete(requete); 
	}
}