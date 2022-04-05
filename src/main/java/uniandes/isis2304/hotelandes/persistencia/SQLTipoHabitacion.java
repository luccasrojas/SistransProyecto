
package uniandes.isis2304.hotelandes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.hotelandes.negocio.TipoHabitacion;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto TIPOHABITACION de Hotelandes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Equipo C-09
 */
class SQLTipoHabitacion 
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
	public SQLTipoHabitacion (PersistenciaHotelandes ph)
	{
		this.ph = ph;
	}

	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un TIPOHABITACION a la base de datos de Hotelandes
	 * @param pm - El manejador de persistencia
	 * @param tipoHabitacion - Cada posible tipo de una habitación
	 * @return El número de tuplas insertadas
	 */
	public long adicionarTipoHabitacion (PersistenceManager pm, String tipoHabitacion)
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + ph.darTablaTipoHabitacion () + "(TIPO_HABITACION) values (?)");
        q.setParameters(tipoHabitacion);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS TIPOSHABITACIONES de la 
	 * base de datos de Hotelandes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos TIPOHABITACION
	 */
	public List<TipoHabitacion> darTiposHabitaciones (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaTipoHabitacion ());
		q.setResultClass(TipoHabitacion.class);
		return (List<TipoHabitacion>) q.executeList();
	}
	public long eliminarTipoHabitacionPorId(PersistenceManager pm, String id)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + ph.darTablaTipoHabitacion () + " WHERE TIPO_HABITACION = ?");
		q.setParameters(id);
		return (long) q.executeUnique();
	}
	public TipoHabitacion darTipoHabitacionPorId(PersistenceManager pm, String id)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaTipoHabitacion () + " WHERE TIPO_HABITACION = ?");
		q.setResultClass(TipoHabitacion.class);
		q.setParameters(id);
		return (TipoHabitacion) q.executeUnique();
	}
	
}
