package ihm;

import java.io.File;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;

import accesBd.DbManager;
import accesBd.Request;

public final class JMainWindow extends JFrame {
	protected JSplitPane panneauPrincipal = new JSplitPane();
	protected PanneauGauche panneauGauche;
	protected panneauDroite panneauDroite;
	
	protected DbManager dbManager;

	public JMainWindow(DbManager dbManager ) {
		super(dbManager.getName() + " - BlindDBExplorer");
		
		this.dbManager = dbManager;
		
		setSize(500, 500);
		Request requete = null;
		
		panneauGauche = new PanneauGauche(dbManager);
		panneauDroite = new panneauDroite(dbManager);
		
		panneauPrincipal = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT
				,
		panneauGauche, panneauDroite);
		panneauPrincipal.setDividerLocation(150);

		setContentPane(panneauPrincipal);
		
		pack();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setVisible(true);
	}
}