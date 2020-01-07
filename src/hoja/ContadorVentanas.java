package hoja;

public class ContadorVentanas {
	
	int contador;
	
	/**
	 * Metodo que crea el contador y lo inicializa a 0
	 * @param valor
	 */
	public ContadorVentanas() {
		contador = 0;
	}
	
	/**
	 * Metodo que devuelve el contador
	 * @return contador
	 */
	public int getValue() {
		return this.contador;
	}
	
	/**
	 * Metodo que aumenta en uno el valor del contador
	 */
	public void masContador() {
		this.contador++;
	}
	
	/**
	 * Metodo que reduce en uno el valor del contador
	 */
	public void menosContador() {
		this.contador--;
	}
}