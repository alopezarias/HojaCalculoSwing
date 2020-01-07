package hoja;

import javax.swing.table.DefaultTableModel;

public class MiModelo extends DefaultTableModel{
	
	private static final long serialVersionUID = 1L;

	/**
	 * Este metodo me permite poner la fila 0 y la columna 0 como no editables
	 * ya que son cabezales de la tabla, y no deben poder editarse
	 */
   public boolean isCellEditable (int row, int column){
       if (column == 0 || row==0)
          return false;
       return true;
   }
}