
package uniandes.isis2304.hotelandes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.hotelandes.negocio.Producto;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto PRODUCTO de Hotelandes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Equipo C-09
 */
class SQLProducto 
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
	public SQLProducto (PersistenciaHotelandes ph)
	{
		this.ph = ph;
	}

	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un PRODUCTO a la base de datos de Hotelandes
	 * @param pm - El manejador de persistencia
	 * @param idServicio - Id del servicio que ofrece el producto
	 * @param idProducto - Id del producto dentro del servicio que lo ofrece
	 * @param nombre - Nombre del producto
	 * @param costo - Costo del producto
	 * @param planTodoIncluido - Booleano (0 o 1) que indica si el producto está incluido en el plan "Todo Incluido"
	 * @param duracion - Si el producto es un producto de SPA (masaje, etc) tiene una duración, de lo contrario no
	 * @return El número de tuplas insertadas
	 */
	public long adicionarProducto (PersistenceManager pm, long idServicio, long idProducto, String nombre, double costo, int planTodoIncluido, String duracion)
	{

		Query q;
		if (duracion == "0"){
			q = pm.newQuery(SQL, "INSERT INTO " + ph.darTablaProducto () + "(ID_SERVICIO, ID_PRODUCTO, nombre, costo, PLAN_TODO_INCLUIDO) values (?, ?, ?, ?, ?)");
        	q.setParameters(idServicio, idProducto, nombre, costo, planTodoIncluido);
		}
		else{
			q = pm.newQuery(SQL, "INSERT INTO " + ph.darTablaProducto () + "(ID_SERVICIO, ID_PRODUCTO, nombre, costo, PLAN_TODO_INCLUIDO, duracion) values (?, ?, ?, ?, ?, ?)");
			q.setParameters(idServicio, idProducto, nombre, costo, planTodoIncluido, duracion);
		}
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar PRODUCTOS de la base de datos de Hotelandes, por su nombre
	 * @param pm - El manejador de persistencia
	 * @param nombreProducto - El nombre del producto
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarProductoPorId (PersistenceManager pm, long idServicio,long idProducto)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + ph.darTablaProducto () + " WHERE ID_SERVICIO = ? AND ID_PRODUCTO = ?");
		q.setParameters(idServicio, idProducto);
		return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS PRODUCTOS de la 
	 * base de datos de Hotelandes, por su nombre
	 * @param pm - El manejador de persistencia
	 * @param nombreProducto - El nombre de producto buscado
	 * @return Una lista de objetos PRODUCTO que tienen el nombre dado
	 */
	public List<Producto> darProductosPorServicio (PersistenceManager pm, long idServicio) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaProducto () + " WHERE ID_SERVICIO = ?");
		q.setResultClass(Producto.class);
		q.setParameters(idServicio);
		return (List<Producto>) q.executeList();
	}
	public Producto darProductoPorId (PersistenceManager pm, long idServicio, long idProducto) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaProducto () + " WHERE ID_SERVICIO = ? AND ID_PRODUCTO = ?");
		q.setResultClass(Producto.class);
		q.setParameters(idServicio, idProducto);
		return (Producto) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS PRODUCTOS de la 
	 * base de datos de Hotelandes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos PRODUCTO
	 */
	public List<Producto> darProductos (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaProducto ());
		q.setResultClass(Producto.class);
		return (List<Producto>) q.executeList();
	}
	
}
