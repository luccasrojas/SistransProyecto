
package uniandes.isis2304.hotelandes.interfazApp;

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
import java.util.List;

import javax.jdo.JDODataStoreException;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import uniandes.isis2304.hotelandes.negocio.*;


/**
 * Clase principal de la interfaz
 * @author Equipo C-09
 */
@SuppressWarnings("serial")

public class InterfazHotelandesApp extends JFrame implements ActionListener
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(InterfazHotelandesApp.class.getName());
	
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
    private Hotelandes hotelandes;
    
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
    public InterfazHotelandesApp( )
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
        hotelandes = new Hotelandes (tableConfig);
        
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
			JOptionPane.showMessageDialog(null, "No se encontró un archivo de configuración de interfaz válido: " + tipo, "Hotelandes App", JOptionPane.ERROR_MESSAGE);
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
			titulo = "Hotelandes APP Default";
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
	 * 			CRUD de CadenaHotelera
	 *****************************************************************/
    public void adicionarCadenaHotelera( )
    {
    	try 
    	{
    		String nombre = JOptionPane.showInputDialog (this, "Nombre de la cadena hotelera?", "Adicionar cadena hotelera", JOptionPane.QUESTION_MESSAGE);
    		if (nombre != null)
    		{
        		VOCadenaHotelera c = hotelandes.adicionarCadenaHotelera (nombre);
        		if (c == null)
        		{
        			throw new Exception ("No se pudo crear una cadena hotelera con nombre: " + nombre);
        		}
        		String resultado = "En adicionarCadenaHotelera\n\n";
        		resultado += "Cadena Hotelera adicionada exitosamente: " + c;
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
	
    public void listarCadenaHotelera( )
    {
    	try 
    	{
			List <VOCadenaHotelera> lista = hotelandes.darVOCadenasHoteleras();

			String resultado = "En listarCadenaHotelera";
			resultado +=  "\n" + listarCadenaHoteleraStr(lista);
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }

	private String listarCadenaHoteleraStr(List<VOCadenaHotelera> lista) 
    {
    	String resp = "Las cadenas hoteleras existentes son:\n";
    	int i = 1;
        for (VOCadenaHotelera c : lista)
        {
        	resp += i++ + ". " + c.toString() + "\n";
        }
        return resp;
	}


    public void eliminarCadenaHoteleraPorId( )
    {
    	try 
    	{
    		String nombre = JOptionPane.showInputDialog (this, "Nombre de la cadena hotelera?", "Borrar la cadena hotelera por el nombre", JOptionPane.QUESTION_MESSAGE);
    		if (nombre != null)
    		{
    			long cEliminados = hotelandes.eliminarCadenaHoteleraPorId (nombre);

    			String resultado = "En eliminar CadenaHotelera\n\n";
    			resultado += cEliminados + " Cadenas Hoteleras eliminadas\n";
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
	/*
	//El ID de una cadena hotelera es su mismo nombre.
    public void buscarCadenaHoteleraPorId( )
    {
    	try 
    	{
    		String nombre = JOptionPane.showInputDialog (this, "Nombre de la cadena hotelera?", "Buscar cadena hotelera por nombre", JOptionPane.QUESTION_MESSAGE);
    		if (nombre != null)
    		{
    			VOCadenaHotelera cadenaHotelera = hotelandes.darCadenaHoteleraPorId (nombre);
    			String resultado = "En buscar Tipo Bebida por nombre\n\n";
    			if (cadenaHotelera != null)
    			{
        			resultado += "La cadena hotelera es: " + cadenaHotelera;
    			}
    			else
    			{
        			resultado += "Una cadena hotelera con nombre: " + nombre + " NO EXISTE\n";    				
    			}
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
	*/

	/* ****************************************************************
	 * 			CRUD de RolUsuario
	 *****************************************************************/
    public void adicionarRolUsuario( )
    {
    	try 
    	{
    		String rol = JOptionPane.showInputDialog (this, "Nuevo Rol de Usuario?", "Adicionar rol de usuario", JOptionPane.QUESTION_MESSAGE);
    		if (rol != null)
    		{
        		VORolUsuario c = hotelandes.adicionarRolUsuario (rol);
        		if (c == null)
        		{
        			throw new Exception ("No se pudo crear el rol de usuario: " + rol);
        		}
        		String resultado = "En adicionarRolUsuario\n\n";
        		resultado += "Rol Usuario adicionado exitosamente: " + c;
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
	
    public void listarRolUsuario( )
    {
    	try 
    	{
			List <VORolUsuario> lista = hotelandes.darVORolesUsuario();

			String resultado = "En listarRolUsuario";
			resultado +=  "\n" + listarRolUsuarioStr(lista);
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }

    private String listarRolUsuarioStr(List<VORolUsuario> lista) 
    {
    	String resp = "Los roles de usuario existentes son:\n";
    	int i = 1;
        for (VORolUsuario c : lista)
        {
        	resp += i++ + ". " + c.toString() + "\n";
        }
        return resp;
	}

    public void eliminarRolUsuarioPorId( )
    {
    	try 
    	{
    		String rol = JOptionPane.showInputDialog (this, "Rol de usuario?", "Borrar el rol de usuario por el rol", JOptionPane.QUESTION_MESSAGE);
    		if (rol != null)
    		{
    			long cEliminados = hotelandes.eliminarRolUsuarioPorId (rol);

    			String resultado = "En eliminar RolUsuario\n\n";
    			resultado += cEliminados + " Roles de Usuario eliminados\n";
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
	 * 			CRUD de TipoHabitacion
	 *****************************************************************/
    public void adicionarTipoHabitacion() {
		try 
		{
			String tipo = JOptionPane.showInputDialog (this, "Nuevo Tipo de Habitacion?", "Adicionar tipo de habitacion", JOptionPane.QUESTION_MESSAGE);
			if (tipo != null)
			{
				VOTipoHabitacion c = hotelandes.adicionarTipoHabitacion (tipo);
				if (c == null)
				{
					throw new Exception ("No se pudo crear el tipo de habitacion: " + tipo);
				}
				String resultado = "En adicionarTipoHabitacion\n\n";
				resultado += "Tipo Habitacion adicionado exitosamente: " + c;
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

	public void listarTipoHabitacion() {
		try 
		{
			List <VOTipoHabitacion> lista = hotelandes.darVOTiposHabitacion();

			String resultado = "En listarTipoHabitacion";
			resultado +=  "\n" + listarTipoHabitacionStr(lista);
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
	
	private String listarTipoHabitacionStr(List<VOTipoHabitacion> lista) 
	{
		String resp = "Los tipos de habitacion existentes son:\n";
		int i = 1;
		for (VOTipoHabitacion c : lista)
		{
			resp += i++ + ". " + c.toString() + "\n";
		}
		return resp;
	}

	public void eliminarTipoHabitacionPorId() {
		try 
		{
			String tipo = JOptionPane.showInputDialog (this, "Tipo de Habitacion?", "Borrar el tipo de habitacion por el tipo", JOptionPane.QUESTION_MESSAGE);
			if (tipo != null)
			{
				long cEliminados = hotelandes.eliminarTipoHabitacionPorId (tipo);

				String resultado = "En eliminarTipoHabitacion\n\n";
				resultado += cEliminados + " Tipos de Habitacion eliminados\n";
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
	 * 			CRUD de TipoServicio
	 *****************************************************************/

	public void adicionarTipoServicio() {
		try 
		{
			String tipo = JOptionPane.showInputDialog (this, "Nuevo Tipo de Servicio?", "Adicionar tipo de servicio", JOptionPane.QUESTION_MESSAGE);
			if (tipo != null)
			{
				VOTipoServicio c = hotelandes.adicionarTipoServicio (tipo);
				if (c == null)
				{
					throw new Exception ("No se pudo crear el tipo de servicio: " + tipo);
				}
				String resultado = "En adicionarTipoServicio\n\n";
				resultado += "Tipo Servicio adicionado exitosamente: " + c;
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
	
	public void listarTipoServicio() {
		try 
		{
			List <VOTipoServicio> lista = hotelandes.darVOTiposServicio();

			String resultado = "En listarTipoServicio";
			resultado +=  "\n" + listarTipoServicioStr(lista);
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

	private String listarTipoServicioStr(List<VOTipoServicio> lista) 
	{
		String resp = "Los tipos de servicio existentes son:\n";
		int i = 1;
		for (VOTipoServicio c : lista)
		{
			resp += i++ + ". " + c.toString() + "\n";
		}
		return resp;
	}

	public void eliminarTipoServicioPorId() {
		try 
		{
			String tipo = JOptionPane.showInputDialog (this, "Tipo de Servicio?", "Borrar el tipo de servicio por el tipo", JOptionPane.QUESTION_MESSAGE);
			if (tipo != null)
			{
				long cEliminados = hotelandes.eliminarTipoServicioPorId (tipo);

				String resultado = "En eliminarTipoServicio\n\n";
				resultado += cEliminados + " Tipos de Servicio eliminados\n";
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
	 * 			CRUD de Hotel
	 *****************************************************************/

	public void adicionarHotel() {
		try 
		{
			JTextField nombreCadena = new JTextField();
			JTextField nombreHotel = new JTextField();
			JTextField pais = new JTextField();
			JTextField ciudad = new JTextField();
			JTextField direccion = new JTextField();

			int result = JOptionPane.showConfirmDialog(this, new JComponent[] {
				new JLabel("Informacion del hotel:"),
				new JLabel("Nombre de la cadena:"), nombreCadena,
				new JLabel("Nombre del hotel:"), nombreHotel, 
				new JLabel("Pais del hotel:"), pais, 
				new JLabel("Ciudad del hotel:"), ciudad, 
				new JLabel("Direccion del hotel:"), direccion
				}, "Adicionar Hotel", JOptionPane.PLAIN_MESSAGE);
			if (result == JOptionPane.OK_OPTION && (
					nombreCadena.getText().length() == 0 ||
					nombreHotel.getText().length() == 0 ||
					pais.getText().length() == 0 ||
					ciudad.getText().length() == 0 ||
					direccion.getText().length() == 0)
			) {
				JOptionPane.showMessageDialog(this, new JLabel("Se deben completar todos los campos"), "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				String nombreCadenaVal = nombreCadena.getText();
				String nombreHotelVal = nombreHotel.getText();
				String paisVal = pais.getText();
				String ciudadVal = ciudad.getText();
				String direccionVal = direccion.getText();

				VOHotel c = hotelandes.adicionarHotel(nombreCadenaVal, nombreHotelVal, paisVal, ciudadVal, direccionVal);
				if (c == null)
				{
					throw new Exception ("No se pudo crear el hotel: " + nombreHotelVal);
				}
				String resultado = "En adicionarHotel\n\n";
				resultado += "Hotel adicionado exitosamente: " + c;
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			}
		}
		catch (Exception e) 
		{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void listarHotel() {
		try 
		{
			List <VOHotel> lista = hotelandes.darVOHoteles();

			String resultado = "En listarHotel";
			resultado +=  "\n" + listarHotelStr(lista);
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

	private String listarHotelStr(List<VOHotel> lista) 
	{
		String resp = "Los hoteles existentes son:\n";
		int i = 1;
		for (VOHotel c : lista)
		{
			resp += i++ + ". " + c.toString() + "\n";
		}
		return resp;
	}

	public void eliminarHotelPorId() {
		try 
		{
			String nombre = JOptionPane.showInputDialog (this, "Nombre del hotel?", "Borrar el hotel por el nombre", JOptionPane.QUESTION_MESSAGE);
			if (nombre != null)
			{
				hotelandes.eliminarHotelPorId (nombre);

				String resultado = "En eliminarHotelPorId\n\n";
				resultado += "Hotel eliminado exitosamente: " + nombre;
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

	public void buscarHotelPorId() {
		try 
		{
			String nombre = JOptionPane.showInputDialog (this, "Nombre del hotel?", "Buscando hotel por nombre", JOptionPane.QUESTION_MESSAGE);
			if (nombre != null)
			{
				VOHotel c = hotelandes.darHotelPorId(nombre);

				String resultado = "En buscarHotelPorNombre\n\n";
				if (c != null)
				{
					resultado += "Hotel encontrado: " + c.toString() + "\n";
				}
				else
				{
					resultado += "Hotel con nombre " + nombre + " NO EXISTE\n";
				}
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
	 * 			CRUD de Usuario
	 *****************************************************************/

	public void adicionarUsuario() {
		try 
		{
			JTextField nombre = new JTextField();
			JTextField tipoDocumento = new JTextField();
			JTextField numeroDocumento = new JTextField();
			JTextField correo = new JTextField();
			JTextField contrasena = new JTextField();
			JTextField rolUsuario = new JTextField();

			int result = JOptionPane.showConfirmDialog(null, new JComponent[] { 
				new JLabel("Informacion del hotel:"),
				new JLabel("Nombre:"), nombre, 
				new JLabel("Tipo de documento:"), tipoDocumento,
				new JLabel("Numero de documento:"), numeroDocumento,
				new JLabel("Correo:"), correo,
				new JLabel("Contrasena:"), contrasena,
				new JLabel("Rol de usuario:"), rolUsuario
				}, "Adicionar usuario", JOptionPane.PLAIN_MESSAGE);
			
			if (result == JOptionPane.OK_OPTION && (
					nombre.getText().length() == 0 ||
					tipoDocumento.getText().length() == 0 ||
					numeroDocumento.getText().length() == 0 ||
					correo.getText().length() == 0 ||
					contrasena.getText().length() == 0 ||
					rolUsuario.getText().length() == 0)
			) {
				JOptionPane.showMessageDialog(this, new JLabel("Se deben completar todos los campos"), "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				String nombreVal = nombre.getText();
				TipoDocumento tipoDocumentoVal = TipoDocumento.valueOf(tipoDocumento.getText());
				int numeroDocumentoVal = Integer.parseInt(numeroDocumento.getText());
				String correoVal = correo.getText();
				String contrasenaVal = contrasena.getText();
				String rolUsuarioVal = rolUsuario.getText();
				VOUsuario c = hotelandes.adicionarUsuario(nombreVal, tipoDocumentoVal, numeroDocumentoVal, correoVal, contrasenaVal, rolUsuarioVal);
				if (c == null)
				{
					throw new Exception ("No se pudo crear el usuario: " + tipoDocumentoVal + " " + numeroDocumentoVal);
				}
				String resultado = "En adicionarUsuario\n\n";
				resultado += "Usuario adicionado exitosamente: " + c;
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			}

		}
		catch (Exception e) 
		{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void listarUsuario() {
		try 
		{
			List<VOUsuario> lista = hotelandes.darVOUsuarios();

			String resultado = "En listarUsuario";
			resultado += "\n" + listarUsuarioStr(lista);
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);
		}
		catch (Exception e) 
		{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	private String listarUsuarioStr(List<VOUsuario> lista)
	{
		String resp = "Los usuarios existentes son:\n";
		int i = 1;
		for (VOUsuario c : lista)
		{
			resp += i++ + ". " + c.toString() + "\n";
		}
		return resp;
	}

	public void eliminarUsuarioPorId() {
		try
		{
			JTextField tipoDocumento = new JTextField();
			JTextField numeroDocumento = new JTextField();

			int result = JOptionPane.showConfirmDialog(null, new JComponent[] { 
				new JLabel("Informacion del usuario:"),
				new JLabel("Tipo de documento:"), tipoDocumento,
				new JLabel("Numero de documento:"), numeroDocumento
				}, "Eliminar usuario", JOptionPane.PLAIN_MESSAGE);
			
			if (result == JOptionPane.OK_OPTION && (
					tipoDocumento.getText().length() == 0 ||
					numeroDocumento.getText().length() == 0)) {
				JOptionPane.showMessageDialog(this, new JLabel("Se deben completar todos los campos"), "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				TipoDocumento tipoDocumentoVal = TipoDocumento.valueOf(tipoDocumento.getText());
				int numeroDocumentoVal = Integer.parseInt(numeroDocumento.getText());

				hotelandes.eliminarUsuarioPorId(tipoDocumentoVal, numeroDocumentoVal);
				String resultado = "En eliminarUsuarioPorId\n\n";
				resultado += "Usuario eliminado exitosamente: " + tipoDocumentoVal + " " + numeroDocumentoVal;
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			}

		}
		catch (Exception e) 
		{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void buscarUsuarioPorId() {
		try
		{
			JTextField tipoDocumento = new JTextField();
			JTextField numeroDocumento = new JTextField();

			int result = JOptionPane.showConfirmDialog(null, new JComponent[] { 
				new JLabel("Informacion del usuario:"),
				new JLabel("Tipo de documento:"), tipoDocumento,
				new JLabel("Numero de documento:"), numeroDocumento
				}, "Buscar usuario", JOptionPane.PLAIN_MESSAGE);
			
			if (result == JOptionPane.OK_OPTION && (
					tipoDocumento.getText().length() == 0 ||
					numeroDocumento.getText().length() == 0)) {
				JOptionPane.showMessageDialog(this, new JLabel("Se deben completar todos los campos"), "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				TipoDocumento tipoDocumentoVal = TipoDocumento.valueOf(tipoDocumento.getText());
				int numeroDocumentoVal = Integer.parseInt(numeroDocumento.getText());

				VOUsuario c = hotelandes.darUsuarioPorId(tipoDocumentoVal, numeroDocumentoVal);
				
				String resultado = "En buscarUsuarioPorId\n\n";
				if (c != null)
				{
					resultado += "Usuario encontrado: " + c;
				}
				else
				{
					resultado += "No existe el usuario: " + tipoDocumentoVal + " " + numeroDocumentoVal;
				}
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
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
	 * 			CRUD de Habitacion
	 *****************************************************************/

	public void adicionarHabitacion() {
		try
		{
			JTextField nombreHotel = new JTextField();
			JTextField numero = new JTextField();
			JTextField tipoHabitacion = new JTextField();
			JTextField costoPorNoche = new JTextField();
			JTextField descripcion = new JTextField();
			JTextField capacidadMin = new JTextField();
			JTextField capacidadMax = new JTextField();

			int result = JOptionPane.showConfirmDialog(null, new JComponent[] { 
				new JLabel("Informacion de la habitacion:"),
				new JLabel("Nombre del hotel:"), nombreHotel,
				new JLabel("Numero:"), numero,
				new JLabel("Tipo de Habitacion:"), tipoHabitacion,
				new JLabel("Costo por noche:"), costoPorNoche,
				new JLabel("Descripcion:"), descripcion,
				new JLabel("Capacidad minima:"), capacidadMin,
				new JLabel("Capacidad maxima:"), capacidadMax
				}, "Adicionar habitacion", JOptionPane.PLAIN_MESSAGE);
			
			if (result == JOptionPane.OK_OPTION && (
					nombreHotel.getText().length() == 0 ||
					numero.getText().length() == 0 ||
					tipoHabitacion.getText().length() == 0 ||
					costoPorNoche.getText().length() == 0 ||
					descripcion.getText().length() == 0 ||
					capacidadMin.getText().length() == 0 ||
					capacidadMax.getText().length() == 0)) {
				JOptionPane.showMessageDialog(this, new JLabel("Se deben completar todos los campos"), "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				String nombreHotelVal = nombreHotel.getText();
				int numeroVal = Integer.parseInt(numero.getText());
				String tipoHabitacionVal = tipoHabitacion.getText();
				double costoPorNocheVal = Double.parseDouble(costoPorNoche.getText());
				String descripcionVal = descripcion.getText();
				int capacidadMinVal = Integer.parseInt(capacidadMin.getText());
				int capacidadMaxVal = Integer.parseInt(capacidadMax.getText());

				VOHabitacion c = hotelandes.adicionarHabitacion(nombreHotelVal, numeroVal, tipoHabitacionVal, costoPorNocheVal, descripcionVal, capacidadMinVal, capacidadMaxVal);
				if (c == null)
				{
					throw new Exception("No se pudo adicionar la habitacion: " + nombreHotelVal + " - " + numeroVal);
				}
				String resultado = "En adicionarHabitacion\n\n";
				resultado += "Habitacion adicionada exitosamente: " + c;
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			}
		}
		catch (Exception e) 
		{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void listarHabitacion() {
		try
		{
			List <VOHabitacion> lista = hotelandes.darVOHabitaciones();

			String resultado = "En listarHabitacion";
			resultado +=  "\n" + listarHabitacionStr(lista);
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

	private String listarHabitacionStr(List<VOHabitacion> lista) {
		String resp = "Las habitaciones existentes son:\n";
		int i = 1;
		for (VOHabitacion c : lista)
		{
			resp += i++ + ". " + c.toString() + "\n"; 
		}
		return resp;
	}

	public void eliminarHabitacionPorId() {
		try
		{
			JTextField nombreHotel = new JTextField();
			JTextField numero = new JTextField();
			int result = JOptionPane.showConfirmDialog(null, new JComponent[] { 
				new JLabel("Informacion de la habitacion:"),
				new JLabel("Nombre del hotel:"), nombreHotel,
				new JLabel("Numero:"), numero
				}, "Eliminar habitacion", JOptionPane.PLAIN_MESSAGE);
			
			if (result == JOptionPane.OK_OPTION && (
					nombreHotel.getText().length() == 0 ||
					numero.getText().length() == 0)) {
				JOptionPane.showMessageDialog(this, new JLabel("Se deben completar todos los campos"), "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				String nombreHotelVal = nombreHotel.getText();
				int numeroVal = Integer.parseInt(numero.getText());

				hotelandes.eliminarHabitacionPorId(nombreHotelVal, numeroVal);
				String resultado = "En eliminarHabitacionPorId\n\n";
				resultado += "Habitacion eliminada exitosamente: " + nombreHotelVal + " - " + numeroVal;
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			}
		}
		catch (Exception e) 
		{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void buscarHabitacionPorId() {
		try
		{
			JTextField nombreHotel = new JTextField();
			JTextField numero = new JTextField();
			int result = JOptionPane.showConfirmDialog(null, new JComponent[] { 
				new JLabel("Informacion de la habitacion:"),
				new JLabel("Nombre del hotel:"), nombreHotel,
				new JLabel("Numero:"), numero
				}, "Buscar habitacion", JOptionPane.PLAIN_MESSAGE);
			
			if (result == JOptionPane.OK_OPTION && (
					nombreHotel.getText().length() == 0 ||
					numero.getText().length() == 0)) {
				JOptionPane.showMessageDialog(this, new JLabel("Se deben completar todos los campos"), "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				String nombreHotelVal = nombreHotel.getText();
				int numeroVal = Integer.parseInt(numero.getText());

				VOHabitacion c = hotelandes.darHabitacionPorId(nombreHotelVal, numeroVal);
				String resultado = "En buscarHabitacionPorId\n\n";
				if (c == null)
				{
					throw new Exception("No existe la habitacion: " + nombreHotelVal + " - " + numeroVal);
				}
				else
				{
					resultado += "Habitacion encontrada: " + c;
				}
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
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
	 * 			CRUD de Servicio y Producto
	 *****************************************************************/

	public void adicionarServicio() {
		try
		{
			JTextField nombreHotel = new JTextField();
			JTextField horaInicio = new JTextField();
			JTextField horaFin = new JTextField();
			JTextField tipoServicio = new JTextField();
			int result = JOptionPane.showConfirmDialog(null, new JComponent[] { 
				new JLabel("Informacion de la habitacion:"),
				new JLabel("Nombre del hotel:"), nombreHotel,
				new JLabel("Hora de Inicio [double] (opcional):"), horaInicio,
				new JLabel("Hora de Fin [double] (opcional):"), horaFin,
				new JLabel("Tipo de Servicio:"), tipoServicio
				}, "Adicionar servicio", JOptionPane.PLAIN_MESSAGE);
			
			if (result == JOptionPane.OK_OPTION && (
					nombreHotel.getText().length() == 0 ||
					tipoServicio.getText().length() == 0)) {
				JOptionPane.showMessageDialog(this, new JLabel("Se deben completar todos los campos obligatorios"), "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				String nombreHotelVal = nombreHotel.getText();  
				String horaInicioVal = horaInicio.getText();
				String horaFinVal = horaFin.getText();
				String tipoServicioVal = tipoServicio.getText();

				VOServicio c = hotelandes.adicionarServicio(nombreHotelVal, horaInicioVal, horaFinVal, tipoServicioVal);
				//TODO cambiar adicionarServicio por cada uno de los servicios dependiendo de tipoServicioVal

				if (c == null)
				{
					throw new Exception ("No se pudo adicionar el servicio");
				}
				String resultado = "En adicionarServicio\n\n";
				resultado += "Servicio adicionado exitosamente: " + c;
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			}
		}
		catch (Exception e)
		{
//				e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void listarServicio() {
		try
		{
			List <VOServicio> lista = hotelandes.darVOServicios();

			String resultado = "En listarServicio";
			resultado += "\n" + listarServicioStr(lista);
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	private String listarServicioStr(List<VOServicio> lista) {
		String resp = "Los servicios existentes son:\n";
		int i = 1;
		for (VOServicio c : lista)
		{
			resp += i++ + "." + c.toString() + "\n";
		}
		return resp;
	}

	public void eliminarServicioPorId() {
		try 
		{
			String idStr = JOptionPane.showInputDialog(this, "Id del servicio?", "Borrar el servicio por id", JOptionPane.QUESTION_MESSAGE);
			if (idStr != null)
			{
				long id = Long.valueOf(idStr);
				hotelandes.eliminarServicioPorId(id);

				String resultado = "En eliminarServicioPorId\n\n";
				resultado += "Servicio eliminado exitosamente: " + id;
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

	public void buscarServicioPorId() {
		try 
		{
			String idStr = JOptionPane.showInputDialog (this, "Id del servicio?", "Buscando el servicio por id", JOptionPane.QUESTION_MESSAGE);
			if (idStr != null)
			{
				long id = Long.valueOf(idStr);
				VOServicio c = hotelandes.darServicioPorId(id);
				String resultado = "En buscarServicioPorId\n\n";
				if (c != null)
				{
					resultado += "Servicio encontrado: " + c.toString() + "\n";
				}
				else
				{
					resultado += "Servicio con id " + id + " NO EXISTE";
				}
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

	public void adicionarProductoAServicio() {
		try 
		{
			JTextField idServicio = new JTextField();
			JTextField nombre = new JTextField();
			JTextField costo = new JTextField();
			JTextField planTodoIncluido = new JTextField();
			JTextField duracion = new JTextField();

			int result = JOptionPane.showConfirmDialog(null, new JComponent[] {
				new JLabel("Id del servicio al que pertenece:"), idServicio,
				new JLabel("Nombre del producto:"), nombre, 
				new JLabel("Costo del producto:"), costo, 
				new JLabel("¿Plan todo incluido? (si o no):"), planTodoIncluido, 
				new JLabel("Duracion (0 si no es producto de SPA):"), duracion
			}, "Adicionar producto a servicio", JOptionPane.PLAIN_MESSAGE);

			if (result == JOptionPane.OK_OPTION && (
				idServicio.getText().length() == 0 ||
				nombre.getText().length() == 0 || 
				costo.getText().length() == 0 ||
				planTodoIncluido.getText().length() == 0)) {
					JOptionPane.showMessageDialog(this, new JLabel("Se deben completar todos los campos obligatorios"), "Error", JOptionPane.ERROR_MESSAGE);
				}
			else {
				long idServicioVal = Long.valueOf(idServicio.getText());
				String nombreVal = nombre.getText();
				double costoVal = Double.parseDouble(costo.getText());
				int planTodoIncluidoVal = planTodoIncluido.getText().equals("si") ? 1 : 0;
				String duracionVal = duracion.getText();
				if(duracionVal.equals(""))
				{
					duracionVal = "0";
				}
				VOProducto c = hotelandes.adicionarProducto(idServicioVal, nombreVal, costoVal, planTodoIncluidoVal, duracionVal);

				if (c == null) {
					throw new Exception ("No se pudo adicionar el producto al servicio con id: " + idServicioVal);
				}
				String resultado = "En adicionarProductoAServicio\n\n";
				resultado += "Producto adicionado exitosamente: " + c.toString();
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			}			
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void listarProductosDeServicio() {
		try 
		{
			String idServicioStr = JOptionPane.showInputDialog(this, "Id del servicio?", "Listar los productos de un servicio", JOptionPane.QUESTION_MESSAGE);
			if (idServicioStr != null)
			{
				long idServicio = Long.valueOf(idServicioStr);
				List <VOProducto> lista = hotelandes.darVOProductosPorServicio(idServicio);
				String resultado = "En listarProductosDeServicio";
				resultado += "\n"+ listarProductosDeServicioStr(lista);
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

	private String listarProductosDeServicioStr(List<VOProducto> lista) 
	{
		String resp = "Los productos del servicio son:\n";
		int i = 1;
		for (VOProducto c : lista)
		{
			resp += i++ + ". " + c.toString() + "\n";
		}
		return resp;
	}

	/****************************************************************
	 * CRUD de PlanConsumo
	 ****************************************************************/

	public void adicionarPlanConsumo() {
		try 
		{
			JTextField nombreHotel = new JTextField();
			JTextField nombre = new JTextField();
			JTextField duracionMin = new JTextField();
			JTextField descuento = new JTextField();
			
			int result = JOptionPane.showConfirmDialog(null, new JComponent[] {
				new JLabel("Informacion del plan de consumo:"),
				new JLabel("Nombre del hotel:"), nombreHotel,
				new JLabel("Nombre del plan:"), nombre, 
				new JLabel("Duracion minima (en dias):"), duracionMin, 
				new JLabel("Descuento (en porcentaje):"), descuento
			}, "Adicionar plan de consumo", JOptionPane.PLAIN_MESSAGE);

			if (result == JOptionPane.OK_OPTION && (
					nombreHotel.getText().length() == 0 ||
					nombre.getText().length() == 0 ||
					duracionMin.getText().length() == 0 ||
					descuento.getText().length() == 0))	{
				JOptionPane.showMessageDialog(this, new JLabel("Se deben completar todos los campos"), "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				String nombreHotelVal = nombreHotel.getText();
				String nombreVal = nombre.getText();
				int duracionMinVal = Integer.parseInt(duracionMin.getText());
				double descuentoVal = Double.parseDouble(descuento.getText());
				
				VOPlanConsumo c = hotelandes.adicionarPlanConsumo(nombreHotelVal, nombreVal, duracionMinVal, descuentoVal);

				if (c == null) {
					throw new Exception ("No se pudo adicionar el plan de consumo");
				}
				String resultado = "En adicionarPlanConsumo\n\n";
				resultado += "Plan de consumo adicionado exitosamente: " + c.toString();
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			}
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void listarPlanConsumo() {
		try 
		{
			List <VOPlanConsumo> lista = hotelandes.darVOPlanesConsumo();
			String resultado = "En listarPlanConsumo";
			resultado += "\n"+ listarPlanConsumoStr(lista);
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	private String listarPlanConsumoStr(List<VOPlanConsumo> lista) {
		String resp = "Los planes de consumo son:\n";
		int i = 1;
		for (VOPlanConsumo c : lista)
		{
			resp += i++ + ". " + c.toString() + "\n";
		}
		return resp;
	}

	public void eliminarPlanConsumoPorId() {
		try 
		{
			String idStr = JOptionPane.showInputDialog(this, "Id del plan de consumo?", "Borrar el plan de consumo por id", JOptionPane.QUESTION_MESSAGE);
			if (idStr != null)
			{
				long id = Long.valueOf(idStr);
				hotelandes.eliminarPlanConsumoPorId(id);

				String resultado = "En eliminarPlanConsumoPorId\n\n";
				resultado += "Plan de Consumo eliminado exitosamente: " + id;
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

	public void buscarPlanConsumoPorId() {
		try 
		{
			String idStr = JOptionPane.showInputDialog (this, "Id del plan de consumo?", "Buscando el plan de consumo por id", JOptionPane.QUESTION_MESSAGE);
			if (idStr != null)
			{
				long id = Long.valueOf(idStr);
				VOPlanConsumo c = hotelandes.darPlanConsumoPorId(id);
				String resultado = "En buscarPlanConsumoPorId\n\n";
				if (c != null)
				{
					resultado += "Plan de consumo encontrado: " + c.toString() + "\n";
				}
				else
				{
					resultado += "Plan de consumo con id " + id + " NO EXISTE";
				}
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

	/****************************************************************
	 * CRUD de ReservaHabitacion (sin Habitacion asignada)
	 ****************************************************************/

	public void adicionarReservaHabitacion() {
		try 
		{
			JTextField fechaIn = new JTextField();
			JTextField fechaOut = new JTextField();
			JTextField numPersonas = new JTextField();
			JTextField nombreHotel = new JTextField();
			JTextField idPlanConsumo = new JTextField();

			int result = JOptionPane.showConfirmDialog(null, new JComponent[] {
					new JLabel("Informacion de la reserva:"),
					new JLabel("Fecha de inicio:"), fechaIn,
					new JLabel("Fecha de fin:"), fechaOut,
					new JLabel("Numero de personas:"), numPersonas,
					new JLabel("Nombre del hotel:"), nombreHotel,
					new JLabel("Id del plan de consumo:"), idPlanConsumo},
					"Adicionar ReservaHabitacion", JOptionPane.PLAIN_MESSAGE);
			
			if (result == JOptionPane.OK_OPTION && (
				fechaIn.getText().length() == 0 ||
				fechaOut.getText().length() == 0 ||
				numPersonas.getText().length() == 0 ||
				nombreHotel.getText().length() == 0 ||
				idPlanConsumo.getText().length() == 0)) {
				JOptionPane.showMessageDialog(this, new JLabel("Se deben completar todos los campos"), "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				String fechaInVal = fechaIn.getText();
				String fechaOutVal = fechaOut.getText();
				int numPersonasInt = Integer.parseInt(numPersonas.getText());
				String nombreHotelStr = nombreHotel.getText();
				long idPlanConsumoLong = Long.parseLong(idPlanConsumo.getText());
				
				VOReservaHabitacion c = hotelandes.adicionarReservaHabitacion(fechaInVal, fechaOutVal, numPersonasInt, nombreHotelStr, idPlanConsumoLong);
				
				String resultado = "En adicionarReservaHabitacion\n\n";
				resultado += "Reserva de habitacion adicionada exitosamente: " + c;
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			}
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void listarReservaHabitacion() {
		try 
		{
			List<VOReservaHabitacion> lista = hotelandes.darVOReservasHabitaciones();
			
			String resultado = "En listarReservaHabitacion";
			resultado += "\n" + listarReservaHabitacionStr(lista);
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	private String listarReservaHabitacionStr(List<VOReservaHabitacion> lista) {
		String resp = "Las reservas de habitacion existentes son:\n";
		int i = 1;
		for (VOReservaHabitacion c : lista)
		{
			resp += i++ + "." + c.toString() + "\n";
		}
		return resp;
	}

	public void eliminarReservaHabitacionPorId() {
		try 
		{
			String idStr = JOptionPane.showInputDialog(this, "Id de la reserva de habitacion?", "Borrar la reserva de habitacion por id", JOptionPane.QUESTION_MESSAGE);
			if (idStr != null)
			{
				long id = Long.valueOf(idStr);
				hotelandes.eliminarReservaHabitacionPorId(id);

				String resultado = "En eliminarReservaHabitacionPorId\n\n";
				resultado += "Reserva Habitacion eliminada exitosamente: " + id;
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

	public void buscarReservaHabitacionPorId() {
		try 
		{
			String idStr = JOptionPane.showInputDialog (this, "Id de la reserva de habitacion?", "Buscando la reserva de habitacion por id", JOptionPane.QUESTION_MESSAGE);
			if (idStr != null)
			{
				long id = Long.valueOf(idStr);
				VOReservaHabitacion c = hotelandes.darReservaHabitacionPorId(id);
				String resultado = "En buscarReservaHabitacionPorId\n\n";
				if (c != null)
				{
					resultado += "Reserva de Habitacion encontrada: " + c.toString() + "\n";
				}
				else
				{
					resultado += "Reserva de Habitacion con id " + id + " NO EXISTE";
				}
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

	/****************************************************************
	 * CRUD de ReservaServicio
	 ****************************************************************/

	public void adicionarReservaServicio() {
		try
		{
			JTextField idServicio = new JTextField();
			JTextField idReservaHabitacion = new JTextField();
			JTextField fecha = new JTextField();
			JTextField duracion = new JTextField();

			int result = JOptionPane.showConfirmDialog(null, new JComponent[] {
					new JLabel("Informacion de la reserva de servicio:"),
					new JLabel("Id del servicio a reservar:"), idServicio,
					new JLabel("Id de la Reserva de Habitacion:"), idReservaHabitacion,
					new JLabel("Fecha:"), fecha,
					new JLabel("Duracion:"), duracion
				}, "Adicionar Reserva Servicio", JOptionPane.PLAIN_MESSAGE);

			if (result == JOptionPane.OK_OPTION && (
				idServicio.getText().length() == 0 ||
				idReservaHabitacion.getText().length() == 0 ||
				fecha.getText().length() == 0 ||
				duracion.getText().length() == 0
			)) {
				JOptionPane.showMessageDialog(this, new JLabel("Se deben completar todos los campos"), "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				long idServicioLong = Long.valueOf(idServicio.getText());
				long idReservaHabitacionLong = Long.valueOf(idReservaHabitacion.getText());
				String fechaVal = fecha.getText();
				int duracionVal = Integer.valueOf(duracion.getText());
				hotelandes.adicionarReservaServicio(idServicioLong, idReservaHabitacionLong, fechaVal, duracionVal);
				panelDatos.actualizarInterfaz("Reserva de Servicio adicionada exitosamente");
			}
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void listarReservaServicio() {
		try
		{
			List<VOReservaServicio> lista = hotelandes.darVOReservasServicios();
			String resultado = "En listarReservaServicio";
			resultado += "\n" + listarReservaServicioStr(lista);
			resultado += "\n Operación terminada";
			panelDatos.actualizarInterfaz(resultado);
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	private String listarReservaServicioStr(List<VOReservaServicio> lista) {
		String resp = "Las reservas de servicio existentes son:\n";
		int i = 1;
		for (VOReservaServicio c : lista)
		{
			resp += i++ + "." + c.toString() + "\n";
		}
		return resp;
	}

	public void eliminarReservaServicioPorId() {
		try 
		{
			String idStr = JOptionPane.showInputDialog(this, "Id de la reserva de servicio?", "Borrar la reserva de servicio por id", JOptionPane.QUESTION_MESSAGE);
			if (idStr != null)
			{
				long id = Long.valueOf(idStr);
				hotelandes.eliminarReservaServicioPorId(id);

				String resultado = "En eliminarReservaServicioPorId\n\n";
				resultado += "Reserva de Servicio eliminado exitosamente: " + id;
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

	public void buscarReservaServicioPorId() {
		try 
		{
			String idStr = JOptionPane.showInputDialog (this, "Id de la reserva de servicio?", "Buscando la reserva de servicio por id", JOptionPane.QUESTION_MESSAGE);
			if (idStr != null)
			{
				long id = Long.valueOf(idStr);
				VOReservaServicio c = hotelandes.darReservaServicioPorId(id);
				String resultado = "En buscarReservaServicioPorId\n\n";
				if (c != null)
				{
					resultado += "Reserva de Servicio encontrada: " + c.toString() + "\n";
				}
				else
				{
					resultado += "Reserva de Servicio con id " + id + " NO EXISTE";
				}
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

	/****************************************************************
	 * Update de ReservaHabitacion (asiginar habitacion)
	 ****************************************************************/

	public void asignarHabitacion() {
		try 
		{
			JTextField idReserva = new JTextField();
			JTextField numHabitacion = new JTextField();
			int result = JOptionPane.showConfirmDialog(null, new JComponent[] {
					new JLabel("Informacion de la actualizacion"),
					new JLabel("Id de la reserva:"), idReserva,
					new JLabel("Numero de habitacion:"), numHabitacion
			}, "Asignar habitacion", JOptionPane.PLAIN_MESSAGE);

			if (result == JOptionPane.OK_OPTION && (
				idReserva.getText().length() == 0 ||
				numHabitacion.getText().length() == 0))
			{
				JOptionPane.showMessageDialog(this, new JLabel("Se deben completar todos los campos"), "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				long idReservaLong = Long.valueOf(idReserva.getText());
				int numHabitacionInt = Integer.valueOf(numHabitacion.getText());
				hotelandes.actualizarHabitacionReservaHabitacion(idReservaLong, numHabitacionInt);

				String resultado = "En asignarHabitacion\n\n";
				resultado += "Habitacion actualizada exitosamente: " + idReservaLong + " con habitacion " + numHabitacionInt;
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			}
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	/****************************************************************
	 * CRUD de CuentaServicio
	 ****************************************************************/
	 public void adicionarCuentaServicio() 
	 {
		 try
		{
			JTextField costo = new JTextField();
			JTextField concepto = new JTextField();
			JTextField fecha = new JTextField();
			JTextField idServicio = new JTextField();
			JTextField idReservaHabitacion = new JTextField();
			JTextField registradoPorTipoDocumento = new JTextField();
			JTextField registradoPorNumDocumento = new JTextField();

			int result = JOptionPane.showConfirmDialog(null, new JComponent[] { 
				new JLabel("Informacion de las fechas:"),
				new JLabel("Costo del servicio:"), costo,
				new JLabel("Concepto:"), concepto,
				new JLabel("Fecha:"), fecha,
				new JLabel("Id del servicio:"), idServicio,
				new JLabel("Id de la reserva de habitacion:"), idReservaHabitacion,
				new JLabel("Tipo de documento del usuario que registra:"), registradoPorTipoDocumento,
				new JLabel("Numero de documento del usuario que registra:"), registradoPorNumDocumento
	
				}, "Indice de ocupacion de las habitaciones de un hotel", JOptionPane.PLAIN_MESSAGE);
			
			if (result == JOptionPane.OK_OPTION && (
					costo.getText().length() == 0 ||
					concepto.getText().length() == 0 ||
					fecha.getText().length() == 0 
					|| idServicio.getText().length() == 0 ||
					idReservaHabitacion.getText().length() == 0 ||
					registradoPorTipoDocumento.getText().length() == 0 ||
					registradoPorNumDocumento.getText().length() == 0
					))
			{
				JOptionPane.showMessageDialog(this, new JLabel("Se deben completar todos los campos"), "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				String costoVal = costo.getText();
				String conceptoVal = concepto.getText();
				String fechaVal = fecha.getText();
				String idServicioVal = idServicio.getText();
				String idReservaHabitacionVal = idReservaHabitacion.getText();
				String registradoPorTipoDocumentoVal = registradoPorTipoDocumento.getText();
				String registradoPorNumDocumentoVal = registradoPorNumDocumento.getText();
				CuentaServicio c = hotelandes.adicionarCuentaServicio(costoVal, conceptoVal, fechaVal, idServicioVal, idReservaHabitacionVal, registradoPorTipoDocumentoVal, registradoPorNumDocumentoVal);
				if (c == null)
				{
					throw new Exception("No se pudo realziar la consulta ");
				}
				String resultado = "Se anadio el servicio con id: "+c.getIdServicio()+" a la cuenta servicio"+"\n\n";
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			}
		}
		catch (Exception e) 
		{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	 }

	/****************************************************************
	 * RF 11
	 ****************************************************************/

	public void registrarSalidaDeReserva() {
		try 
		{
			String idReservaStr = JOptionPane.showInputDialog (this, "Id de la reserva?", "Registrando salida", JOptionPane.QUESTION_MESSAGE);
			if (idReservaStr != null)
			{
				String valor = hotelandes.registrarSalida(idReservaStr);
				System.out.println(valor);

				String resultado = "En registrarSalida\n\n";
				resultado += "Se ha registrado la salida con éxito.\n";
				resultado += "El valor total a pagar es de: $" + valor;
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


	/********************************
	 * RFCs
	 ********************************/

	//Requerimientos de consulta
	public void darDineroRecolectadoPorServiciosEnCadaHabitacion()
	{
		try
		{
			JTextField fechaInf = new JTextField();
			JTextField fechaSup = new JTextField();

			int result = JOptionPane.showConfirmDialog(null, new JComponent[] { 
				new JLabel("Informacion de la habitacion:"),
				new JLabel("Fecha inferior:"), fechaInf,
				new JLabel("Fecha superior:"), fechaSup
	
				}, "Dar dinero recolectado por servicios en cada habitacion", JOptionPane.PLAIN_MESSAGE);
			
			if (result == JOptionPane.OK_OPTION && (
					fechaInf.getText().length() == 0 ||
					fechaSup.getText().length() == 0 )) {
				JOptionPane.showMessageDialog(this, new JLabel("Se deben completar todos los campos"), "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				String fechaInfVal = fechaInf.getText();
				String fechaSupVal = fechaSup.getText();
				List<String[]> c = hotelandes.darDineroRecolectadoPorServiciosEnCadaHabitacion(fechaInfVal,fechaSupVal);
				if (c == null)
				{
					throw new Exception("No se pudo realziar la consulta ");
				}
				String resultado = " El resultado de la consutla es:\n\n";
				for(String[] elemento:c)
				{
					resultado +="Nombre hotel: "+elemento[0]+" Numero de habitacion: "+elemento[1]+" Dinero recolectado: "+elemento[2]+"\n";
				}
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			}
		}
		catch (Exception e) 
		{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	public void darLosServiciosMasPopulares()
	{
		try
		{
			JTextField fechaInf = new JTextField();
			JTextField fechaSup = new JTextField();

			int result = JOptionPane.showConfirmDialog(null, new JComponent[] { 
				new JLabel("Informacion de las fechas:"),
				new JLabel("Fecha inferior:"), fechaInf,
				new JLabel("Fecha superior:"), fechaSup
	
				}, "Dar los servicios mas populares", JOptionPane.PLAIN_MESSAGE);
			
			if (result == JOptionPane.OK_OPTION && (
					fechaInf.getText().length() == 0 ||
					fechaSup.getText().length() == 0 )) {
				JOptionPane.showMessageDialog(this, new JLabel("Se deben completar todos los campos"), "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				String fechaInfVal = fechaInf.getText();
				String fechaSupVal = fechaSup.getText();
				List<String[]> c = hotelandes.darLosServiciosMasPopulares(fechaInfVal,fechaSupVal);
				if (c == null)
				{
					throw new Exception("No se pudo realziar la consulta ");
				}
				String resultado = " Los servicios mas populares en orden son:\n\n";
				for(String[] elemento:c)
				{
					resultado +="Tipo servicio:  "+elemento[0]+"\n";
				}
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			}
		}
		catch (Exception e) 
		{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	public void darIndiceOcupacionHabitacionPorFecha()
	{
		try
		{
			JTextField nombreHotel = new JTextField();
			JTextField fechaInf = new JTextField();
			JTextField fechaSup = new JTextField();

			int result = JOptionPane.showConfirmDialog(null, new JComponent[] { 
				new JLabel("Informacion de las fechas:"),
				new JLabel("Nombre hotel:"), nombreHotel,
				new JLabel("Fecha inferior:"), fechaInf,
				new JLabel("Fecha superior:"), fechaSup
	
				}, "Indice de ocupacion de las habitaciones de un hotel", JOptionPane.PLAIN_MESSAGE);
			
			if (result == JOptionPane.OK_OPTION && (
					nombreHotel.getText().length() == 0 ||
					fechaInf.getText().length() == 0 ||
					fechaSup.getText().length() == 0 )) {
				JOptionPane.showMessageDialog(this, new JLabel("Se deben completar todos los campos"), "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				String nombreHotelVal = nombreHotel.getText();
				String fechaInfVal = fechaInf.getText();
				String fechaSupVal = fechaSup.getText();
				List<String[]> c = hotelandes.darIndiceOcupacionHabitacionPorFecha(nombreHotelVal,fechaInfVal,fechaSupVal);
				if (c == null)
				{
					throw new Exception("No se pudo realziar la consulta ");
				}
				String resultado = "El indice de ocupacion de las habitaciones de: "+nombreHotel.getText()+" es el siguiente:"+"\n\n";
				for(String[] elemento:c)
				{
					resultado +="Nombre hotel :  "+elemento[0]+ " Numero habitacion: "+elemento[1]+" Indice de ocupacion: "+elemento[2]+"\n";
				}
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			}
		}
		catch (Exception e) 
		{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	public void darServiciosPorCaracteristica()
	{
		try
		{
			JTextField condicion = new JTextField();


			int result = JOptionPane.showConfirmDialog(null, new JComponent[] { 
				new JLabel("Informacion de los servicios segun una condicion:"),
				new JLabel("Condicion:"), condicion
				}, "Dar servicios por caracteristica", JOptionPane.PLAIN_MESSAGE);
			
			if (result == JOptionPane.OK_OPTION && (
					condicion.getText().length() == 0  )) {
				JOptionPane.showMessageDialog(this, new JLabel("Se deben completar todos los campos"), "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				String condicionVal = condicion.getText();
				List<String[]> c = hotelandes.darServiciosPorCaracteristica(condicionVal);
				if (c == null)
				{
					throw new Exception("No se pudo realizar la consulta ");
				}
				String resultado = " Los servicios con la caracteristica:\n\n";
				for(String[] elemento:c)
				{
					resultado +="Nombre del hotel: "+elemento[0]+ " Id del servicio: "+elemento[1]+" Hora de inicio del servicio: "+elemento[2]+" Hora de fin del servicio: " +elemento[3]+" Tipo del servicio: "+elemento[4]+" Id cuenta: "+elemento[5]+" Costo: "+ elemento[6]+" Concepto: "+elemento[7]+" Fecha: "+elemento[8]+" Id del servico: "+elemento[9]+" Id reserva habitacion: "+elemento[10]+" Registrado por tipo de documento: "+ elemento[11]+" Registrado por numero de documento: "+elemento[12]+"\n";
				}
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			}
		}
		catch (Exception e) 
		{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	public void darConsumoPorUsuario()
	{
		try
		{
			JTextField tipoDoc = new JTextField();
			JTextField numDoc = new JTextField();
			JTextField fechaInf = new JTextField();
			JTextField fechaSup = new JTextField();

			int result = JOptionPane.showConfirmDialog(null, new JComponent[] { 
				new JLabel("Informacion de las fechas:"),
				new JLabel("Tipo de documento:"), tipoDoc,
				new JLabel("Numero de documento:"), numDoc,
				new JLabel("Fecha inferior:"), fechaInf,
				new JLabel("Fecha superior:"), fechaSup
	
				}, "Consumo por un usuario ", JOptionPane.PLAIN_MESSAGE);
			
			if (result == JOptionPane.OK_OPTION && (
					tipoDoc.getText().length() == 0 ||
					numDoc.getText().length() == 0 ||
					fechaInf.getText().length() == 0 ||
					fechaSup.getText().length() == 0 )) {
				JOptionPane.showMessageDialog(this, new JLabel("Se deben completar todos los campos"), "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				String tipoDocVa = tipoDoc.getText();
				String numDocVa = numDoc.getText();
				String fechaInfVal = fechaInf.getText();
				String fechaSupVal = fechaSup.getText();
				TipoDocumento tipoDocVal= TipoDocumento.valueOf(tipoDocVa);
				int numDocVal = Integer.parseInt(numDocVa);
				List<String[]> c = hotelandes.darConsumoPorUsuario(tipoDocVal,numDocVal,fechaInfVal,fechaSupVal);
				if (c == null)
				{
					throw new Exception("No se pudo realziar la consulta ");
				}
				String resultado = "El consumo del usuario:  "+tipoDocVa+" : "+numDocVa+" es el siguiente:"+"\n\n";
				for(String[] elemento:c)
				{
					resultado +="El id del servicio es:  "+elemento[0]+ " Fecha: "+elemento[1]+" Concepto: "+elemento[2]+" Costo: "+elemento[3]+"\n";
				}
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
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
	 * 			Métodos administrativos
	 *****************************************************************/
	/**
	 * Muestra el log de Hotelandes
	 */
	public void mostrarLogHotelandes ()
	{
		mostrarArchivo ("hotelandes.log");
	}
	
	/**
	 * Muestra el log de datanucleus
	 */
	public void mostrarLogDatanuecleus ()
	{
		mostrarArchivo ("datanucleus.log");
	}
	
	/**
	 * Limpia el contenido del log de hotelandes
	 * Muestra en el panel de datos la traza de la ejecución
	 */
	public void limpiarLogHotelandes ()
	{
		// Ejecución de la operación y recolección de los resultados
		boolean resp = limpiarArchivo ("hotelandes.log");

		// Generación de la cadena de caracteres con la traza de la ejecución de la demo
		String resultado = "\n\n************ Limpiando el log de hotelandes ************ \n";
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
	 * Limpia todas las tuplas de todas las tablas de la base de datos de hotelandes
	 * Muestra en el panel de datos el número de tuplas eliminadas de cada tabla
	 */
	public void limpiarBD ()
	{
		try  
		{
    		// Ejecución de la demo y recolección de los resultados
			long eliminados [] = hotelandes.limpiarHotelandes();
			
			// Generación de la cadena de caracteres con la traza de la ejecución de la demo
			String resultado = "\n\n************ Limpiando la base de datos ************ \n";
			resultado += eliminados [0] + " Gustan eliminados\n";
			resultado += eliminados [1] + " Sirven eliminados\n";
			resultado += eliminados [2] + " Visitan eliminados\n";
			resultado += eliminados [3] + " Bebidas eliminadas\n";
			resultado += eliminados [4] + " Tipos de bebida eliminados\n";
			resultado += eliminados [5] + " Bebedores eliminados\n";
			resultado += eliminados [6] + " Bares eliminados\n";
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
	 * Muestra el modelo conceptual de Hotelandes
	 */
	public void mostrarModeloConceptual ()
	{
		mostrarArchivo ("data/ModeloConceptualHotelandes.pdf");
	}
	
	/**
	 * Muestra el esquema de la base de datos de Hotelandes
	 */
	public void mostrarEsquemaBD ()
	{
		mostrarArchivo ("data/EsquemaBDHotelandes.pdf");
	}
	
	/**
	 * Muestra el script de creación de la base de datos
	 */
	public void mostrarScriptBD ()
	{
		mostrarArchivo ("data/EsquemaHotelandes.sql");
	}

	
	/**
	 * Muestra la documentación Javadoc del proyectp
	 */
	public void mostrarJavadoc ()
	{
		mostrarArchivo ("doc/index.html");
	}
	
	/**
     * Muestra la información acerca del desarrollo de esta apicación
     */
    public void acercaDe ()
    {
		String resultado = "\n\n ************************************\n\n";
		resultado += " * Universidad	de	los	Andes	(Bogotá	- Colombia)\n";
		resultado += " * Departamento	de	Ingeniería	de	Sistemas	y	Computación\n";
		resultado += " * Licenciado	bajo	el	esquema	Academic Free License versión 2.1\n";
		resultado += " * \n";		
		resultado += " * Curso: isis2304 - Sistemas Transaccionales\n";
		resultado += " * Proyecto: Hotelandes\n";
		resultado += " * @version 1.0\n";
		resultado += " * @author Equipo C-09\n";
		resultado += " * Abril de 2022\n";
		resultado += "\n ************************************\n\n";

		panelDatos.actualizarInterfaz(resultado);		
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
		resultado += "\n\nRevise datanucleus.log y hotelandes.log para más detalles";
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
			Method req = InterfazHotelandesApp.class.getMethod ( evento );			
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
            InterfazHotelandesApp interfaz = new InterfazHotelandesApp( );
            interfaz.setVisible( true );
        }
        catch( Exception e )
        {
            e.printStackTrace( );
        }
    }
}
