import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import accesBd.DbManager;
import ihm.JLogin;
import ihm.JMainWindow;

/**
 * @author Fabrice Luneau
 * 
 *         revied on 16/01/16
 *
 *         Launcher for the application
 *
 */
public final class BlindDbExplorer {
	private DbManager dbManager;

	private JLogin jLogin;

	public BlindDbExplorer(DbManager dbManager) {
		this.dbManager = dbManager;
		this.jLogin = new JLogin(dbManager, "conf/");

		jLogin.getButtonConnect().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (jLogin.connect()) {
					launch();
				}
			}
		});
	}

	private void launch() {
		new JMainWindow(dbManager);
	}

	public static void main(String[] args) {
		new BlindDbExplorer(new DbManager());
	}
}