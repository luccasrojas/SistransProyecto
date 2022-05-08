package uniandes.isis2304.hotelandes.negocio;
import java.sql.Timestamp;
import java.util.Optional;
public class ReservaHabitacion implements VOReservaHabitacion
{
    private long idReservaHabitacion;
    private Timestamp fechaIn;
    private Timestamp fechaOut;
    private int numPersonas;
    private double cuentaMinibar;
    private String nombreHotel;
    private int numeroHabitacion;
    private long idPlanConsumo;
    private int pagado;
    private Optional<Long> idConvencion;
    
    public ReservaHabitacion() 
    {
        this.idReservaHabitacion =0;
        this.fechaIn=new Timestamp(0);
        this.fechaOut=new Timestamp(0);
        this.numPersonas=0;
        this.cuentaMinibar=0;
        this.nombreHotel="";
        this.numeroHabitacion=0;
        this.idPlanConsumo=0;
        this.pagado=0;
        this.idConvencion=Optional.of(0L);
    }

    public ReservaHabitacion(long idReservaHabitacion, Timestamp fechaIn, Timestamp fechaOut, int numPersonas,
            double cuentaMinibar, String nombreHotel, int numeroHabitacion, long idPlanConsumo, int pagado, Optional<Long> idConvencion) {
        this.idReservaHabitacion = idReservaHabitacion;
        this.fechaIn = fechaIn;
        this.fechaOut = fechaOut;
        this.numPersonas = numPersonas;
        this.cuentaMinibar = cuentaMinibar;
        this.nombreHotel = nombreHotel;
        this.numeroHabitacion = numeroHabitacion;
        this.idPlanConsumo = idPlanConsumo;
        this.pagado = pagado;
        this.idConvencion = idConvencion;
    }


    public long getIdReservaHabitacion() {
        return idReservaHabitacion;
    }

    public void setIdReservaHabitacion(long idReservaHabitacion) {
        this.idReservaHabitacion = idReservaHabitacion;
    }

    public Timestamp getFechaIn() {
        return fechaIn;
    }

    public void setFechaIn(Timestamp fechaIn) {
        this.fechaIn = fechaIn;
    }

    public Timestamp getFechaOut() {
        return fechaOut;
    }

    public void setFechaOut(Timestamp fechaOut) {
        this.fechaOut = fechaOut;
    }

    public int getNumPersonas() {
        return numPersonas;
    }

    public void setNumPersonas(int numPersonas) {
        this.numPersonas = numPersonas;
    }

    public double getCuentaMinibar() {
        return cuentaMinibar;
    }

    public void setCuentaMinibar(double cuentaMinibar) {
        this.cuentaMinibar = cuentaMinibar;
    }

    public String getNombreHotel() {
        return nombreHotel;
    }

    public void setNombreHotel(String nombreHotel) {
        this.nombreHotel = nombreHotel;
    }

    public int getNumeroHabitacion() {
        return numeroHabitacion;
    }

    public void setNumeroHabitacion(int numeroHabitacion) {
        this.numeroHabitacion = numeroHabitacion;
    }

    public long getIdPlanConsumo() {
        return idPlanConsumo;
    }

    public void setIdPlanConsumo(long idPlanConsumo) {
        this.idPlanConsumo = idPlanConsumo;
    }
    
    public int getPagado() {
		return pagado;
	}

	public void setPagado(int pagado) {
		this.pagado = pagado;
	}

    public Optional<Long> getIdConvencion() {
        return idConvencion;
    }

    public void setIdConvencion(Optional<Long> idConvencion) {
        this.idConvencion = idConvencion;
    }

	@Override
	public String toString() {
		return "ReservaHabitacion [idReservaHabitacion=" + idReservaHabitacion + ", fechaIn=" + fechaIn + ", fechaOut="
				+ fechaOut + ", numPersonas=" + numPersonas + ", cuentaMinibar=" + cuentaMinibar + ", nombreHotel="
				+ nombreHotel + ", numeroHabitacion=" + numeroHabitacion + ", idPlanConsumo=" + idPlanConsumo
				+ ", pagado=" + pagado + "]";
	}

}