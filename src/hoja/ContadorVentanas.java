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
	
	//Devuelve el contador
	public int getValue() {
		return this.contador;
	}
	
	//Aumenta el contador
	public void masContador() {
		this.contador++;
	}
	
	//Reduce el contador
	public void menosContador() {
		this.contador--;
	}
}
