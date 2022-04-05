package uniandes.isis2304.hotelandes.negocio;

import java.sql.Timestamp;

public class ReservaServicio implements VOReservaServicio {
    //Atributos 
    private long idReservaServicio;
    private long idServicio;
    private long idReservaHabitacion;
    private Timestamp fecha;
    private int duracion;
    //Metodos
    public ReservaServicio() 
    {
        this.idReservaServicio =0;
        this.idServicio =0;
        this.idReservaHabitacion =0;
        this.fecha = new Timestamp(0);
        this.duracion =0;
    }
    public ReservaServicio(long idReservaServicio, long idServicio,long idReservaHabitacion,Timestamp fecha,int duracion)
    {
        this.idReservaServicio = idReservaServicio;
        this.idServicio = idServicio;
        this.idReservaHabitacion=idReservaHabitacion;
        this.fecha = fecha;
        this.duracion= duracion;
    }

    
    public long getIdReservaServicio() {
        return idReservaServicio;
    }
    public void setIdReservaServicio(long idReservaServicio) {
        this.idReservaServicio = idReservaServicio;
    }
    public long getIdServicio() {
        return idServicio;
    }
    public void setIdServicio(long idServicio) {
        this.idServicio = idServicio;
    }
    public long getIdReservaHabitacion() {
        return idReservaHabitacion;
    }
    public void setIdReservaHabitacion(long idReservaHabitacion) {
        this.idReservaHabitacion = idReservaHabitacion;
    }
    public Timestamp getFecha() {
        return fecha;
    }
    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }
    public int getDuracion() {
        return duracion;
    }
    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }
    @Override
	/**
	 * @return Una cadena de caracteres con todos los atributos del bar
	 */
	public String toString() 
	{
		return "ReservaServicio [idReservaServicio=" + idReservaServicio + ", idServicio=" + idServicio + ", idReservaHabitacion=" + idReservaHabitacion + ", fecha=" + fecha
				+ ", duracion=" + duracion + "]";
	}
}