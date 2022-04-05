
package uniandes.isis2304.hotelandes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.hotelandes.negocio.RolUsuario;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto ROLUSUARIO de Hotelandes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Equipo C-09
 */
class SQLRolUsuario 
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
	public SQLRolUsuario (PersistenciaHotelandes ph)
	{
		this.ph = ph;
	}

	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un ROLUSUARIO a la base de datos de Hotelandes
	 * @param pm - El manejador de persistencia
	 * @param rol - Cada posible rol de un usuario
	 * @return El número de tuplas insertadas
	 */
	public long adicionarRolUsuario (PersistenceManager pm, String rol)
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + ph.darTablaRolUsuario () + "(rol) values (?)");
        q.setParameters(rol);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UN ROLUSUARIO de la base de datos de Hotelandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param rol - El identificador del rol de usuario
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarRolUsuarioPorId (PersistenceManager pm, String rol)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + ph.darTablaRolUsuario() + " WHERE rol = ?");
        q.setParameters(rol);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UN ROLUSUARIO de la 
	 * base de datos de Hotelandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param ROL - El identificador del rol de usuario
	 * @return El objeto ROLUSUARIO que tiene el identificador dado
	 */
	public RolUsuario darRolUsuarioPorId (PersistenceManager pm, String rol) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaRolUsuario () + " WHERE rol = ?");
		q.setResultClass(RolUsuario.class);
		q.setParameters(rol);
		return (RolUsuario) q.executeUnique();
	}


	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS ROLESUSUARIO de la 
	 * base de datos de Hotelandes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos ROLUSUARIO
	 */
	public List<RolUsuario> darRolesUsuario (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaRolUsuario ());
		q.setResultClass(RolUsuario.class);
		return (List<RolUsuario>) q.executeList();
	}
	
}
