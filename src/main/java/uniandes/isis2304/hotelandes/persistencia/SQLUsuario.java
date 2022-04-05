
package uniandes.isis2304.hotelandes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.hotelandes.negocio.TipoDocumento;
import uniandes.isis2304.hotelandes.negocio.Usuario;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto USUARIO de Hotelandes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Equipo C-09
 */
class SQLUsuario 
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
	public SQLUsuario (PersistenciaHotelandes ph)
	{
		this.ph = ph;
	}

	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un USUARIO a la base de datos de Hotelandes
	 * @param pm - El manejador de persistencia
	 * @param nombreHotel - Nombre del hotel en el que se registró el usuario
	 * @param nombre - Nombre del usuario
	 * @param tipoDocumento - Tipo de documento del usuario
	 * @param numeroDocumento - Número de documento del usuario
	 * @param correo - Correo del usuario
	 * @param contrasena - Contraseña del usuario en Hotelandes
	 * @param rolUsuario - Rol del usuario
	 * @return El número de tuplas insertadas
	 */
	public long adicionarUsuario (PersistenceManager pm, String nombre, TipoDocumento tipoDocumento, int numeroDocumento, String correo, String contrasena, String rolUsuario)
	{
		String tipoDoc = tipoDocumento.toString();
        Query q = pm.newQuery(SQL, "INSERT INTO " + ph.darTablaUsuario () + "(NOMBRE, TIPO_DOCUMENTO, NUMERO_DOCUMENTO, CORREO, CONTRASENA, ROL_USUARIO) values (?, ?, ?, ?, ?, ?)");
        q.setParameters(nombre, tipoDoc, numeroDocumento, correo, contrasena, rolUsuario);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar USUARIOS de la base de datos de Hotelandes, por su nombre
	 * @param pm - El manejador de persistencia
	 * @param nombreUsuario - El nombre del usuario
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarUsuarioPorId (PersistenceManager pm, TipoDocumento tipoDocumento,int numeroDocumento) 
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + ph.darTablaUsuario () + " WHERE TIPO_DOCUMENTO = ? AND NUMERO_DOCUMENTO = ?");
		q.setParameters(tipoDocumento, numeroDocumento);
		return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS USUARIOS de la 
	 * base de datos de Hotelandes, por su nombre
	 * @param pm - El manejador de persistencia
	 * @param nombreUsuario - El nombre de usuario buscado
	 * @return Una lista de objetos USUARIO que tienen el nombre dado
	 */
	public List<Usuario> darUsuariosPorNombre (PersistenceManager pm, String nombreUsuario) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaUsuario () + " WHERE nombre = ?");
		q.setResultClass(Usuario.class);
		q.setParameters(nombreUsuario);
		return (List<Usuario>) q.executeList();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS USUARIOS de la 
	 * base de datos de Hotelandes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos USUARIO
	 */
	public List<Usuario> darUsuarios (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaUsuario ());
		q.setResultClass(Usuario.class);
		return (List<Usuario>) q.executeList();
	}
	public Usuario darUsuarioPorId(PersistenceManager pm, TipoDocumento tipoDocumento, int numeroDocumento) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaUsuario () + " WHERE tipoDocumento = ? AND numeroDocumento = ?");
		q.setResultClass(Usuario.class);
		q.setParameters(tipoDocumento, numeroDocumento);
		return (Usuario) q.executeUnique();
	}
	
}
