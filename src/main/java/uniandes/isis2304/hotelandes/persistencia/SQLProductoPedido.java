
package uniandes.isis2304.hotelandes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.hotelandes.negocio.ProductoPedido;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto PRODUCTOPEDIDO de Hotelandes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Equipo C-09
 */
class SQLProductoPedido 
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
	public SQLProductoPedido (PersistenciaHotelandes ph)
	{
		this.ph = ph;
	}

	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un PRODUCTOPEDIDO a la base de datos de Hotelandes
	 * @param pm - El manejador de persistencia
	 * @param idPedido - Id del pedido
	 * @param idServicio - Id del servicio que ofrece los productos del pedido
	 * @param idProducto - Id de un producto que pertenece al pedido
	 * @param cantidad - Cantidad del producto que se incluyeron en el pedido
	 * @return El número de tuplas insertadas
	 */
	public long adicionarProductoPedido (PersistenceManager pm, long idPedido, long idServicio, long idProducto, int cantidad)
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + ph.darTablaProductoPedido () + "(ID_PEDIDO, ID_SERVICIO, ID_PRODUCTO, cantidad) values (?, ?, ?, ?)");
        q.setParameters(idPedido, idServicio, idProducto, cantidad);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar LOS PRODUCTOPEDIDO de la base de datos de Hotelandes, por su pedido
	 * @param pm - El manejador de persistencia
	 * @param idPedido - El identificador del pedido
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarProductoPedidoPorId (PersistenceManager pm, long idPedido,long idServicio, long idProducto)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + ph.darTablaProductoPedido () + " WHERE ID_PEDIDO = ? AND ID_SERVICIO = ? AND ID_PRODUCTO = ?");
		q.setParameters(idPedido, idServicio, idProducto);
		return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UN PRODUCTOPEDIDO de la 
	 * base de datos de Hotelandes, por su pedido
	 * @param pm - El manejador de persistencia
	 * @param idPedido - El identificador del productopedido
	 * @return El objeto PRODUCTOPEDIDO que tiene el identificador dado
	 */
	public ProductoPedido darProductoPedidoPorIdPedido (PersistenceManager pm, long idPedido) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaProductoPedido () + " WHERE ID_PEDIDO = ?");
		q.setResultClass(ProductoPedido.class);
		q.setParameters(idPedido);
		return (ProductoPedido) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS PRODUCTOSPEDIDOS de la 
	 * base de datos de Hotelandes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos PRODUCTOPEDIDO
	 */
	public List<ProductoPedido> darProductosPedidos (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaProductoPedido ());
		q.setResultClass(ProductoPedido.class);
		return (List<ProductoPedido>) q.executeList();
	}
	public ProductoPedido darProductoPedidoPorId(PersistenceManager pm, long idPedido,long idServicio,long idProducto)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaProductoPedido () + " WHERE ID_PEDIDO = ? AND ID_SERVICIO = ? AND ID_PRODUCTO = ?");
		q.setResultClass(ProductoPedido.class);
		q.setParameters(idPedido, idServicio, idProducto);
		return (ProductoPedido) q.executeUnique();
	} 	
}
