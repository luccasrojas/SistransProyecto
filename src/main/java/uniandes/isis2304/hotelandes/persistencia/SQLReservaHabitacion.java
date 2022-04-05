
package uniandes.isis2304.hotelandes.persistencia;

import java.util.List;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.hotelandes.negocio.ReservaHabitacion;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto RESERVAHABITACION de Hotelandes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Equipo C-09
 */
class SQLReservaHabitacion 
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
	public SQLReservaHabitacion (PersistenciaHotelandes ph)
	{
		this.ph = ph;
	}

	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un RESERVAHABITACION a la base de datos de Hotelandes
	 * @param pm - El manejador de persistencia
	 * @param idReservaHabitacion - Id de la reserva de habitación
	 * @param fechaIn - Fecha de ingreso de la reserva de habitación
	 * @param fechaOut - Fecha de salida de la reserva de habitación
	 * @param numPersonas - Número de personas reservadas para la habitación
	 * @param cuentaMinibar - Cuenta que se lleva del minibar en la reserva, si la habitación tiene minibar
	 * @param nombreHotel - Nombre del hotel en el que se reservó
	 * @param numeroHabitacion - Número de la habitación reservada
	 * @param idPlanConsumo - Id del plan de consumo con el que se costeará la reserva
	 * @return El número de tuplas insertadas
	 */
	public long adicionarReservaHabitacion (PersistenceManager pm, long idReservaHabitacion, String fechaIn, String fechaOut, int numPersonas, String nombreHotel, long idPlanConsumo, int pagado)
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + ph.darTablaReservaHabitacion () + "(ID_RESERVA_HABITACION, FECHA_IN, FECHA_OUT, NUM_PERSONAS, CUENTA_MINIBAR, NOMBRE_HOTEL, ID_PLAN_CONSUMO, PAGADO) values (?, ?, ?, ?, ?, ?, ?, ?)");
        q.setParameters(idReservaHabitacion, fechaIn, fechaOut, numPersonas, 0, nombreHotel, idPlanConsumo, pagado);
        return (long) q.executeUnique();
	}

	public long actualizarHabitacion (PersistenceManager pm, long idReservaHabitacion, int numeroHabitacion)
	{
		String peticion="UPDATE "+ph.darTablaReservaHabitacion()+" SET NUMERO_HABITACION = " + numeroHabitacion + " WHERE ID_RESERVA_HABITACION = "+ idReservaHabitacion;
		Query q = pm.newQuery(SQL,peticion);
		return (long) q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UN RESERVAHABITACION de la base de datos de Hotelandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idReservaHabitacion - El identificador del reservahabitacion
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarReservaHabitacionPorId (PersistenceManager pm, long idReservaHabitacion)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + ph.darTablaReservaHabitacion () + " WHERE ID_RESERVA_HABITACION = ?");
        q.setParameters(idReservaHabitacion);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UN RESERVAHABITACION de la 
	 * base de datos de Hotelandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idReservaHabitacion - El identificador del reservahabitacion
	 * @return El objeto RESERVAHABITACION que tiene el identificador dado
	 */
	public ReservaHabitacion darReservaHabitacionPorId (PersistenceManager pm, long idReservaHabitacion) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaReservaHabitacion () + " WHERE ID_RESERVA_HABITACION = ?");
		q.setResultClass(ReservaHabitacion.class);
		q.setParameters(idReservaHabitacion);
		return (ReservaHabitacion) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS RESERVASHABITACIONES de la 
	 * base de datos de Hotelandes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos RESERVAHABITACION
	 */
	public List<ReservaHabitacion> darReservasHabitaciones (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaReservaHabitacion ());
		q.setResultClass(ReservaHabitacion.class);
		return (List<ReservaHabitacion>) q.executeList();
	}
/*
	public class Consulta
	{
		public double numero;
		
		public Consulta()
		{
			this.numero = 0;
		}
		public Consulta(double numero)
		{
			this.numero=numero;
		}
		
		public double getNumero() {
			return numero;
		}
		public void setNumero(double numero) {
			this.numero = numero;
		}
		@Override
		public String toString() {
			return "Consulta [numero=" + numero + "]";
		}			
	}
*/
	public String registrarSalida(PersistenceManager pm, String idReservaHabitacion)
	{
		String peticion="SELECT A.A_PAGAR+B.A_PAGAR AS A_PAGAR";
		String tabla1="SELECT ID_RESERVA_HABITACION AS ID_RESERVA_HABITACION, SUM(COSTO) AS A_PAGAR FROM " + ph.darTablaCuentaServicio()+" WHERE ID_RESERVA_HABITACION = "+idReservaHabitacion+" GROUP BY ID_RESERVA_HABITACION";
		String tabla2 = "SELECT ID_RESERVA_HABITACION AS ID_RESERVA_HABITACION,CUENTA_MINIBAR AS A_PAGAR FROM " + ph.darTablaReservaHabitacion()+" WHERE ID_RESERVA_HABITACION =  "+idReservaHabitacion;
		peticion+=" FROM( ("+tabla1+") A"+ " FULL OUTER JOIN ( "+tabla2+") B ON A.ID_RESERVA_HABITACION = B.ID_RESERVA_HABITACION)";
		Query q = pm.newQuery(SQL,peticion);
		/*  UPDATE RESERVA_HABITACION SET pagado = 1 WHERE ID_RESERVA_HABITACION = ? */
		return q.executeUnique().toString();
	}

	public void registrarSalidaCambiarEstado(PersistenceManager pm, String idReservaHabitacion)
	{
		String peticion="UPDATE "+ph.darTablaReservaHabitacion()+" SET PAGADO = 1 WHERE ID_RESERVA_HABITACION = "+ idReservaHabitacion;
		Query q = pm.newQuery(SQL,peticion);
		q.executeUnique();
	}
	

	
}
