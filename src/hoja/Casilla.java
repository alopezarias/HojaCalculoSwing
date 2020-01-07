package hoja;

public class Casilla{
	  
	private String cont;
	private int fila;
	private int columna;
	
	/**
	 * Constructor de la casilla pasandole el contenido
	 * @param contenido  El string que contendra la casilla
	 */
	public Casilla(String contenido) {
		  this.cont = contenido;
	 }
	
	/**
	 * Constructor de la casilla pasandole el contenido, y sus coordenadas
	 * @param contenido  El string que contendra la casilla
	 * @param fil Coordenada x
	 * @param col Coordenada
	 */
	public Casilla(String contenido, int fil, int col) {
		  this.cont = contenido;
		  this.fila = fil;
		  this.columna = col;
	 }
	
	/**
	 * Metodo que setea el contenido de la casilla
	 * @param cont  El string que contendra la casilla
	 */
	public void setCont(String cont) {
		this.cont = cont;
	}
	
	/**
	 * Metodo que devuelve el contenido de la casilla
	 * @return cont El string que contendra la casilla
	 */
	public String getCont() {
		return this.cont;
	}
	
	/**
	 * Metodo que setea la fila de la casilla
	 * @param num  Integer de la fila
	 */
	public void setFila(int num) {
		this.fila = num;
	}
	
	/**
	 * Metodo que setea la columna de la casilla
	 * @param num  Integer de la columna
	 */
	public void setCol(int num) {
		this.columna = num;
	}
	
	/**
	 * Metodo que devuelve la fila
	 * @return fila  Integer de la fila
	 */
	public int getFila() {
		return this.fila;
	}
	
	/**
	 * Metodo que devuelve la columna
	 * @return columna  Integer de la columna
	 */
	public int getCol() {
		return this.columna;
	}
}