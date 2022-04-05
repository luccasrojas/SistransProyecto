
package uniandes.isis2304.hotelandes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.hotelandes.negocio.Hotel;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto HOTEL de Hotelandes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Equipo C-09
 */
class SQLHotel 
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
	public SQLHotel (PersistenciaHotelandes ph)
	{
		this.ph = ph;
	}

	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un HOTEL a la base de datos de Hotelandes
	 * @param pm - El manejador de persistencia
	 * @param nombreCadena - Nombre de la cadena a la que pertenece el hotel
	 * @param nombreHotel - Nombre del hotel
	 * @param pais - País en el que está ubicado el hotel
	 * @param ciudad - Ciudad en la que está ubicado el hotel
	 * @param direccion - Dirección exacta en la que está ubicado el hotel
	 * @return El número de tuplas insertadas
	 */
	public long adicionarHotel (PersistenceManager pm, String nombreCadena, String nombreHotel, String pais, String ciudad, String direccion)
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + ph.darTablaHotel () + "(NOMBRE_CADENA, NOMBRE_HOTEL, pais, ciudad, direccion) values (?, ?, ?, ?, ?)");
        q.setParameters(nombreCadena, nombreHotel, pais, ciudad, direccion);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UN HOTEL de la base de datos de Hotelandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param nombreHotel - El identificador del hotel (el nombre)
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarHotelPorId (PersistenceManager pm, String nombreHotel)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + ph.darTablaHotel () + " WHERE NOMBRE_HOTEL = ?");
        q.setParameters(nombreHotel);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UN HOTEL de la 
	 * base de datos de Hotelandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param nombreHotel - El identificador del hotel (el nombre)
	 * @return El objeto HOTEL que tiene el identificador dado
	 */
	public Hotel darHotelPorId (PersistenceManager pm, String nombreHotel) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaHotel () + " WHERE NOMBRE_HOTEL = ?");
		q.setResultClass(Hotel.class);
		q.setParameters(nombreHotel);
		return (Hotel) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS HOTELES de la 
	 * base de datos de Hotelandes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos HOTEL
	 */
	public List<Hotel> darHoteles (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaHotel ());
		q.setResultClass(Hotel.class);
		return (List<Hotel>) q.executeList();
	}
	
}
