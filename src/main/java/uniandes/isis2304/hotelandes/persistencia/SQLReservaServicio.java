
package uniandes.isis2304.hotelandes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.hotelandes.negocio.ReservaServicio;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto RESERVASERVICIO de Hotelandes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Equipo C-09
 */
class SQLReservaServicio 
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
	public SQLReservaServicio (PersistenciaHotelandes ph)
	{
		this.ph = ph;
	}

	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un RESERVASERVICIO a la base de datos de Hotelandes
	 * @param pm - El manejador de persistencia
	 * @param idReservaServicio - Id de la reserva del servicio
	 * @param idServicio - Id del servicio que se reservó
	 * @param idReservaHabitacion - Id de la reserva de habitación, que reservó el servicio
	 * @param fecha - Fecha en la que se efectuará la reserva
	 * @param duracion - Duración (en minutos) que tendrá la reserva
	 * @return El número de tuplas insertadas
	 */
	public long adicionarReservaServicio (PersistenceManager pm, long idReservaServicio, long idServicio, long idReservaHabitacion, String fecha, int duracion)
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + ph.darTablaReservaServicio () + "(ID_RESERVA_SERVICIO, ID_SERVICIO, ID_RESERVA_HABITACION, fecha, duracion) values (?, ?, ?, ?, ?)");
        q.setParameters(idReservaServicio, idServicio, idReservaHabitacion, fecha, duracion);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UN RESERVASERVICIO de la base de datos de Hotelandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idReservaServicio - El identificador del reservaservicio
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarReservaServicioPorId (PersistenceManager pm, long idReservaServicio)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + ph.darTablaReservaServicio () + " WHERE ID_RESERVA_HABITACION = ?");
        q.setParameters(idReservaServicio);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UN RESERVASERVICIO de la 
	 * base de datos de Hotelandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idReservaServicio - El identificador del reservaservicio
	 * @return El objeto RESERVASERVICIO que tiene el identificador dado
	 */
	public ReservaServicio darReservaServicioPorId (PersistenceManager pm, long idReservaServicio) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaReservaServicio () + " WHERE ID_RESERVA_HABITACION = ?");
		q.setResultClass(ReservaServicio.class);
		q.setParameters(idReservaServicio);
		return (ReservaServicio) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS RESERVASERVICIOS de la 
	 * base de datos de Hotelandes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos RESERVASERVICIO
	 */
	public List<ReservaServicio> darReservaServicios (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaReservaServicio ());
		q.setResultClass(ReservaServicio.class);
		return (List<ReservaServicio>) q.executeList();
	}
	
}
