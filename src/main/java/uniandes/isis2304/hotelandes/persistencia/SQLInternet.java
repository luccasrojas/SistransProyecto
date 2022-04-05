
package uniandes.isis2304.hotelandes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.hotelandes.negocio.Internet;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto INTERNET de Hotelandes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Equipo C-09
 */
class SQLInternet 
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
	public SQLInternet (PersistenciaHotelandes ph)
	{
		this.ph = ph;
	}

	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un INTERNET a la base de datos de Hotelandes
	 * @param pm - El manejador de persistencia
	 * @param idServicio - Id del internet
	 * @param capacidad - Capacidad de conexiones del internet
	 * @param costoPorDia - Costo por día del internet
	 * @return El número de tuplas insertadas
	 */
	public long adicionarInternet (PersistenceManager pm, long idServicio, double capacidad, double costoPorDia)
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + ph.darTablaInternet () + "(ID_SERVICIO, capacidad, COSTO_POR_DIA) values (?, ?, ?)");
        q.setParameters(idServicio, capacidad, costoPorDia);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UN INTERNET de la base de datos de Hotelandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idServicio - El identificador del internet
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarInternetPorId (PersistenceManager pm, long idServicio)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + ph.darTablaInternet () + " WHERE ID_SERVICIO = ?");
        q.setParameters(idServicio);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UN INTERNET de la 
	 * base de datos de Hotelandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idServicio - El identificador del internet
	 * @return El objeto INTERNET que tiene el identificador dado
	 */
	public Internet darInternetPorId (PersistenceManager pm, long idServicio) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaInternet () + " WHERE ID_SERVICIO = ?");
		q.setResultClass(Internet.class);
		q.setParameters(idServicio);
		return (Internet) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS INTERNETS de la 
	 * base de datos de Hotelandes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos INTERNET
	 */
	public List<Internet> darInternets (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaInternet ());
		q.setResultClass(Internet.class);
		return (List<Internet>) q.executeList();
	}
	
}
