
package uniandes.isis2304.hotelandes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.hotelandes.negocio.TipoServicio;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto TIPOSERVICIO de Hotelandes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Equipo C-09
 */
class SQLTipoServicio 
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
	public SQLTipoServicio (PersistenciaHotelandes ph)
	{
		this.ph = ph;
	}

	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un TIPOSERVICIO a la base de datos de Hotelandes
	 * @param pm - El manejador de persistencia
	 * @param tipoServicio - Cada posible tipo de servicio
	 * @return El número de tuplas insertadas
	 */
	public long adicionarTipoServicio (PersistenceManager pm, String tipoServicio)
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + ph.darTablaTipoServicio () + "(TIPO_SERVICIO) values (?)");
        q.setParameters(tipoServicio);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS TIPOSSERVICIOS de la 
	 * base de datos de Hotelandes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos TIPOSERVICIO
	 */
	public List<TipoServicio> darTiposServicios (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaTipoServicio ());
		q.setResultClass(TipoServicio.class);
		return (List<TipoServicio>) q.executeList();
	}
	public long eliminarTipoServicioPorId (PersistenceManager pm, String tipoServicio)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + ph.darTablaTipoServicio () + " WHERE TIPO_SERVICIO = ?");
		q.setParameters(tipoServicio);
		return (long) q.executeUnique();
	}
	public TipoServicio darTipoServicioPorId (PersistenceManager pm, String tipoServicio)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaTipoServicio () + " WHERE TIPO_SERVICIO = ?");
		q.setResultClass(TipoServicio.class);
		q.setParameters(tipoServicio);
		return (TipoServicio) q.executeUnique();
	}
	
}
