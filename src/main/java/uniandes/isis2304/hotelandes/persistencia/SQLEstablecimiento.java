
package uniandes.isis2304.hotelandes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.hotelandes.negocio.Establecimiento;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto ESTABLECIMIENTO de Hotelandes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Equipo C-09
 */
class SQLEstablecimiento 
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
	public SQLEstablecimiento (PersistenciaHotelandes ph)
	{
		this.ph = ph;
	}

	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un ESTABLECIMIENTO a la base de datos de Hotelandes
	 * @param pm - El manejador de persistencia
	 * @param idServicio - Id del establecimiento
	 * @param nombre - Nombre del establecimiento
	 * @param capacidad - Capacidad de personas del establecimiento (si tiene)
	 * @param estiloTipo - Estilo o tipo del establecimiento
	 * @param descuentoPlan - Descuento en un plan específico, del establecimiento
	 * @return El número de tuplas insertadas
	 */
	public long adicionarEstablecimiento (PersistenceManager pm, long idServicio, String nombre, int capacidad, String estiloTipo, double descuentoPlan)
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + ph.darTablaEstablecimiento () + "(idServicio, nombre, capacidad, estiloTipo, descuentoPlan) values (?, ?, ?, ?, ?)");
        q.setParameters(idServicio, nombre, capacidad, estiloTipo, descuentoPlan);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar ESTABLECIMIENTOS de la base de datos de Hotelandes, por su nombre
	 * @param pm - El manejador de persistencia
	 * @param nombreEstablecimiento - El nombre del establecimiento
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarEstablecimientosPorNombre (PersistenceManager pm, String nombreEstablecimiento)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + ph.darTablaEstablecimiento () + " WHERE nombre = ?");
        q.setParameters(nombreEstablecimiento);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS ESTABLECIMIENTOS de la 
	 * base de datos de Hotelandes, por su nombre
	 * @param pm - El manejador de persistencia
	 * @param nombreEstablecimiento - El nombre de establecimiento buscado
	 * @return Una lista de objetos ESTABLECIMIENTO que tienen el nombre dado
	 */
	public List<Establecimiento> darEstablecimientosPorNombre (PersistenceManager pm, String nombreEstablecimiento) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaEstablecimiento () + " WHERE nombre = ?");
		q.setResultClass(Establecimiento.class);
		q.setParameters(nombreEstablecimiento);
		return (List<Establecimiento>) q.executeList();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UN ESTABLECIMIENTO de la base de datos de Hotelandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idServicio - El identificador del establecimiento
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarEstablecimientoPorId (PersistenceManager pm, long idServicio)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + ph.darTablaEstablecimiento () + " WHERE idServicio = ?");
        q.setParameters(idServicio);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UN ESTABLECIMIENTO de la 
	 * base de datos de Hotelandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idServicio - El identificador del establecimiento
	 * @return El objeto ESTABLECIMIENTO que tiene el identificador dado
	 */
	public Establecimiento darEstablecimientoPorId (PersistenceManager pm, long idServicio) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaEstablecimiento () + " WHERE idServicio = ?");
		q.setResultClass(Establecimiento.class);
		q.setParameters(idServicio);
		return (Establecimiento) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS ESTABLECIMIENTOS de la 
	 * base de datos de Hotelandes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos ESTABLECIMIENTO
	 */
	public List<Establecimiento> darEstablecimientos (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaEstablecimiento ());
		q.setResultClass(Establecimiento.class);
		return (List<Establecimiento>) q.executeList();
	}
	
}
