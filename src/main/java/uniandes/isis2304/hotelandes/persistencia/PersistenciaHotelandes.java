package uniandes.isis2304.hotelandes.persistencia;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.jdo.JDODataStoreException;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import org.apache.log4j.Logger;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import uniandes.isis2304.hotelandes.negocio.*;

/**
 * Clase para el manejador de persistencia del proyecto Hotelandes
 */
public class PersistenciaHotelandes {

	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecuci�n
	 */
	private static Logger log = Logger.getLogger(PersistenciaHotelandes.class.getName());
	
	/**
	 * Cadena para indicar el tipo de sentencias que se va a utilizar en una consulta
	 */
	public final static String SQL = "javax.jdo.query.SQL";

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * Atributo privado que es el �nico objeto de la clase - Patr�n SINGLETON
	 */
	private static PersistenciaHotelandes instance;
	
	/**
	 * F�brica de Manejadores de persistencia, para el manejo correcto de las transacciones
	 */
	private PersistenceManagerFactory pmf;
	
	/**
	 * Arreglo de cadenas con los nombres de las tablas de la base de datos, en su orden:
	 * Secuenciador, ...
	 */
	private List <String> tablas;
	
	/**
	 * Atributo para el acceso a las sentencias SQL propias a PersistenciaHotelandes
	 */
	private SQLUtil sqlUtil;

	private SQLReservaServicio sqlReservaServicio;
	private SQLProductoPedido sqlProductoPedido;
	private SQLPedido sqlPedido;
	private SQLCuentaServicio sqlCuentaServicio;
	private SQLHuespedReserva sqlHuespedReserva;
	private SQLReservaHabitacion sqlReservaHabitacion;
	private SQLPlanConsumo sqlPlanConsumo;
	private SQLProducto sqlProducto;
	private SQLSpaa sqlSpaa;
	private SQLSalonReunion sqlSalonReunion;
	private SQLSalonConferencia sqlSalonConferencia;
	private SQLPrestamoUtensilio sqlPrestamoUtensilio;
	private SQLEstablecimiento sqlEstablecimiento;
	private SQLInternet sqlInternet;
	private SQLGimnasio sqlGimnasio;
	private SQLPiscina sqlPiscina;
	private SQLHabitacion sqlHabitacion;
	private SQLServicio sqlServicio;
	private SQLUsuario sqlUsuario;
	private SQLHotel sqlHotel;
	private SQLTipoServicio sqlTipoServicio;
	private SQLTipoHabitacion sqlTipoHabitacion;
	private SQLRolUsuario sqlRolUsuario;
	private SQLCadenaHotelera sqlCadenaHotelera;
		
	/* ****************************************************************
	 * 			M�todos del MANEJADOR DE PERSISTENCIA
	 *****************************************************************/

	/**
	 * Constructor privado con valores por defecto - Patr�n SINGLETON
	 */
	private PersistenciaHotelandes ()
	{
		pmf = JDOHelper.getPersistenceManagerFactory("Hotelandes");		
		crearClasesSQL ();
		
		tablas = new LinkedList<String>();
		tablas.add("Hotelandes_sequence");
		tablas.add("CADENA_HOTELERA");
		tablas.add("ROL_USUARIO");
		tablas.add("TIPO_HABITACION");
		tablas.add("TIPO_SERVICIO");
		tablas.add("HOTEL");
		tablas.add("USUARIO");
		tablas.add("HABITACION");
		tablas.add("SERVICIO");
		tablas.add("PISCINA");
		tablas.add("GIMNASIO");
		tablas.add("INTERNET");
		tablas.add("ESTABLECIMIENTO");
		tablas.add("PRESTAMO_UTENSILIO");
		tablas.add("SALON_CONFERENCIA");
		tablas.add("SALON_REUNION");
		tablas.add("SPAA");
		tablas.add("PRODUCTO");
		tablas.add("PLAN_CONSUMO");
		tablas.add("RESERVA_HABITACION");
		tablas.add("HUESPED_RESERVA");
		tablas.add("CUENTA_SERVICIO");
		tablas.add("PEDIDO");
		tablas.add("PRODUCTO_PEDIDO");
		tablas.add("RESERVA_SERVICIO");

		sqlUtil = new SQLUtil(this);
}

	/**
	 * Constructor privado, que recibe los nombres de las tablas en un objeto Json - Patr�n SINGLETON
	 * @param tableConfig - Objeto Json que contiene los nombres de las tablas y de la unidad de persistencia a manejar
	 */
	private PersistenciaHotelandes (JsonObject tableConfig)
	{
		crearClasesSQL ();
		tablas = leerNombresTablas (tableConfig);
		
		String unidadPersistencia = tableConfig.get ("unidadPersistencia").getAsString ();
		log.trace ("Accediendo unidad de persistencia: " + unidadPersistencia);
		pmf = JDOHelper.getPersistenceManagerFactory (unidadPersistencia);
	}

	/**
	 * @return Retorna el �nico objeto PersistenciaHotelandes existente - Patr�n SINGLETON
	 */
	public static PersistenciaHotelandes getInstance ()
	{
		if (instance == null)
		{
			instance = new PersistenciaHotelandes ();
		}
		return instance;
	}
	
	/**
	 * Constructor que toma los nombres de las tablas de la base de datos del objeto tableConfig
	 * @param tableConfig - El objeto JSON con los nombres de las tablas
	 * @return Retorna el �nico objeto PersistenciaHotelandes existente - Patr�n SINGLETON
	 */
	public static PersistenciaHotelandes getInstance (JsonObject tableConfig)
	{
		if (instance == null)
		{
			instance = new PersistenciaHotelandes (tableConfig);
		}
		return instance;
	}

	/**
	 * Cierra la conexi�n con la base de datos
	 */
	public void cerrarUnidadPersistencia ()
	{
		pmf.close ();
		instance = null;
	}
	
	/**
	 * Genera una lista con los nombres de las tablas de la base de datos
	 * @param tableConfig - El objeto Json con los nombres de las tablas
	 * @return La lista con los nombres del secuenciador y de las tablas
	 */
	private List <String> leerNombresTablas (JsonObject tableConfig)
	{
		JsonArray nombres = tableConfig.getAsJsonArray("tablas") ;

		List <String> resp = new LinkedList <String> ();
		for (JsonElement nom : nombres)
		{
			resp.add (nom.getAsString ());
		}
		
		return resp;
	}
	
	/**
	 * Crea los atributos de clases de apoyo SQL
	 */
	private void crearClasesSQL ()
	{
		sqlCadenaHotelera = new SQLCadenaHotelera(this);
		sqlRolUsuario = new SQLRolUsuario(this);
		sqlTipoHabitacion = new SQLTipoHabitacion(this);
		sqlTipoServicio = new SQLTipoServicio(this);
		sqlHotel = new SQLHotel(this);
		sqlUsuario = new SQLUsuario(this);
		sqlHabitacion =new SQLHabitacion(this);
		sqlServicio = new SQLServicio(this);
		sqlPiscina = new SQLPiscina(this);
		sqlGimnasio =new SQLGimnasio(this);
		sqlInternet = new SQLInternet(this);
		sqlEstablecimiento = new SQLEstablecimiento(this);
		sqlPrestamoUtensilio = new SQLPrestamoUtensilio(this);
		sqlSalonConferencia = new SQLSalonConferencia(this);
		sqlSalonReunion = new SQLSalonReunion(this);
		sqlSpaa = new SQLSpaa(this);
		sqlProducto = new SQLProducto(this);
		sqlPlanConsumo = new SQLPlanConsumo(this);
		sqlReservaHabitacion = new SQLReservaHabitacion(this);
		sqlHuespedReserva = new SQLHuespedReserva(this);
		sqlCuentaServicio = new SQLCuentaServicio(this);
		sqlPedido = new SQLPedido(this);
		sqlProductoPedido = new SQLProductoPedido(this);
		sqlReservaServicio = new SQLReservaServicio(this);
		sqlUtil = new SQLUtil(this);
		
	}

	public String darSeqHotelAndes() 
	{
		return tablas.get(0);
	}
	public String darTablaCadenaHotelera() 
	{
		return tablas.get(1);
	} 
	public String darTablaRolUsuario()
	{
		return tablas.get(2);
	}
	public String darTablaTipoHabitacion() 
	{
		return tablas.get(3);
	}
	public String darTablaTipoServicio() 
	{
		return tablas.get(4);
	}
	public String darTablaHotel() 
	{
		return tablas.get(5);
	}
	public String darTablaUsuario() 
	{
		return tablas.get(6);
	}
	public String darTablaHabitacion() 
	{
		return tablas.get(7);
	}
	public String darTablaServicio()
	{
		return tablas.get(8);
	}
	public String darTablaPiscina()
	{
		return tablas.get(9);
	}
	public String darTablaGimnasio()
	{
		return tablas.get(10);
	}
	public String darTablaInternet()
	{ 
		return tablas.get(11);
	}
	public String darTablaEstablecimiento()
	{
		return tablas.get(12);
	}
	public String darTablaPrestamoUtensilio()
	{
		return tablas.get(13);
	}
	public String darTablaSalonConferencia()
	{
		return tablas.get(14);
	}
	public String darTablaSalonReunion()
	{
		return tablas.get(15);
	}
	public String darTablaSpaa()
	{
		return tablas.get(16);
	}
	public String darTablaProducto()
	{
		return tablas.get(17);
	}
	public String darTablaPlanConsumo()
	{
		return tablas.get(18);
	}
	public String darTablaReservaHabitacion()
	{
		return tablas.get(19);
	}
	public String darTablaHuespedReserva()
	{
		return tablas.get(20);
	}
	public String darTablaCuentaServicio()
	{
		return tablas.get(21);
	}
	public String darTablaPedido()
	{
		return tablas.get(22);
	}
	public String darTablaProductoPedido()
	{
		return tablas.get(23);
	}
	public String darTablaReservaServicio()
	{
		return tablas.get(24);
	}
	private long nextval()
	{
        long resp = sqlUtil.nextval(pmf.getPersistenceManager());
        log.trace("Generando secuencia: " + resp);
        return resp+100;
    }
	/**
	 * Extrae el mensaje de la exception JDODataStoreException embebido en la Exception e, que da el detalle específico del problema encontrado
	 * @param e - La excepción que ocurrio
	 * @return El mensaje de la excepción JDO
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

	public long [] limpiarHotelandes ()
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long [] resp = sqlUtil.limpiarHotelandes (pm);
            tx.commit ();
            log.info ("Borrada la base de datos");
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return new long[] {-1, -1, -1, -1, -1, -1, -1};
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	//Metodos para adicionar
	public CadenaHotelera adicionarCadenaHotelera(String nombre)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long tuplasInsertadas = sqlCadenaHotelera.adicionarCadenaHotelera(pm,nombre);
			tx.commit ();
			
			log.trace ("Inserción de cadena hotelera: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");
			
			return new CadenaHotelera(nombre);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	public RolUsuario adicionarRolUsuario(String nombre)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long tuplasInsertadas = sqlRolUsuario.adicionarRolUsuario(pm,nombre);
			tx.commit ();
			
			log.trace ("Inserción de rol usuario: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");
			
			return new RolUsuario(nombre);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	public TipoHabitacion adicionarTipoHabitacion(String nombre)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long tuplasInsertadas = sqlTipoHabitacion.adicionarTipoHabitacion(pm,nombre);
			tx.commit ();
			
			log.trace ("Inserción de tipo habitacion: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");
			
			return new TipoHabitacion(nombre);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	public TipoServicio adicionarTipoServicio(String nombre)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long tuplasInsertadas = sqlTipoServicio.adicionarTipoServicio(pm,nombre);
			tx.commit ();
			
			log.trace ("Inserción de tipo servicio: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");
			
			return new TipoServicio(nombre);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	public Hotel adicionarHotel(String nombreCadena, String nombreHotel,String pais,String ciudad,String direccion)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long tuplasInsertadas = sqlHotel.adicionarHotel(pm,nombreCadena,nombreHotel,pais,ciudad,direccion);
			tx.commit ();
			
			log.trace ("Inserción de hotel: " + nombreHotel + ": " + tuplasInsertadas + " tuplas insertadas");
			
			return new Hotel(nombreCadena,nombreHotel,pais,ciudad,direccion);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	public Usuario adicionarUsuario(String nombre,TipoDocumento tipoDocumento,int numeroDocumento,String correo,String contrasena,String rolUsuario)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long tuplasInsertadas = sqlUsuario.adicionarUsuario(pm,nombre,tipoDocumento,numeroDocumento,correo,contrasena,rolUsuario);
			tx.commit ();
			
			log.trace ("Inserción de usuario: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");
			
			return new Usuario(nombre,tipoDocumento,numeroDocumento,correo,contrasena,rolUsuario);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	public Habitacion adicionarHabitacion(String nombreHotel,int numero,String tipoHabitacion,double costoPorNoche,String descripcion,int capacidadMin,int capacidadMax)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long tuplasInsertadas = sqlHabitacion.adicionarHabitacion(pm,nombreHotel,numero,tipoHabitacion,costoPorNoche,descripcion,capacidadMin,capacidadMax);
			tx.commit ();
			
			log.trace ("Inserción de habitacion: " + nombreHotel + ": " + tuplasInsertadas + " tuplas insertadas");
			
			return new Habitacion(nombreHotel,numero,tipoHabitacion,costoPorNoche,descripcion,capacidadMin,capacidadMax);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	public Servicio adicionarServicio(String nombreHotel,String horaInicio, String horaFin,String tipoServicio)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long id = nextval();
			long tuplasInsertadas = sqlServicio.adicionarServicio(pm,nombreHotel,id,horaInicio,horaFin,tipoServicio);
			tx.commit ();
			
			log.trace ("Inserción de servicio: " + nombreHotel + ": " + tuplasInsertadas + " tuplas insertadas");
			
			return new Servicio(nombreHotel,id,horaInicio.equals("") ? 0.0 : Double.parseDouble(horaInicio),horaFin.equals("") ? 0.0 : Double.parseDouble(horaFin),tipoServicio);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	public Piscina adicionarPiscina(String nombreHotel,String horaInicio,String horaFin,int capacidad,double profundidad,double costo)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			Servicio servicio = adicionarServicio(nombreHotel,horaInicio,horaFin,"PISCINA");
			tx.begin();
			long tuplasInsertadas = sqlPiscina.adicionarPiscina(pm,servicio.getIdServicio(),capacidad,profundidad,costo);
			tx.commit ();
			
			log.trace ("Inserción de piscina: " + servicio.getIdServicio() + ": " + tuplasInsertadas + " tuplas insertadas");
			
			return new Piscina(servicio.getIdServicio(),capacidad,profundidad,costo);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	public Gimnasio adicionarGimnasio(String nombreHotel,String horaInicio,String horaFin,int capacidad,String descripcion,double costo)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			Servicio servicio = adicionarServicio(nombreHotel,horaInicio,horaFin,"GIMNASIO");
			tx.begin();
			long tuplasInsertadas = sqlGimnasio.adicionarGimnasio(pm,servicio.getIdServicio(),capacidad,descripcion,costo);
			tx.commit ();
			
			log.trace ("Inserción de gimnasio: " + servicio.getIdServicio() + ": " + tuplasInsertadas + " tuplas insertadas");
			
			return new Gimnasio(servicio.getIdServicio(),capacidad,descripcion,costo);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	public Internet adicionarInternet(String nombreHotel,String horaInicio,String horaFin,double capacidad,double costoPorDia)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			Servicio servicio = adicionarServicio(nombreHotel,horaInicio,horaFin,"INTERNET");
			tx.begin();
			long tuplasInsertadas = sqlInternet.adicionarInternet(pm,servicio.getIdServicio(),capacidad,costoPorDia);
			tx.commit ();
			
			log.trace ("Inserción de internet: " + servicio.getIdServicio() + ": " + tuplasInsertadas + " tuplas insertadas");
			
			return new Internet(servicio.getIdServicio(),capacidad,costoPorDia);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	public Establecimiento adicionarEstablecimiento(String nombreHotel,String horaInicio,String horaFin,String nombre,int capacidad,String estiloTipo,double descuentoPlan)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			Servicio servicio = adicionarServicio(nombreHotel,horaInicio,horaFin,"ESTABLECIMIENTO");
			tx.begin();
			long tuplasInsertadas = sqlEstablecimiento.adicionarEstablecimiento(pm,servicio.getIdServicio(),nombre,capacidad,estiloTipo,descuentoPlan);
			tx.commit ();
			
			log.trace ("Inserción de establecimiento: " + servicio.getIdServicio() + ": " + tuplasInsertadas + " tuplas insertadas");
			
			return new Establecimiento(servicio.getIdServicio(),nombre,capacidad,estiloTipo,descuentoPlan);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	public PrestamoUtensilio adicionarPrestamoUtensilio(String nombreHotel,String horaInicio,String horaFin,String nombreUtensilio)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			Servicio servicio = adicionarServicio(nombreHotel,horaInicio,horaFin,"PRESTAMO");
			tx.begin();
			long tuplasInsertadas = sqlPrestamoUtensilio.adicionarPrestamoUtensilio(pm,servicio.getIdServicio(),nombreUtensilio);
			tx.commit ();
			
			log.trace ("Inserción de prestamo de utensilio: " + servicio.getIdServicio() + ": " + tuplasInsertadas + " tuplas insertadas");
			
			return new PrestamoUtensilio(servicio.getIdServicio(),nombreUtensilio);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	public SalonConferencia adicionarSalonConferencia(String nombreHotel,String horaInicio,String horaFin,int numero,int capacidad,double costoPorHora)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			Servicio servicio = adicionarServicio(nombreHotel,horaInicio,horaFin,"SALON_CONFERENCIA");
			tx.begin();
			long tuplasInsertadas = sqlSalonConferencia.adicionarSalonConferencia(pm,servicio.getIdServicio(),numero,capacidad,costoPorHora);
			tx.commit ();
			
			log.trace ("Inserción de salon de conferencia: " + servicio.getIdServicio() + ": " + tuplasInsertadas + " tuplas insertadas");
			
			return new SalonConferencia(servicio.getIdServicio(),numero,capacidad,costoPorHora);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	public SalonReunion adicionarSalonReunion(String nombreHotel,String horaInicio,String horaFin,int numero,double costoPorHora,double costoAdicional)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			Servicio servicio = adicionarServicio(nombreHotel,horaInicio,horaFin,"SALON_REUNION");
			tx.begin();
			long tuplasInsertadas = sqlSalonReunion.adicionarSalonReunion(pm,servicio.getIdServicio(),numero,costoPorHora,costoAdicional);
			tx.commit ();
			
			log.trace ("Inserción de salon de reunion: " + servicio.getIdServicio() + ": " + tuplasInsertadas + " tuplas insertadas");
			
			return new SalonReunion(servicio.getIdServicio(),numero,costoPorHora,costoAdicional);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	public Spaa adicionarSpaa(String nombreHotel,String horaInicio,String horaFin,int capacidad)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			Servicio servicio = adicionarServicio(nombreHotel,horaInicio,horaFin,"SPAA");
			tx.begin();
			long tuplasInsertadas = sqlSpaa.adicionarSpaa(pm,servicio.getIdServicio(),capacidad);
			tx.commit ();
			
			log.trace ("Inserción de spaa: " + servicio.getIdServicio() + ": " + tuplasInsertadas + " tuplas insertadas");
			
			return new Spaa(servicio.getIdServicio(),capacidad);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	public Producto adicionarProducto(long idServicio,String nombre,double costo,int planTodoIncluido,String duracion)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			long id = nextval();
			tx.begin();

			long tuplasInsertadas = sqlProducto.adicionarProducto(pm,idServicio,id,nombre,costo,planTodoIncluido,duracion);
			tx.commit ();
			
			log.trace ("Inserción de producto: " + idServicio + ": " + tuplasInsertadas + " tuplas insertadas");
			return new Producto(idServicio,id,nombre,costo,planTodoIncluido,Integer.parseInt(duracion));
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	public PlanConsumo adicionarPlanConsumo(String nombreHotel,String nombre,int duracionMin,double descuento)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			long id = nextval ();
			tx.begin();
			long tuplasInsertadas = sqlPlanConsumo.adicionarPlanConsumo(pm,nombreHotel,id,nombre,duracionMin,descuento);
			tx.commit ();
			
			log.trace ("Inserción de plan de consumo: " + nombreHotel + ": " + tuplasInsertadas + " tuplas insertadas");
			
			return new PlanConsumo(nombreHotel,id,nombre,duracionMin,descuento);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	
	public ReservaHabitacion adicionarReservaHabitacion(String fechaIn,String fechaOut,int numPersonas,String nombreHotel,long idPlanConsumo,Optional<Long> idConvencion)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			long id = nextval ();
			tx.begin();
			long tuplasInsertadas = sqlReservaHabitacion.adicionarReservaHabitacion(pm,id,fechaIn,fechaOut,numPersonas,nombreHotel,idPlanConsumo,0,idConvencion);
			tx.commit ();
			
			log.trace ("Inserción de reserva de habitacion: " + id + ": " + tuplasInsertadas + " tuplas insertadas");
			
			return new ReservaHabitacion(id,Timestamp.valueOf(fechaIn),Timestamp.valueOf(fechaOut),numPersonas,0,nombreHotel,0,idPlanConsumo,0,idConvencion);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	public long actualizarHabitacionReservaHabitacion(long idReservaHabitacion, int numeroHabitacion) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long tuplasInsertadas = sqlReservaHabitacion.actualizarHabitacion(pm,idReservaHabitacion,numeroHabitacion);
			tx.commit ();
			
			return tuplasInsertadas;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	public HuespedReserva adicionarHuespedReserva(TipoDocumento tipoDocumento,int numeroDocumento, int acompanante)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			long id = nextval ();
			tx.begin();
			long tuplasInsertadas = sqlHuespedReserva.adicionarHuespedReserva(pm,id,tipoDocumento,numeroDocumento,acompanante);
			tx.commit ();
			
			log.trace ("Inserción de huesped reserva: " + id + ": " + tuplasInsertadas + " tuplas insertadas");
			
			return new HuespedReserva(id,tipoDocumento,numeroDocumento,acompanante);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	
	public CuentaServicio adicionarCuentaServicio(String costo,String concepto, String fecha,String idServicio,String idReservaHabitacion,String registradoPorTipoDocumento,String registradoPorNumeroDocumento)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		//try
		//{
			System.out.println("1");
			long id = nextval ();
			System.out.println("2");
			tx.begin();
			System.out.println("3");
			long tuplasInsertadas = sqlCuentaServicio.adicionarCuentaServicio(pm,String.valueOf(id),costo,concepto,fecha,idServicio,idReservaHabitacion,registradoPorTipoDocumento,registradoPorNumeroDocumento);
			System.out.println("4");
			tx.commit ();
			System.out.println("5");
			log.trace ("Inserción de cuenta de servicio: " + id + ": " + tuplasInsertadas + " tuplas insertadas");
			System.out.println("aca");
			return new CuentaServicio(id,Double.parseDouble(costo),concepto,Timestamp.valueOf(fecha),Long.parseLong(idServicio),Long.parseLong(idReservaHabitacion),TipoDocumento.valueOf(registradoPorTipoDocumento),Integer.parseInt(registradoPorNumeroDocumento));

		//}
		/*
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
		*/
	}
	public Pedido adicionarPedido(int pagoHabitacion,long idCuenta)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			long id = nextval ();
			tx.begin();
			long tuplasInsertadas = sqlPedido.adicionarPedido(pm,id,pagoHabitacion,idCuenta);
			tx.commit ();
			
			log.trace ("Inserción de pedido: " + id + ": " + tuplasInsertadas + " tuplas insertadas");
			
			return new Pedido(id,pagoHabitacion,idCuenta);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	public ProductoPedido adicionarProductoPedido(long idPedido,long idServicio,long idProducto,int cantidad)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long tuplasInsertadas = sqlProductoPedido.adicionarProductoPedido(pm,idPedido,idServicio,idProducto,cantidad);
			tx.commit ();
			
			log.trace ("Inserción de producto pedido: " + idPedido + ": " + tuplasInsertadas + " tuplas insertadas");
			
			return new ProductoPedido(idPedido,idServicio,idProducto,cantidad);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	public ReservaServicio adicionarReservaServicio(long idServicio,long idReservaHabitacion,String fecha,int duracion)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			long id = nextval ();
			tx.begin();
			long tuplasInsertadas = sqlReservaServicio.adicionarReservaServicio(pm,id,idServicio,idReservaHabitacion,fecha,duracion);
			tx.commit ();
			
			log.trace ("Inserción de reserva de servicio: " + id + ": " + tuplasInsertadas + " tuplas insertadas");
			
			return new ReservaServicio(id,idServicio,idReservaHabitacion,Timestamp.valueOf(fecha),duracion);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	
	
	//Todos los metodos de dar por id
	public CadenaHotelera darCadenaHoteleraPorId(String nombre)
	{
		return sqlCadenaHotelera.darCadenaHoteleraPorId(pmf.getPersistenceManager(), nombre);
	}
	public RolUsuario darRolUsuarioPorId(String idRol)
	{
		return sqlRolUsuario.darRolUsuarioPorId(pmf.getPersistenceManager(), idRol);
	}
	public TipoHabitacion darTipoHabitacionPorId(String tipoHabitacion)
	{
		return sqlTipoHabitacion.darTipoHabitacionPorId(pmf.getPersistenceManager(), tipoHabitacion);
	}
	public TipoServicio darTipoServicioPorId(String tipoServicio)
	{
		return sqlTipoServicio.darTipoServicioPorId(pmf.getPersistenceManager(), tipoServicio);
	}
	public Hotel darHotelPorId(String nombreHotel)
	{
		return sqlHotel.darHotelPorId(pmf.getPersistenceManager(), nombreHotel);
	}
	public Usuario darUsuarioPorId(TipoDocumento tipoDoc,int numDoc)
	{
		return sqlUsuario.darUsuarioPorId(pmf.getPersistenceManager(), tipoDoc,numDoc);
	}
	public Habitacion darHabitacionPorId(String nombreHotel,int numero)
	{
		return sqlHabitacion.darHabitacionPorId(pmf.getPersistenceManager(), nombreHotel,numero);
	}
	public Servicio darServicioPorId(long idServicio)
	{
		return sqlServicio.darServicioPorId(pmf.getPersistenceManager(), idServicio);
	}
	public Piscina darPiscinaPorId(long idServicio)
	{
		return sqlPiscina.darPiscinaPorId(pmf.getPersistenceManager(), idServicio);
	}
	public Gimnasio darGimnasioPorId(long idServicio)
	{
		return sqlGimnasio.darGimnasioPorId(pmf.getPersistenceManager(), idServicio);
	}
	public Internet darInternetPorId(long idServicio)
	{
		return sqlInternet.darInternetPorId(pmf.getPersistenceManager(), idServicio);
	}
	public Establecimiento darEstablecimientoPorId(long idServicio)
	{
		return sqlEstablecimiento.darEstablecimientoPorId(pmf.getPersistenceManager(), idServicio);
	}
	public PrestamoUtensilio darPrestamoUtensilioPorId(long idServicio)
	{
		return sqlPrestamoUtensilio.darPrestamoUtensilioPorId(pmf.getPersistenceManager(), idServicio);
	}
	public SalonConferencia darSalonConferenciaPorId(long idServicio)
	{
		return sqlSalonConferencia.darSalonConferenciaPorId(pmf.getPersistenceManager(), idServicio);
	} 
	public SalonReunion darSalonReunionPorId(long idServicio)
	{
		return sqlSalonReunion.darSalonReunionPorId(pmf.getPersistenceManager(), idServicio);
	}
	public Spaa darSpaaPorId(long idServicio)
	{
		return sqlSpaa.darSpaaPorId(pmf.getPersistenceManager(), idServicio);
	}
	public Producto darProductoPorId(long idServicio,long idProducto)
	{
		return sqlProducto.darProductoPorId(pmf.getPersistenceManager(), idServicio,idProducto);
	}
	public PlanConsumo darPlanConsumoPorId(long idPlanConsumo)
	{
		return sqlPlanConsumo.darPlanConsumoPorId(pmf.getPersistenceManager(), idPlanConsumo);
	}
	public ReservaHabitacion darReservaHabitacionPorId(long idReservaHabitacion)
	{
		return sqlReservaHabitacion.darReservaHabitacionPorId(pmf.getPersistenceManager(), idReservaHabitacion);
	}
	public HuespedReserva darHuespedReservaPorId(long idReservaHabitacion)
	{
		return sqlHuespedReserva.darHuespedReservaPorId(pmf.getPersistenceManager(), idReservaHabitacion);
	}
	public CuentaServicio darCuentaServicioPorId(long idCuentaServicio)
	{
		return sqlCuentaServicio.darCuentaServicioPorId(pmf.getPersistenceManager(), idCuentaServicio);
	}
	public Pedido darPedidoPorId(long idPedido)
	{
		return sqlPedido.darPedidoPorId(pmf.getPersistenceManager(), idPedido);
	}
	public ProductoPedido darProductoPedidoPorId(long idPedido,long idServicio,long idProducto)
	{
		return sqlProductoPedido.darProductoPedidoPorId(pmf.getPersistenceManager(), idPedido,idServicio,idProducto);
	}
	public ReservaServicio darReservaServicioPorId(long idReservaServicio)
	{
		return sqlReservaServicio.darReservaServicioPorId(pmf.getPersistenceManager(), idReservaServicio);
	}



	//Todos los metodos de eliminar
	public long eliminarCadenaHoteleraPorId(String idCadenaHotelera)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlCadenaHotelera.eliminarCadenaHoteleraPorId(pm,idCadenaHotelera);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	public long eliminarRolUsuarioPorId(String idRolUsuario)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlRolUsuario.eliminarRolUsuarioPorId(pm,idRolUsuario);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	public long eliminarTipoHabitacionPorId(String idTipoHabitacion)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlTipoHabitacion.eliminarTipoHabitacionPorId(pm,idTipoHabitacion);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	public long eliminarTipoServicioPorId (String idTipoServicio)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlTipoServicio.eliminarTipoServicioPorId(pm,idTipoServicio);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	public long eliminarHotelPorId(String nombreHotel)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlHotel.eliminarHotelPorId(pm,nombreHotel);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	public long eliminarUsuarioPorId(TipoDocumento tipoDoc,int numeroDoc)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlUsuario.eliminarUsuarioPorId(pm,tipoDoc,numeroDoc);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	public long eliminarHabitacionPorId(String nombreHotel,int numero)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlHabitacion.eliminarHabitacionPorId(pm,nombreHotel,numero);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	public long eliminarServicioPorId(long idServicio)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlServicio.eliminarServicioPorId(pm,idServicio);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	public long eliminarPiscinaPorId(long idServicio)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlPiscina.eliminarPiscinaPorId(pm,idServicio);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	public long eliminarGimnasioPorId(long idServicio)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlGimnasio.eliminarGimnasioPorId(pm,idServicio);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	public long eliminarInternetPorId(long idInternet)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlInternet.eliminarInternetPorId(pm,idInternet);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	public long eliminarEstablecimientoPorId(long idServicio)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlEstablecimiento.eliminarEstablecimientoPorId(pm,idServicio);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	public long eliminarPrestamoUtensilioPorId(long idServicio)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlPrestamoUtensilio.eliminarPrestamoUtensilioPorId(pm,idServicio);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	public long eliminarSalonConferenciaPorId(long idServicio)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlSalonConferencia.eliminarSalonConferenciaPorId(pm,idServicio);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	public long eliminarSalonReunionPorId(long idServicio)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlSalonReunion.eliminarSalonReunionPorId(pm,idServicio);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	public long eliminarSpaaPorId(long idServicio)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlSpaa.eliminarSpaaPorId(pm,idServicio);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	public long eliminarProductoPorId(long idServicio,long idProducto)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlProducto.eliminarProductoPorId(pm,idServicio,idProducto);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	public long eliminarPlanConsumoPorId(long idPlanConsumo)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlPlanConsumo.eliminarPlanConsumoPorId(pm,idPlanConsumo);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	public long eliminarReservaHabitacionPorId(long idReservaHabitacion)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlReservaHabitacion.eliminarReservaHabitacionPorId(pm,idReservaHabitacion);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	public long eliminarHuespedReservaPorId(long idReservaHabitacion)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlHuespedReserva.eliminarHuespedReservaPorId(pm,idReservaHabitacion);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	public long eliminarCuentaServicioPorId(long idCuenta)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlCuentaServicio.eliminarCuentaServicioPorId(pm,idCuenta);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	public long eliminarPedidoPorId(long idPedido)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlPedido.eliminarPedidoPorId(pm,idPedido);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	public long eliminarProductoPedidoPorId(long idPedido,long idServicio,long idProducto)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlProductoPedido.eliminarProductoPedidoPorId(pm,idPedido,idServicio,idProducto);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	public long eliminarReservaServicioPorId(long idReservaServicio)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlReservaServicio.eliminarReservaServicioPorId(pm,idReservaServicio);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	//Metodos para dar todos
	public List<CadenaHotelera> darCadenasHoteleras()
	{
		return sqlCadenaHotelera.darCadenasHoteleras(pmf.getPersistenceManager());
	}
	public List<RolUsuario> darRolesUsuario()
	{
		return sqlRolUsuario.darRolesUsuario(pmf.getPersistenceManager());
	}
	public List<TipoHabitacion> darTiposHabitacion()
	{
		return sqlTipoHabitacion.darTiposHabitaciones(pmf.getPersistenceManager());
	}
	public List<TipoServicio> darTiposServicio()
	{
		return sqlTipoServicio.darTiposServicios(pmf.getPersistenceManager());
	}
	public List<Hotel> darHoteles()
	{
		return sqlHotel.darHoteles(pmf.getPersistenceManager());
	}
	public List<Usuario> darUsuarios()
	{
		return sqlUsuario.darUsuarios(pmf.getPersistenceManager());
	}
	public List<Habitacion> darHabitaciones()
	{
		return sqlHabitacion.darHabitaciones(pmf.getPersistenceManager());
	}
	public List<Servicio> darServicios()
	{
		return sqlServicio.darServicios(pmf.getPersistenceManager());
	}
	public List<Piscina> darPiscinas()
	{
		return sqlPiscina.darPiscinas(pmf.getPersistenceManager());
	}
	public List<Gimnasio> darGimnasios()
	{
		return sqlGimnasio.darGimnasios(pmf.getPersistenceManager());
	}
	public List<Internet> darInternets()
	{
		return sqlInternet.darInternets(pmf.getPersistenceManager());
	}
	public List<Establecimiento> darEstablecimientos()
	{
		return sqlEstablecimiento.darEstablecimientos(pmf.getPersistenceManager());
	}
	public List<PrestamoUtensilio> darPrestamosUtensilios()
	{
		return sqlPrestamoUtensilio.darPrestamosUtensilios(pmf.getPersistenceManager());
	}
	public List<SalonConferencia> darSalonesConferencias()
	{
		return sqlSalonConferencia.darSalonesConferencia(pmf.getPersistenceManager());
	}
	public List<SalonReunion> darSalonesReuniones()
	{
		return sqlSalonReunion.darSalonesReunion(pmf.getPersistenceManager());
	}
	public List<Spaa> darSpaas()
	{
		return sqlSpaa.darSpaas(pmf.getPersistenceManager());
	}
	public List<Producto> darProductos()
	{
		return sqlProducto.darProductos(pmf.getPersistenceManager());
	}
	public List<PlanConsumo> darPlanesConsumo()
	{
		return sqlPlanConsumo.darPlanesConsumo(pmf.getPersistenceManager());
	}
	public List<ReservaHabitacion> darReservasHabitaciones()
	{
		return sqlReservaHabitacion.darReservasHabitaciones(pmf.getPersistenceManager());
	}
	public List<HuespedReserva> darHuespedesReservas()
	{
		return sqlHuespedReserva.darHuespedesReserva(pmf.getPersistenceManager());
	}
	public List<CuentaServicio> darCuentasServicios()
	{
		return sqlCuentaServicio.darCuentasServicios(pmf.getPersistenceManager());
	}
	public List<Pedido> darPedidos()
	{
		return sqlPedido.darPedidos(pmf.getPersistenceManager());
	}
	public List<ProductoPedido> darProductosPedidos()
	{
		return sqlProductoPedido.darProductosPedidos(pmf.getPersistenceManager());
	}
	public List<ReservaServicio> darReservasServicios()
	{
		return sqlReservaServicio.darReservaServicios(pmf.getPersistenceManager());
	}

	//Requerimientos funcionales de consulta

	//Requerimiento funcional de consutla 1
	public List<String []> darDineroRecolectadoPorServiciosEnCadaHabitacion(String fechaInf,String fechaSup)
	{
		List<String []> rep = new LinkedList<String []>();
		List<Object[]> tuplas = sqlCuentaServicio.darDineroRecolectadoPorServiciosEnCadaHabitacion(pmf.getPersistenceManager(), fechaInf, fechaSup);
		for(Object[] tupla: tuplas)
		{
			String[] aux = new String [3];
			aux[0] = tupla[0].toString();
			aux[1] = tupla[1].toString();
			aux[2] = tupla[2].toString();
			rep.add(aux);
		}
		return rep;
	}
	//Requerimiento funcional de consulta 2
	public List<String[]> darLosServiciosMasPopulares(String fechaInf,String fechaSup)
	{
		List<String []> rep = new LinkedList<String []>();
		List<Object[]> tuplas = sqlCuentaServicio.darLosServiciosMasPopulares(pmf.getPersistenceManager(), fechaInf, fechaSup);
		for (Object tupla:tuplas)
		{
			String[] aux = new String [1];
			aux[0]= tupla.toString();
			rep.add(aux);
			
		}
		return rep;
	}
	//Requerimiento funcional de consulta 3
	public List<String[]> darIndiceOcupacionHabitacionPorFecha(String nombreHotel,String fechaInf,String fechaSup)
	{
		List<String []> rep = new LinkedList<String []>();
		List<Object[]> tuplas = sqlHabitacion.darIndiceOcupacionHabitacionPorFecha(pmf.getPersistenceManager(),nombreHotel, fechaInf, fechaSup);
		for (Object[] tupla:tuplas)
		{
			String[] aux = new String [3];
			aux[0]= tupla[0].toString();
			aux[1]= tupla[1].toString();
			aux[2]= tupla[2].toString();
			rep.add(aux);
			System.out.println(rep);
		}
		return rep;
	}
	//Requerimiento funcional de consulta 4
	public List<String[]> darServiciosPorCaracteristica(String caracteristica)
	{
		List<String []> rep = new LinkedList<String []>();
		List<Object[]> tuplas = sqlCuentaServicio.darServiciosPorCaracteristicas(pmf.getPersistenceManager(), caracteristica);
		for (Object[] tupla:tuplas)
		{
			String[] aux = new String [13];
			aux[0]= tupla[0].toString();
			aux[1]= tupla[1].toString();
			if(tupla[2]==null)
			{
				aux[2]="NA";
			}
			else
			{
				aux[2]= tupla[2].toString();
			}
			if(tupla[3]==null)
			{
				aux[3]="NA";
			}
			else
			{
				aux[3]= tupla[3].toString();
			}
			aux[4]= tupla[4].toString();
			aux[5]= tupla[5].toString();
			aux[6]= tupla[6].toString();
			aux[7]= tupla[7].toString();
			aux[8]= tupla[8].toString();
			aux[9]= tupla[9].toString();
			aux[10]= tupla[10].toString();
			if(tupla[11]==null)
			{
				aux[11]="NA";
			}
			else
			{
				aux[11]= tupla[11].toString();
			}
			if(tupla[12]==null)
			{
				aux[12]="NA";
			}
			else
			{
				aux[12]= tupla[12].toString();
			}
			rep.add(aux);
			System.out.println(rep);
		}
		return rep;
	}
	//Requerimiento funcional de consulta 5
	public List<String[]> darConsumoPorUsuario(TipoDocumento tipoDocumento,int numDoc,String fechaInf,String fechaSup)
	{
		List<String []> rep = new LinkedList<String []>();
		String tipoDoc = tipoDocumento.toString();
		List<Object[]> tuplas = sqlHuespedReserva.darConsumoPorUsuario(pmf.getPersistenceManager(),tipoDoc,numDoc, fechaInf, fechaSup);
		for (Object[] tupla:tuplas)
		{
			String[] aux = new String [4];
			aux[0]= tupla[0].toString();
			aux[1]= tupla[1].toString();
			aux[2]= tupla[2].toString();
			aux[3]= tupla[3].toString();
			rep.add(aux);
		}
		return rep;
	}
	public String registrarSalida(String idReservaHabitacion)
	{
		String res = sqlReservaHabitacion.registrarSalida(pmf.getPersistenceManager(), idReservaHabitacion);
		sqlReservaHabitacion.registrarSalidaCambiarEstado(pmf.getPersistenceManager(), idReservaHabitacion);
		return res;
		/*
		List<String []> rep = new LinkedList<String []>();
		List<Object[]> tuplas = sqlReservaHabitacion.registrarSalida(pmf.getPersistenceManager(),idReservaHabitacion);
		System.out.println("1");
		System.out.println(tuplas.get(0)[0]);
		for (Object[] tupla:tuplas)
		{
			String[] aux = new String [1];
			aux[0]= tupla[0].toString();
			rep.add(aux);
		}
		return rep.get(0)[0];
		*/
	}
	public List<Producto> darProductosPorServicio(long idServicio)
	{
		return sqlProducto.darProductosPorServicio(pmf.getPersistenceManager(),idServicio);
	}
	
}
