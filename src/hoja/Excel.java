package hoja;

public class Excel{
  
	int fil, col;

	/**
	 * Constructor del Excel con filas y columnas
	 * @param filas Integer de las filas
	 * @param columnas Integer de las columnas
	 */
	public Excel(int filas, int columnas){
		this.fil = filas;
		this.col = columnas;
	}
	
	/**
	 * Metodo que se encarga de recibir una tabla, pasarla a una matriz de strings y calcular el resultado
	 * @param contenido String con los datos de la tabla
	 * @return salida La tabla toda calculada
	 *
	 * @throws Exception Si no consigue calcular la tabla o tiene algun error
	 */
	public String excel(String contenido) throws Exception {

		Hoja hoja = new Hoja(fil, col);
		StringBuffer salida = new StringBuffer();
		
		String [] filas = contenido.split("\n");
		for(int i=0; i<fil; i++){
			String [] elementos = filas[i].split(" ");
			for(int j=0; j<col; j++){
				hoja.setDatos(i, j, elementos[j]);
			}
		}		
		
		if(!hoja.proceder()) {
			salida.append(hoja.toString());
		}else
			throw new Exception();
		
		return salida.toString();
	}
}