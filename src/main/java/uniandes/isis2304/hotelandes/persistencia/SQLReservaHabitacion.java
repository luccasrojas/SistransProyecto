
package uniandes.isis2304.hotelandes.persistencia;

import java.util.HashMap;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import uniandes.isis2304.hotelandes.negocio.ReservaHabitacion;
import uniandes.isis2304.hotelandes.negocio.TipoDocumento;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto RESERVAHABITACION de Hotelandes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Equipo C-09
 */
class SQLReservaHabitacion 
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
	public SQLReservaHabitacion (PersistenciaHotelandes ph)
	{
		this.ph = ph;
	}

	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un RESERVAHABITACION a la base de datos de Hotelandes
	 * @param pm - El manejador de persistencia
	 * @param idReservaHabitacion - Id de la reserva de habitación
	 * @param fechaIn - Fecha de ingreso de la reserva de habitación
	 * @param fechaOut - Fecha de salida de la reserva de habitación
	 * @param numPersonas - Número de personas reservadas para la habitación
	 * @param cuentaMinibar - Cuenta que se lleva del minibar en la reserva, si la habitación tiene minibar
	 * @param nombreHotel - Nombre del hotel en el que se reservó
	 * @param numeroHabitacion - Número de la habitación reservada
	 * @param idPlanConsumo - Id del plan de consumo con el que se costeará la reserva
	 * @return El número de tuplas insertadas
	 */
	public long adicionarReservaHabitacion (PersistenceManager pm, long idReservaHabitacion, String fechaIn, String fechaOut, int numPersonas, String nombreHotel, Long idPlanConsumo, int pagado, long idConvencion)
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + ph.darTablaReservaHabitacion () + "(ID_RESERVA_HABITACION, FECHA_IN, FECHA_OUT, NUM_PERSONAS, CUENTA_MINIBAR, NOMBRE_HOTEL, ID_PLAN_CONSUMO, PAGADO, ID_CONVENCION) values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
        q.setParameters(idReservaHabitacion, fechaIn, fechaOut, numPersonas, 0, nombreHotel, idPlanConsumo, pagado);
        return (long) q.executeUnique();
	}

	public long actualizarHabitacion (PersistenceManager pm, long idReservaHabitacion, int numeroHabitacion)
	{
		String peticion="UPDATE "+ph.darTablaReservaHabitacion()+" SET NUMERO_HABITACION = " + numeroHabitacion + " WHERE ID_RESERVA_HABITACION = "+ idReservaHabitacion;
		Query q = pm.newQuery(SQL,peticion);
		return (long) q.executeUnique();

	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UN RESERVAHABITACION de la base de datos de Hotelandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idReservaHabitacion - El identificador del reservahabitacion
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarReservaHabitacionPorId (PersistenceManager pm, long idReservaHabitacion)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + ph.darTablaReservaHabitacion () + " WHERE ID_RESERVA_HABITACION = ?");
        q.setParameters(idReservaHabitacion);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UN RESERVAHABITACION de la 
	 * base de datos de Hotelandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idReservaHabitacion - El identificador del reservahabitacion
	 * @return El objeto RESERVAHABITACION que tiene el identificador dado
	 */
	public ReservaHabitacion darReservaHabitacionPorId (PersistenceManager pm, long idReservaHabitacion) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaReservaHabitacion () + " WHERE ID_RESERVA_HABITACION = ?");
		q.setResultClass(ReservaHabitacion.class);
		q.setParameters(idReservaHabitacion);
		return (ReservaHabitacion) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS RESERVASHABITACIONES de la 
	 * base de datos de Hotelandes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos RESERVAHABITACION
	 */
	public List<ReservaHabitacion> darReservasHabitaciones (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaReservaHabitacion ());
		q.setResultClass(ReservaHabitacion.class);
		return (List<ReservaHabitacion>) q.executeList();
	}
/*
	public class Consulta
	{
		public double numero;
		
		public Consulta()
		{
			this.numero = 0;
		}
		public Consulta(double numero)
		{
			this.numero=numero;
		}
		
		public double getNumero() {
			return numero;
		}
		public void setNumero(double numero) {
			this.numero = numero;
		}
		@Override
		public String toString() {
			return "Consulta [numero=" + numero + "]";
		}			
	}
*/
	public String registrarSalida(PersistenceManager pm, String idReservaHabitacion)
	{
		String peticion="SELECT A.A_PAGAR+B.A_PAGAR AS A_PAGAR";
		String tabla1="SELECT ID_RESERVA_HABITACION AS ID_RESERVA_HABITACION, SUM(COSTO) AS A_PAGAR FROM " + ph.darTablaCuentaServicio()+" WHERE ID_RESERVA_HABITACION = "+idReservaHabitacion+" GROUP BY ID_RESERVA_HABITACION";
		String tabla2 = "SELECT ID_RESERVA_HABITACION AS ID_RESERVA_HABITACION,CUENTA_MINIBAR AS A_PAGAR FROM " + ph.darTablaReservaHabitacion()+" WHERE ID_RESERVA_HABITACION =  "+idReservaHabitacion;
		peticion+=" FROM( ("+tabla1+") A"+ " FULL OUTER JOIN ( "+tabla2+") B ON A.ID_RESERVA_HABITACION = B.ID_RESERVA_HABITACION)";
		Query q = pm.newQuery(SQL,peticion);
		/*  UPDATE RESERVA_HABITACION SET pagado = 1 WHERE ID_RESERVA_HABITACION = ? */
		return q.executeUnique().toString();
	}

	public void registrarSalidaCambiarEstado(PersistenceManager pm, String idReservaHabitacion)
	{
		String peticion="UPDATE "+ph.darTablaReservaHabitacion()+" SET PAGADO = 1 WHERE ID_RESERVA_HABITACION = "+ idReservaHabitacion;
		Query q = pm.newQuery(SQL,peticion);
		q.executeUnique();
	}
	
	public String reservarAlojamientoParaConvencion(String tiposHabitacion,String cantidad,String servicios)
	{
		return null;
	}




	//Funcion que devuelve las habitaciones disponibles para una fecha dada
	public List<Object> darHabitacionesDisponibles(PersistenceManager pm, String fecha, String nombreHotel,String tipoHabitacion)
	{
		String peticion = "SELECT NUMERO FROM HABITACION WHERE (NOMBRE_HOTEL,NUMERO) NOT IN (SELECT HABITACION.NOMBRE_HOTEL,HABITACION.NUMERO FROM HABITACION INNER JOIN (SELECT NOMBRE_HOTEL,NUMERO_HABITACION FROM RESERVA_HABITACION WHERE NOMBRE_HOTEL = '"+nombreHotel+ "' AND FECHA_IN<= '"+fecha+"' AND FECHA_OUT>= '"+fecha+"' ) A ON HABITACION.NOMBRE_HOTEL =A.NOMBRE_HOTEL  AND HABITACION.NUMERO = A.NUMERO_HABITACION)";
		peticion += " AND TIPO_HABITACION = '"+tipoHabitacion+"'";
		Query q = pm.newQuery(SQL,peticion);
		return q.executeList();
	}

	public String darNumeroReservas(PersistenceManager pm,String fechaIn,String fechaOut,String nombreHotel)
	{
		System.out.println("7.2");
		String peticion = "SELECT COUNT(*) FROM RESERVA_HABITACION WHERE (NOMBRE_HOTEL,NUMERO_HABITACION) NOT IN (SELECT NOMBRE_HOTEL,NUMERO_HABITACION FROM RESERVA_HABITACION WHERE NOMBRE_HOTEL = '"+nombreHotel+"' AND NUMERO_HABITACION IS NULL AND ( '"+fechaOut+"' <=FECHA_IN OR '"+fechaIn+"' >=FECHA_OUT)) AND NUMERO_HABITACION IS NULL";
		Query q = pm.newQuery(SQL,peticion);
		System.out.println("7.3");
		System.out.println(q.executeList());
		System.out.println("7.4");
		return q.executeUnique().toString();
	}
	public ArrayList<Object> darHabitacionesDisponiblesParaConvencionTipo(PersistenceManager pm, String fechaIn,String fechaOut, String nombreHotel, String tipoHabitacion)
	{
		try{
		long dayInms = 24*60*60*1000;
		String dateFormat = "dd/MM/yy";
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		Date dateIn = sdf.parse(fechaIn);
		Date date = dateIn;
		ArrayList<Object> habitaciones= new ArrayList<Object>(darHabitacionesDisponibles(pm, fechaIn, nombreHotel, tipoHabitacion)); 
		while (date.getTime()<=sdf.parse(fechaOut).getTime())
		{
			List<Object> habitacionesDisponibles = darHabitacionesDisponibles(pm,sdf.format(date),nombreHotel,tipoHabitacion);
			for (Object habitacion:habitaciones)
			{
				if(!habitacionesDisponibles.contains(habitacion))
				{
					habitaciones.remove(habitacion);
				}
			}
			date.setTime(date.getTime()+dayInms);
		}
		return habitaciones;
		
	}catch(Exception e){
		e.printStackTrace();
		return null;
	}
	}

	//Funcion que devuelve las habitaciones disponibles para una fecha dada
	//GRANDE
	public List<HashMap<String,ArrayList<String>>> darHabitacionesDisponiblesParaConvencion(PersistenceManager pm, String fechaIn,String fechaOut, String nombreHotel, HashMap<String,List<List<String[]>>> tiposHabitacionCantidad, String[] servicios)
	{
		try
		{
			System.out.println("3");
			List<HashMap<String,ArrayList<String>>> res = new ArrayList<HashMap<String,ArrayList<String>>>();
			System.out.println("4");
			boolean c = false;
			for(String tipoHabitacion:tiposHabitacionCantidad.keySet())
			{
				System.out.println("5");
				int cantidad = tiposHabitacionCantidad.get(tipoHabitacion).size();
				ArrayList<Object> habitaciones = darHabitacionesDisponiblesParaConvencionTipo(pm,fechaIn,fechaOut,nombreHotel,tipoHabitacion);
				System.out.println("6");
				ArrayList<String> strHabitaciones = new ArrayList<String>();
				for (Object habitacion:habitaciones)
				{
					System.out.println("7");
					strHabitaciones.add(habitacion.toString());
					System.out.println("7.1");
				}
				if((habitaciones.size()-Integer.parseInt(darNumeroReservas(pm, fechaIn, fechaOut, nombreHotel)))<cantidad)
				{
					System.out.println("8");
					return null;
				}
				else
				{
					System.out.println("9");
					HashMap<String,ArrayList<String>> habitacionesDisponibles = new HashMap<String,ArrayList<String>>();
					habitacionesDisponibles.put(tipoHabitacion, strHabitaciones);
					res.add(habitacionesDisponibles);
				}

			}
			if (!c)
			{
				System.out.println("10");
				return res;
			}
			else
			{
				return null;
			}
				
		}
		catch(Exception e)
		{
			return null;
		}
	}
	public void reservarHabitaciones(SQLHuespedReserva hr,PersistenceManager pm,String nombreHotel,String fechaIn,String fechaOut,HashMap<String,ArrayList<String>> habitacionesPorTipo,HashMap<String,ArrayList<ArrayList<String[]>>> tiposHabitacionClientes,long idConvencion)
	{
		for(String tipoHabitacion:habitacionesPorTipo.keySet())
		//tipoHabitacion -> String del tipo de habitacion
		{
			ArrayList<String> habitacionPorTipo = new ArrayList<String>(habitacionesPorTipo.get(tipoHabitacion));
			ArrayList<ArrayList<String[]>> usuarios = new ArrayList<ArrayList<String[]>>(tiposHabitacionClientes.get(tipoHabitacion));
			//Lista de usuarios por tipo de habitacion (ArryList<ArrayList<Object>>)
			SQLUtil aux =new SQLUtil(ph);
			for(int i=0;i<usuarios.size();i++)
			{
				long idReservaHabitacion = aux.nextval(pm);
				//Por cada una de las habitaciones necesarias hacer la reserva y generar huesped reserva
				adicionarReservaHabitacion(pm, idReservaHabitacion, fechaIn, fechaOut,usuarios.get(i).size() , nombreHotel,Long.valueOf(habitacionPorTipo.get(i)),0, idConvencion);
				for(int j=0;j<usuarios.get(i).size();j++)
				{
					//Todos los que se quedan en una misma habitacion anadirlos a huesped reserva
					if (j==0)
					{
						//Persistence manager pm,long idReservaHabitacion,Tipo documento tipoDocumento,int numeroDocumento,int acompanante
					hr.adicionarHuespedReserva(pm, idReservaHabitacion, TipoDocumento.valueOf(usuarios.get(i).get(j)[0]),Integer.parseInt(usuarios.get(i).get(j)[2]), 1);
					}
					else
					{
					hr.adicionarHuespedReserva(pm, idReservaHabitacion, TipoDocumento.valueOf(usuarios.get(i).get(j)[0]),Integer.parseInt(usuarios.get(i).get(j)[2]), 0);
					}

				}
				
			}
		}
	}	
	/*
	public ArrayList<Object> darHabitacionesParaReservas(PersistenceManager pm, String fechaIn,String fechaOut, String nombreHotel,String tipoHabitacion)
	{
		try
		{
			ArrayList<Object> res = new ArrayList<Object>();
			
			boolean c = true;
			for(String tipoHabitacion:tiposHabitacionCantidad.keySet())
			{
				int cantidad = tiposHabitacionCantidad.get(tipoHabitacion).size();
				ArrayList<Object> habitaciones = darHabitacionesDisponiblesParaConvencionTipo(pm,fechaIn,fechaOut,nombreHotel,tipoHabitacion);
				if(habitaciones.size()-darNumeroReservas(pm, fechaIn, fechaOut, nombreHotel)<cantidad)
				{
					c = false;
				}
				else
				{
					res.add(habitaciones);
				}

			}
			if (c)
			{
				return res;
			}
			else
			{
				return null;
			}
				
		}
		catch(Exception e)
		{
			return null;
		}
	}*/	


	//Requerimeinto de consulta 6 
	//RC6

}
