package ihm;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import accesBd.DbManager;

public final class JTreeDataBase extends JScrollPane {
	private DefaultMutableTreeNode databaseRoot;

	private JTree databasExplorer;

	private DbManager dbManager;

	public JTreeDataBase(DbManager dbManager) {
		super();

		this.dbManager = dbManager;

		loadDatabaseSchema();
		databasExplorer = new JTree(databaseRoot);
		
		add(databasExplorer);
		setViewportView(databasExplorer);;
	}

	public void loadDatabaseSchema() {
		DefaultMutableTreeNode nodeTable;
		DefaultMutableTreeNode nodColumns;

		databaseRoot = new DefaultMutableTreeNode("Database");

		ArrayList<String> tableList = dbManager.getTableList();

		for (String table : tableList) {
			String value = table + " ";
value += dbManager.getNumOfFieldsForTable(table);

			nodeTable = new DefaultMutableTreeNode(value);

			databaseRoot.add(nodeTable);

			ArrayList<String> columnList = dbManager.getColumnsForTable(table);

			for (String column:columnList) {
				nodColumns = new DefaultMutableTreeNode(column);
				nodeTable.add(nodColumns);
			}
		}
	}
	
	public void refresh() {
		loadDatabaseSchema();
		((DefaultTreeModel) databasExplorer.getModel()).reload();

		}
}