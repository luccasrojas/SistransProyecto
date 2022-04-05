
package uniandes.isis2304.hotelandes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.hotelandes.negocio.PrestamoUtensilio;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto PRESTAMOUTENSILIO de Hotelandes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Equipo C-09
 */
class SQLPrestamoUtensilio 
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
	public SQLPrestamoUtensilio (PersistenciaHotelandes ph)
	{
		this.ph = ph;
	}

	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un PRESTAMOUTENSILIO a la base de datos de Hotelandes
	 * @param pm - El manejador de persistencia
	 * @param idServicio - Id del préstamo del utensilio específico
	 * @param nombreUtensilio - Nombre del utensilio específico
	 * @return El número de tuplas insertadas
	 */
	public long adicionarPrestamoUtensilio (PersistenceManager pm, long idServicio, String nombreUtensilio)
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + ph.darTablaPrestamoUtensilio () + "(ID_SERVICIO, NOMBRE_UTENSILIO) values (?, ?)");
        q.setParameters(idServicio, nombreUtensilio);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UN PRESTAMOUTENSILIO de la base de datos de Hotelandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idServicio - El identificador del prestamoutensilio
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarPrestamoUtensilioPorId (PersistenceManager pm, long idServicio)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + ph.darTablaPrestamoUtensilio () + " WHERE ID_SERVICIO = ?");
        q.setParameters(idServicio);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UN PRESTAMOUTENSILIO de la 
	 * base de datos de Hotelandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idServicio - El identificador del prestamoutensilio
	 * @return El objeto PRESTAMOUTENSILIO que tiene el identificador dado
	 */
	public PrestamoUtensilio darPrestamoUtensilioPorId (PersistenceManager pm, long idServicio) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaPrestamoUtensilio () + " WHERE ID_SERVICIO = ?");
		q.setResultClass(PrestamoUtensilio.class);
		q.setParameters(idServicio);
		return (PrestamoUtensilio) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS PRESTAMOSUTENSILIOS de la 
	 * base de datos de Hotelandes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos PRESTAMOUTENSILIO
	 */
	public List<PrestamoUtensilio> darPrestamosUtensilios (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaPrestamoUtensilio ());
		q.setResultClass(PrestamoUtensilio.class);
		return (List<PrestamoUtensilio>) q.executeList();
	}
	
}
