package hoja;

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
		Ventana v = new Ventana(0, 0, true, contador);
		int num = contador.getValue();
		/*System.out.println("Ventanas: "+contador.getValue());
		while(contador.getValue()!=0) {
			System.out.println("Ventanas: "+contador.getValue());
		}
		System.out.println("Ventanas: "+contador.getValue());
			System.exit(0);*/
	}
}