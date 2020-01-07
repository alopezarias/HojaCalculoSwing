package hoja;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class MiRender extends DefaultTableCellRenderer{

	private static final long serialVersionUID = 1L;
	static int [] vertical = null;
	static int [] horizontal = null;
	
	/**
	 * Con este metodo puedo controlar donde me encuentro en mi tabla y conseguir un formato
	 * de colores y estilo para la misma.
	 */
	public Component getTableCellRendererComponent(JTable table,
			Object value, boolean isSelected, boolean hasFocus, int row,
			int column) {
			
		JLabel cell = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		//this.setHorizontalAlignment(SwingConstants.RIGHT);
		table.setRowSelectionAllowed(false);
		table.setColumnSelectionAllowed(false);
	
		if(column==0 && row==0) {
			cell.setBackground(Color.BLACK);
			cell.setForeground(Color.BLACK);
		}else if(column==0 || row==0) {
			cell.setBackground(Color.DARK_GRAY);
			cell.setForeground(Color.WHITE);
		}else {
			cell.setBackground(Color.WHITE);
			cell.setForeground(Color.BLACK);
			if(hasFocus == true) {
				vertical = new int[2];
				horizontal = new int[2];
				vertical[0] = row;
				vertical[1] = 0;
				horizontal[0] = 0;
				horizontal[1] = column;
			} 
		}
		
		if((vertical!=null && row==vertical[0] && column==vertical[1])) {
			cell.setBackground(Color.GRAY);
			cell.setForeground(Color.WHITE);
			vertical=null;
		}
		if((horizontal!=null && row==horizontal[0] && column==horizontal[1])) {
			cell.setBackground(Color.GRAY);
			cell.setForeground(Color.WHITE);
			horizontal=null;
		}
		
		return cell;
	}
}