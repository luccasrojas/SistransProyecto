
package uniandes.isis2304.hotelandes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.hotelandes.negocio.Piscina;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto PISCINA de Hotelandes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Equipo C-09
 */
class SQLPiscina 
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
	public SQLPiscina (PersistenciaHotelandes ph)
	{
		this.ph = ph;
	}

	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un PISCINA a la base de datos de Hotelandes
	 * @param pm - El manejador de persistencia
	 * @param idServicio - Id de la piscina
	 * @param capacidad - Capacidad de personas de la piscina
	 * @param profundidad - Profundidad (en metros) de la piscina
	 * @param costo - Costo de la piscina
	 * @return El número de tuplas insertadas
	 */
	public long adicionarPiscina (PersistenceManager pm, long idServicio, int capacidad, double profundidad, double costo)
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + ph.darTablaPiscina () + "(ID_SERVICIO, capacidad, profundidad, costo) values (?, ?, ?, ?)");
        q.setParameters(idServicio, capacidad, profundidad, costo);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UN PISCINA de la base de datos de Hotelandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idServicio - El identificador del piscina
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarPiscinaPorId (PersistenceManager pm, long idServicio)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + ph.darTablaPiscina () + " WHERE ID_SERVICIO = ?");
        q.setParameters(idServicio);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UN PISCINA de la 
	 * base de datos de Hotelandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idServicio - El identificador del piscina
	 * @return El objeto PISCINA que tiene el identificador dado
	 */
	public Piscina darPiscinaPorId (PersistenceManager pm, long idServicio) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaPiscina () + " WHERE ID_SERVICIO = ?");
		q.setResultClass(Piscina.class);
		q.setParameters(idServicio);
		return (Piscina) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS PISCINAS de la 
	 * base de datos de Hotelandes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos PISCINA
	 */
	public List<Piscina> darPiscinas (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaPiscina ());
		q.setResultClass(Piscina.class);
		return (List<Piscina>) q.executeList();
	}
	
}
