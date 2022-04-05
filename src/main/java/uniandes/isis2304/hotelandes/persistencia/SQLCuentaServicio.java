
package uniandes.isis2304.hotelandes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.hotelandes.negocio.CuentaServicio;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto CUENTASERVICIO de Hotelandes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Equipo C-09
 */
class SQLCuentaServicio 
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
	public SQLCuentaServicio (PersistenciaHotelandes ph)
	{
		this.ph = ph;
	}

	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un CUENTASERVICIO a la base de datos de Hotelandes
	 * @param pm - El manejador de persistencia
	 * @param idCuenta - Id de la cuenta de un servicio
	 * @param costo - Costo total de la cuenta
	 * @param concepto - Concepto o descripción de qué se está cobrando exactamente en la cuenta
	 * @param fecha - Fecha en la que se generó la cuenta
	 * @param idServicio - Id del servicio al que se atribuye la cuenta
	 * @param idReservaHabitacion - Id de la reserva de la habitación a la que se carga la cuenta
	 * @param registradoPorTipoDocumento - Tipo de documento del usuario que registró la cuenta
	 * @param registradoPorNumeroDocumento - Número de documento del usuario que registró la cuenta
	 * @return El número de tuplas insertadas
	 */
	public long adicionarCuentaServicio (PersistenceManager pm, String idCuenta, String costo, String concepto, String fecha, String idServicio, String idReservaHabitacion, String registradoPorTipoDocumento, String registradoPorNumeroDocumento)
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + ph.darTablaCuentaServicio () + "(ID_CUENTA, costo, concepto, fecha, ID_SERVICIO, ID_RESERVA_HABITACION, REGISTRADO_POR_TIPO_DOCUMENTO, REGISTRADO_POR_NUMERO_DOCUMENTO) values (?, ?, ?, ?, ?, ?, ?, ?)");
        q.setParameters(idCuenta, costo, concepto, fecha, idServicio, idReservaHabitacion, registradoPorTipoDocumento, registradoPorNumeroDocumento);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UN CUENTASERVICIO de la base de datos de Hotelandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idCuenta - El identificador del cuentaservicio
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarCuentaServicioPorId (PersistenceManager pm, long idCuenta)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + ph.darTablaCuentaServicio () + " WHERE idCuenta = ?");
        q.setParameters(idCuenta);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UN CUENTASERVICIO de la 
	 * base de datos de Hotelandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idCuenta - El identificador del cuentaservicio
	 * @return El objeto CUENTASERVICIO que tiene el identificador dado
	 */
	public CuentaServicio darCuentaServicioPorId (PersistenceManager pm, long idCuenta) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaCuentaServicio () + " WHERE idCuenta = ?");
		q.setResultClass(CuentaServicio.class);
		q.setParameters(idCuenta);
		return (CuentaServicio) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS CUENTASSERVICIOS de la 
	 * base de datos de Hotelandes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos CUENTASERVICIO
	 */
	public List<CuentaServicio> darCuentasServicios (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaCuentaServicio ());
		q.setResultClass(CuentaServicio.class);
		return (List<CuentaServicio>) q.executeList();
	}
	public List<Object []> darDineroRecolectadoPorServiciosEnCadaHabitacion(PersistenceManager pm,String fechaInf,String fechaSup)
	{
		String peticion = 	"SELECT RESERVA_HABITACION.NOMBRE_HOTEL,RESERVA_HABITACION.NUMERO_HABITACION,SUM(D.SC)";
		String tabla = "SELECT ID_RESERVA_HABITACION,SUM(COSTO) SC FROM "+ph.darTablaCuentaServicio()+" WHERE FECHA>=? AND FECHA<=?"+" GROUP BY ID_RESERVA_HABITACION";
		peticion+= " FROM " + ph.darTablaReservaHabitacion() +" INNER JOIN ("+tabla+") D";
		peticion+= " ON D.ID_RESERVA_HABITACION = RESERVA_HABITACION.ID_RESERVA_HABITACION";
		peticion+= " GROUP BY RESERVA_HABITACION.NOMBRE_HOTEL,RESERVA_HABITACION.NUMERO_HABITACION";
		peticion+= " ORDER BY RESERVA_HABITACION.NOMBRE_HOTEL,RESERVA_HABITACION.NUMERO_HABITACION";
		Query q = pm.newQuery(SQL,peticion);
		q.setParameters(fechaInf,fechaSup);
		return q.executeList();
	}
	public List<Object []> darLosServiciosMasPopulares(PersistenceManager pm,String fechaInf,String fechaSup) 
	{
		String peticion = "SELECT TIPO_SERVICIO";
		String tabla = "SELECT ID_SERVICIO,COUNT(*) AS CONTEO FROM CUENTA_SERVICIO WHERE FECHA>=? AND FECHA<=? GROUP BY ID_SERVICIO";
		peticion += " FROM "+ph.darTablaServicio() +" INNER JOIN "+"("+tabla+") A";
		peticion+=" ON SERVICIO.ID_SERVICIO = A.ID_SERVICIO";
		peticion +=" ORDER BY A.CONTEO DESC ";
		peticion+="FETCH FIRST 20 ROWS ONLY";
		Query q = pm.newQuery(SQL,peticion);
		q.setParameters(fechaInf,fechaSup);
		return q.executeList();
	}
	
	public List<Object []> darServiciosPorCaracteristicas(PersistenceManager pm,String caracteristicas) 
	{
		String peticion ="SELECT *";
		peticion+=" FROM "+ph.darTablaServicio() +" INNER JOIN "+ph.darTablaCuentaServicio() +" ON SERVICIO.ID_SERVICIO = CUENTA_SERVICIO.ID_SERVICIO";
		peticion+=" WHERE "+caracteristicas;
		Query q = pm.newQuery(SQL,peticion);
		System.out.println(q);
		return q.executeList();
	}
	
}


