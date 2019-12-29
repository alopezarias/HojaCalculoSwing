package hoja;

public class Excel{
  
	int fil, col;
	public Excel(int filas, int columnas){
		this.fil = filas;
		this.col = columnas;
	}
	
	public String excel(String contenido) throws Exception {

		Hoja hoja = new Hoja(fil, col);
		StringBuffer salida = new StringBuffer();
		
		/*String[] casillas = contenido.split(";");
		for(int i=0; i<fil; i++) {
			for(int j=0; j<col; j++) {
				hoja.setDatos(i, j, casillas[(j)+(i)*col]);
			}
		}*/
		
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