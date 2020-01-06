package hoja;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Main {
	
	static ContadorVentanas contador = new ContadorVentanas();
	
	/**
	 * Este metodo es el main de todo el programa
	 * 
	 * Con el creo la ventana inicial con la que interactua el usuario
	 * de primeras con el programa. Desde esa interfaz tiene la opcion de
	 * crear una hoja nueva o cargar una existente.
	 * 
	 * @param args
	 */
	public static void main(String [] args) {
		JOptionPane.showMessageDialog(new JFrame(), "RECUERDA: NO PUEDE HABER CASILLAS VACÍAS\nSI QUIERES UNA CASILLA VACÍA, COLOCA UN \"0\" EN ELLA", "ANTES DE EMPEZAR...", JOptionPane.INFORMATION_MESSAGE);
		Ventana v = new Ventana(0, 0, true, contador);
		while(contador.getValue()!=0) {
			System.out.println("Ventanas: "+contador.getValue());
		}
		System.out.println("Ventanas: "+contador.getValue());
		v.dispose();
		System.exit(0);
	}
}