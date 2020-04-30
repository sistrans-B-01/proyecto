/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad	de	los	Andes	(Bogotá	- Colombia)
 * Departamento	de	Ingeniería	de	Sistemas	y	Computación
 * Licenciado	bajo	el	esquema	Academic Free License versión 2.1
 * 		
 * Curso: isis2304 - Sistemas Transaccionales
 * Proyecto: Parranderos Uniandes
 * @version 1.0
 * @author Germán Bravo
 * Julio de 2018
 * 
 * Revisado por: Claudia Jiménez, Christian Ariza
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package uniandes.isis2304.parranderos.interfazApp;

import java.awt.BorderLayout; 
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.List;

import javax.jdo.JDODataStoreException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import uniandes.isis2304.parranderos.negocio.AlohAndes;
import uniandes.isis2304.parranderos.negocio.Oferta;
import uniandes.isis2304.parranderos.negocio.VOApartamento;
import uniandes.isis2304.parranderos.negocio.VOCliente;
import uniandes.isis2304.parranderos.negocio.VODominio;
import uniandes.isis2304.parranderos.negocio.VOHabitacion;
import uniandes.isis2304.parranderos.negocio.VOOferta;
import uniandes.isis2304.parranderos.negocio.VOOfertaApartamento;
import uniandes.isis2304.parranderos.negocio.VOOfertaHabitacion;
import uniandes.isis2304.parranderos.negocio.VOOfertaVivienda;
import uniandes.isis2304.parranderos.negocio.VOPropietario;
import uniandes.isis2304.parranderos.negocio.VOReserva;
import uniandes.isis2304.parranderos.negocio.VOVecinos;
import uniandes.isis2304.parranderos.negocio.VOVivienda;

/**
 * Clase principal de la interfaz
 * @author Germán Bravo
 */
@SuppressWarnings("serial")

public class InterfazAlohandesApp extends JFrame implements ActionListener
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(InterfazAlohandesApp.class.getName());
	
	/**
	 * Ruta al archivo de configuración de la interfaz
	 */
	private static final String CONFIG_INTERFAZ = "./src/main/resources/config/interfaceConfigApp.json"; 
	
	/**
	 * Ruta al archivo de configuración de los nombres de tablas de la base de datos
	 */
	private static final String CONFIG_TABLAS = "./src/main/resources/config/TablasBD_A.json"; 
	
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
    /**
     * Objeto JSON con los nombres de las tablas de la base de datos que se quieren utilizar
     */
    private JsonObject tableConfig;
    
    /**
     * Asociación a la clase principal del negocio.
     */
    private AlohAndes parranderos;
    
	/* ****************************************************************
	 * 			Atributos de interfaz
	 *****************************************************************/
    /**
     * Objeto JSON con la configuración de interfaz de la app.
     */
    private JsonObject guiConfig;
    
    /**
     * Panel de despliegue de interacción para los requerimientos
     */
    private PanelDatos panelDatos;
    
    /**
     * Menú de la aplicación
     */
    private JMenuBar menuBar;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
    /**
     * Construye la ventana principal de la aplicación. <br>
     * <b>post:</b> Todos los componentes de la interfaz fueron inicializados.
     */
    public InterfazAlohandesApp( )
    {
        // Carga la configuración de la interfaz desde un archivo JSON
        guiConfig = openConfig ("Interfaz", CONFIG_INTERFAZ);
        
        // Configura la apariencia del frame que contiene la interfaz gráfica
        configurarFrame ( );
        if (guiConfig != null) 	   
        {
     	   crearMenu( guiConfig.getAsJsonArray("menuBar") );
        }
        
        tableConfig = openConfig ("Tablas BD", CONFIG_TABLAS);
        parranderos = new AlohAndes (tableConfig);
        
    	String path = guiConfig.get("bannerPath").getAsString();
        panelDatos = new PanelDatos ( );

        setLayout (new BorderLayout());
        add (new JLabel (new ImageIcon (path)), BorderLayout.NORTH );          
        add( panelDatos, BorderLayout.CENTER );        
    }
    
	/* ****************************************************************
	 * 			Métodos de configuración de la interfaz
	 *****************************************************************/
    /**
     * Lee datos de configuración para la aplicació, a partir de un archivo JSON o con valores por defecto si hay errores.
     * @param tipo - El tipo de configuración deseada
     * @param archConfig - Archivo Json que contiene la configuración
     * @return Un objeto JSON con la configuración del tipo especificado
     * 			NULL si hay un error en el archivo.
     */
    private JsonObject openConfig (String tipo, String archConfig)
    {
    	JsonObject config = null;
		try 
		{
			Gson gson = new Gson( );
			FileReader file = new FileReader (archConfig);
			JsonReader reader = new JsonReader ( file );
			config = gson.fromJson(reader, JsonObject.class);
			log.info ("Se encontró un archivo de configuración válido: " + tipo);
		} 
		catch (Exception e)
		{
//			e.printStackTrace ();
			log.info ("NO se encontró un archivo de configuración válido");			
			JOptionPane.showMessageDialog(null, "No se encontró un archivo de configuración de interfaz válido: " + tipo, "Parranderos App", JOptionPane.ERROR_MESSAGE);
		}	
        return config;
    }
    
    /**
     * Método para configurar el frame principal de la aplicación
     */
    private void configurarFrame(  )
    {
    	int alto = 0;
    	int ancho = 0;
    	String titulo = "";	
    	
    	if ( guiConfig == null )
    	{
    		log.info ( "Se aplica configuración por defecto" );			
			titulo = "Parranderos APP Default";
			alto = 300;
			ancho = 500;
    	}
    	else
    	{
			log.info ( "Se aplica configuración indicada en el archivo de configuración" );
    		titulo = guiConfig.get("title").getAsString();
			alto= guiConfig.get("frameH").getAsInt();
			ancho = guiConfig.get("frameW").getAsInt();
    	}
    	
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setLocation (50,50);
        setResizable( true );
        setBackground( Color.WHITE );

        setTitle( titulo );
		setSize ( ancho, alto);        
    }

    /**
     * Método para crear el menú de la aplicación con base em el objeto JSON leído
     * Genera una barra de menú y los menús con sus respectivas opciones
     * @param jsonMenu - Arreglo Json con los menùs deseados
     */
    private void crearMenu(  JsonArray jsonMenu )
    {    	
    	// Creación de la barra de menús
        menuBar = new JMenuBar();       
        for (JsonElement men : jsonMenu)
        {
        	// Creación de cada uno de los menús
        	JsonObject jom = men.getAsJsonObject(); 

        	String menuTitle = jom.get("menuTitle").getAsString();        	
        	JsonArray opciones = jom.getAsJsonArray("options");
        	
        	JMenu menu = new JMenu( menuTitle);
        	
        	for (JsonElement op : opciones)
        	{       	
        		// Creación de cada una de las opciones del menú
        		JsonObject jo = op.getAsJsonObject(); 
        		String lb =   jo.get("label").getAsString();
        		String event = jo.get("event").getAsString();
        		
        		JMenuItem mItem = new JMenuItem( lb );
        		mItem.addActionListener( this );
        		mItem.setActionCommand(event);
        		
        		menu.add(mItem);
        	}       
        	menuBar.add( menu );
        }        
        setJMenuBar ( menuBar );	
    }
    
	/* ****************************************************************
	 * 			Adicion de Operadores
	 *****************************************************************/
    /**
     * Adiciona un tipo de bebida con la información dada por el usuario
     * Se crea una nueva tupla de tipoBebida en la base de datos, si un tipo de bebida con ese nombre no existía
     */
    public void adicionarPropietario( )
    {
    	try 
    	{
    		String nombre = JOptionPane.showInputDialog (this, "Nombre", "Adicionar un propietario", JOptionPane.QUESTION_MESSAGE);
    		String tipoid= JOptionPane.showInputDialog(this, "Tipo de Identificacion", "Adicionar un propietario",JOptionPane.QUESTION_MESSAGE);
    		String id= JOptionPane.showInputDialog(this, "Numero de identificacion", "Adicionar un propietario",JOptionPane.QUESTION_MESSAGE);
    		String tipop= JOptionPane.showInputDialog(this, "Tipo de propietario", "Adicionar un propietario",JOptionPane.QUESTION_MESSAGE);

    		if (id != null)
    		{
        		VOPropietario tb = parranderos.adicionarPropietario(nombre, Long.valueOf(id), tipoid, tipop);
        		if (tb == null)
        		{
        			throw new Exception ("No se pudo crear un propietario con nombre: " + nombre);
        		}
        		String resultado = "En adicionarPropietario\n\n";
        		resultado += "Propietario adicionado exitosamente: " + tb;
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    public void adicionarVecino( )
    {
    	try 
    	{
    		String nombre = JOptionPane.showInputDialog (this, "Nombre", "Adicionar un vecino", JOptionPane.QUESTION_MESSAGE);
    		String tipoid= JOptionPane.showInputDialog(this, "Tipo de Identificacion", "Adicionar un vecino", JOptionPane.QUESTION_MESSAGE);
    		String id= JOptionPane.showInputDialog(this, "Numero de identificacion", "Adicionar un vecino", JOptionPane.QUESTION_MESSAGE);

    		if (id != null)
    		{
        		VOVecinos tb = parranderos.adicionarVecino(nombre, Long.valueOf(id), tipoid);
        		if (tb == null)
        		{
        			throw new Exception ("No se pudo crear vecino con nombre: " + nombre);
        		}
        		String resultado = "En adicionarVecino\n\n";
        		resultado += "Vecino adicionado exitosamente: " + tb;
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    public void adicionarDominio( )
    {
    	try 
    	{
    		String tiporeg= JOptionPane.showInputDialog(this, "Tipo de Registro", "Adicionar dominio", JOptionPane.QUESTION_MESSAGE);
    		String registro = JOptionPane.showInputDialog (this, "Registro", "Adicionar dominio", JOptionPane.QUESTION_MESSAGE);
    		String nombre= JOptionPane.showInputDialog(this, "Nombre", "Adicionar dominio", JOptionPane.QUESTION_MESSAGE);
    		String horario= JOptionPane.showInputDialog(this, "Horario", "Adicionar dominio", JOptionPane.QUESTION_MESSAGE);
    		String ubicacion= JOptionPane.showInputDialog(this, "Ubicacion", "Adicionar dominio", JOptionPane.QUESTION_MESSAGE);

    		if (registro != null)
    		{
        		VODominio tb = parranderos.adicionarDominio(Long.valueOf(registro), tiporeg, nombre, horario, ubicacion);
        		if (tb == null)
        		{
        			throw new Exception ("No se pudo crear un dominio con nombre: " + nombre);
        		}
        		String resultado = "En adicionarDominio\n\n";
        		resultado += "Dominio adicionado exitosamente: " + tb;
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    /* ****************************************************************
	 * 			Adicion de Propuestas de alojamiento
	 *****************************************************************/

    public void adicionarApartamento( )
    {
    	try 
    	{
    		String tipoProp= JOptionPane.showInputDialog(this, "Tipo de Id del propietario", "Adicionar un apartamento", JOptionPane.QUESTION_MESSAGE);
    		String idProp = JOptionPane.showInputDialog (this, "Numero Id del propietario", "Adicionar un apartamento", JOptionPane.QUESTION_MESSAGE);
    		String servicios= JOptionPane.showInputDialog(this, "Servicios", "Adicionar un apartamento", JOptionPane.QUESTION_MESSAGE);
    		String numeroHabit= JOptionPane.showInputDialog(this, "Numero de habitaciones", "Adicionar un apartamento", JOptionPane.QUESTION_MESSAGE);
    		String ubicacion= JOptionPane.showInputDialog(this, "Ubicacion", "Adicionar un apartamento", JOptionPane.QUESTION_MESSAGE);

    		if (ubicacion != null)
    		{
        		VOApartamento tb = parranderos.adicionarApartamento(Integer.valueOf(numeroHabit), servicios, ubicacion, Long.valueOf(idProp), tipoProp);
        		if (tb == null)
        		{
        			throw new Exception ("No se pudo crear un apartamento con ubicacion: " + ubicacion);
        		}
        		String resultado = "En adicionarApartamento\n\n";
        		resultado += "Apartamento adicionado exitosamente: " + tb;
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    public void adicionarVivienda( )
    {
    	try 
    	{
    		String tipoId= JOptionPane.showInputDialog(this, "Tipo de Id del vecino", "Adicionar una vivienda", JOptionPane.QUESTION_MESSAGE);
    		String idVeci= JOptionPane.showInputDialog(this, "Numero Id del vecino", "Adicionar una vivienda", JOptionPane.QUESTION_MESSAGE);
    		String cara= JOptionPane.showInputDialog(this, "Caracteristicas del seguro", "Adicionar una vivienda", JOptionPane.QUESTION_MESSAGE);
    		String menaje = JOptionPane.showInputDialog (this, "Menaje ofrecido", "Adicionar una vivienda", JOptionPane.QUESTION_MESSAGE);
    		String diasUsada= JOptionPane.showInputDialog(this, "Numero de dias usada", "Adicionar una vivienda", JOptionPane.QUESTION_MESSAGE);
    		String numeroHabi= JOptionPane.showInputDialog(this, "Numero de habitaciones", "Adicionar una vivienda", JOptionPane.QUESTION_MESSAGE);
    		String ubicacion= JOptionPane.showInputDialog(this, "Ubicacion", "Adicionar una vivienda", JOptionPane.QUESTION_MESSAGE);
    		String servicios= JOptionPane.showInputDialog(this, "Servicios ofrecidos", "Adicionar una vivienda", JOptionPane.QUESTION_MESSAGE);

    		if (ubicacion != null)
    		{
        		VOVivienda tb = parranderos.adicionarVivienda(cara, menaje, Integer.valueOf(diasUsada), Integer.valueOf(numeroHabi), servicios, ubicacion, Long.valueOf(idVeci), tipoId);
        		if (tb == null)
        		{
        			throw new Exception ("No se pudo crear una vivienda con ubicacion: " + ubicacion);
        		}
        		String resultado = "En adicionarVivienda\n\n";
        		resultado += "Vivienda adicionada exitosamente: " + tb;
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    public void adicionarHabitacion( )
    {
    	try 
    	{
    		String tipoRegDom= JOptionPane.showInputDialog(this, "Tipo de dominio", "Adicionar una habitacion", JOptionPane.QUESTION_MESSAGE);
    		String regDom= JOptionPane.showInputDialog(this, "Numero de registro del dominio", "Adicionar una habitacion", JOptionPane.QUESTION_MESSAGE);
    		String capa= JOptionPane.showInputDialog(this, "Capacidad", "Adicionar una habitacion", JOptionPane.QUESTION_MESSAGE);
    		String con = JOptionPane.showInputDialog (this, "Compratida con cuantos?", "Adicionar una habitacion", JOptionPane.QUESTION_MESSAGE);
    		String diasUsada= JOptionPane.showInputDialog(this, "Numero de dias usada", "Adicionar una habitacion", JOptionPane.QUESTION_MESSAGE);
    		String numEst= JOptionPane.showInputDialog(this, "Numero de estrellas", "Adicionar una habitacion", JOptionPane.QUESTION_MESSAGE);
    		String numeroHabi= JOptionPane.showInputDialog(this, "Numero de habitacion", "Adicionar una habitacion", JOptionPane.QUESTION_MESSAGE);
    		String precio= JOptionPane.showInputDialog(this, "Precio", "Adicionar una habitacion", JOptionPane.QUESTION_MESSAGE);
    		String servicios= JOptionPane.showInputDialog(this, "Servicios ofrecidos", "Adicionar tipo de bebida", JOptionPane.QUESTION_MESSAGE);
    		String tamano= JOptionPane.showInputDialog(this, "Tamano", "Adicionar una habitacion", JOptionPane.QUESTION_MESSAGE);
    		String categoria= JOptionPane.showInputDialog(this, "Categoria", "Adicionar una habitacion", JOptionPane.QUESTION_MESSAGE);

    		if (regDom != null)
    		{
        		VOHabitacion tb = parranderos.adicionarHabitacion(Integer.valueOf(capa), Integer.valueOf(con), Integer.valueOf(diasUsada), Integer.valueOf(numEst), Integer.valueOf(numeroHabi), Double.valueOf(precio), servicios, tamano, categoria, Long.valueOf(regDom), tipoRegDom);
        		if (tb == null)
        		{
        			throw new Exception ("No se pudo crear una habitacion con Registro de dominio: " + regDom);
        		}
        		String resultado = "En adicionarHabitacion\n\n";
        		resultado += "Habitacion adicionada exitosamente: " + tb;
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    /* ****************************************************************
	 * 			Adicion de Clientes
	 *****************************************************************/
    /**
     * Adiciona un tipo de bebida con la información dada por el usuario
     * Se crea una nueva tupla de tipoBebida en la base de datos, si un tipo de bebida con ese nombre no existía
     */
    public void adicionarCliente( )
    {
    	try 
    	{
    		String nombre = JOptionPane.showInputDialog (this, "Nombre", "Adicionar un cliente", JOptionPane.QUESTION_MESSAGE);
    		String tipoid= JOptionPane.showInputDialog(this, "Tipo de Identificacion", "Adicionar un cliente", JOptionPane.QUESTION_MESSAGE);
    		String id= JOptionPane.showInputDialog(this, "Numero de identificacion", "Adicionar un cliente", JOptionPane.QUESTION_MESSAGE);
    		String tipoCli= JOptionPane.showInputDialog(this, "Tipo de Cliente", "Adicionar un cliente", JOptionPane.QUESTION_MESSAGE);

    		if (id != null)
    		{
        		VOCliente tb = parranderos.adicionarCliente(nombre, Long.valueOf(id), tipoid, tipoCli);
        		if (tb == null)
        		{
        			throw new Exception ("No se pudo crear un cliente con nombre: " + nombre);
        		}
        		String resultado = "En adicionarCliente\n\n";
        		resultado += "Cliente adicionado exitosamente: " + tb;
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    /* ****************************************************************
	 * 			Adicion y Eliminacion de Ofertas
	 *****************************************************************/
    
    public void adicionarOferta( )
    {
    	try 
    	{
    		String descuento= JOptionPane.showInputDialog(this, "Descuento", "Adicionar una oferta", JOptionPane.QUESTION_MESSAGE);
    		String diasActiva = JOptionPane.showInputDialog (this, "Numero de dias activa", "Adicionar una oferta", JOptionPane.QUESTION_MESSAGE);
    		String diasUsada = JOptionPane.showInputDialog (this, "Numero de dias usada", "Adicionar una oferta", JOptionPane.QUESTION_MESSAGE);
    		String fechaIni= JOptionPane.showInputDialog(this, "Fecha de inicio", "Adicionar una oferta", JOptionPane.QUESTION_MESSAGE);
    		String fechaFin= JOptionPane.showInputDialog(this, "Fecha de finalizacion", "Adicionar una oferta", JOptionPane.QUESTION_MESSAGE);
    		String tiempoCont= JOptionPane.showInputDialog(this, "Tiempo del contrato", "Adicionar una oferta", JOptionPane.QUESTION_MESSAGE);

    		if (fechaIni != null && fechaFin != null)
    		{
        		VOOferta tb = parranderos.adicionarOferta(Integer.valueOf(descuento), Integer.valueOf(diasActiva), Integer.valueOf(diasUsada), Timestamp.valueOf(fechaFin), Timestamp.valueOf(fechaIni), tiempoCont);
        		if (tb == null)
        		{
        			throw new Exception ("No se pudo crear una oferta con fecha Inicio: " + fechaIni + "y fecha Final: " + fechaFin);
        		}
        		String resultado = "En adicionarOferta\n\n";
        		resultado += "Oferta adicionada exitosamente: " + tb;
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    public void relacionarOfertaApartamento( )
    {
    	try 
    	{
    		String idOferta= JOptionPane.showInputDialog(this, "El id de la oferta", JOptionPane.QUESTION_MESSAGE);
    		String idApartamento = JOptionPane.showInputDialog (this, "La ubicacion del apartamento", JOptionPane.QUESTION_MESSAGE);

    		if (idOferta != null && idApartamento != null)
    		{
        		VOOfertaApartamento tb = parranderos.adicionarOfertaApartamento(Long.valueOf(idOferta), idApartamento);
        		if (tb == null)
        		{
        			throw new Exception ("No se pudo crear una ofertaApartamento con idOferta: " + idOferta + "y idApartamento: " + idApartamento);
        		}
        		String resultado = "En relacionarOfertaApartamento\n\n";
        		resultado += "OfertaApartamento relacionada exitosamente: " + tb;
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    public void relacionarOfertaVivienda( )
    {
    	try 
    	{
    		String idOferta= JOptionPane.showInputDialog(this, "El id de la oferta",  JOptionPane.QUESTION_MESSAGE);
    		String idVivienda = JOptionPane.showInputDialog (this, "La ubicacion de la vivienda", JOptionPane.QUESTION_MESSAGE);

    		if (idOferta != null && idVivienda != null)
    		{
        		VOOfertaVivienda tb = parranderos.adicionarOfertaVivienda(Long.valueOf(idOferta), idVivienda);
        		if (tb == null)
        		{
        			throw new Exception ("No se pudo crear una ofertaVivienda con idOferta: " + idOferta + "y idVivienda: " + idVivienda);
        		}
        		String resultado = "En relacionarOfertaVivienda\n\n";
        		resultado += "OfertaVivienda relacionada exitosamente: " + tb;
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    public void relacionarOfertaHabitacion( )
    {
    	try 
    	{
    		String idOferta= JOptionPane.showInputDialog(this, "El id de la oferta", JOptionPane.QUESTION_MESSAGE);
    		String idHabitacion = JOptionPane.showInputDialog (this, "El id de la habitacion", JOptionPane.QUESTION_MESSAGE);

    		if (idOferta != null && idHabitacion != null)
    		{
        		VOOfertaHabitacion tb = parranderos.adicionarOfertaHabitacion(Long.valueOf(idOferta), Long.valueOf(idHabitacion));
        		if (tb == null)
        		{
        			throw new Exception ("No se pudo crear una ofertaHabitacion con idOferta: " + idOferta + "y idHabitacion: " + idHabitacion);
        		}
        		String resultado = "En relacionarOfertaHabitacion\n\n";
        		resultado += "OfertaHabitacion relacionada exitosamente: " + tb;
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    public void eliminarOfertaApartamento( )
    {
    	try 
    	{
    		String idOferta = JOptionPane.showInputDialog (this, "Id de la oferta", JOptionPane.QUESTION_MESSAGE);
    		String idApartamento = JOptionPane.showInputDialog (this, "Ubicacion del apartamento", JOptionPane.QUESTION_MESSAGE);

    		if (idOferta != null && idApartamento != null)
    		{
    			long tbEliminados = parranderos.eliminarOfertaApartamentoPorIds(Long.valueOf(idOferta), idApartamento);

    			String resultado = "En eliminarOfertaApartamento\n\n";
    			resultado += tbEliminados + " Ofertas de apartamento eliminadas\n";
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    public void eliminarOfertaVivienda( )
    {
    	try 
    	{
    		String idOferta = JOptionPane.showInputDialog (this, "Id de la oferta", JOptionPane.QUESTION_MESSAGE);
    		String idVivienda = JOptionPane.showInputDialog (this, "Ubicacion de la vivienda", JOptionPane.QUESTION_MESSAGE);

    		if (idOferta != null && idVivienda != null)
    		{
    			long tbEliminados = parranderos.eliminarOfertaViviendaPorIds(Long.valueOf(idOferta), idVivienda);

    			String resultado = "En eliminarOfertaVivienda\n\n";
    			resultado += tbEliminados + " Ofertas de vivienda eliminadas\n";
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    public void eliminarOfertaHabitacion( )
    {
    	try 
    	{
    		String idOferta = JOptionPane.showInputDialog (this, "Id de la oferta", JOptionPane.QUESTION_MESSAGE);
    		String idHabitacion = JOptionPane.showInputDialog (this, "Id de la habitacion", JOptionPane.QUESTION_MESSAGE);

    		if (idOferta != null && idHabitacion != null)
    		{
    			long tbEliminados = parranderos.eliminarOfertaHabitacionPorIds(Long.valueOf(idOferta), Long.valueOf(idHabitacion));

    			String resultado = "En eliminarOfertaHabitacion\n\n";
    			resultado += tbEliminados + " Ofertas de habitacion eliminadas\n";
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    public void eliminarOfertaPorId( )
    {
    	try 
    	{
    		String idOferta = JOptionPane.showInputDialog (this, "Id de la oferta", "Borrar oferta por Id", JOptionPane.QUESTION_MESSAGE);

    		if (idOferta != null)
    		{
    			long tbEliminados = parranderos.eliminarOfertaPorId(Long.valueOf(idOferta));

    			String resultado = "En eliminarOferta\n\n";
    			resultado += tbEliminados + " Ofertas eliminadas\n";
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    /* ****************************************************************
	 * 			Adicion y Eliminacion de RESERVAS
	 *****************************************************************/
    
    public void adicionarReserva( )
    {
    	try 
    	{
    		String tipoIdCli= JOptionPane.showInputDialog(this, "Tipo de identificacion del cliente", "Adicionar una reserva", JOptionPane.QUESTION_MESSAGE);
    		String idCli= JOptionPane.showInputDialog(this, "Numero de identificacion del cliente", "Adicionar una reserva", JOptionPane.QUESTION_MESSAGE);
    		String idOfe= JOptionPane.showInputDialog(this, "Identificador de la oferta", "Adicionar una reserva", JOptionPane.QUESTION_MESSAGE);
    		String costoP= JOptionPane.showInputDialog(this, "Descuento", "Adicionar una reserva", JOptionPane.QUESTION_MESSAGE);
    		String costoT = JOptionPane.showInputDialog (this, "Numero de dias activa", "Adicionar una reserva", JOptionPane.QUESTION_MESSAGE);
    		String fechaIni= JOptionPane.showInputDialog(this, "Fecha de inicio", "Adicionar una reserva", JOptionPane.QUESTION_MESSAGE);
    		String fechaFin= JOptionPane.showInputDialog(this, "Fecha de finalizacion", "Adicionar una reserva", JOptionPane.QUESTION_MESSAGE);
    		String tiempoAloj= JOptionPane.showInputDialog(this, "Tiempo del contrato", "Adicionar una reserva", JOptionPane.QUESTION_MESSAGE);

    		if (idCli != null && idOfe != null)
    		{
        		VOReserva tb = parranderos.adicionarReserva(Double.valueOf(costoP), Double.valueOf(costoT), Timestamp.valueOf(fechaFin), Timestamp.valueOf(fechaIni), tiempoAloj, Long.valueOf(idCli), tipoIdCli, Long.valueOf(idOfe));
        		if (tb == null)
        		{
        			throw new Exception ("No se pudo crear una reserva con Id de Cliente: " + idCli + "y Id de oferta: " + idOfe);
        		}
        		String resultado = "En adicionarReserva\n\n";
        		resultado += "Reserva adicionada exitosamente: " + tb;
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    public void eliminarReservaPorId( )
    {
    	try 
    	{
    		String idReserva = JOptionPane.showInputDialog (this, "Id de la reserva", "Borrar reserva por Id", JOptionPane.QUESTION_MESSAGE);

    		if (idReserva != null)
    		{
    			long tbEliminados = parranderos.eliminarReservaPorId(Long.valueOf(idReserva));

    			String resultado = "En eliminarReserva\n\n";
    			resultado += tbEliminados + " Reservas eliminadas\n";
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    /* ****************************************************************
	 * 			Metodos de CONSULTA
	 *****************************************************************/
    
    /**
     * Consulta en la base de datos los tipos de bebida existentes y los muestra en el panel de datos de la aplicación
     */
    public void listarDineroPorProveedor( )
    {
    	try 
    	{
			List <Object[]> lista = parranderos.darDineroRecibidPorProveedor();

			String resultado = "En listarDineroRecibido";
			resultado +=  "\n" + lista;
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    /**
     * Consulta en la base de datos los tipos de bebida existentes y los muestra en el panel de datos de la aplicación
     */
    public void listar20Ofertaspopulares( )
    {
    	try 
    	{
			List <Oferta> lista = parranderos.dar20OfertasMasPopulares();

			String resultado = "En listarOfertasPopulares";
			resultado +=  "\n" + lista;
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    /**
     * Consulta en la base de datos los tipos de bebida existentes y los muestra en el panel de datos de la aplicación
     */
    public void listarIndiceOcupacion( )
    {
    	try 
    	{
			List <Object[]> lista = parranderos.darIndiceOcupacion();

			String resultado = "En listarIndiceOcupacion";
			resultado +=  "\n" + lista;
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }


	/* ****************************************************************
	 * 			Métodos administrativos
	 *****************************************************************/
	/**
	 * Muestra el log de Parranderos
	 */
	public void mostrarLogAlohandes ()
	{
		mostrarArchivo ("parranderos.log");
	}
	
	/**
	 * Muestra el log de datanucleus
	 */
	public void mostrarLogDatanuecleus ()
	{
		mostrarArchivo ("datanucleus.log");
	}
	
	/**
	 * Limpia el contenido del log de parranderos
	 * Muestra en el panel de datos la traza de la ejecución
	 */
	public void limpiarLogAlohandes ()
	{
		// Ejecución de la operación y recolección de los resultados
		boolean resp = limpiarArchivo ("parranderos.log");

		// Generación de la cadena de caracteres con la traza de la ejecución de la demo
		String resultado = "\n\n************ Limpiando el log de parranderos ************ \n";
		resultado += "Archivo " + (resp ? "limpiado exitosamente" : "NO PUDO ser limpiado !!");
		resultado += "\nLimpieza terminada";

		panelDatos.actualizarInterfaz(resultado);
	}
	
	/**
	 * Limpia el contenido del log de datanucleus
	 * Muestra en el panel de datos la traza de la ejecución
	 */
	public void limpiarLogDatanucleus ()
	{
		// Ejecución de la operación y recolección de los resultados
		boolean resp = limpiarArchivo ("datanucleus.log");

		// Generación de la cadena de caracteres con la traza de la ejecución de la demo
		String resultado = "\n\n************ Limpiando el log de datanucleus ************ \n";
		resultado += "Archivo " + (resp ? "limpiado exitosamente" : "NO PUDO ser limpiado !!");
		resultado += "\nLimpieza terminada";

		panelDatos.actualizarInterfaz(resultado);
	}
	
	/**
	 * Limpia todas las tuplas de todas las tablas de la base de datos de parranderos
	 * Muestra en el panel de datos el número de tuplas eliminadas de cada tabla
	 */
	public void limpiarBD ()
	{
		try 
		{
    		// Ejecución de la demo y recolección de los resultados
			long eliminados [] = parranderos.limpiarAlohandes();
			
			// Generación de la cadena de caracteres con la traza de la ejecución de la demo
			String resultado = "\n\n************ Limpiando la base de datos ************ \n";
			resultado += eliminados [0] + " Oferta_Apartamento eliminados\n";
			resultado += eliminados [1] + " Oferta_Vivienda eliminados\n";
			resultado += eliminados [2] + " Oferta_Habitacion eliminados\n";
			resultado += eliminados [3] + " Servicios_Adicionales eliminadas\n";
			resultado += eliminados [4] + " Reserva de bebida eliminados\n";
			resultado += eliminados [5] + " Oferta eliminados\n";
			resultado += eliminados [6] + " Habitacion eliminados\n";
			resultado += eliminados [7] + " Apartamento eliminados\n";
			resultado += eliminados [8] + " Vivienda eliminados\n";
			resultado += eliminados [9] + " Cliente eliminados\n";
			resultado += eliminados [10] + " Vecinos eliminados\n";
			resultado += eliminados [11] + " Dominio eliminados\n";
			resultado += eliminados [12] + " Propietario eliminados\n";
			resultado += "\nLimpieza terminada";
   
			panelDatos.actualizarInterfaz(resultado);
		} 
		catch (Exception e) 
		{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	
	/**
	 * Muestra la presentación general del proyecto
	 */
	public void mostrarPresentacionGeneral ()
	{
		mostrarArchivo ("data/00-ST-ParranderosJDO.pdf");
	}
	
	/**
	 * Muestra el modelo conceptual de Parranderos
	 */
	public void mostrarModeloConceptual ()
	{
		mostrarArchivo ("data/Modelo Conceptual Parranderos.pdf");
	}
	
	/**
	 * Muestra el esquema de la base de datos de Parranderos
	 */
	public void mostrarEsquemaBD ()
	{
		mostrarArchivo ("data/Esquema BD Parranderos.pdf");
	}
	
	/**
	 * Muestra el script de creación de la base de datos
	 */
	public void mostrarScriptBD ()
	{
		mostrarArchivo ("data/EsquemaParranderos.sql");
	}
	
	/**
	 * Muestra la arquitectura de referencia para Parranderos
	 */
	public void mostrarArqRef ()
	{
		mostrarArchivo ("data/ArquitecturaReferencia.pdf");
	}
	
	/**
	 * Muestra la documentación Javadoc del proyectp
	 */
	public void mostrarJavadoc ()
	{
		mostrarArchivo ("doc/index.html");
	}    

	/* ****************************************************************
	 * 			Métodos privados para la presentación de resultados y otras operaciones
	 *****************************************************************/
    /**
     * Genera una cadena de caracteres con la lista de los tipos de bebida recibida: una línea por cada tipo de bebida
     * @param lista - La lista con los tipos de bebida
     * @return La cadena con una líea para cada tipo de bebida recibido
     */
    /**private String listarTiposBebida(List<VOTipoBebida> lista) 
    {
    	String resp = "Los tipos de bebida existentes son:\n";
    	int i = 1;
        for (VOTipoBebida tb : lista)
        {
        	resp += i++ + ". " + tb.toString() + "\n";
        }
        return resp;
	}

    /**
     * Genera una cadena de caracteres con la descripción de la excepcion e, haciendo énfasis en las excepcionsde JDO
     * @param e - La excepción recibida
     * @return La descripción de la excepción, cuando es javax.jdo.JDODataStoreException, "" de lo contrario
     */
	private String darDetalleException(Exception e) 
	{
		String resp = "";
		if (e.getClass().getName().equals("javax.jdo.JDODataStoreException"))
		{
			JDODataStoreException je = (javax.jdo.JDODataStoreException) e;
			return je.getNestedExceptions() [0].getMessage();
		}
		return resp;
	}

	/**
	 * Genera una cadena para indicar al usuario que hubo un error en la aplicación
	 * @param e - La excepción generada
	 * @return La cadena con la información de la excepción y detalles adicionales
	 */
	private String generarMensajeError(Exception e) 
	{
		String resultado = "************ Error en la ejecución\n";
		resultado += e.getLocalizedMessage() + ", " + darDetalleException(e);
		resultado += "\n\nRevise datanucleus.log y parranderos.log para más detalles";
		return resultado;
	}

	/**
	 * Limpia el contenido de un archivo dado su nombre
	 * @param nombreArchivo - El nombre del archivo que se quiere borrar
	 * @return true si se pudo limpiar
	 */
	private boolean limpiarArchivo(String nombreArchivo) 
	{
		BufferedWriter bw;
		try 
		{
			bw = new BufferedWriter(new FileWriter(new File (nombreArchivo)));
			bw.write ("");
			bw.close ();
			return true;
		} 
		catch (IOException e) 
		{
//			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Abre el archivo dado como parámetro con la aplicación por defecto del sistema
	 * @param nombreArchivo - El nombre del archivo que se quiere mostrar
	 */
	private void mostrarArchivo (String nombreArchivo)
	{
		try
		{
			Desktop.getDesktop().open(new File(nombreArchivo));
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* ****************************************************************
	 * 			Métodos de la Interacción
	 *****************************************************************/
    /**
     * Método para la ejecución de los eventos que enlazan el menú con los métodos de negocio
     * Invoca al método correspondiente según el evento recibido
     * @param pEvento - El evento del usuario
     */
    @Override
	public void actionPerformed(ActionEvent pEvento)
	{
		String evento = pEvento.getActionCommand( );		
        try 
        {
			Method req = InterfazAlohandesApp.class.getMethod ( evento );			
			req.invoke ( this );
		} 
        catch (Exception e) 
        {
			e.printStackTrace();
		} 
	}
    
	/* ****************************************************************
	 * 			Programa principal
	 *****************************************************************/
    /**
     * Este método ejecuta la aplicación, creando una nueva interfaz
     * @param args Arreglo de argumentos que se recibe por línea de comandos
     */
    public static void main( String[] args )
    {
        try
        {
        	
            // Unifica la interfaz para Mac y para Windows.
            UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName( ) );
            InterfazAlohandesApp interfaz = new InterfazAlohandesApp( );
            interfaz.setVisible( true );
        }
        catch( Exception e )
        {
            e.printStackTrace( );
        }
    }
}
