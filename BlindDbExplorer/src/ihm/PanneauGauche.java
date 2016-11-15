package ihm;

import java.awt.FlowLayout;
import java.awt.event.KeyEvent;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;

import accesBd.DbManager;

public final class PanneauGauche extends JPanel {
	protected JPanel pane1 = new JPanel();
	protected JScrollPane pane2 = new JScrollPane();

	protected JLabel labelExplorateur = new JLabel("Explorateur");
	protected JTreeDataBase databaseExplorer;

	public PanneauGauche(DbManager connection) {
		pane1.add(labelExplorateur);
		
databaseExplorer = new JTreeDataBase(connection);
		pane2.setViewportView(databaseExplorer);

		add(pane1);
add(pane2);

		labelExplorateur.setDisplayedMnemonic(KeyEvent.VK_E);
		labelExplorateur.setLabelFor(databaseExplorer);
		
//		pane1.setLayout(new FlowLayout(FlowLayout.LEFT));
		//pane2.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
//		pack();
	}
	
	public JTreeDataBase	getDataBaseExplorer() {
		return databaseExplorer;
	}
}