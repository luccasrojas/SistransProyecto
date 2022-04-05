
package uniandes.isis2304.hotelandes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.hotelandes.negocio.Servicio;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto SERVICIO de Hotelandes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Equipo C-09
 */
class SQLServicio 
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Cadena que representa el tipo de consulta que se va a realizar en las sentencias de acceso a la base de datos
	 * Se renombra acá para facilitar la escritura de las sentencias
	 */
	private final static String SQL = PersistenciaHotelandes.SQL;

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia general de la aplicación
	 */
	private PersistenciaHotelandes ph;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/

	/**
	 * Constructor
	 * @param ph - El Manejador de persistencia de la aplicación
	 */
	public SQLServicio (PersistenciaHotelandes ph)
	{
		this.ph = ph;
	}

	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un SERVICIO a la base de datos de Hotelandes
	 * @param pm - El manejador de persistencia
	 * @param nombreHotel - Nombre del hotel que ofrece el servicio
	 * @param idServicio - Id del servicio
	 * @param horaInicio - Hora de apertura diaria del servicio (si tiene)
	 * @param horaFin - Hora de cierre diario del servicio (si tiene)
	 * @param tipoServicio - Tipo del servicio
	 * @return El número de tuplas insertadas
	 */
	public long adicionarServicio (PersistenceManager pm, String nombreHotel, long idServicio, String horaInicio, String horaFin, String tipoServicio)
	{
		Query q;
		if (horaInicio == "" || horaFin == ""){
	        q = pm.newQuery(SQL, "INSERT INTO " + ph.darTablaServicio () + "(NOMBRE_HOTEL, ID_SERVICIO, TIPO_SERVICIO) values (?, ?, ?)");
    		q.setParameters(nombreHotel, idServicio, tipoServicio);
		}
		else {
			q = pm.newQuery(SQL, "INSERT INTO " + ph.darTablaServicio () + "(NOMBRE_HOTEL, ID_SERVICIO, HORA_INICIO, HORA_FIN, TIPO_SERVICIO) values (?, ?, ?, ?, ?)");
    		q.setParameters(nombreHotel, idServicio, horaInicio, horaFin, tipoServicio);
		}
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar SERVICIOS de la base de datos de Hotelandes, por el nombre de su hotel
	 * @param pm - El manejador de persistencia
	 * @param nombreServicio - El nombre del hotel que ofrece el servicio
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarServiciosPorNombreHotel (PersistenceManager pm, String nombreHotel)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + ph.darTablaServicio () + " WHERE NOMBRE_HOTEL = ?");
        q.setParameters(nombreHotel);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS SERVICIOS de la 
	 * base de datos de Hotelandes, por el nombre de su hotel
	 * @param pm - El manejador de persistencia
	 * @param nombreServicio - El nombre del hotel que ofrece el servicio
	 * @return Una lista de objetos SERVICIO que ofrece el hotel de nombre dado
	 */
	public List<Servicio> darServiciosPorNombre (PersistenceManager pm, String nombreHotel) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaServicio () + " WHERE NOMBRE_HOTEL = ?");
		q.setResultClass(Servicio.class);
		q.setParameters(nombreHotel);
		return (List<Servicio>) q.executeList();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UN SERVICIO de la base de datos de Hotelandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idServicio - El identificador del servicio
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarServicioPorId (PersistenceManager pm, long idServicio)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + ph.darTablaServicio () + " WHERE ID_SERVICIO = ?");
        q.setParameters(idServicio);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UN SERVICIO de la 
	 * base de datos de Hotelandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idServicio - El identificador del servicio
	 * @return El objeto SERVICIO que tiene el identificador dado
	 */
	public Servicio darServicioPorId (PersistenceManager pm, long idServicio) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaServicio () + " WHERE ID_SERVICIO = ?");
		q.setResultClass(Servicio.class);
		q.setParameters(idServicio);
		return (Servicio) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS SERVICIOS de la 
	 * base de datos de Hotelandes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos SERVICIO
	 */
	public List<Servicio> darServicios (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaServicio ());
		q.setResultClass(Servicio.class);
		return (List<Servicio>) q.executeList();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS SERVICIOS de la 
	 * base de datos de Hotelandes, por su tipo
	 * @param pm - El manejador de persistencia
	 * @param nombreServicio - El tipo del servicio
	 * @return Una lista de objetos SERVICIO que son del tipo dado
	 */
	public List<Servicio> darServiciosPorTipo (PersistenceManager pm, String tipoServicio) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaServicio () + " WHERE TIPO_SERVICIO = ?");
		q.setResultClass(Servicio.class);
		q.setParameters(tipoServicio);
		return (List<Servicio>) q.executeList();
	}

}
