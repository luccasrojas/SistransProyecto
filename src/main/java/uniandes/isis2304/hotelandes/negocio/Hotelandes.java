
package uniandes.isis2304.hotelandes.negocio;

/*
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
*/

import org.apache.log4j.Logger;

import uniandes.isis2304.hotelandes.persistencia.PersistenciaHotelandes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.JsonObject;
 
/**
 * Clase principal de Hotelandes
 */
public class Hotelandes {

	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecuci�n
	 */
	private static Logger log = Logger.getLogger(Hotelandes.class.getName());
	
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia
	 */
	private PersistenciaHotelandes ph;
	
	/* ****************************************************************
	 * 			M�todos
	 *****************************************************************/
	/**
	 * El constructor por defecto
	 */
	public Hotelandes()
	{
		ph = PersistenciaHotelandes.getInstance();
	}
	
	/**
	 * El constructor que recibe los nombres de las tablas en tableConfig
	 * @param tableConfig - Objeto Json con los nombres de las tablas y de la unidad de persistencia
	 */
	public Hotelandes (JsonObject tableConfig)
	{
		ph = PersistenciaHotelandes.getInstance (tableConfig);
	}

	/**
	 * Cierra la conexi�n con la base de datos (Unidad de persistencia)
	 */
	public void cerrarUnidadPersistencia ()
	{
		ph.cerrarUnidadPersistencia ();
	}

	public long [] limpiarHotelandes ()
	{
        log.info ("Limpiando la BD de Hotelandes");
        long [] borrrados = ph.limpiarHotelandes();	
        log.info ("Limpiando la BD de Hotelandes: Listo!");
        return borrrados;
	}

	//Metodos de adicionar 
	public CadenaHotelera adicionarCadenaHotelera(String nombre)
	{
		log.info ("Adicionando cadena hotelera " + nombre);
		CadenaHotelera cadenaHotelera = ph.adicionarCadenaHotelera(nombre);
		log.info ("Adicionando cadena hotelera " + nombre );
		return cadenaHotelera;
	}
	public RolUsuario adicionarRolUsuario(String rol)
	{
		log.info ("Adicionando rol de usuario " + rol);
		RolUsuario rolUsuario = ph.adicionarRolUsuario(rol);
		log.info ("Adicionando rol de usuario " + rol);
		return rolUsuario;
	}
	public TipoHabitacion adicionarTipoHabitacion(String tipoHabitacion)
	{
		log.info ("Adicionando tipo de habitacion " + tipoHabitacion);
		TipoHabitacion tipoHabitacion1 = ph.adicionarTipoHabitacion(tipoHabitacion);
		log.info ("Adicionando tipo de habitacion " + tipoHabitacion );
		return tipoHabitacion1;
	}
	public TipoServicio adicionarTipoServicio(String tipoServicio)
	{
		log.info ("Adicionando tipo de servicio " + tipoServicio);
		TipoServicio tipoServicio1 = ph.adicionarTipoServicio(tipoServicio);
		log.info ("Adicionando tipo de servicio " + tipoServicio );
		return tipoServicio1;
	}
	public Hotel adicionarHotel(String nombreCadena,String nombreHotel,String pais,String ciudad,String direccion)
	{
		log.info ("Adicionando hotel " + nombreHotel);
		Hotel hotel = ph.adicionarHotel(nombreCadena,nombreHotel,pais,ciudad,direccion);
		log.info ("Adicionando hotel " + nombreHotel);
		return hotel;
	}
	public Usuario adicionarUsuario(String nombre,TipoDocumento tipoDocumento,int numeroDocumento,String correo,String contrasena,String rol)
	{
		log.info ("Adicionando usuario " + nombre);
		Usuario usuario = ph.adicionarUsuario(nombre,tipoDocumento,numeroDocumento,correo,contrasena,rol);
		log.info ("Adicionando usuario " + nombre);
		return usuario;
	}
	public Habitacion adicionarHabitacion(String nombreHotel,int numero,String tipoHabitacion,double costoPorNoche,String descripcion,int capacidadMin,int capacidadMax)
	{
		log.info ("Adicionando habitacion " + numero);
		Habitacion habitacion = ph.adicionarHabitacion(nombreHotel,numero,tipoHabitacion,costoPorNoche,descripcion,capacidadMin,capacidadMax);
		log.info ("Adicionando habitacion " + numero);
		return habitacion;
	}
	public Servicio adicionarServicio(String nombreHotel,String horaInicio,String horaFin,String tipoServicio)
	{
		log.info ("Adicionando servicio " + tipoServicio);
		Servicio servicio = ph.adicionarServicio(nombreHotel,horaInicio,horaFin,tipoServicio);
		log.info ("Adicionando servicio " + tipoServicio);
		return servicio;
	}
	public Piscina adicionarPiscina(String nombreHotel,String horaInicio,String horaFin,int capacidad,double profundidad,double costo)
	{
		log.info ("Adicionando piscina ");
		Piscina piscina = ph.adicionarPiscina(nombreHotel,horaInicio,horaFin,capacidad,profundidad,costo);
		log.info ("Adicionando piscina " + piscina.getIdServicio());
		return piscina;
	}
	public Gimnasio adicionarGimnasio(String nombreHotel,String horaInicio,String horaFin,int capacidad,String descripcion,double costo)
	{
		log.info ("Adicionando gimnasio ");
		Gimnasio gimnasio = ph.adicionarGimnasio(nombreHotel,horaInicio,horaFin,capacidad,descripcion,costo);
		log.info ("Adicionando gimnasio " + gimnasio.getIdServicio());
		return gimnasio;
	}
	public Internet adicionarInternet(String nombreHotel,String horaInicio,String horaFin,double capacidad,double costoPorDia)
	{
		log.info ("Adicionando internet ");
		Internet internet = ph.adicionarInternet(nombreHotel,horaInicio,horaFin,capacidad,costoPorDia);
		log.info ("Adicionando internet " + internet.getIdServicio());
		return internet;
	}
	public Establecimiento adicionarEstablecimiento(String nombreHotel,String horaInicio,String horaFin,String nombre,int capacidad,String estiloTipo,double descuentoPlan)
	{
		log.info ("Adicionando establecimiento ");
		Establecimiento establecimiento = ph.adicionarEstablecimiento(nombreHotel,horaInicio,horaFin,nombre,capacidad,estiloTipo,descuentoPlan);
		log.info ("Adicionando establecimiento " + establecimiento.getIdServicio());
		return establecimiento;
	}
	public PrestamoUtensilio adicionarPrestamoUtensilio(String nombreHotel,String horaInicio,String horaFin,String nombreUtensilio)
	{
		log.info ("Adicionando prestamo de utensilio ");
		PrestamoUtensilio prestamoUtensilio = ph.adicionarPrestamoUtensilio(nombreHotel,horaInicio,horaFin,nombreUtensilio);
		log.info ("Adicionando prestamo de utensilio " + prestamoUtensilio.getIdServicio());
		return prestamoUtensilio;
	}
	public SalonConferencia adicionarSalonConferencia(String nombreHotel,String horaInicio,String horaFin,int numero,int capacidad,double costoPorHora)
	{
		log.info ("Adicionando salon de conferencia ");
		SalonConferencia salonConferencia = ph.adicionarSalonConferencia(nombreHotel,horaInicio,horaFin,numero,capacidad,costoPorHora);
		log.info ("Adicionando salon de conferencia " + salonConferencia.getIdServicio());
		return salonConferencia;
	}
	public SalonReunion adicionarSalonReunion(String nombreHotel,String horaInicio,String horaFin,int numero,double costoPorHora,double costoAdicional)
	{
		log.info ("Adicionando salon de reunion " );
		SalonReunion salonReunion = ph.adicionarSalonReunion(nombreHotel,horaInicio,horaFin,numero,costoPorHora,costoAdicional);
		log.info ("Adicionando salon de reunion " + salonReunion.getIdServicio());
		return salonReunion;
	}
	public Spaa adicionarSpaa(String nombreHotel,String horaInicio,String horaFin,int capacidad)
	{
		log.info ("Adicionando spa ");
		Spaa spaa = ph.adicionarSpaa(nombreHotel,horaInicio,horaFin,capacidad);
		log.info ("Adicionando spa " + spaa.getIdServicio());
		return spaa;
	}
	public Producto adicionarProducto(long idServicio,String nombre,double costo,int planTodoIncluido,String duracion)
	{
		log.info ("Adicionando producto " );
		Producto producto = ph.adicionarProducto(idServicio,nombre,costo,planTodoIncluido,duracion);
		log.info ("Adicionando producto " + producto.getIdServicio());
		return producto;
	}
	public PlanConsumo adicionarPlanConsumo(String nombreHotel,String nombre,int duracionMin,double descuento)
	{
		log.info ("Adicionando plan de consumo ");
		PlanConsumo planConsumo = ph.adicionarPlanConsumo(nombreHotel,nombre,duracionMin,descuento);
		log.info ("Adicionando plan de consumo " + planConsumo.getIdPlanConsumo());
		return planConsumo;
	}
	public ReservaHabitacion adicionarReservaHabitacion(String fechaIn,String fechaOut,int numPersonas,String nombreHotel,long idPlanConsumo)
	{
		log.info ("Adicionando reserva de habitacion");
		ReservaHabitacion reservaHabitacion = ph.adicionarReservaHabitacion(fechaIn,fechaOut,numPersonas,nombreHotel,idPlanConsumo,null);
		log.info ("Adicionando reserva de habitacion");
		return reservaHabitacion;
	}
	public HuespedReserva adicionarHuespedReserva(TipoDocumento tipoDocumento,int numeroDocumento,int acompanante)
	{
		log.info ("Adicionando huesped de reserva de habitacion ");
		HuespedReserva huespedReserva = ph.adicionarHuespedReserva(tipoDocumento,numeroDocumento,acompanante);
		log.info ("Adicionando huesped de reserva de habitacion " + huespedReserva.getIdReservaHabitacion());
		return huespedReserva;
	}
	public CuentaServicio adicionarCuentaServicio(String costo,String concepto,String fecha,String idServicio, String idReservaHabitacion,String tipoDocumento,String registradoProNumDocumento)
	{
		log.info ("Adicionando cuenta de servicio ");
		CuentaServicio cuentaServicio = ph.adicionarCuentaServicio(costo,concepto,fecha,idServicio,idReservaHabitacion,tipoDocumento,registradoProNumDocumento);
		log.info ("Adicionando cuenta de servicio " + cuentaServicio.getIdCuenta());
		return cuentaServicio;
	}
	public Pedido adicionarPedido(int pagoHabitacion,long idCuenta)
	{
		log.info ("Adicionando pedido ");
		Pedido pedido = ph.adicionarPedido(pagoHabitacion,idCuenta);
		log.info ("Adicionando pedido " + pedido.getIdPedido());
		return pedido;
	}
	public ProductoPedido adicionarProductoPedido(long idPedido,long idServicio,long idProducto,int cantidad)
	{
		log.info ("Adicionando producto pedido " + idPedido);
		ProductoPedido productoPedido = ph.adicionarProductoPedido(idPedido,idServicio,idProducto,cantidad);
		log.info ("Adicionando producto pedido " + idPedido);
		return productoPedido;
	}
	public ReservaServicio adicionarReservaServicio(long idServicio,long idReservaHabitacion,String fecha,int duracion)
	{
		log.info ("Adicionando reserva de servicio ");
		ReservaServicio reservaServicio = ph.adicionarReservaServicio(idServicio,idReservaHabitacion,fecha,duracion);
		log.info ("Adicionando reserva de servicio " + reservaServicio.getIdReservaServicio());
		return reservaServicio;
	}
	public long actualizarHabitacionReservaHabitacion(long idReservaHabitacion,int numeroHabitacion)
	{
		log.info ("Actualizar habitacion de reserva de habitacion ");
		long reservaActualizada = ph.actualizarHabitacionReservaHabitacion(idReservaHabitacion,numeroHabitacion);
		log.info ("Actualizando habitacion reserva de habitacion ");
		return reservaActualizada;
	}
	//Metodos de eliminar por id
	public long eliminarCadenaHoteleraPorId(String nombre)
	{
		log.info ("Eliminando cadena hotelera por id: " + nombre);
		long idCadenaHoteleraEliminada = ph.eliminarCadenaHoteleraPorId(nombre);
		log.info ("Eliminando cadena hotelera por id: " + nombre+" tuplas eliminadas");
		return idCadenaHoteleraEliminada;
	}
	public long eliminarRolUsuarioPorId(String rol)
	{
		log.info ("Eliminando rol de usuario por id: " + rol);
		long idRolUsuarioEliminado = ph.eliminarRolUsuarioPorId(rol);
		log.info ("Eliminando rol de usuario por id: " + rol+" tuplas eliminadas");
		return idRolUsuarioEliminado;
	}
	public long eliminarTipoHabitacionPorId(String tipoHabitacion)
	{
		log.info ("Eliminando tipo de habitacion por id: " + tipoHabitacion);
		long idTipoHabitacionEliminado = ph.eliminarTipoHabitacionPorId(tipoHabitacion);
		log.info ("Eliminando tipo de habitacion por id: " + tipoHabitacion+" tuplas eliminadas");
		return idTipoHabitacionEliminado;
	}
	public long eliminarTipoServicioPorId(String tipoServicio)
	{
		log.info ("Eliminando tipo de servicio por id: " + tipoServicio);
		long idTipoServicioEliminado = ph.eliminarTipoServicioPorId(tipoServicio);
		log.info ("Eliminando tipo de servicio por id: " + tipoServicio+" tuplas eliminadas");
		return idTipoServicioEliminado;
	}
	public long eliminarHotelPorId(String nombre)
	{
		log.info ("Eliminando hotel por id: " + nombre);
		long idHotelEliminado = ph.eliminarHotelPorId(nombre);
		log.info ("Eliminando hotel por id: " + nombre+" tuplas eliminadas");
		return idHotelEliminado;
	}
	public long eliminarUsuarioPorId(TipoDocumento tipoDocumento,int numDoc)
	{
		log.info ("Eliminando usuario por id: " + tipoDocumento+" "+numDoc);
		long idUsuarioEliminado = ph.eliminarUsuarioPorId(tipoDocumento,numDoc);
		log.info ("Eliminando usuario por id: " + tipoDocumento+" "+numDoc+" tuplas eliminadas");
		return idUsuarioEliminado;
	}
	public long eliminarHabitacionPorId(String nombreHotel,int numero)
	{
		log.info ("Eliminando habitacion por id: " + nombreHotel+" "+numero);
		long idHabitacionEliminada = ph.eliminarHabitacionPorId(nombreHotel,numero);
		log.info ("Eliminando habitacion por id: " + nombreHotel+" "+numero+" tuplas eliminadas");
		return idHabitacionEliminada;
	}
	public long eliminarServicioPorId(long idServicio)
	{
		log.info ("Eliminando servicio por id: " + idServicio);
		long idServicioEliminado = ph.eliminarServicioPorId(idServicio);
		log.info ("Eliminando servicio por id: " + idServicio+" tuplas eliminadas");
		return idServicioEliminado;
	}
	public long eliminarPiscinaPorId(long idServicio)
	{
		log.info ("Eliminando piscina por id: " + idServicio);
		long idPiscinaEliminada = ph.eliminarPiscinaPorId(idServicio);
		log.info ("Eliminando piscina por id: " + idServicio+" tuplas eliminadas");
		return idPiscinaEliminada;
	}
	public long eliminarGimnasioPorId(long idServicio)
	{
		log.info ("Eliminando gimnasio por id: " + idServicio);
		long idGimnasioEliminado = ph.eliminarGimnasioPorId(idServicio);
		log.info ("Eliminando gimnasio por id: " + idServicio+" tuplas eliminadas");
		return idGimnasioEliminado;
	}
	public long eliminarInternetPorId(long idServicio)
	{
		log.info ("Eliminando internet por id: " + idServicio);
		long idInternetEliminado = ph.eliminarInternetPorId(idServicio);
		log.info ("Eliminando internet por id: " + idServicio+" tuplas eliminadas");
		return idInternetEliminado;
	}
	public long eliminarEstablecimientoPorId(long idServicio)
	{
		log.info ("Eliminando establecimiento por id: " + idServicio);
		long idEstablecimientoEliminado = ph.eliminarEstablecimientoPorId(idServicio);
		log.info ("Eliminando establecimiento por id: " + idServicio+" tuplas eliminadas");
		return idEstablecimientoEliminado;
	}
	public long eliminarPrestamoUtensilioPorId(long idServicio)
	{
		log.info ("Eliminando prestamo de utensilio por id: " + idServicio);
		long idPrestamoUtensilioEliminado = ph.eliminarPrestamoUtensilioPorId(idServicio);
		log.info ("Eliminando prestamo de utensilio por id: " + idServicio+" tuplas eliminadas");
		return idPrestamoUtensilioEliminado;
	}
	public long eliminarSalonConferenciaPorId(long idServicio)
	{
		log.info ("Eliminando salon de conferencia por id: " + idServicio);
		long idSalonConferenciaEliminado = ph.eliminarSalonConferenciaPorId(idServicio);
		log.info ("Eliminando salon de conferencia por id: " + idServicio+" tuplas eliminadas");
		return idSalonConferenciaEliminado;
	}
	public long eliminarSalonReunionPorId(long idServicio)
	{
		log.info ("Eliminando salon de reunion por id: " + idServicio);
		long idSalonReunionEliminado = ph.eliminarSalonReunionPorId(idServicio);
		log.info ("Eliminando salon de reunion por id: " + idServicio+" tuplas eliminadas");
		return idSalonReunionEliminado;
	}
	public long eliminarSpaaPorId(long idServicio)
	{
		log.info ("Eliminando spa por id: " + idServicio);
		long idSpaaEliminado = ph.eliminarSpaaPorId(idServicio);
		log.info ("Eliminando spa por id: " + idServicio+" tuplas eliminadas");
		return idSpaaEliminado;
	}
	public long eliminarProductoPorId(long idServicio,long idProducto)
	{
		log.info ("Eliminando producto por id: " + idServicio+" "+idProducto);
		long idProductoEliminado = ph.eliminarProductoPorId(idServicio,idProducto);
		log.info ("Eliminando producto por id: " + idServicio+" "+idProducto+" tuplas eliminadas");
		return idProductoEliminado;
	}
	public long eliminarPlanConsumoPorId(long idPlan)
	{
		log.info ("Eliminando plan de consumo por id: " + idPlan);
		long idPlanConsumoEliminado = ph.eliminarPlanConsumoPorId(idPlan);
		log.info ("Eliminando plan de consumo por id: " + idPlan+" tuplas eliminadas");
		return idPlanConsumoEliminado;
	}
	public long eliminarReservaHabitacionPorId(long idReserva)
	{
		log.info ("Eliminando reserva de habitacion por id: " + idReserva);
		long idReservaHabitacionEliminado = ph.eliminarReservaHabitacionPorId(idReserva);
		log.info ("Eliminando reserva de habitacion por id: " + idReserva+" tuplas eliminadas");
		return idReservaHabitacionEliminado;
	}
	public long eliminarHuespedReservaPorId(long idReserva)
	{
		log.info ("Eliminando huesped de reserva por id: " + idReserva);
		long idHuespedReservaEliminado = ph.eliminarHuespedReservaPorId(idReserva);
		log.info ("Eliminando huesped de reserva por id: " + idReserva+" tuplas eliminadas");
		return idHuespedReservaEliminado;
	}
	public long eliminarCuentaServicioPorId(long idCuenta)
	{
		log.info ("Eliminando cuenta de servicio por id: " + idCuenta);
		long idCuentaServicioEliminado = ph.eliminarCuentaServicioPorId(idCuenta);
		log.info ("Eliminando cuenta de servicio por id: " + idCuenta+" tuplas eliminadas");
		return idCuentaServicioEliminado;
	}
	public long eliminarProductoPedidoPorId(long idPedido,long idServicio,long idProducto)
	{
		log.info ("Eliminando producto de pedido por id: " + idPedido+" "+idServicio+" "+idProducto);
		long idProductoPedidoEliminado = ph.eliminarProductoPedidoPorId(idPedido,idServicio,idProducto);
		log.info ("Eliminando producto de pedido por id: " + idPedido+" "+idServicio+" "+idProducto+" tuplas eliminadas");
		return idProductoPedidoEliminado;
	}
	public long eliminarReservaServicioPorId(long idReservaServicio)
	{
		log.info ("Eliminando reserva de servicio por id: " + idReservaServicio);
		long idReservaServicioEliminado = ph.eliminarReservaServicioPorId(idReservaServicio);
		log.info ("Eliminando reserva de servicio por id: " + idReservaServicio+" tuplas eliminadas");
		return idReservaServicioEliminado;
	}


	//Metodos para dar por id
	public CadenaHotelera darCadenaHoteleraPorId(String nombre)
	{
		log.info ("Buscando cadena hotelera por nombre: " + nombre);
		CadenaHotelera cadenaHotelera = ph.darCadenaHoteleraPorId(nombre);
		log.info ("Buscando cadena hotelera por nombre: " + nombre!=null ? nombre: "NO EXISTE");
		return cadenaHotelera;
	}
	public RolUsuario darRolUsuarioPorId(String rol)
	{
		log.info ("Buscando rol de usuario por nombre: " + rol);
		RolUsuario rolUsuario = ph.darRolUsuarioPorId(rol);
		log.info ("Buscando rol de usuario por nombre: " + rol!=null ? rol: "NO EXISTE");
		return rolUsuario;
	}
	public TipoHabitacion darTipoHabitacionPorId(String tipoHabitacion)
	{
		log.info ("Buscando tipo de habitacion por nombre: " + tipoHabitacion);
		TipoHabitacion tipoHabitacionEncontrado = ph.darTipoHabitacionPorId(tipoHabitacion);
		log.info ("Buscando tipo de habitacion por nombre: " + tipoHabitacion!=null ? tipoHabitacion: "NO EXISTE");
		return tipoHabitacionEncontrado;
	}
	public TipoServicio darTipoServicioPorId(String tipoServicio)
	{
		log.info ("Buscando tipo de servicio por nombre: " + tipoServicio);
		TipoServicio tipoServicioEncontrado = ph.darTipoServicioPorId(tipoServicio);
		log.info ("Buscando tipo de servicio por nombre: " + tipoServicio!=null ? tipoServicio: "NO EXISTE");
		return tipoServicioEncontrado;
	}
	public Hotel darHotelPorId(String nombreHotel)
	{
		log.info ("Buscando hotel por nombre: " + nombreHotel);
		Hotel hotel = ph.darHotelPorId(nombreHotel);
		log.info ("Buscando hotel por nombre: " + nombreHotel!=null ? nombreHotel: "NO EXISTE");
		return hotel;
	}
	public Usuario darUsuarioPorId(TipoDocumento tipoDocumento,int numeroDocumento)
	{
		log.info ("Buscando usuario por numero de documento: " + numeroDocumento);
		Usuario usuario = ph.darUsuarioPorId(tipoDocumento,numeroDocumento);
		log.info ("Buscando usuario por numero de documento: " + numeroDocumento!=null ? numeroDocumento: "NO EXISTE");
		return usuario;
	}
	public Habitacion darHabitacionPorId(String nombreHotel,int numero)
	{
		log.info ("Buscando habitacion por numero: " + numero);
		Habitacion habitacion = ph.darHabitacionPorId(nombreHotel,numero);
		log.info ("Buscando habitacion por numero: " + numero!=null ? numero: "NO EXISTE");
		return habitacion;
	}
	public Servicio darServicioPorId(long idServicio)
	{
		log.info ("Buscando servicio por id: " + idServicio);
		Servicio servicio = ph.darServicioPorId(idServicio);
		log.info ("Buscando servicio por id: " + idServicio!=null ? idServicio: "NO EXISTE");
		return servicio;
	}
	public Piscina darPiscinaPorId(long idServicio)
	{
		log.info ("Buscando piscina por id: " + idServicio);
		Piscina piscina = ph.darPiscinaPorId(idServicio);
		log.info ("Buscando piscina por id: " + idServicio!=null ? idServicio: "NO EXISTE");
		return piscina;
	}
	public Gimnasio darGimnasioPorId(long idServicio)
	{
		log.info ("Buscando gimnasio por id: " + idServicio);
		Gimnasio gimnasio = ph.darGimnasioPorId(idServicio);
		log.info ("Buscando gimnasio por id: " + idServicio!=null ? idServicio: "NO EXISTE");
		return gimnasio;
	}
	public Internet darInternetPorId(long idServicio)
	{
		log.info ("Buscando internet por id: " + idServicio);
		Internet internet = ph.darInternetPorId(idServicio);
		log.info ("Buscando internet por id: " + idServicio!=null ? idServicio: "NO EXISTE");
		return internet;
	}
	public Establecimiento darEstablecimientoPorId(long idServicio)
	{
		log.info ("Buscando establecimiento por id: " + idServicio);
		Establecimiento establecimiento = ph.darEstablecimientoPorId(idServicio);
		log.info ("Buscando establecimiento por id: " + idServicio!=null ? idServicio: "NO EXISTE");
		return establecimiento;
	}
	public PrestamoUtensilio darPrestamoUtensilioPorId(long idServicio)
	{
		log.info ("Buscando prestamo de utensilio por id: " + idServicio);
		PrestamoUtensilio prestamoUtensilio = ph.darPrestamoUtensilioPorId(idServicio);
		log.info ("Buscando prestamo de utensilio por id: " + idServicio!=null ? idServicio: "NO EXISTE");
		return prestamoUtensilio;
	}
	public SalonConferencia darSalonConferenciaPorId(long idServicio)
	{
		log.info ("Buscando salon de conferencia por id: " + idServicio);
		SalonConferencia salonConferencia = ph.darSalonConferenciaPorId(idServicio);
		log.info ("Buscando salon de conferencia por id: " + idServicio!=null ? idServicio: "NO EXISTE");
		return salonConferencia;
	}
	public SalonReunion darSalonReunionPorId(long idServicio)
	{
		log.info ("Buscando salon de reunion por id: " + idServicio);
		SalonReunion salonReunion = ph.darSalonReunionPorId(idServicio);
		log.info ("Buscando salon de reunion por id: " + idServicio!=null ? idServicio: "NO EXISTE");
		return salonReunion;
	}
	public Spaa darSpaaPorId(long idServicio)
	{
		log.info ("Buscando spa por id: " + idServicio);
		Spaa spa = ph.darSpaaPorId(idServicio);
		log.info ("Buscando spa por id: " + idServicio!=null ? idServicio: "NO EXISTE");
		return spa;
	}
	public Producto darProductoPorId(long idServicio, long idProducto)
	{
		log.info ("Buscando producto por id: " + idProducto);
		Producto producto = ph.darProductoPorId(idServicio,idProducto);
		log.info ("Buscando producto por id: " + idProducto!=null ? idProducto: "NO EXISTE");
		return producto;
	}
	public PlanConsumo darPlanConsumoPorId(long idPlanConsumo)
	{
		log.info ("Buscando plan de consumo por id: " + idPlanConsumo);
		PlanConsumo planConsumo = ph.darPlanConsumoPorId(idPlanConsumo);
		log.info ("Buscando plan de consumo por id: " + idPlanConsumo!=null ? idPlanConsumo: "NO EXISTE");
		return planConsumo;
	}
	public ReservaHabitacion darReservaHabitacionPorId(long idReservaHabitacion)
	{
		log.info ("Buscando reserva de habitacion por id: " + idReservaHabitacion);
		ReservaHabitacion reservaHabitacion = ph.darReservaHabitacionPorId(idReservaHabitacion);
		log.info ("Buscando reserva de habitacion por id: " + idReservaHabitacion!=null ? idReservaHabitacion: "NO EXISTE");
		return reservaHabitacion;
	}
	public HuespedReserva darHuespedReservaPorId(long idReservaHabitacion)
	{
		log.info ("Buscando huesped de reserva de habitacion por id: " + idReservaHabitacion);
		HuespedReserva huespedReserva = ph.darHuespedReservaPorId(idReservaHabitacion);
		log.info ("Buscando huesped de reserva de habitacion por id: " + idReservaHabitacion!=null ? idReservaHabitacion: "NO EXISTE");
		return huespedReserva;
	}
	public CuentaServicio darCuentaServicioPorId(long idCuentaServicio)
	{
		log.info ("Buscando cuenta de servicio por id: " + idCuentaServicio);
		CuentaServicio cuentaServicio = ph.darCuentaServicioPorId(idCuentaServicio);
		log.info ("Buscando cuenta de servicio por id: " + idCuentaServicio!=null ? idCuentaServicio: "NO EXISTE");
		return cuentaServicio;
	}
	public Pedido darPedidoPorId(long idPedido)
	{
		log.info ("Buscando pedido por id: " + idPedido);
		Pedido pedido = ph.darPedidoPorId(idPedido);
		log.info ("Buscando pedido por id: " + idPedido!=null ? idPedido: "NO EXISTE");
		return pedido;
	}
	public ProductoPedido darProductoPedidoPorId(long idPedido,long idServicio,long idProducto)
	{
		log.info ("Buscando producto de pedido por id: " + idProducto);
		ProductoPedido productoPedido = ph.darProductoPedidoPorId(idPedido,idServicio,idProducto);
		log.info ("Buscando producto de pedido por id: " + idProducto!=null ? idProducto: "NO EXISTE");
		return productoPedido;
	}
	public ReservaServicio darReservaServicioPorId(long idReservaServicio)
	{
		log.info ("Buscando reserva de servicio por id: " + idReservaServicio);
		ReservaServicio reservaServicio = ph.darReservaServicioPorId(idReservaServicio);
		log.info ("Buscando reserva de servicio por id: " + idReservaServicio!=null ? idReservaServicio: "NO EXISTE");
		return reservaServicio;
	}


	//Metodos para dar todos
	public List<VOCadenaHotelera> darVOCadenasHoteleras()
	{
		log.info ("Consultando todas las cadenas hoteleras");
		List<VOCadenaHotelera> cadenasHoteleras = new LinkedList<VOCadenaHotelera>();
		for(CadenaHotelera cadenaHotelera: ph.darCadenasHoteleras())
		{
			cadenasHoteleras.add(cadenaHotelera);
		}
		log.info ("Generando los VO de Cadena hotelera: " + cadenasHoteleras.size() + " cadena hotelera existentes");
		return cadenasHoteleras;
	}
	public List<VORolUsuario> darVORolesUsuario()
	{
		log.info ("Consultando todos los roles de usuario");
		List<VORolUsuario> rolesUsuario = new LinkedList<VORolUsuario>();
		for(RolUsuario rolUsuario: ph.darRolesUsuario())
		{
			rolesUsuario.add(rolUsuario);
		}
		log.info ("Generando los VO de Rol de usuario: " + rolesUsuario.size() + " roles de usuario existentes");
		return rolesUsuario;
	}
	public List<VOTipoHabitacion> darVOTiposHabitacion()
	{
		log.info ("Consultando todos los tipos de habitacion");
		List<VOTipoHabitacion> tiposHabitacion = new LinkedList<VOTipoHabitacion>();
		for(TipoHabitacion tipoHabitacion: ph.darTiposHabitacion())
		{
			tiposHabitacion.add(tipoHabitacion);
		}
		log.info ("Generando los VO de Tipo de habitacion: " + tiposHabitacion.size() + " tipos de habitacion existentes");
		return tiposHabitacion;
	}
	public List<VOTipoServicio> darVOTiposServicio()
	{
		log.info ("Consultando todos los tipos de servicio");
		List<VOTipoServicio> tiposServicio = new LinkedList<VOTipoServicio>();
		for(TipoServicio tipoServicio: ph.darTiposServicio())
		{
			tiposServicio.add(tipoServicio);
		}
		log.info ("Generando los VO de Tipo de servicio: " + tiposServicio.size() + " tipos de servicio existentes");
		return tiposServicio;
	}
	public List<VOHotel> darVOHoteles()
	{
		log.info ("Consultando todos los hoteles");
		List<VOHotel> hoteles = new LinkedList<VOHotel>();
		for(Hotel hotel: ph.darHoteles())
		{
			hoteles.add(hotel);
		}
		log.info ("Generando los VO de Hotel: " + hoteles.size() + " hoteles existentes");
		return hoteles;
	}
	public List<VOUsuario> darVOUsuarios()
	{
		log.info ("Consultando todos los usuarios");
		List<VOUsuario> usuarios = new LinkedList<VOUsuario>();
		for(Usuario usuario: ph.darUsuarios())
		{
			usuarios.add(usuario);
		}
		log.info ("Generando los VO de Usuario: " + usuarios.size() + " usuarios existentes");
		return usuarios;
	}
	public List<VOHabitacion> darVOHabitaciones()
	{
		log.info ("Consultando todas las habitaciones");
		List<VOHabitacion> habitaciones = new LinkedList<VOHabitacion>();
		for(Habitacion habitacion: ph.darHabitaciones())
		{
			habitaciones.add(habitacion);
		}
		log.info ("Generando los VO de Habitacion: " + habitaciones.size() + " habitaciones existentes");
		return habitaciones;
	}
	public List<VOServicio> darVOServicios()
	{
		log.info ("Consultando todos los servicios");
		List<VOServicio> servicios = new LinkedList<VOServicio>();
		for(Servicio servicio: ph.darServicios())
		{
			servicios.add(servicio);
		}
		log.info ("Generando los VO de Servicio: " + servicios.size() + " servicios existentes");
		return servicios;
	}
	public List<VOPiscina> darVOPiscinas()
	{
		log.info ("Consultando todas las piscinas");
		List<VOPiscina> piscinas = new LinkedList<VOPiscina>();
		for(Piscina piscina: ph.darPiscinas())
		{
			piscinas.add(piscina);
		}
		log.info ("Generando los VO de Piscina: " + piscinas.size() + " piscinas existentes");
		return piscinas;
	}
	public List<VOGimnasio> darVOGimnasios()
	{
		log.info ("Consultando todos los gimnasios");
		List<VOGimnasio> gimnasios = new LinkedList<VOGimnasio>();
		for(Gimnasio gimnasio: ph.darGimnasios())
		{
			gimnasios.add(gimnasio);
		}
		log.info ("Generando los VO de Gimnasio: " + gimnasios.size() + " gimnasios existentes");
		return gimnasios;
	}
	public List<VOInternet> darVOInternets()
	{
		log.info ("Consultando todos los internets");
		List<VOInternet> internets = new LinkedList<VOInternet>();
		for(Internet internet: ph.darInternets())
		{
			internets.add(internet);
		}
		log.info ("Generando los VO de Internet: " + internets.size() + " internets existentes");
		return internets;
	}
	public List<VOEstablecimiento> darVOEstablecimientos()
	{
		log.info ("Consultando todos los establecimientos");
		List<VOEstablecimiento> establecimientos = new LinkedList<VOEstablecimiento>();
		for(Establecimiento establecimiento: ph.darEstablecimientos())
		{
			establecimientos.add(establecimiento);
		}
		log.info ("Generando los VO de Establecimiento: " + establecimientos.size() + " establecimientos existentes");
		return establecimientos;
	}
	public List<VOPrestamoUtensilio> darVOPrestamosUtensilios()
	{
		log.info ("Consultando todos los prestamos de utensilios");
		List<VOPrestamoUtensilio> prestamosUtensilios = new LinkedList<VOPrestamoUtensilio>();
		for(PrestamoUtensilio prestamoUtensilio: ph.darPrestamosUtensilios())
		{
			prestamosUtensilios.add(prestamoUtensilio);
		}
		log.info ("Generando los VO de Prestamo de utensilio: " + prestamosUtensilios.size() + " prestamos de utensilios existentes");
		return prestamosUtensilios;
	}
	public List<VOSalonConferencia> darVOSalonesConferencias()
	{
		log.info ("Consultando todos los salones de conferencias");
		List<VOSalonConferencia> salonesConferencias = new LinkedList<VOSalonConferencia>();
		for(SalonConferencia salonConferencia: ph.darSalonesConferencias())
		{
			salonesConferencias.add(salonConferencia);
		}
		log.info ("Generando los VO de Salon de conferencia: " + salonesConferencias.size() + " salones de conferencias existentes");
		return salonesConferencias;
	}
	public List<VOSalonReunion> darVOSalonesReuniones()
	{
		log.info ("Consultando todos los salones de reuniones");
		List<VOSalonReunion> salonesReuniones = new LinkedList<VOSalonReunion>();
		for(SalonReunion salonReunion: ph.darSalonesReuniones())
		{
			salonesReuniones.add(salonReunion);
		}
		log.info ("Generando los VO de Salon de reunion: " + salonesReuniones.size() + " salones de reuniones existentes");
		return salonesReuniones;
	}
	public List<VOSpaa> darVOSpaas()
	{
		log.info ("Consultando todos los spaas");
		List<VOSpaa> spaas = new LinkedList<VOSpaa>();
		for(Spaa spaa: ph.darSpaas())
		{
			spaas.add(spaa);
		}
		log.info ("Generando los VO de Spaa: " + spaas.size() + " spaas existentes");
		return spaas;
	}
	public List<VOProducto> darVOProductos()
	{
		log.info ("Consultando todos los productos");
		List<VOProducto> productos = new LinkedList<VOProducto>();
		for(Producto producto: ph.darProductos())
		{
			productos.add(producto);
		}
		log.info ("Generando los VO de Producto: " + productos.size() + " productos existentes");
		return productos;
	}
	public List<VOPlanConsumo> darVOPlanesConsumo()
	{
		log.info ("Consultando todos los planes de consumo");
		List<VOPlanConsumo> planesConsumo = new LinkedList<VOPlanConsumo>();
		for(PlanConsumo planConsumo: ph.darPlanesConsumo())
		{
			planesConsumo.add(planConsumo);
		}
		log.info ("Generando los VO de Plan de consumo: " + planesConsumo.size() + " planes de consumo existentes");
		return planesConsumo;
	}
	public List<VOReservaHabitacion> darVOReservasHabitaciones()
	{
		log.info ("Consultando todas las reservas de habitaciones");
		List<VOReservaHabitacion> reservasHabitaciones = new LinkedList<VOReservaHabitacion>();
		for(ReservaHabitacion reservaHabitacion: ph.darReservasHabitaciones())
		{
			reservasHabitaciones.add(reservaHabitacion);
		}
		log.info ("Generando los VO de Reserva de habitacion: " + reservasHabitaciones.size() + " reservas de habitaciones existentes");
		return reservasHabitaciones;
	}
	public List<VOHuespedReserva> darVOHuespedesReservas()
	{
		log.info ("Consultando todas las huespedes de reservas");
		List<VOHuespedReserva> huespedesReservas = new LinkedList<VOHuespedReserva>();
		for(HuespedReserva huespedReserva: ph.darHuespedesReservas())
		{
			huespedesReservas.add(huespedReserva);
		}
		log.info ("Generando los VO de Huesped de reserva: " + huespedesReservas.size() + " huespedes de reservas existentes");
		return huespedesReservas;
	}
	public List<VOCuentaServicio> darVOCuentasServicios()
	{
		log.info ("Consultando todas las cuentas de servicios");
		List<VOCuentaServicio> cuentasServicios = new LinkedList<VOCuentaServicio>();
		for(CuentaServicio cuentaServicio: ph.darCuentasServicios())
		{
			cuentasServicios.add(cuentaServicio);
		}
		log.info ("Generando los VO de Cuenta de servicio: " + cuentasServicios.size() + " cuentas de servicios existentes");
		return cuentasServicios;
	}
	public List<VOPedido> darVOPedidos()
	{
		log.info ("Consultando todos los pedidos");
		List<VOPedido> pedidos = new LinkedList<VOPedido>();
		for(Pedido pedido: ph.darPedidos())
		{
			pedidos.add(pedido);
		}
		log.info ("Generando los VO de Pedido: " + pedidos.size() + " pedidos existentes");
		return pedidos;
	}
	public List<VOProductoPedido> darVOProductosPedidos()
	{
		log.info ("Consultando todos los productos de pedidos");
		List<VOProductoPedido> productosPedidos = new LinkedList<VOProductoPedido>();
		for(ProductoPedido productoPedido: ph.darProductosPedidos())
		{
			productosPedidos.add(productoPedido);
		}
		log.info ("Generando los VO de Producto de pedido: " + productosPedidos.size() + " productos de pedidos existentes");
		return productosPedidos;
	}
	public List<VOReservaServicio> darVOReservasServicios()
	{
		log.info ("Consultando todas las reservas de servicios");
		List<VOReservaServicio> reservasServicios = new LinkedList<VOReservaServicio>();
		for(ReservaServicio reservaServicio: ph.darReservasServicios())
		{
			reservasServicios.add(reservaServicio);
		}
		log.info ("Generando los VO de Reserva de servicio: " + reservasServicios.size() + " reservas de servicios existentes");
		return reservasServicios;
	}


	//Requerimientos funcionales de consulta


	public List<String[]> darDineroRecolectadoPorServiciosEnCadaHabitacion(String fechaInf,String fechaSup)
	{
		log.info ("Consultando el dinero recolectado por servicios en cada habitacion");
		List<String[]> resultado = new LinkedList<String[]>();
		resultado = ph.darDineroRecolectadoPorServiciosEnCadaHabitacion(fechaInf, fechaSup);
		log.info ("Generando el VO de dinero recolectado por servicios en cada habitacion: " + resultado.size() + " habitaciones existentes");
		return resultado;
	}
	public List<String[]> darLosServiciosMasPopulares(String fechaInf,String fechaSup)
	{
		log.info ("Consultando los servicios mas populares");
		List<String[]> resultado = new LinkedList<String[]>();
		resultado = ph.darLosServiciosMasPopulares(fechaInf, fechaSup);
		log.info ("Generando el VO de los servicios mas populares: " + resultado.size() + " servicios existentes");
		return resultado;
	}
	public List<String[]> darIndiceOcupacionHabitacionPorFecha(String nombreHotel,String fechaInf,String fechaSup)
	{
		log.info ("Consultando el indice de ocupacion de habitacion por fecha");
		List<String[]> resultado = new LinkedList<String[]>();
		resultado = ph.darIndiceOcupacionHabitacionPorFecha(nombreHotel, fechaInf, fechaSup);
		log.info ("Generando el VO de indice de ocupacion de habitacion por fecha: " + resultado.size() + " habitaciones existentes");
		return resultado;
	}
	public List<String[]> darServiciosPorCaracteristica(String caracteristica)
	{
		log.info ("Consultando los servicios por caracteristica");
		List<String[]> resultado = new LinkedList<String[]>();
		resultado = ph.darServiciosPorCaracteristica(caracteristica);
		log.info ("Generando el VO de los servicios por caracteristica: " + resultado.size() + " servicios existentes");
		return resultado;
	}
	public List<String[]> darConsumoPorUsuario(TipoDocumento tipoDocumento,int numDoc,String fechaInf,String fechaSup)
	{
		log.info ("Consultando el consumo por usuario");
		List<String[]> resultado = new LinkedList<String[]>();
		resultado = ph.darConsumoPorUsuario(tipoDocumento, numDoc, fechaInf, fechaSup);
		log.info ("Generando el VO de consumo por usuario: " + resultado.size() + " usuarios existentes");
		return resultado;
	}

	//registrar salida
	public String registrarSalida(String idReservaHabitacion)
	{
		log.info ("Registrando salida de la habitacion: " + idReservaHabitacion);
		String resultado = ph.registrarSalida(idReservaHabitacion);
		log.info ("Salida registrada de la habitacion: " + idReservaHabitacion);
		return resultado;
	}

	public List<VOProducto> darVOProductosPorServicio(long idServicio)
	{
		log.info ("Consultando todos los productos de un servicio");
		List<VOProducto> productos = new LinkedList<VOProducto>();
		for(Producto producto: ph.darProductosPorServicio(idServicio))
		{
			productos.add(producto);
		}
		log.info ("Generando los VO de Producto: " + productos.size() + " productos existentes");
		return productos;
	}

	//Registrar reserva de una convención
	public List<HashMap<String,ArrayList<String>>> registrarReservaConvencion(String nombreHotel,String fechaInicio,String fechaFin,HashMap<String,List<List<String[]>>> tiposHabitacion, String[] servicios)
	{
		System.out.println("1");
		return ph.inscribirReservasConvencion(nombreHotel, fechaInicio, fechaFin, tiposHabitacion, servicios);
	}
}
