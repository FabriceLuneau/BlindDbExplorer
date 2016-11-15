package ihm;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import accesBd.Request;

public class JTableRequestViewer extends JTable {
	private TableModel tableModel;
	private Request requete;

	public JTableRequestViewer(Request requete) {
		this.requete = requete;

		tableModel = new MonTableModel();
		setModel(tableModel);

		setCellSelectionEnabled(true);

		setEnabled(true);

		setVisible(true);

		changeSelection(0, 0, false, false);
		// requet//requestFocusInWindow();
	}

	public Request getRequete() {
		return requete;
	}

	public void setRequete(Request requete) {
		this.requete = requete;
		((AbstractTableModel) tableModel).fireTableStructureChanged();
		((AbstractTableModel) tableModel).fireTableDataChanged();
		changeSelection(0, 0, false, false);
	}

	public TableModel getTableModel() {
		return tableModel;
	}

	class MonTableModel extends AbstractTableModel {

		@Override
		public int getColumnCount() {
			if (requete != null) {
				return requete.getColumnCount();
			} else {
				return 1;
			}
		}

		@Override
		public int getRowCount() {
			if (requete != null) {
				return requete.getRowCount();
			} else {
				return 1;
			}
		}

		@Override
		public Object getValueAt(int row, int column) {
			if (requete != null) {
				return requete.getValueAt(row, column);
			} else {
				return "Aucun résultat";
			}
		}

		@Override
		public String getColumnName(int column) {
			if (requete != null) {
				return requete.getColumnName(column);
			} else {
				return "";
			}
		}
	}

}
