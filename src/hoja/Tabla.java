package hoja;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
//##########################################//
//IMPORTANDO LIBRERIAS Y COMPLEMENTOS
//##########################################//
import java.util.ArrayDeque;
import java.util.Deque;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;

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
	int contadorCicloDeshacer  = 0;
	int variableEspecial = 0;
	boolean ciclo = false;
	
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
			fila[j] = " ";
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
			this.updateTable();
			this.guardarModificacion();

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

		String [] filas = contenido.split("\n");
		for(int i=0; i<fil; i++){
			String [] elementos = filas[i].split(" ");
			for(int j=0; j<col; j++){
				this.excel[i+1][j+1] = elementos[j];
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
		if(!tabla.getValueAt(fila, columna).toString().contentEquals(excel[fila][columna].toString()))
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
					break;
				}
			}
			if(!prueba) {
				break;
			}
		}
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
	}

	/**
	 * Metodo que guarda los estados no repetidos de la tabla
	 */
	public void guardarModificacion() {

		try{
			String[][] estado = null;
			String[][] nuevo = new String[filas+1][columnas+1];
			
			for(int i=0; i<=filas; i++) {
				for(int j=0; j<=columnas; j++) {
					nuevo[i][j] = this.excel[i][j];
				}
			}
			
			if(accionesDeshacer.size()!=0) {
				estado = this.sacarDeColaDeshacer();
			}else {
				this.guardarEnColaDeshacer(nuevo);
			}
			
			if(estado!=null && !isEquals(nuevo, estado)){

				this.guardarEnColaDeshacer(estado);
				this.guardarEnColaDeshacer(nuevo);

			}else{
				
				this.guardarEnColaDeshacer(estado);
				
			}
			
			
			if(this.ciclo == true && this.variableEspecial==1) {
				
				this.contadorCicloDeshacer = accionesDeshacer.size()+accionesRehacer.size();
				ciclo = false;
				this.variableEspecial++;

			}else if(this.contadorCicloDeshacer!=0) {

				if(this.contadorCicloDeshacer != accionesDeshacer.size()+accionesRehacer.size()) {
					
					accionesRehacer.clear();
					ciclo = false;
					this.variableEspecial = 0;
				}
			}
		}catch(Exception e){
			JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "Fallo de Tabla", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	//##########################################//
	//METODOS PARA EL ACCESO A LAS COLAS
	//##########################################//
	
	/**
	 * Metemos un objeto en la cola de deshacer
	 */
	public void guardarEnColaDeshacer(String[][] tabla) {
		this.accionesDeshacer.push(tabla);
	}
	
	/**
	 * Sacamos un objeto de la cola de deshacer
	 */
	public String[][] sacarDeColaDeshacer(){
		String[][] tabla = this.accionesDeshacer.poll();
		return tabla;
	}
	
	/**
	 * Metemos un objeto en la cola de rehacer
	 */
	public void guardarEnColaRehacer(String[][] tabla) {
		this.accionesRehacer.push(tabla);
	}
	
	/**
	 * Sacamos un objeto de la cola de rehacer
	 */
	public String[][] sacarDeColaRehacer(){
		String[][] tabla = this.accionesRehacer.poll();
		return tabla;
	}
	
	//##########################################//
	//DESHACER
	//##########################################//
	/**
	 * Metodo que deshace las acciones previamente hechas en la tabla
	 * @throws TableException
	 */
	public void deshacer() throws TableException{
		
			try {
				
				if(accionesDeshacer.size()!=0) {
					String [][] paraRehacer = this.sacarDeColaDeshacer();
					String [][] nuevo = null;
					
					if(accionesDeshacer.size()!=0) {
						nuevo = this.sacarDeColaDeshacer();
						this.guardarEnColaRehacer(paraRehacer);
						this.ciclo = true;
						this.variableEspecial++;
					}else {
						this.guardarEnColaDeshacer(paraRehacer);
						throw new TableException("No se pueden deshacer más acciones");
					}
						
					for(int i=0; i<=filas; i++) {
						for(int j=0; j<=columnas; j++) {
							this.excel[i][j] = nuevo[i][j];
						}
					}
					
				}
				updateTable();
				
			}catch(Exception e) {
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
		
		if(accionesRehacer.size()!=0){
			try {
		
				String [][] nuevo = this.sacarDeColaRehacer();
				for(int i=0; i<=filas; i++) {
					for(int j=0; j<=columnas; j++) {
						this.excel[i][j] = nuevo[i][j];
					}
				}
				updateTable(); 
				
			}catch(Exception e) {
				throw new TableException("No se pueden rehacer más acciones");
			}
		}else{
			JOptionPane.showMessageDialog(new JFrame(), "La cola de rehacer esta vacía", "Fallo de Tabla", JOptionPane.ERROR_MESSAGE);
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
	
	/**
	 * Aqui meto valores en los parametros pedidos para dar formato y tamanio a la tabla
	 */
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
	
	/**
	 * Consigo el int del tamanio de la fuente
	 */
	public static int getTamanioFuente() {
		return tamanioFuente;
	}
	
	/**
	 * Consigo un string de la fuente en cuestioin
	 */
	public static String getFuente() {
		return fuente;
	}
	
	/**
	 * Metodo que cambia una fuente por una dada
	 */
	public static void changeFont(String f) {
		fuente = f;
	}
	
	/**
	 * Metodo que actualiza la fuente en la tabla en cuestion
	 */
	public void cambioFuente() {
		tabla.setFont(new java.awt.Font(fuente, 0, getTamanioFuente()));
		updateTable();
	}
	
	/**
	 * Pasamos el this.excel a un String para poder guardarlo en la cola
	 * @param excel El this.excel
	 * @return cadena.toString() El excel en formato cadena de texto
	 */
	public String ExceltoString(String[][] excel) {
		StringBuffer cadena = new StringBuffer();
		for(int i=0; i<=filas; i++) {
			for(int j=0; j<=columnas; j++) {
				cadena.append(excel[i][j].toString());
				cadena.append(" ");
			}
			cadena.append("\n");
		}
		return cadena.toString();
	}
	
	/**
	 * Metodo para vaciar el excel y ponerlo todo a 0
	 */
	public void vaciar() {
		for(int i=0; i<=filas; i++) {
			for(int j=0; j<=columnas; j++) {
				this.excel[i][j] = "0";
			}
		}
		this.updateTable();
		this.guardarModificacion();
	}
	
	/**
	 * Metodo para ignorar ciertos atajos del teclado en la tabla y así ahorrarnos problemas de compatibilidades
	 */
	public void createKeybindings(JTable table) {
		//##########################################//
		//CANCELACION DE ENTER
		//##########################################//	
		table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Enter");
		table.getActionMap().put("Enter", new AbstractAction() {
		        @Override
		        public void actionPerformed(ActionEvent ae) {
		            //do something on JTable enter pressed
		        }
		});
		//##########################################//
		//CANCELACION DE LAS FLECHAS
		//##########################################//
		table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "Arriba");
		table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "Abajo");
		table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "Izquierda");
		table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "Derecha");
		
		table.getActionMap().put("Arriba", new AbstractAction() {
		        @Override
		        public void actionPerformed(ActionEvent ae) {
		            //do something on JTable enter pressed
		        }
		});
		table.getActionMap().put("Abajo", new AbstractAction() {
	        @Override
	        public void actionPerformed(ActionEvent ae) {
	            //do something on JTable enter pressed
	        }
		});
		table.getActionMap().put("Izquierda", new AbstractAction() {
	        @Override
	        public void actionPerformed(ActionEvent ae) {
	            //do something on JTable enter pressed
	        }
		});
		table.getActionMap().put("Derecha", new AbstractAction() {
	        @Override
	        public void actionPerformed(ActionEvent ae) {
	            //do something on JTable enter pressed
	        }
		});
		//##########################################//
		//CANCELACION DE TABULADOR
		//##########################################//	
		table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0), "Tab");
		table.getActionMap().put("Tab", new AbstractAction() {
		        @Override
		        public void actionPerformed(ActionEvent ae) {
		            //do something on JTable enter pressed
		        }
		});
	}
//##########################################//
//FIN DE LA CLASE TABLA
//##########################################//	
}