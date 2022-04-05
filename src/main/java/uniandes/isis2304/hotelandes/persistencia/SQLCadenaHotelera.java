
package uniandes.isis2304.hotelandes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.hotelandes.negocio.CadenaHotelera;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto CADENAHOTELERA de Hotelandes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Equipo C-09
 */
class SQLCadenaHotelera 
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
	public SQLCadenaHotelera (PersistenciaHotelandes ph)
	{
		this.ph = ph;
	}

	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un CADENAHOTELERA a la base de datos de Hotelandes
	 * @param pm - El manejador de persistencia
	 * @param nombre - El nombre de la cadena hotelera
	 * @return El número de tuplas insertadas
	 */
	public long adicionarCadenaHotelera (PersistenceManager pm, String nombre)
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + ph.darTablaCadenaHotelera () + "(nombre) values (?)");
        q.setParameters(nombre);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UN CADENAHOTELERA de la base de datos de Hotelandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param nombre - El identificador del cadenahotelera (el nombre)
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarCadenaHoteleraPorId (PersistenceManager pm, String nombre)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + ph.darTablaCadenaHotelera() + " WHERE nombre = ?");
        q.setParameters(nombre);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UN CADENAHOTELERA de la 
	 * base de datos de Hotelandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param nombre - El identificador del cadenahotelera (el nombre)
	 * @return El objeto HOTEL que tiene el identificador dado
	 */
	public CadenaHotelera darCadenaHoteleraPorId (PersistenceManager pm, String nombre) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaCadenaHotelera() + " WHERE nombre = ?");
		q.setResultClass(CadenaHotelera.class);
		q.setParameters(nombre);
		return (CadenaHotelera) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS CADENASHOTELERAS de la 
	 * base de datos de Hotelandes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos CADENAHOTELERA
	 */
	public List<CadenaHotelera> darCadenasHoteleras (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaCadenaHotelera ());
		q.setResultClass(CadenaHotelera.class);
		return (List<CadenaHotelera>) q.executeList();
	}
	
}
