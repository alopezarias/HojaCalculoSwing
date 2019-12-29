package hoja;

//##########################################//
//IMPORTANDO LIBRERIAS Y COMPLEMENTOS
//##########################################//
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.NoSuchElementException;

import javax.swing.JTable;

//##########################################//
//CLASE TABLA QUE MANEJA EL RESTO DE COSAS
//##########################################//
public class Tabla {
	
	//##########################################//
	//DECLARACION DE LOS OBJETOS QUE VOY A USAR
	//##########################################//
	JTable tabla;
	MiModelo modelo;
	MiRender render;
	int filas, columnas;
	String [][] excel;
	static int tamanioFuente = 15;
	static String fuente = "Tahoma";
	Deque<String[][]> accionesDeshacer = new ArrayDeque<String[][]>();
	Deque<String[][]> accionesRehacer = new ArrayDeque<String[][]>();
	
	//##########################################//
	//METODO CONSTRUCTOR DE LAS TABLAS
	//##########################################//
	/**
	* Este metodo se encarga de crear las tablas segun filas y columnas que le pasemos
	* @param fil filas
	* @param col columnas
	*/
	public Tabla(int fil, int col) {

		//##########################################//
		//ASIGNACION DE PARAMETROS
		//##########################################//
		this.filas = fil;
		this.columnas = col;
		this.modelo = new MiModelo();
		
		//##########################################//
		//CREACION DE LAS COLUMNAS Y TODAS CASILL=0
		//##########################################//
		String [] fila = new String[col+1];
		for(int j=0; j<=col; j++) {
			modelo.addColumn(" ");
			fila[j] = "0";
		}
		
		//##########################################//
		//CREACION DE LOS CABECEROS DE LAS COLUMNAS
		//##########################################//
		String [] filaLetras = new String[col+1];
		filaLetras[0] = "  ";
		for(int a=1; a<=col; a++) {
			filaLetras[a] = toChar(a-1);
		}
		modelo.addRow(filaLetras);
		
		//##########################################//
		//CREACION DE LOS CABECEROS DE LAS FILAS
		//##########################################//
		for(int i=1; i<=fil; i++) {
			modelo.addRow(fila);
			modelo.setValueAt(Integer.toString(i), i, 0);
		}
	
		//##########################################//
		//A LA TABLA LE ASIGNO MI MODELO
		//##########################################//
		tabla = new JTable(modelo);

		//##########################################//
		//A LA TABLA LE PONGO MI RENDER
		//##########################################//
		render = new MiRender();
		tabla.setDefaultRenderer(Object.class, render);

		//##########################################//
		//SETEO DE TAMAÑO DE LA TABLA
		//##########################################//
		tabla.getColumnModel().getColumn(0).setPreferredWidth(39);
		tabla.getColumnModel().getColumn(0).setMaxWidth(39);
		tabla.getColumnModel().getColumn(0).setMinWidth(39);
		
		for(int a=1; a<=columnas; a++) {
			tabla.getColumnModel().getColumn(a).setPreferredWidth(150);
			tabla.getColumnModel().getColumn(a).setMaxWidth(150);
			tabla.getColumnModel().getColumn(a).setMinWidth(150);
		}
		tabla.setRowHeight(21);
		//cambioDimensiones(tamanioAncho(2), tamanioAnchoCab(2), tamanioAlto(2), 2);
		tabla.setFont(new java.awt.Font("Tahoma", 0, 15));

		//##########################################//
		//CREACION DE LA MATRIZ QUE CONTENDRA LA TABLA
		//##########################################//
		excel = new String[fil+1][col+1];
		
		for(int i=0; i<=filas; i++) {
			for(int j=0; j<=columnas; j++) {
				excel[i][j] = tabla.getValueAt(i, j).toString();
			}
		}
		//##########################################//
		//FIN CONSTRUCTOR TABLA
		//##########################################//
	}
	
	//##########################################//
	//METODO PARA CCALCULAR LA TABLA ENTERA
	//##########################################//
	/**
	* Este metodo devuelve el calculo de la hoja integro
	*
	* @throws Exception cuando la hoja de calculo no puede calcularse por alguna razon
	*/
	public void calcular() throws TableException{

		Excel nuevo = new Excel(filas, columnas);

		try{
			String resultado = nuevo.excel(toCalculate());
			toTable(resultado, filas, columnas);

		}catch(Exception e) {
			throw new TableException("Error en el calculo de la tabla");
		}
	}
	
	//##########################################//
	//METODOS GETER DE LA TABLA
	//##########################################//
	/**
	* Este metodo devuelve el parámetro JTable
	*/
	public JTable getTable() {
		return this.tabla;
	}

	/**
	* Este metodo devuelve el parámetro MiRender
	*/
	public MiRender getRender() {
		return this.render;
	}
	
	/**
	* Este metodo devuelve el parámetro MiModelo
	*/
	public MiModelo getModel() {
		return this.modelo;
	}
	
	//##########################################//
	//METODOS TRANSFORMADORES DE LA TABLA
	//##########################################//
	/**
	* Este metodo pasa la hoja a un formato valido para guardarla
	*/
	public String toText() {
		StringBuffer hoja = new StringBuffer();
		hoja.append(filas + " " + columnas + "\n");
		
		hoja.append(toCalculate());
		return hoja.toString();
	}
	
	/**
	* Este metodo pasa la hoja a formato valido para calcularla
	*/
	public String toCalculate() {
		StringBuffer hoja = new StringBuffer();
		
		/*for(int i=1; i<=filas; i++) {
			for(int j=1; j<=columnas; j++) {
				hoja.append(excel[i][j]);
				hoja.append(";");
			}
		}*/

		for(int i=1; i<=filas; i++) {
			for(int j=1; j<=columnas; j++) {
				hoja.append(excel[i][j]);
				if(j!=columnas){
					hoja.append(" ");
				}
			}
			if(i!=filas){
				hoja.append("\n");
			}
		}

		return hoja.toString();
	}
	
	/**
	* Este metodo pasa un string a la tabla que tenemos (para leer archivo)
	*/
	public void toTable(String contenido, int fil, int col) {

		System.out.println(contenido);
		/*String[] casillas = contenido.split(";");
		for(int i=1; i<=fil; i++) {
			for(int j=1; j<=col; j++) {
				this.excel[i][j] = casillas[(j-1)+(i-1)*col];
				updateCell(i, j);
			}
		}*/

		String [] filas = contenido.split("\n");
		for(int i=0; i<fil; i++){
			String [] elementos = filas[i].split(" ");
			for(int j=0; j<col; j++){
				this.excel[i+1][j+1] = elementos[j];
				updateCell(i+1, j+1);
			}
		}	
	}

	/**
	* Este metodo pasa un numero a su correspondiente referencia en letras
	*/
	public static String toChar(int column) {
		
		String letra = "";
		for(;column>=0;column = column/26 -1) {
			letra = (char) ((char) (column%26)+'A')+letra;
		}
		return letra;
	}

	//##########################################//
	//METODOS ACTUALIZADORES DE LA TABLA
	//##########################################//
	/**
	* Este metodo actualiza los valores del excel con los introducidos en la tabla
	*/
	public void updateExcel(int fila, int columna) {
		excel[fila][columna] = tabla.getValueAt(fila, columna).toString();
	}
	
	/**
	* Este metodo actualiza los valores de la tabla con los del excel (nivel casilla)
	*/
	public void updateCell(int fila, int columna) {
		tabla.setValueAt(excel[fila][columna].toString(), fila, columna);
	}
	
	/**
	* Este metodo actualiza los valores de la tabla con los del excel (nivel tabla)
	*/
	public void updateTable() {
		for(int i=1; i<=filas; i++) {
			for(int j=1; j<=columnas; j++) {
				updateCell(i, j);
			}
		}
	}
	
	//##########################################//
	//METODOS CHECKERS DE LA TABLA
	//##########################################//
	/**
	* Este metodo comprueba si un excel es igual a otro
	*/
	public boolean isEquals(String[][] a, String[][] b) {
		boolean prueba = true;
		
		for(int i=1; i<=filas; i++) {
			for(int j=1; j<=columnas; j++) {
				if(!(a[i][j].contentEquals(b[i][j]))) {
					prueba=false;
					System.out.println("No son IGUALEEEEEES");
					break;
				}
			}
			if(!prueba) {
				break;
			}
		}
		System.out.println("si no he dicho nada, son iguales");
		return prueba;
	}
	
	//##########################################//
	//GUARDAR EN DESHACER
	//##########################################//
	public void guardarPrimeraModificacion() {
			//accionesDeshacer.push(this.excel);
		String[][] nuevo = new String[filas+1][columnas+1];
		for(int i=0; i<=filas; i++) {
			for(int j=0; j<=columnas; j++) {
				nuevo[i][j] = this.excel[i][j];
			}
		}
		
		this.guardarEnColaDeshacer(nuevo);
		
		System.out.println("se ha guardado el estado inicial de la tabla");
	}
	/**
	 * Metodo que guarda los estados no repetidos de la tabla
	 */
	public void guardarModificacion() {
		String[][] estado = this.sacarDeColaDeshacer();//accionesDeshacer.poll();

		String[][] nuevo = new String[filas+1][columnas+1];
			for(int i=0; i<=filas; i++) {
				for(int j=0; j<=columnas; j++) {
					nuevo[i][j] = this.excel[i][j];
				}
			}

		if(estado!=null && !isEquals(nuevo, estado)){
			//accionesDeshacer.push(estado);
			this.guardarEnColaDeshacer(estado);
			//accionesDeshacer.push(this.excel);
			this.guardarEnColaDeshacer(nuevo);
			//this.guardarEnColaDeshacer(this.excel);
			System.out.println("el estado a guardar NOOOOO es el mismo");
		}else{
			//accionesDeshacer.push(estado);
			this.guardarEnColaDeshacer(estado);
			System.out.println("el estado a guardar es el mismo");
		}
	}
	
	public void guardarEnColaDeshacer(String[][] tabla) {
		this.accionesDeshacer.push(tabla);
		System.out.println("el estado a guardar es el mismo");
	}
	
	public String[][] sacarDeColaDeshacer(){
		return this.accionesDeshacer.poll();
	}
	
	public void guardarEnColaRehacer(String[][] tabla) {
		this.accionesRehacer.push(tabla);
		System.out.println("el estado a guardar es el mismo");
	}
	
	public String[][] sacarDeColaRehacer(){
		return this.accionesRehacer.poll();
	}
	
	/*public Deque<String[][]> colaDeshacer(){
		return this.accionesDeshacer;
	}
	
	public Deque<String[][]> colaRehacer(){
		return this.accionesRehacer;
	}*/

	//##########################################//
	//DESHACER
	//##########################################//
	/**
	 * Metodo que deshace las acciones previamente hechas en la tabla
	 * @throws TableException
	 */
	public void deshacer() throws TableException{
		try {
			//accionesRehacer.push(accionesDeshacer.poll());
			this.guardarEnColaRehacer(this.sacarDeColaDeshacer());
			
			String [][] nuevo = this.sacarDeColaDeshacer();//accionesDeshacer.poll();
			for(int i=0; i<=filas; i++) {
				for(int j=0; j<=columnas; j++) {
					this.excel[i][j] = nuevo[i][j];
				}
			}
			updateTable();
			//System.out.println("Se ha deshecho una accion en la tabla");
		}catch(NoSuchElementException e) {
			throw new TableException("No se pueden deshacer más acciones");
		}
	}

	//##########################################//
	//REHACER
	//##########################################//
	/**
	 * Metodo que rehace las acciones previamente hechas en la tabla
	 * @throws TableException
	 */
	public void rehacer() throws TableException{
		try {
			//this.excel = accionesRehacer.poll();
			
			String [][] nuevo = this.sacarDeColaRehacer();//accionesDeshacer.poll();
			for(int i=0; i<=filas; i++) {
				for(int j=0; j<=columnas; j++) {
					this.excel[i][j] = nuevo[i][j];
				}
			}
			updateTable();
			//System.out.println("Se ha rehecho una accion en la tabla");
			guardarModificacion();
		}catch(NoSuchElementException e) {
			throw new TableException("No se pueden rehacer más acciones");
		}
	}

	
	//##########################################//
	//ZOOM
	//##########################################//
	/**
	 * Metodo que cambia las dimensiones de la tabla segun 'valor'
	 * @throws TableException
	 */
	public void zoom(int valor){
		if(valor>=0 && valor<5){
			cambioDimensiones(this.tamanioAncho(valor), this.tamanioAnchoCab(valor), this.tamanioAlto(valor), valor);
		}else{
			System.out.println("Se ha introducido una opcion incorrecta, revisa el metodo listener de mas y menos en Ventana.java");
		}
	}
	
	private void cambioDimensiones(int ancho, int anchoC, int alto, int fuente) {
		tabla.getColumnModel().getColumn(0).setPreferredWidth(anchoC);
		tabla.getColumnModel().getColumn(0).setMaxWidth(anchoC);
		tabla.getColumnModel().getColumn(0).setMinWidth(anchoC);
		for(int a=1; a<=columnas; a++) {
			tabla.getColumnModel().getColumn(a).setPreferredWidth(ancho);
			tabla.getColumnModel().getColumn(a).setMaxWidth(ancho);
			tabla.getColumnModel().getColumn(a).setMinWidth(ancho);
		}
		tabla.setRowHeight(alto);
		tamanioFuente(fuente);
		cambioFuente();
	}
	
	/**
	 * Este metodo le cambia los valores del tamanio del texto para funcionar con el en otros ambitos, como la alineacion o la fuente
	 * @param valor Tipo de vista que se está ejecutando
	 */
	public static void tamanioFuente(int valor) {
		int [] datos = {9, 12, 15, 19, 24};
		
		if(valor>=0 && valor<5){
			tamanioFuente = datos[valor];
		}else{
			tamanioFuente = datos[2];
		}
	}
	
	/**
	 * Este metodo le cambia los valores alto de la casilla
	 * @param valor Tipo de vista que se está ejecutando
	 */
	public int tamanioAlto(int valor) {
		int [] datos = {15, 19, 21, 24, 28};
		int resultado;
		
		if(valor>=0 && valor<5){
			resultado = datos[valor];
		}else{
			resultado = datos[2];
		}

		return resultado;
	}
	
	/**
	 * Este metodo le cambia los valores del ancho de los cabezales (columna 0)
	 * @param valor Tipo de vista que se está ejecutando
	 */
	public int tamanioAnchoCab(int valor) {
		int [] datos = {17, 30, 39, 70, 100};
		int resultado;
		
		if(valor>=0 && valor<5){
			resultado = datos[valor];
		}else{
			resultado = datos[2];
		}

		return resultado;
	}
	
	/**
	 * Este metodo le cambia los valores del ancho de las casillas
	 * @param valor Tipo de vista que se está ejecutando
	 */
	public int tamanioAncho(int valor) {
		int [] datos = {70, 100, 150, 200, 250};
		int resultado;

		if(valor>=0 && valor<5){
			resultado = datos[valor];
		}else{
			resultado = datos[2];
		}

		return resultado;
	}
	
	public static int getTamanioFuente() {
		return tamanioFuente;
	}
	
	public static String getFuente() {
		return fuente;
	}
	
	public static void changeFont(String f) {
		fuente = f;
	}
	
	public void cambioFuente() {
		tabla.setFont(new java.awt.Font(fuente, 0, getTamanioFuente()));
		updateTable();
	}
//##########################################//
//FIN DE LA CLASE TABLA
//##########################################//	
}

/*public void deshacer() throws TableException{
		try {
			Object estado = colaDeshacer.poll();

			if(estado instanceof Casilla) {
				Casilla casilla = (Casilla) estado;
				if(tabla.getValueAt(casilla.getFila(), casilla.getCol()).toString().equals(casilla.getCont().toString())) {
					deshacer();
				}else {
					this.excel[casilla.getFila()][casilla.getCol()] = casilla.getCont();
					updateCell(casilla.getFila(), casilla.getCol());
				}
			}else if(estado instanceof String[][]){
				this.excel = (String[][]) estado;
				updateTable();
			}else if(estado==null){
				throw new TableException("No hay nada en la cola de deshacer");
			}else {	
				//colaDeshacer.push(estado);
				throw new TableException("Se ha encolado un objeto que no es correcto, revisa el metodo encolar");
			}
		}catch(NoSuchElementException e) {
			throw new TableException("No se pueden deshacer más acciones");
		}
	}
	
	public void meterColaDeshacer() {
		colaDeshacer.push(this.excel);
	}
	
	public void meterColaDeshacer(int fil, int col) {
		Casilla nueva = new Casilla(this.excel[fil][col], fil, col);
		colaDeshacer.push(nueva);
	}
	
	public boolean isLastCell(int fil, int col) {
		boolean prueba = false;
		if(fil==this.filas && col==this.columnas) {
			prueba=true;
		}
		return prueba;
	}*/

	//KY6DHY5K

	//R55ESXSNEJ45QPEQ