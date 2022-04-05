
package uniandes.isis2304.hotelandes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.hotelandes.negocio.SalonConferencia;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto SALONCONFERENCIA de Hotelandes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Equipo C-09
 */
class SQLSalonConferencia 
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
	public SQLSalonConferencia (PersistenciaHotelandes ph)
	{
		this.ph = ph;
	}

	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un SALONCONFERENCIA a la base de datos de Hotelandes
	 * @param pm - El manejador de persistencia
	 * @param idServicio - Id del servicio que hace referencia al salón de conferencia
	 * @param numero - Número del salón de conferencia
	 * @param capacidad - Capacidad de personas en el salón de conferencia
	 * @param costoPorHora - Costo por hora del salón de conferencia
	 * @return El número de tuplas insertadas
	 */
	public long adicionarSalonConferencia (PersistenceManager pm, long idServicio, int numero, int capacidad, double costoPorHora)
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + ph.darTablaSalonConferencia () + "(ID_SERVICIO, numero, capacidad, COSTO_POR_HORA) values (?, ?, ?, ?)");
        q.setParameters(idServicio, numero, capacidad, costoPorHora);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UN SALONCONFERENCIA de la base de datos de Hotelandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idServicio - El identificador del salonconferencia
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarSalonConferenciaPorId (PersistenceManager pm, long idServicio)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + ph.darTablaSalonConferencia () + " WHERE ID_SERVICIO = ?");
        q.setParameters(idServicio);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UN SALONCONFERENCIA de la 
	 * base de datos de Hotelandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idServicio - El identificador del salonconferencia
	 * @return El objeto SALONCONFERENCIA que tiene el identificador dado
	 */
	public SalonConferencia darSalonConferenciaPorId (PersistenceManager pm, long idServicio) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaSalonConferencia () + " WHERE ID_SERVICIO = ?");
		q.setResultClass(SalonConferencia.class);
		q.setParameters(idServicio);
		return (SalonConferencia) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS SALONESCONFERENCIA de la 
	 * base de datos de Hotelandes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos SALONCONFERENCIA
	 */
	public List<SalonConferencia> darSalonesConferencia (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaSalonConferencia ());
		q.setResultClass(SalonConferencia.class);
		return (List<SalonConferencia>) q.executeList();
	}
	
}
