
package uniandes.isis2304.hotelandes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.hotelandes.negocio.HuespedReserva;
import uniandes.isis2304.hotelandes.negocio.TipoDocumento;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto HUESPEDRESERVA de Hotelandes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Equipo C-09
 */
class SQLHuespedReserva 
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
	public SQLHuespedReserva (PersistenciaHotelandes ph)
	{
		this.ph = ph;
	}

	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un HUESPEDRESERVA a la base de datos de Hotelandes
	 * @param pm - El manejador de persistencia
	 * @param idReservaHabitacion - Id de la reserva de la habitación del huesped
	 * @param tipoDocumento - Tipo de documento del huesped en la reserva específica
	 * @param numeroDocumento - Número de documento del huesped en la reserva específica
	 * @return El número de tuplas insertadas
	 */
	public long adicionarHuespedReserva (PersistenceManager pm, long idReservaHabitacion, TipoDocumento tipoDocumento, int numeroDocumento,int acompanante)
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + ph.darTablaHuespedReserva () + "(ID_RESERVA_HABITACION, TIPO_DOCUMENTO, NUMERO_DOCUMENTO, ACOMPANANTE) values (?, ?, ?, ?)");
        q.setParameters(idReservaHabitacion, tipoDocumento.toString(), numeroDocumento,acompanante);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UN HUESPEDRESERVA de la base de datos de Hotelandes, por una reserva específica
	 * @param pm - El manejador de persistencia
	 * @param idReservaHabitacion - El identificador de la reserva del/los huéspedes
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarHuespedReservaPorId (PersistenceManager pm, long idReservaHabitacion)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + ph.darTablaHuespedReserva () + " WHERE ID_RESERVA_HABITACION = ?");
        q.setParameters(idReservaHabitacion);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UN HUESPEDRESERVA de la 
	 * base de datos de Hotelandes, por el identificador de una reserva
	 * @param pm - El manejador de persistencia
	 * @param idReservaHabitacion - El identificador de la reserva del/los huéspedes
	 * @return El objeto HUESPEDRESERVA que tiene el identificador dado
	 */
	public HuespedReserva darHuespedReservaPorIdReservaHabitacion (PersistenceManager pm, long idReservaHabitacion) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaHuespedReserva () + " WHERE ID_RESERVA_HABITACION = ?");
		q.setResultClass(HuespedReserva.class);
		q.setParameters(idReservaHabitacion);
		return (HuespedReserva) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS HUESPEDESRESERVA de la 
	 * base de datos de Hotelandes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos HUESPEDRESERVA
	 */
	public List<HuespedReserva> darHuespedesReserva (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaHuespedReserva ());
		q.setResultClass(HuespedReserva.class);
		return (List<HuespedReserva>) q.executeList();
	}
	public HuespedReserva darHuespedReservaPorId(PersistenceManager pm, long id) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaHuespedReserva () + " WHERE id = ?");
		q.setResultClass(HuespedReserva.class);
		q.setParameters(id);
		return (HuespedReserva) q.executeUnique();
	}
	public List<Object[]> darConsumoPorUsuario(PersistenceManager pm,String tipoDoc,int numDoc,String fechaInf,String fechaSup)
	{
		String peticion;
		peticion = "SELECT CUENTA_SERVICIO.ID_SERVICIO,CUENTA_SERVICIO.FECHA,CUENTA_SERVICIO.CONCEPTO,CUENTA_SERVICIO.COSTO";
		String tabla = "(SELECT ID_RESERVA_HABITACION FROM HUESPED_RESERVA WHERE TIPO_DOCUMENTO = ? AND NUMERO_DOCUMENTO = ?) B";
		peticion+=" FROM "+ph.darTablaCuentaServicio() +" INNER JOIN "+tabla;
		peticion+=" ON B.ID_RESERVA_HABITACION = CUENTA_SERVICIO.ID_RESERVA_HABITACION";
		peticion +=" WHERE CUENTA_SERVICIO.FECHA >= ? AND CUENTA_SERVICIO.FECHA <= ?";
		Query q = pm.newQuery(SQL,peticion);
		q.setParameters(tipoDoc,numDoc,fechaInf,fechaSup);
		return q.executeList();
	}
}
