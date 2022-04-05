
package uniandes.isis2304.hotelandes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.hotelandes.negocio.Habitacion;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto HABITACION de Hotelandes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Equipo C-09
 */
class SQLHabitacion 
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
	public SQLHabitacion (PersistenciaHotelandes ph)
	{
		this.ph = ph;
	}

	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un HABITACION a la base de datos de Hotelandes
	 * @param pm - El manejador de persistencia
	 * @param nombreHotel - Nombre del hotel de la habitación
	 * @param numero - Número de la habitación
	 * @param tipoHabitacion - Tipo de la habitación
	 * @param costoPorNoche - Costo por noche de la habitación
	 * @param descripcion - Descripción de la habitación
	 * @param capacidadMin - Capacidad mínima de la habitación
	 * @param capacidadMax - Capacidad máxima de la habitación
	 * @return El número de tuplas insertadas
	 */
	public long adicionarHabitacion (PersistenceManager pm, String nombreHotel, int numero, String tipoHabitacion, double costoPorNoche, String descripcion, int capacidadMin, int capacidadMax)
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + ph.darTablaHabitacion () + "(NOMBRE_HOTEL, numero, tipo_habitacion, costo_por_noche, descripcion, capacidad_min, capacidad_max) values (?, ?, ?, ?, ?, ?, ?)");
        q.setParameters(nombreHotel, numero, tipoHabitacion, costoPorNoche, descripcion, capacidadMin, capacidadMax);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar HABITACIONES de la base de datos de Hotelandes, por el nombre de su hotel
	 * @param pm - El manejador de persistencia
	 * @param nombreHotel - El nombre del hotel
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarHabitacionPorId (PersistenceManager pm, String nombreHotel, int numero)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + ph.darTablaHabitacion () + " WHERE nombre_hotel = ? AND numero = ?");
		q.setParameters(nombreHotel, numero);
		return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS HABITACIONES de la 
	 * base de datos de Hotelandes, por el nombre del hotel en el que están
	 * @param pm - El manejador de persistencia
	 * @param nombreHotel - El nombre del hotel buscado
	 * @return Una lista de objetos HABITACION que están en el hotel del nombre dado
	 */
	public List<Habitacion> darHabitacionesPorNombreHotel (PersistenceManager pm, String nombreHotel) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaHabitacion () + " WHERE nombre_hotel = ?");
		q.setResultClass(Habitacion.class);
		q.setParameters(nombreHotel);
		return (List<Habitacion>) q.executeList();
	}
	public Habitacion darHabitacionPorId (PersistenceManager pm, String nombreHotel, int numero) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaHabitacion () + " WHERE nombre_hotel = ? AND numero = ?");
		q.setResultClass(Habitacion.class);
		q.setParameters(nombreHotel, numero);
		return (Habitacion) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS HABITACIONES de la 
	 * base de datos de Hotelandes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos HABITACION
	 */
	public List<Habitacion> darHabitaciones (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaHabitacion ());
		q.setResultClass(Habitacion.class);
		return (List<Habitacion>) q.executeList();
	}
	public List<Object[]> darIndiceOcupacionHabitacionPorFecha(PersistenceManager pm,String nombreHotel,String fechaInf,String fechaSup)
	{
		String peticion;
		peticion = "SELECT HABITACION.NOMBRE_HOTEL,HABITACION.NUMERO,COUNT(*)";
		peticion +=" FROM "+ph.darTablaHabitacion();
		String tabla = "SELECT NOMBRE_HOTEL,NUMERO_HABITACION,FECHA_IN,FECHA_OUT FROM RESERVA_HABITACION WHERE NOMBRE_HOTEL = ?";
		peticion +=" LEFT OUTER JOIN ("+tabla+") C ON HABITACION.NUMERO = C.NUMERO_HABITACION AND HABITACION.NOMBRE_HOTEL = ?";
		peticion+=" WHERE C.FECHA_IN >=? AND C.FECHA_IN<=?";
		peticion+=" GROUP BY HABITACION.NOMBRE_HOTEL,HABITACION.NUMERO";
		Query q = pm.newQuery(SQL,peticion);
		q.setParameters(nombreHotel,nombreHotel,fechaInf,fechaSup);
		return q.executeList();
	}
	
}
