
package uniandes.isis2304.hotelandes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.hotelandes.negocio.Pedido;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto PEDIDO de Hotelandes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Equipo C-09
 */
class SQLPedido 
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
	public SQLPedido (PersistenciaHotelandes ph)
	{
		this.ph = ph;
	}

	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un PEDIDO a la base de datos de Hotelandes
	 * @param pm - El manejador de persistencia
	 * @param idPedido - Id del pedido
	 * @param pagoHabitacion - Booleano (0 o 1) que indica si el pedido se carga a la habitación o no
	 * @param idCuenta - Id de la cuenta a la que se carga el pedido
	 * @return El número de tuplas insertadas
	 */
	public long adicionarPedido (PersistenceManager pm, long idPedido, int pagoHabitacion, long idCuenta)
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + ph.darTablaPedido () + "(ID_PEDIDO, PAGO_HABITACION, ID_CUENTA) values (?, ?, ?)");
        q.setParameters(idPedido, pagoHabitacion, idCuenta);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UN PEDIDO de la base de datos de Hotelandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idPedido - El identificador del pedido
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarPedidoPorId (PersistenceManager pm, long idPedido)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + ph.darTablaPedido () + " WHERE ID_PEDIDO = ?");
        q.setParameters(idPedido);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UN PEDIDO de la 
	 * base de datos de Hotelandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idPedido - El identificador del pedido
	 * @return El objeto PEDIDO que tiene el identificador dado
	 */
	public Pedido darPedidoPorId (PersistenceManager pm, long idPedido) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaPedido () + " WHERE ID_PEDIDO = ?");
		q.setResultClass(Pedido.class);
		q.setParameters(idPedido);
		return (Pedido) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS PEDIDOS de la 
	 * base de datos de Hotelandes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos PEDIDO
	 */
	public List<Pedido> darPedidos (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaPedido ());
		q.setResultClass(Pedido.class);
		return (List<Pedido>) q.executeList();
	}
	
}
