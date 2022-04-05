
package uniandes.isis2304.hotelandes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.hotelandes.negocio.Gimnasio;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto GIMNASIO de Hotelandes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Equipo C-09
 */
class SQLGimnasio 
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
	public SQLGimnasio (PersistenciaHotelandes ph)
	{
		this.ph = ph;
	}

	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un GIMNASIO a la base de datos de Hotelandes
	 * @param pm - El manejador de persistencia
	 * @param idServicio - Id del gimnasio
	 * @param capacidad - Capacidad de personas del gimnasio
	 * @param descripcion - Descripción del gimnasio, que incluye las máquinas que hay en él
	 * @param costo - Costo del gimnasio
	 * @return El número de tuplas insertadas
	 */
	public long adicionarGimnasio (PersistenceManager pm, long idServicio, int capacidad, String descripcion, double costo)
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + ph.darTablaGimnasio () + "(idServicio, capacidad, descripcion, costo) values (?, ?, ?, ?)");
        q.setParameters(idServicio, capacidad, descripcion, costo);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UN GIMNASIO de la base de datos de Hotelandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idServicio - El identificador del gimnasio
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarGimnasioPorId (PersistenceManager pm, long idServicio)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + ph.darTablaGimnasio () + " WHERE idServicio = ?");
        q.setParameters(idServicio);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UN GIMNASIO de la 
	 * base de datos de Hotelandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idServicio - El identificador del gimnasio
	 * @return El objeto GIMNASIO que tiene el identificador dado
	 */
	public Gimnasio darGimnasioPorId (PersistenceManager pm, long idServicio) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaGimnasio () + " WHERE idServicio = ?");
		q.setResultClass(Gimnasio.class);
		q.setParameters(idServicio);
		return (Gimnasio) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS GIMNASIOS de la 
	 * base de datos de Hotelandes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos GIMNASIO
	 */
	public List<Gimnasio> darGimnasios (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaGimnasio ());
		q.setResultClass(Gimnasio.class);
		return (List<Gimnasio>) q.executeList();
	}
	
}
