package hoja;

public class Casilla{
	  
	private String cont;
	private int fila;
	private int columna;
	
	public Casilla(String contenido) {
		  this.cont = contenido;
	 }
	
	public Casilla(String contenido, int fil, int col) {
		  this.cont = contenido;
		  this.fila = fil;
		  this.columna = col;
	 }
	
	public void setCont(String cont) {
		this.cont = cont;
	}
	
	public String getCont() {
		return this.cont;
	}
	
	public void setFila(int a) {
		this.fila = a;
	}
	
	public void setCol(int a) {
		this.columna = a;
	}
	
	public int getFila() {
		return this.fila;
	}
	
	public int getCol() {
		return this.columna;
	}
}