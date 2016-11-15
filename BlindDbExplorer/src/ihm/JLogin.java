package ihm;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Configurator.Configurator;
import Configurator.ConfiguratorManager;
import accesBd.DbManager;

/**
 * @author Luneau
 * 
 *         Window for database connection
 * 
 */
public final class JLogin extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final String TITLE = "DataBase Login";
	private static final String NEWENTRYNAME = "new";

	private JPanel mainPanel;

	private JLabel labelConfiguration;
	private JList<Configurator> listConfigurations;

	private JButton buttonDelete;

	private JLabel labelURL;
	private JTextField fieldURL;

	private JLabel labelLogin;
	private JTextField fieldLogin;

	private JLabel labelPassword;
	private JPasswordField fieldPassword;

	private JLabel labelDriver;
	private JTextField fieldDriver;

	private JButton buttonBrowse;
	private JButton buttonConnect;

	private DbManager dbManager;
	private ConfiguratorManager configuratorManager;
	private boolean configurationModified = false;
	private final String configurationsPath;

	public JLogin(DbManager dbManager) {
		super(TITLE);

		this.dbManager = dbManager;
		this.configurationsPath = "conf/";

		build();
	}

	public JLogin(DbManager dbManager, String configurationsPath) {
		super(TITLE);

		this.dbManager = dbManager;
		this.configurationsPath = configurationsPath;

		build();
	}

	private void build() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(8, 2));
		setContentPane(mainPanel);
		
		configuratorManager= new ConfiguratorManager(configurationsPath);
		loadAllConfigurations();

		listConfigurations = new JList(configuratorManager.toArray());
		labelConfiguration = new JLabel("Configuration : ");
		mainPanel.add(labelConfiguration);
		mainPanel.add(listConfigurations);
		labelConfiguration.setLabelFor(listConfigurations);

		listConfigurations.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				loadConfiguration();
				configurationModified = false;
			}
		});
		
		mainPanel.add(new JLabel(""));
		buttonDelete = new JButton("Delete");
		mainPanel.add(buttonDelete);
		
		buttonDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
					deleteConfiguration();
			}
			});
		
		labelURL = new JLabel("URL : ");
		mainPanel.add(labelURL);
		fieldURL = new JTextField(50);
		mainPanel.add(fieldURL);
		labelURL.setLabelFor(fieldURL);

		labelLogin = new JLabel("Login : ");
		mainPanel.add(labelLogin);
		fieldLogin = new JTextField(15);
		mainPanel.add(fieldLogin);
		labelLogin.setLabelFor(fieldLogin);

		labelPassword = new JLabel("Password : ");
		mainPanel.add(labelPassword);
		fieldPassword = new JPasswordField(15);
		mainPanel.add(fieldPassword);
		labelPassword.setLabelFor(fieldPassword);

		labelDriver = new JLabel("Driver : ");
		mainPanel.add(labelDriver);
		fieldDriver = new JTextField(50);
		mainPanel.add(fieldDriver);
		labelDriver.setLabelFor(fieldDriver);

		mainPanel.add(new JLabel(""));

		buttonBrowse = new JButton("Browse");
		mainPanel.add(buttonBrowse);
		buttonBrowse.setMnemonic('B');

		buttonBrowse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser choix = new JFileChooser("lib");
				choix.showOpenDialog(null);
				File driverFile = choix.getSelectedFile();

				fieldDriver.setText(driverFile.getAbsolutePath());
			}
		});

		buttonConnect = new JButton("Connect");
		mainPanel.add(buttonConnect);
		buttonConnect.setMnemonic('C');

		// To detect user configuration changes
		JTextField[] textFields = { fieldURL, fieldLogin, fieldDriver };

		for (JTextField textField : textFields) {
			textField.getDocument().addDocumentListener(new DocumentListener() {
				@Override
				public void changedUpdate(DocumentEvent arg0) {
					configurationModified = true;
				}

				@Override
				public void insertUpdate(DocumentEvent arg0) {
					configurationModified = true;
				}

				@Override
				public void removeUpdate(DocumentEvent arg0) {
					configurationModified = true;
				}
			});
		}

		pack();

		setVisible(true);

		fieldURL.requestFocusInWindow();
		listConfigurations.setSelectedIndex(0);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void loadAllConfigurations() {
		Configurator configurator = configuratorManager.add();
		configurator.put("name", NEWENTRYNAME);
		configurator.put("url", "");
		configurator.put("user", "");
		configurator.put("password", "");
		configurator.put("driver", "");

		try {
			configuratorManager.loadAll();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void loadConfiguration() {
		Configurator configurator = listConfigurations.getSelectedValue();

		fieldURL.setText(configurator.get("url"));
		fieldLogin.setText(configurator.get("user"));
		fieldDriver.setText(configurator.get("driver"));
	}

	private boolean updateConfiguration() {
		try {
			Configurator configurator = listConfigurations.getSelectedValue();
			configurator.put("url", fieldURL.getText());
			configurator.put("user", fieldLogin.getText());
			configurator.put("driver", fieldDriver.getText());
			configurator.save();
			JOptionPane.showMessageDialog(this, "Unable to save configuration !");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Unable to save configuration !");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private boolean saveConfiguration() {
		try {
			JTextField fieldConfigurationName = new JTextField();

			if (JOptionPane.showOptionDialog(null, new Object[] { "New configuration name ?", fieldConfigurationName },
					"Save", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null) == 0) {
				Configurator configurator = configuratorManager.add();
				configurator.put("name", fieldConfigurationName.getText().toString());
				configurator.put("url", fieldURL.getText());
				configurator.put("user", fieldLogin.getText());
				configurator.put("driver", fieldDriver.getText());

				configurator.save();
			}
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(this, "Unable to save configuration !");
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Unable to save configuration !");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private void deleteConfiguration() {
		if (JOptionPane.showConfirmDialog(this, "Are you sure to delete configuration ?") == 0) {
			configuratorManager.delete(listConfigurations.getSelectedValue());
			listConfigurations.setListData(configuratorManager.toArray());
		}
	}

	public boolean connect() {
		// Set up the Db Manager with user entriesURL(
		dbManager.setName(listConfigurations.getSelectedValue().toString());// Attntion
		dbManager.setUrl(fieldURL.getText());
dbManager.setUser(fieldLogin.getText());
		dbManager.setPassword(fieldPassword.getText());

			// Trying to load JDBC driver
		if (!this.dbManager.loadDriver(fieldDriver.getText())) {
			JOptionPane.showMessageDialog(this, "Unable to load JDBC driver !");
			return false;
		}
		
		// we can try to connect to database
		if (!dbManager.openConnection()) {
			JOptionPane.showMessageDialog(this, "Unable to load or connect database !");

			return false;
		}

		// If connection success save new configuration or configuration changes
		if (listConfigurations.getSelectedValue().toString().equals("new")) {
			if (saveConfiguration()) {
				return true;
			} else {
				JOptionPane.showMessageDialog(this, "Unable to save configuration !");
				return false;
			}
		}

		if (configurationModified) {
			switch (JOptionPane.showOptionDialog(null,
					"Configuration has been modified.\nWould you like to save changes ?", "Information",
					JOptionPane.INFORMATION_MESSAGE, JOptionPane.QUESTION_MESSAGE, null,
					new String[] { "Save", "Save as", "Don't save" }, null)) {
			case 0:
				return updateConfiguration();
			case 1:
				return saveConfiguration();
			}
		}

		return this.dbManager.openConnection();
	}

	public JButton getButtonConnect() {
		return buttonConnect;
	}
}