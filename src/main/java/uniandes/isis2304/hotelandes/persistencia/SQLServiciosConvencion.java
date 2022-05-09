package uniandes.isis2304.hotelandes.persistencia;


import javax.jdo.PersistenceManager;
import javax.jdo.Query;


/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto CUENTASERVICIO de Hotelandes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Equipo C-09
 */
class SQLServiciosConvencion 
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
	public SQLServiciosConvencion (PersistenciaHotelandes ph)
	{
		this.ph = ph;
	}

	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un CUENTASERVICIO a la base de datos de Hotelandes
     * @param pm - El manejador de persistencia
	 * @return El número de tuplas insertadas
	 */
	public long adicionarServiciosConvencion (PersistenceManager pm, String idConvencion, String idServicio)
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + ph.darTablaServiciosConvencion() + "(ID_CONVENCION, ID_SERVICIO) values (?, ?)");
        q.setParameters(idConvencion, idServicio);
        return (long) q.executeUnique();
	}
	
}


