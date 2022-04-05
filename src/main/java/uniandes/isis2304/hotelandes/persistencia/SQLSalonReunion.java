
package uniandes.isis2304.hotelandes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.hotelandes.negocio.SalonReunion;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto SALONREUNION de Hotelandes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Equipo C-09
 */
class SQLSalonReunion 
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
	public SQLSalonReunion (PersistenciaHotelandes ph)
	{
		this.ph = ph;
	}

	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un SALONREUNION a la base de datos de Hotelandes
	 * @param pm - El manejador de persistencia
	 * @param idServicio - Id del servicio que hace referencia al salón de reunión
	 * @param numero - Número del salón de reunión
	 * @param costoPorHora - Costo por hora del salón de reunión
	 * @param costoAdicional - Costo adicional por utilizar los implementos del salón de reunión
	 * @return El número de tuplas insertadas
	 */
	public long adicionarSalonReunion (PersistenceManager pm, long idServicio, int numero, double costoPorHora, double costoAdicional)
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + ph.darTablaSalonReunion () + "(ID_SERVICIO, numero, COSTO_POR_HORA, COTO_ADICIONAL) values (?, ?, ?, ?)");
        q.setParameters(idServicio, numero, costoPorHora, costoAdicional);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UN SALONREUNION de la base de datos de Hotelandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idServicio - El identificador del salonreunion
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarSalonReunionPorId (PersistenceManager pm, long idServicio)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + ph.darTablaSalonReunion () + " WHERE ID_SERVICIO = ?");
        q.setParameters(idServicio);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UN SALONREUNION de la 
	 * base de datos de Hotelandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idServicio - El identificador del salonreunion
	 * @return El objeto SALONREUNION que tiene el identificador dado
	 */
	public SalonReunion darSalonReunionPorId (PersistenceManager pm, long idServicio) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaSalonReunion () + " WHERE ID_SERVICIO = ?");
		q.setResultClass(SalonReunion.class);
		q.setParameters(idServicio);
		return (SalonReunion) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS SALONESREUNION de la 
	 * base de datos de Hotelandes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos SALONREUNION
	 */
	public List<SalonReunion> darSalonesReunion (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaSalonReunion ());
		q.setResultClass(SalonReunion.class);
		return (List<SalonReunion>) q.executeList();
	}
	
}
