package uniandes.isis2304.hotelandes.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

/**
 * Clase que encapsula los m�todos que hacen acceso a la base de datos
 */
public class SQLUtil {
	
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Cadena que representa el tipo de consulta que se va a realizar en las sentencias de acceso a la base de datos
	 * Se renombra ac� para facilitar la escritura de las sentencias
	 */
	private final static String SQL = PersistenciaHotelandes.SQL;

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia general de la aplicaci�n
	 */
	private PersistenciaHotelandes ph;

	/* ****************************************************************
	 * 			M�todos
	 *****************************************************************/

	/**
	 * Constructor
	 * @param ph - El Manejador de persistencia de la aplicaci�n
	 */
	public SQLUtil (PersistenciaHotelandes ph)
	{
		this.ph = ph;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para obtener un nuevo n�mero de secuencia
	 * @param pm - El manejador de persistencia
	 * @return El n�mero de secuencia generado
	 */
	public long nextval (PersistenceManager pm)
	{
        Query q = pm.newQuery(SQL, "SELECT "+ ph.darSeqHotelAndes () + ".nextval FROM DUAL");
        q.setResultClass(Long.class);
        long resp = (long) q.executeUnique();
        return resp;
	}

	public long [] limpiarHotelandes (PersistenceManager pm)
	{
		Query qCadenaHotelera = pm.newQuery(SQL,"DELETE FROM "+ph.darTablaCadenaHotelera());
		Query qTipoHabitacion = pm.newQuery(SQL,"DELETE FROM "+ph.darTablaTipoHabitacion());
		Query qTipoServicio = pm.newQuery(SQL,"DELETE FROM "+ph.darTablaServicio());
		Query qHotel = pm.newQuery(SQL,"DELETE FROM "+ph.darTablaHotel());
		Query qUsuario = pm.newQuery(SQL,"DELETE FROM "+ph.darTablaUsuario());
		Query qHabitacion = pm.newQuery(SQL,"DELETE FROM "+ph.darTablaHabitacion());
		Query qServicio = pm.newQuery(SQL,"DELETE FROM "+ph.darTablaServicio());
		Query qPiscina = pm.newQuery(SQL,"DELETE FROM "+ph.darTablaPiscina());
		Query qGimnasio = pm.newQuery(SQL,"DELETE FROM "+ph.darTablaGimnasio());
		Query qInternet = pm.newQuery(SQL,"DELETE FROM "+ph.darTablaInternet());
		Query qEstablecimiento = pm.newQuery(SQL,"DELETE FROM "+ph.darTablaEstablecimiento());
		Query qPrestamoUtensilio = pm.newQuery(SQL,"DELETE FROM "+ph.darTablaPrestamoUtensilio());
		Query qSalonConferencia = pm.newQuery(SQL,"DELETE FROM "+ph.darTablaSalonConferencia());
		Query qSalonReunion = pm.newQuery(SQL,"DELETE FROM "+ph.darTablaSalonReunion());
		Query qSpaa = pm.newQuery(SQL,"DELETE FROM "+ph.darTablaSpaa());
		Query qProducto = pm.newQuery(SQL,"DELETE FROM "+ph.darTablaProducto());
		Query qPlanConsumo =pm.newQuery(SQL,"DELETE FROM "+ph.darTablaPlanConsumo());
		Query qReservaHabitacion = pm.newQuery(SQL,"DELETE FROM "+ph.darTablaReservaHabitacion());
		Query qHuespedReserva = pm.newQuery(SQL,"DELETE FROM "+ph.darTablaHuespedReserva());
		Query qCuentaServicio = pm.newQuery(SQL,"DELETE FROM "+ph.darTablaCuentaServicio());
		Query qPedido = pm.newQuery(SQL,"DELETE FROM "+ph.darTablaPedido());
		Query qProductoPedido = pm.newQuery(SQL,"DELETE FROM "+ph.darTablaProductoPedido());
		Query qReservaServicio = pm.newQuery(SQL,"DELETE FROM "+ph.darTablaReservaServicio());

		long cadenaHoteleraE = (long) qCadenaHotelera.executeUnique();
		long tipoHabitacionE = (long) qTipoHabitacion.executeUnique();
		long tipoServicioE = (long) qTipoServicio.executeUnique();
		long hotelE = (long) qHotel.executeUnique();
		long usuarioE = (long) qUsuario.executeUnique();
		long habitacionE = (long) qHabitacion.executeUnique();
		long servicioE = (long) qServicio.executeUnique();
		long piscinaE = (long) qPiscina.executeUnique();
		long gimnasioE = (long) qGimnasio.executeUnique();
		long internetE = (long) qInternet.executeUnique();
		long establecimientoE = (long) qEstablecimiento.executeUnique();
		long prestamoUtensilioE = (long) qPrestamoUtensilio.executeUnique();
		long salonConferenciasE = (long) qSalonConferencia.executeUnique();
		long salonReunionE = (long) qSalonReunion.executeUnique();
		long spaaE = (long) qSpaa.executeUnique();
		long productoE = (long) qProducto.executeUnique();
		long planConsumoE = (long) qPlanConsumo.executeUnique();
		long reservaHabitacionE = (long) qReservaHabitacion.executeUnique();
		long huespedReservaE = (long) qHuespedReserva.executeUnique();
		long cuentaServicioE = (long) qCuentaServicio.executeUnique();
		long pedidoE = (long) qPedido.executeUnique();
		long productoPedidoE = (long) qProductoPedido.executeUnique();
		long reservaServicioE = (long) qReservaServicio.executeUnique();
	
		return new long[] {cadenaHoteleraE, tipoHabitacionE, tipoServicioE, hotelE, 
			usuarioE, habitacionE, servicioE,piscinaE,gimnasioE,internetE,establecimientoE,
			prestamoUtensilioE,salonConferenciasE,salonReunionE,spaaE,productoE,planConsumoE,
			reservaHabitacionE,huespedReservaE,cuentaServicioE,pedidoE,productoPedidoE,reservaServicioE};
	}
	
}
