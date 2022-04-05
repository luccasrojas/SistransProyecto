
package uniandes.isis2304.hotelandes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.hotelandes.negocio.PlanConsumo;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto PLANCONSUMO de Hotelandes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Equipo C-09
 */
class SQLPlanConsumo 
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
	public SQLPlanConsumo (PersistenciaHotelandes ph)
	{
		this.ph = ph;
	}

	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un PLANCONSUMO a la base de datos de Hotelandes
	 * @param pm - El manejador de persistencia
	 * @param idPlanConsumo - Id del plan de consumo
	 * @param nombreHotel - Nombre del hotel que ofrece el plan de consumo
	 * @param nombre - Nombre del plan de consumo
	 * @param duracionMin - Número de noches mínimas de hospedaje para que el plan de consumo se pueda aplicar
	 * @param descuento - Porcentaje de descuento en hospedaje del plan de consumo
	 * @return El número de tuplas insertadas
	 */
	public long adicionarPlanConsumo (PersistenceManager pm,String nombreHotel, long idPlanConsumo, String nombre, int duracionMin, double descuento)
	{
		Query q = pm.newQuery(SQL, "INSERT INTO " + ph.darTablaPlanConsumo () + "(ID_PLAN_CONSUMO, NOMBRE_HOTEL, nombre, DURACION_MIN, descuento) values (?, ?, ?, ?, ?)");
		q.setParameters(idPlanConsumo, nombreHotel, nombre, duracionMin, descuento);
		return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar PLANESCONSUMO de la base de datos de Hotelandes, por su nombre
	 * @param pm - El manejador de persistencia
	 * @param nombrePlanConsumo - El nombre del planconsumo
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarPlanesConsumoPorNombre (PersistenceManager pm, String nombrePlanConsumo)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + ph.darTablaPlanConsumo () + " WHERE nombre = ?");
        q.setParameters(nombrePlanConsumo);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS PLANESCONSUMO de la 
	 * base de datos de Hotelandes, por su nombre
	 * @param pm - El manejador de persistencia
	 * @param nombrePlanConsumo - El nombre de planconsumo buscado
	 * @return Una lista de objetos PLANCONSUMO que tienen el nombre dado
	 */
	public List<PlanConsumo> darPlanesConsumoPorNombre (PersistenceManager pm, String nombrePlanConsumo) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaPlanConsumo () + " WHERE nombre = ?");
		q.setResultClass(PlanConsumo.class);
		q.setParameters(nombrePlanConsumo);
		return (List<PlanConsumo>) q.executeList();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UN PLANCONSUMO de la base de datos de Hotelandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idPlanConsumo - El identificador del planconsumo
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarPlanConsumoPorId (PersistenceManager pm, long idPlanConsumo)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + ph.darTablaPlanConsumo () + " WHERE ID_PLAN_CONSUMO = ?");
        q.setParameters(idPlanConsumo);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UN PLANCONSUMO de la 
	 * base de datos de Hotelandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idPlanConsumo - El identificador del planconsumo
	 * @return El objeto PLANCONSUMO que tiene el identificador dado
	 */
	public PlanConsumo darPlanConsumoPorId (PersistenceManager pm, long idPlanConsumo) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaPlanConsumo () + " WHERE ID_PLAN_CONSUMO = ?");
		q.setResultClass(PlanConsumo.class);
		q.setParameters(idPlanConsumo);
		return (PlanConsumo) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS PLANESCONSUMO de la 
	 * base de datos de Hotelandes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos PLANCONSUMO
	 */
	public List<PlanConsumo> darPlanesConsumo (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaPlanConsumo ());
		q.setResultClass(PlanConsumo.class);
		return (List<PlanConsumo>) q.executeList();
	}
	
}
