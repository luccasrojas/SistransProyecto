
package uniandes.isis2304.hotelandes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.hotelandes.negocio.Spaa;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto SPAA de Hotelandes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Equipo C-09
 */
class SQLSpaa 
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
	public SQLSpaa (PersistenciaHotelandes ph)
	{
		this.ph = ph;
	}

	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un SPAA a la base de datos de Hotelandes
	 * @param pm - El manejador de persistencia
	 * @param idServicio - Id del SPA
	 * @param capacidad - Capacidad de personas en el SPA
	 * @return El número de tuplas insertadas
	 */
	public long adicionarSpaa (PersistenceManager pm, long idServicio, int capacidad)
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + ph.darTablaSpaa () + "(ID_SERVICIO, capacidad) values (?, ?)");
        q.setParameters(idServicio, capacidad);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UN SPAA de la base de datos de Hotelandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idServicio - El identificador del spaa
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarSpaaPorId (PersistenceManager pm, long idServicio)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + ph.darTablaSpaa () + " WHERE ID_SERVICIO = ?");
        q.setParameters(idServicio);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UN SPAA de la 
	 * base de datos de Hotelandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idServicio - El identificador del spaa
	 * @return El objeto SPAA que tiene el identificador dado
	 */
	public Spaa darSpaaPorId (PersistenceManager pm, long idServicio) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaSpaa () + " WHERE ID_SERVICIO = ?");
		q.setResultClass(Spaa.class);
		q.setParameters(idServicio);
		return (Spaa) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS SPAAS de la 
	 * base de datos de Hotelandes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos SPAA
	 */
	public List<Spaa> darSpaas (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaSpaa ());
		q.setResultClass(Spaa.class);
		return (List<Spaa>) q.executeList();
	}
	
}
