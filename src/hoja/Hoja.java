package hoja;

import java.util.regex.Pattern;

public class Hoja{;
	
	private int fil;
	private int col;
	private Casilla hoja[][];
	
	public Hoja() {
		
	}
	/**
	 * Constructor de la clase Hoja
	 * @param filas Numero de filas que contendrá
	 * @param columnas Numero de columnas que contendrá
	 */
	public Hoja(int filas, int columnas) {
		this.fil = filas;
		this.col = columnas;
		this.hoja = new Casilla[fil][col];
		for(int i=0; i<this.fil; i++) {
			for(int j=0; j<this.col; j++) {
				this.hoja[i][j] = new Casilla("");
			}
		}
	}
	
	/**
	 * Esta funcion comprueba si la hoja de calculo esta entera bien, y si lo esta, opera
	 * @return True si esta bien y ha operado, false si no lo es
	 */
	public boolean proceder() {
		boolean fallo = false;
		
		for(int i=0; i<this.fil; i++) {
			for(int j=0; j<this.col; j++) {
				if(!formatoValido(i,j)) {
					fallo = true;
					break;
				}
			}
			if(fallo==true)
				break;
		}
		
		if(fallo==false) {
			for(int i=0; i<this.fil; i++) {
				for(int j=0; j<this.col; j++) {
					String s = operar(i, j)+"";
					this.hoja[i][j].setCont(s);
				}
			}
		}
		
		return fallo;
		
	}
	
	/**
	 * Primero me aseguro de que el formato de la casilla es válido
	 * @param f
	 * @param c
	 * @return True si lo es, false si no lo es
	 */
	private boolean formatoValido(int f, int c) {
		if(contenidoCasilla(this.hoja[f][c])==1 || contenidoCasilla(this.hoja[f][c])==2)
			return true;
		else
			return false;
	}
	
	/**
	 * Esta funcion devuelve lo que tiene cada casilla en un formato de int
	 * @param f
	 * @param c
	 * @return Resultado de la casilla
	 */
	private int operar(int f, int c) {
		int resultado=0, opcion= contenidoCasilla(this.hoja[f][c]);
		if(opcion ==1) {
			resultado = resultado + aplicarFormula(f, c);
		}else {
			resultado = resultado + Integer.parseInt(this.hoja[f][c].getCont());
		}
		return resultado;
	}
	
	/**
	 * Realizamos la suma de las formulas usando recursion con dos funciones a la vez
	 * @param f
	 * @param c
	 * @return La suma de las formulas que aparezcan
	 */
	private int aplicarFormula(int f, int c) {
		int suma = 0, a, fila, columna;
		
		String cont = this.hoja[f][c].getCont().substring(1);
		String array[] = cont.split("\\+");
		
		for(int i=0; i<array.length; i++) {
			a = posicionPrimerNumero( array[i]);
			
			columna = pasarLetrasNumeros(array[i].substring(0, a));
			fila = stringNumero(array[i].substring(a))-1;
			
			suma = suma + operar(fila, columna);
		}
		return suma;
	}
	
	/**
	 * Este metodo se encarga de almacenar los datos en las casillas de la hoja de calculo
	 * @param fila
	 * @param columna
	 * @param cont Contenido de la casilla
	 */
	public void setDatos(int fila, int columna, String cont) {
		this.hoja[fila][columna].setCont(cont);
	}
	
	/**
	 * El metodo comprueba que es lo que hay en una casilla determinada
	 * @param cas Casilla a evaluar
	 * @return 0, si es un formato erróneo
	 * 		   1, si es una fórmula correcta
	 * 		   2, si es un  numero correcto
	 */
	private int contenidoCasilla(Casilla cas) {
		String contenido = cas.getCont();
		int resultado = 0;
		if(contenido.charAt(0)=='=') {
			if(esFormulaValida(contenido))
				resultado=1;
		}else{
			if(esNumeroValido(contenido))
				resultado=2;
		}
		return resultado;
	}
	
	/**
	 * Comprueba si la formula que está introducida, tiene un formato válido
	 * @param formula Formula a evaluar
	 * @return True si es correcto, false si no
	 */
	private boolean esFormulaValida(String formula) {
		boolean resultado = true;
		String cont = formula.substring(1); //Para evaluar el contenido de la fórmula
		
		String separador = Pattern.quote("+");
		String array[] = cont.split(separador);
		
		for(int i=0; i<array.length; i++) {
			if(!esCasillaValida(array[i]))
				resultado = false;
		}
		
		return resultado;
	}
	
	/**
	 * Metodo que comprueba si la casilla es correcta: Formato correcto y existencia dentro de la hoja de calculo
	 * @return True, si todo está en orden. De lo contrario, false;
	 */
	private boolean esCasillaValida(String casilla) {
		boolean correcto = false;
		
		int num = posicionPrimerNumero(casilla);
		if(num!=-1) {
			String colum = casilla.substring(0, num);
			String fila = casilla.substring(num);
			
			if(esNumeroValido(fila) && esColumnaValida(colum))
				if(stringNumero(fila)-1 < this.fil && pasarLetrasNumeros(colum) < this.col)
					correcto = true;
		}
		
		return correcto;
	}
	
	/**
	 * @param num Numero en string
	 * @return Numero en Int
	 */
	public int stringNumero(String num) {
		int res = Integer.parseInt(num);
		return res;
	}
	
	/**
	 * Comprobamos si la fila es correcta
	 * @param fila La fila a evaluar
	 * @return True si es correcta, false si no lo es
	 */
	private boolean esColumnaValida(String col) {
		boolean prueba = false;
		
		if(soloLetras(col)) {
			int num = pasarLetrasNumeros(col);
			if(num!=-1 && num<this.col)
				prueba = true;
		}
		
		return prueba;
	}
	
	/**
	 * Esta funcion transforma las letras de las columnas en numeros
	 * @param a La cadena a evaluar
	 * @return El numero que corresponde con cada cadena de texto, o en su defecto -1
	 */
	public int pasarLetrasNumeros(String a) {
		int num = 0, longitud = a.length();
		
		if(longitud==1) {
			num = a.charAt(0)-64;
		}else if(longitud==2) {
			num = a.charAt(0)-64;
			num += (a.charAt(1)-64)*26;
		}else if(longitud==3) {
			num = a.charAt(0)-64;
			num += (a.charAt(1)-64)*26;
			num += (a.charAt(2)-64)*26*26;
		}else {
			num = -1;
		}
		
		return num-1;
	}
	
	/**
	 * Comprueba si el string solo contiene letras de la A a la Z
	 * @param cad Cadena a evaluar
	 * @return True si solo tiene letras mayúsculas, false si no;
	 */
	private boolean soloLetras(String cad) {
		boolean res = true;
		int longitud = cad.length(), i=0;
		
		while(res==true && i<longitud) {
			
			String s = "" + cad.charAt(i);
			if(!s.matches("[A-Z]"))
			    res = false;
			i++;
		}
		return res;
	}

	/**
	 * Devuelve un int con la posicion del primer numero del string que le pasamos
	 * @param a String que le pasamos
	 * @return La posicion del primer numero; si no hay numeros, devuelve -1
	 */
	private int posicionPrimerNumero(String a) {
		int res = -1, longitud = a.length(), i=0;
		
		while(res==-1 && i<longitud) {
			
			String s = "" + a.charAt(i);
			if(s.matches("[0-9]"))
			    res = i;
			i++;
		}
		return res;
	}
	
	/**
	 * Comprueba si el numero es correcto
	 * @param numero Numero a evaluar
	 * @return True si es correcto, false si no
	 */
	private boolean esNumeroValido(String numero) {
		try {
			Integer.parseInt(numero);
			return true;
		}catch(Exception e) {
			return false;
		}
	}

	/**
	 * El método encargado de imprimir la hoja de cálculo
	 * @return La hoja bien presentada
	 */
	public String toString() {
		 StringBuffer res = new StringBuffer();
		 
		 /*for(int i=0; i<fil; i++) {
			 for(int j=0; j<col; j++) {
				 res.append(hoja[i][j].getCont());
				 res.append(";");
			 }
		 }*/
		 
		 for(int i=0; i<fil; i++) {
			 for(int j=0; j<col; j++) {
				 res.append(hoja[i][j].getCont());
				 if(j!=col-1) {
					 res.append(" ");
				 }
			 }
			 if(i!=fil-1) {
				 res.append("\n");
			 }
		 }
		 
		 return res.toString();
	}
}