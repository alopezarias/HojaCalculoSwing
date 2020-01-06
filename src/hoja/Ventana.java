package hoja;



//##########################################//
//IMPORT DE CLASES Y COMPLEMENTOS
//##########################################//

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Scanner;


//##########################################//
//INICIO DE LA CLASE VENTANA
//##########################################//
public class Ventana extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//##########################################//
	//DECLARACION DE LOS OBJETOS A USAR
	//##########################################//
	ContadorVentanas ventanas;
	JFrame ventana;
	JPanel panel, inferior;
	JButton calcula;
	JLabel celda, flecha;
	JTextField contenido;
	JMenuBar barramenu;
	JMenu archivo, editar, texto, alinear, fuente, ayuda, zoom;
	JMenuItem crear, cargar, archivar, archivarNuevo, deshacer, rehacer, copiar, pegar, cortar, izquierda, centrar, derecha, tahoma, freeMono, gothic, mas, menos, tutorial, acercaDe;
	JScrollPane scrollpane;
	Tabla tabla;
	File fichero = null;
	Scanner sc;
	//Por defecto el zoom empieza con "vista" = 2
	static int vista = 2;
	int filasTabla, columnasTabla;
	static boolean hayTabla;
	String portapapeles = null;

	//##########################################//
	//METODO CONSTRUCTOR DE LA CLASE
	//##########################################//
	public Ventana(int fil, int col, boolean nuevo, ContadorVentanas cont){
		
		this.ventanas = cont;
		ventanas.masContador();
		filasTabla = fil;
		columnasTabla = col;	
		//##########################################//
		//CREACION DEL JFRAME Y DE LAS DIMENSIONES
		//##########################################//
		ventana = new JFrame();
		ventana.setTitle("Hoja de Calculo");
		ventana.setBounds(500, 350, 600, 400);
		
		//##########################################//
		//CONFIGURACION DEL BOTON CERRAR
		//##########################################//
		//setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		ventana.addWindowListener( new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				ventanas.menosContador();
				dispose();
			}
		} );
		
		//##########################################//
		//              BARRA MENU                  //
		//##########################################//
		barramenu = new JMenuBar();
		
			//##########################################//
			//                 ARCHIVO                  //
			//##########################################//
				archivo = new JMenu("Archivo");
				//--------------------------------------//
					crear = new JMenuItem("Nueva Hoja");
					crear.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
					cargar = new JMenuItem("Abrir");
					archivar = new JMenuItem("Guardar");
					archivar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK));
					archivarNuevo = new JMenuItem("Guardar como");
				//--------------------------------------//		
				archivo.add(crear);
				archivo.add(cargar);
				archivo.add(archivar);
				archivo.add(archivarNuevo);
			//------------------------------------------//
			barramenu.add(archivo);
			//------------------------------------------//

		if(nuevo){
			archivar.setEnabled(false);
		}

			//##########################################//
			//                 EDITAR                   //
			//##########################################//
				editar = new JMenu("Editar");
				//--------------------------------------//
					deshacer = new JMenuItem("Deshacer");
					//deshacer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
					rehacer = new JMenuItem("Rehacer");
					//rehacer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
					copiar = new JMenuItem("Copiar");
					//copiar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
					cortar = new JMenuItem("Cortar");
					//cortar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
					pegar = new JMenuItem("Pegar");
					//pegar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
				//--------------------------------------//
				editar.add(copiar);
				editar.add(pegar);
				editar.add(cortar);
				editar.add(new JSeparator());
				editar.add(deshacer);
				editar.add(rehacer);
			//------------------------------------------//
			barramenu.add(editar);
			//------------------------------------------//

			//##########################################//
			//                  TEXTO                   //
			//##########################################//
				texto = new JMenu("Texto");
				//--------------------------------------//
					alinear = new JMenu("Alinear");
					//----------------------------------//
						izquierda = new JMenuItem("Izquierda");
						centrar = new JMenuItem("Centrar");
						derecha = new JMenuItem("Derecha");
					//----------------------------------//
					alinear.add(izquierda);
					alinear.add(centrar);
					alinear.add(derecha);
				//--------------------------------------//
				texto.add(alinear);
				//--------------------------------------//
					fuente = new JMenu("Fuente");
					//----------------------------------//
						tahoma = new JMenuItem("Tahoma");
						freeMono = new JMenuItem("Free Mono");
						gothic = new JMenuItem("URW Gothic L");
					//----------------------------------//
					fuente.add(tahoma);
					fuente.add(freeMono);
					fuente.add(gothic);
				//--------------------------------------//
				texto.add(fuente);
			//------------------------------------------//
			barramenu.add(texto);
			//------------------------------------------//
			
			//##########################################//
			//                  ZOOM                    //
			//##########################################//
				zoom = new JMenu("Zoom");
				//--------------------------------------//
					mas = new JMenuItem("(+) Zoom");
					mas.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_PLUS, ActionEvent.CTRL_MASK));
					menos = new JMenuItem("(-) Zoom");
					menos.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, ActionEvent.CTRL_MASK));
				//--------------------------------------//
				zoom.add(mas);
				zoom.add(menos);
			//------------------------------------------//
			barramenu.add(zoom);
			//------------------------------------------//

			//##########################################//
			//                  AYUDA                   //
			//##########################################//
				ayuda = new JMenu("Ayuda");
				//--------------------------------------//
					tutorial = new JMenuItem("Tutorial");
					tutorial.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
					acercaDe = new JMenuItem("Acerca de...");
				//--------------------------------------//
				ayuda.add(tutorial);
				ayuda.add(new JSeparator());
				ayuda.add(acercaDe);	
			//------------------------------------------//
			barramenu.add(ayuda);
			//------------------------------------------//

		//Anyadimos el menu a la ventana de la interfaz
		ventana.setJMenuBar(barramenu);

		//##########################################//
		//          FIN CONFIGURACION MENU          //
		//##########################################//
		
		//##########################################//
		//METODO PARA CREAR LA TABLA
		//##########################################//
		crear.addActionListener(new ActionListener() {
			int filas = 0, columnas = 0;
			
			@Override
			/**
			 * Este metodo realiza una llamada a getDimension, que se
			 * encarga de generar un panel y devolver los parametros que se
			 * le especifican. Despues crea una nueva ventana con los valores obtenidos.
			 */
			public void actionPerformed(ActionEvent args) {
				filas = getDimension("FILAS");
				columnas = getDimension("COLUMNAS");
				
				Ventana v = new Ventana(filas, columnas, true, ventanas);
				
				if(!hayTabla){
					ventana.dispose();
				}
			}
		});

		//##########################################//
		//METODO PARA CARGAR UNA NUEVA TABLA 
		//##########################################//
		cargar.addActionListener(new ActionListener() {
			
			@Override
			/**
			 * Este metodo se encarga de abrir un nuevo archivo
			 * y dependiendo de si se puede o no, la abre adaptada
			 * o si no suelta un JDialog
			 */
			public void actionPerformed(ActionEvent args) {
				//Con esto seleccionamos el fichero con el que queremos trabajar
				JFileChooser escogerArchivo = new JFileChooser ();
				escogerArchivo.showDialog (null, "Abrir Hoja de Calculo");
				fichero = escogerArchivo.getSelectedFile ();
				
				//Procedemos a leer el fichero que hemos seleccionado
				FileInputStream in;
				try{
					in = new FileInputStream (fichero);
					BufferedReader br = new BufferedReader (new InputStreamReader (in) );
					String primeraLinea = null, linea;
					StringBuffer content = new StringBuffer();
					int i=0;

					//empieza a leer el archivo y a meterlo en un stringbuffer
					while ((linea = br.readLine()) != null){
						if(i==0) {
							primeraLinea = linea;
						}else {
							//content.append(linea);
							content.append(linea + "\n");
						}
						i++;
					}
					
					//Cuando hemos leido el archivo lo cerramos el buffer
					br.close ();
					
					//Cuando tenemos todos hago un split de la primera linea separando por espacios
					String[] coord = primeraLinea.split(" ");
					int filas = 0,columnas = 0;
					
					//Miro a ver si los primeros elementos puedo transformarlos en las coordenadas x,y de la tabla
					try {
						filas = Integer.parseInt(coord[0]);
						columnas = Integer.parseInt(coord[1]);

						Ventana v = new Ventana(filas, columnas, false, ventanas);
						v.tabla.toTable(content.toString(), filas, columnas);
						
						//Cuando abro un archivo permito la ventana de guardar
						archivar.setEnabled(true);
					}catch(Exception e) {
						fichero = null;
						JOptionPane.showMessageDialog(new JFrame(),  "Ha habido un problema leyendo las coordenadas de la tabla", "Fallo de lectura de Tabla", JOptionPane.ERROR_MESSAGE);
					}
				}catch (FileNotFoundException e){
					fichero = null;
					JOptionPane.showMessageDialog(new JFrame(),  "No se ha encontrado el archivo especificado", "Fallo de lectura de Tabla", JOptionPane.ERROR_MESSAGE);
				}catch (IOException e){
					fichero = null;
					JOptionPane.showMessageDialog(new JFrame(),  "Error en el buffer de E/S de lectura", "Fallo de lectura de Tabla", JOptionPane.ERROR_MESSAGE);
				}catch (Exception e){
					fichero = null;
					JOptionPane.showMessageDialog(new JFrame(),  "Ha ocurrido un error al intentar abrir el archivo", "Fallo de lectura de Tabla", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		//##########################################//
		//METODO PARA GUARDAR UNA TABLA
		//##########################################//
		archivar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent args) {
				try{
					PrintWriter escritor = new PrintWriter (fichero);
					escritor.write (tabla.toText());
					escritor.close ();
				}catch (FileNotFoundException e){
					fichero = null;
					JOptionPane.showMessageDialog(new JFrame(), "No se ha encontrado el archivo especificado", "Fallo al Guardar", JOptionPane.ERROR_MESSAGE);
				}catch (Exception e){
					fichero = null;
					JOptionPane.showMessageDialog(new JFrame(), "Ha ocurrido un error al intentar guardar el archivo", "Fallo al Guardar", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		//##########################################//
		//METODO PARA GUARDAR COMO
		//##########################################//
		archivarNuevo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent args) {
					try{
						JFileChooser escogerArchivo = new JFileChooser ();
						escogerArchivo.showDialog (null, "Guardar como");
						fichero=escogerArchivo.getSelectedFile ();
						PrintWriter escritor = new PrintWriter (fichero);
						escritor.write (tabla.toText());
						escritor.close ();
						archivar.setEnabled(true);
					}catch (FileNotFoundException e){
						fichero = null;
						JOptionPane.showMessageDialog(new JFrame(), "No se ha encontrado el archivo especificado", "Fallo al Guardar", JOptionPane.ERROR_MESSAGE);
					}catch (Exception e){
						fichero = null;
						JOptionPane.showMessageDialog(new JFrame(), "Ha ocurrido un error al intentar guardar el archivo", "Fallo al Guardar", JOptionPane.ERROR_MESSAGE);
					}
			}
		});
		//##########################################//
		//ALINEACIONES DE TEXTO
		//##########################################//
		izquierda.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent args) {
				tabla.getRender().setHorizontalAlignment(SwingConstants.LEFT);
				tabla.updateTable();
			}
		});
		centrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent args) {	
				tabla.getRender().setHorizontalAlignment(SwingConstants.CENTER);
				tabla.updateTable();
			}
		});
		derecha.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent args) {
				tabla.getRender().setHorizontalAlignment(SwingConstants.RIGHT);
				tabla.updateTable();
			}
		});
		//##########################################//
		//CAMBIOS DE FUENTES
		//##########################################//
		tahoma.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent args) {
				Tabla.changeFont("Tahoma");
				tabla.cambioFuente();
			}
		});
		freeMono.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent args) {	
				Tabla.changeFont("FreeMono");
				tabla.cambioFuente();
			}
		});
		gothic.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent args) {
				Tabla.changeFont("URW Gothic L");
				tabla.cambioFuente();
			}
		});
		//##########################################//
		//METODOS PARA EL ZOOM
		//##########################################//
		mas.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent args) {
				if(vista!=4) {
					tabla.zoom(++vista);
				}
				tabla.updateTable();
			}
		});
		menos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent args) {
				if(vista!=0) {
					tabla.zoom(--vista);
				}
				tabla.updateTable();
			}
		});		
		//##########################################//
		//METODOS PARA AYUDA
		//##########################################//
		tutorial.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent args) {
				JOptionPane.showMessageDialog(new JFrame(), "Bienvenido al tutorial de uso\n"
						   								  + "de la hoja de cálculo.\n"
						   								  + "\n"
						   								  + "A continuación veremos todo lo\n"
						   								  + "que puedes hacer con esta herramienta\n", 
														   "Tutorial de Uso", JOptionPane.INFORMATION_MESSAGE);
				JOptionPane.showMessageDialog(new JFrame(), "Archivo\n"
						   								  + "\n"
						   								  + "Aqui puedes encontrar las opciones:\n\n"
						   								  + "- Crear, para una nueva hoja de cálculo\n"
						   								  + "- Cargar, para abrir una hoja existente\n"
						   								  + "- Archivar, para guardar la hoja que estamos editando",
														   "Tutorial de Uso", JOptionPane.INFORMATION_MESSAGE);
				JOptionPane.showMessageDialog(new JFrame(), "Editar\n"
						   								  + "\n"
						   								  + "Aqui puedes encontrar las opciones:\n\n"
						   								  + "- Copiar, Pegar, Cortar\n"
						   								  + "- Deshacer y Rehacer\n", 
														   "Tutorial de Uso", JOptionPane.INFORMATION_MESSAGE);
				JOptionPane.showMessageDialog(new JFrame(), "Texto\n"
						   								  + "\n"
						   								  + "Aqui puedes encontrar las opciones:\n\n"
						   								  + "- Alinear, para cambiar la orientación del texto\n"
						   								  + "- Fuente, para cambiar el tipo de fuente del texto\n", 
														   "Tutorial de Uso", JOptionPane.INFORMATION_MESSAGE);
				JOptionPane.showMessageDialog(new JFrame(), "Zoom\n"
						   								  + "\n"
						   								  + "Aqui puedes encontrar las opciones:\n\n"
						   								  + "- Zoom + y Zoom -, para cambiar la vista de la tabla\n", 
														   "Tutorial de Uso", JOptionPane.INFORMATION_MESSAGE);
				JOptionPane.showMessageDialog(new JFrame(), "Ayuda\n"
						   								  + "\n"
						   								  + "Aqui puedes encontrar las opciones:\n\n"
						   								  + "- Tutorial, es la herramienta que estás usando\n"
						   								  + "- Acerca De, para conocer la información del proveedor\n"
						   								  + "y la versión de la aplicación\n", 
														   "Tutorial de Uso", JOptionPane.INFORMATION_MESSAGE);
				JOptionPane.showMessageDialog(new JFrame(), "Final del tutorial\n"
						   								  + "\n"
						   								  + "Una vez vistas todas las funcionalidades ya puedes\n"
						   								  + "sacarle el máximo rendimiento a la Hoja de Cálculo\n", 
														   "Tutorial de Uso", JOptionPane.INFORMATION_MESSAGE);
				
			}
		});
		acercaDe.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent args) {
				JOptionPane.showMessageDialog(new JFrame(), "Hoja de Cálculo\n"
														   +"\n"
														   +"Autor: Ángel López Arias\n"
														   +"Versión: 1.12\n"
														   +"Contacto:\n"
														   +"alopea08@estudiantes.unileon.es", 
														   "Acerca de", JOptionPane.INFORMATION_MESSAGE);
			}
		});			
		//##########################################//
		//METODO DESHACER
		//##########################################//
		deshacer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent args) {
				if(hayTabla){
					try{
						tabla.deshacer();
					}catch(TableException e) {
						JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "Fallo de Tabla", JOptionPane.ERROR_MESSAGE);
					}
				}else{
					JOptionPane.showMessageDialog(new JFrame(), "No hay tabla sobre la que hacer deshacer", "Fallo de Tabla", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		//##########################################//
		//METODO REHACER
		//##########################################//
		rehacer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent args) {
				if(hayTabla){
					try{
						tabla.rehacer();
					}catch(TableException e) {
						JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "Fallo de Tabla", JOptionPane.ERROR_MESSAGE);
					}
				}else{
					JOptionPane.showMessageDialog(new JFrame(), "No hay tabla sobre la que hacer rehacer", "Fallo de Tabla", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		//##########################################//
		//METODO COPIAR, PEGAR Y CORTAR
		//##########################################//
		copiar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent args) {
				int rowIndex = tabla.getTable().getSelectedRow();
				int colIndex = tabla.getTable().getSelectedColumn();
				
				if(rowIndex ==0 || colIndex==0) {
					JOptionPane.showMessageDialog(new JFrame(), "Ninguna casilla seleccionada para copiar", "Fallo de Tabla", JOptionPane.ERROR_MESSAGE);
				}else {
					portapapeles = tabla.getTable().getValueAt(rowIndex, colIndex).toString();
					System.out.println("Texto Copiado = "+ portapapeles);
				}
			}
		});
		pegar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent args) {
				int rowIndex = tabla.getTable().getSelectedRow();
				int colIndex = tabla.getTable().getSelectedColumn();
				
				if(portapapeles == null) {
					JOptionPane.showMessageDialog(new JFrame(), "No hay nada en el portapapeles para pegar", "Fallo de Tabla", JOptionPane.ERROR_MESSAGE);
				}else if(rowIndex ==0 || colIndex==0) {
					JOptionPane.showMessageDialog(new JFrame(), "Ninguna casilla seleccionada para pegar", "Fallo de Tabla", JOptionPane.ERROR_MESSAGE);
				}else {
					tabla.getTable().setValueAt(portapapeles,rowIndex, colIndex);
					System.out.println("Texto Pegado = "+ portapapeles);
					tabla.updateCell(rowIndex, colIndex);
				}
			}
		});
		cortar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent args) {
				int rowIndex = tabla.getTable().getSelectedRow();
				int colIndex = tabla.getTable().getSelectedColumn();
				
				if(rowIndex ==0 || colIndex==0) {
					JOptionPane.showMessageDialog(new JFrame(), "Ninguna casilla seleccionada para cortar", "Fallo de Tabla", JOptionPane.ERROR_MESSAGE);
				}else {
					portapapeles = tabla.getTable().getValueAt(rowIndex, colIndex).toString();
					tabla.getTable().setValueAt("0",rowIndex, colIndex);
					System.out.println("Texto Cortado = "+ portapapeles);
					tabla.updateCell(rowIndex, colIndex);
				}
			}
		});
		
		//##########################################//
		//CONFIGURACION DE LA TABLA
		//##########################################//
		if(filasTabla != 0 && columnasTabla != 0) {
			
			hayTabla = true;
			//Creo la tabla y todo lo referente a ella
			tabla = new Tabla(filasTabla, columnasTabla);
			tabla.guardarPrimeraModificacion(); //PRUEBA DEL METODO DESHACER ---------------------------------------------------------------
			tabla.getRender().setHorizontalAlignment(SwingConstants.CENTER);
			tabla.createKeybindings(tabla.getTable());
				//##########################################//
				//LISTENER PARA PULSACIONES DE LA TABLA
				//##########################################//
				tabla.getTable().addMouseListener(new MouseAdapter() {
					@Override
					/**
					 * Este metodo lo que hace es colorear las columnas y filas afectadas
					 * cuando se pulsa sobre una celda o se selecciona o se edita su
					 * contenido
					 */
					public void mouseClicked(MouseEvent e) {
						tabla.getTable().repaint();
						int rowIndex = tabla.getTable().getSelectedRow();
						int colIndex = tabla.getTable().getSelectedColumn();
						celda.setText("[ "+pasarNumLetra(colIndex) + rowIndex  +" ]");
						celda.updateUI();
						contenido.setText((tabla.getTable().getValueAt(rowIndex,colIndex)).toString());
					}
				});
				//##########################################//
				//LISTENER PARA CAMBIOS EN LA TABLA
				//##########################################//
				tabla.getModel().addTableModelListener(new TableModelListener() {
					@Override
					/**
					 * Cualquier cambio que sufre la tabla, por mínimo que sea es registrado
					 * por este metodo, que se encarga de guardarlo cuando es necesario y de actualizar
					 * la matriz de contenido dentro de la misma tabla
					 */
					public void tableChanged(TableModelEvent arg0) {
						
						System.out.println("Editada la casilla (" + arg0.getColumn() + ", " + arg0.getLastRow() + ")");
						tabla.updateExcel(arg0.getLastRow(), arg0.getColumn());
						tabla.guardarModificacion();//PRUEBA DEL METODO DESHACER ---------------------------------------------------------------
					}
				});
			
			//PANEL QUE CONTIENE todo
			panel = new JPanel();

			//Anyado al panel la tabla
			panel.add(tabla.getTable());
			
			//Anyado al scroll pane el panel
			scrollpane = new JScrollPane(panel);
		}else{
			hayTabla = false;
			scrollpane = new JScrollPane();

			//Ponemos todos los botones que pueden dar error sin tabla, en false
			alinear.setEnabled(false);
			fuente.setEnabled(false);
			archivar.setEnabled(false);
			archivarNuevo.setEnabled(false);
			deshacer.setEnabled(false);
			rehacer.setEnabled(false);
			copiar.setEnabled(false);
			pegar.setEnabled(false);
			cortar.setEnabled(false);
			mas.setEnabled(false);
			menos.setEnabled(false);
		}

		//##########################################//
		//CONSTRUCCION DEL PANEL
		//##########################################//
		
		inferior = new JPanel();
		celda = new JLabel("[CELDA]");
		flecha = new JLabel("  -->  ");
		contenido = new JTextField("Contenido de la casilla");
		calcula = new JButton(" CALCULAR ");
		
		inferior.add(celda);
		inferior.add(flecha);
		inferior.add(contenido);
		inferior.add(calcula);
	
		//##########################################//
		//LISTENER A CALCULAR
		//##########################################//
		calcula.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent args) {
				if(hayTabla){
					try{
						tabla.calcular();
					}catch(TableException e) {
						JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
					}
				}else{
					JOptionPane.showMessageDialog(new JFrame(), "No hay tabla sobre la que calcular", "ERROR", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		//##########################################//
		//CONSTRUCCION DEL PANEL CON POSICIONES
		//##########################################//
		
		ventana.getContentPane().add(scrollpane, BorderLayout.CENTER);
		ventana.getContentPane().add(inferior, BorderLayout.SOUTH);
		
		//Esto es para que la ventana cuadre con lo que hemos creado o lo que abrimos
		if(hayTabla) {
			int ancho = tabla.tamanioAnchoCab(Ventana.vista) + (tabla.tamanioAncho(Ventana.vista)*this.columnasTabla) + 50;
			int alto = tabla.tamanioAlto(Ventana.vista)*(this.filasTabla+7);
			ventana.setBounds(20, 20, ancho, alto);
		}
		ventana.setVisible(true);
		
	//##########################################//
	//FIN CONSTRUCTOR VENTANA
	//##########################################//
	}
	
	//##########################################//
	//METODOS DE AYUDA A LA CONSTRUCCION DE LA VENTANA
	//##########################################//
	/**
	* Metodo que me permite pedir columnas y filas con restricciones
	*/
	private int getDimension(String type) {
		int resultado = 0;
		do {
			try {
				resultado = Integer.parseInt(JOptionPane.showInputDialog("NUMERO DE "+ type +":"));
				if(resultado<=0) {
					JOptionPane.showMessageDialog(null, "EL NUMERO DE "+ type +" DEBE SER MAYOR QUE 0");
					resultado = -1;
				}
			}catch(Exception e) {
				JOptionPane.showMessageDialog(null, "EL NUMERO DE "+ type +" DEBE SER UN NUMERO");
				resultado = -1;
			}
		}while(resultado<=0);

		return resultado;
	}

	/**
	* Metodo para nombrar las columnas con letras
	*/
	public static String pasarNumLetra(int column) {
		String letra = "";
		for(;column>=0;column = column/26 -1) {
			letra = (char) ((char) (column%26)+'@')+letra;
		}
		return letra;
	}
//##########################################//
//FIN DE LA CLASE VENTANA
//##########################################//
}