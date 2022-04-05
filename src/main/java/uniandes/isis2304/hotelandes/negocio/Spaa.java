package uniandes.isis2304.hotelandes.negocio;
public class Spaa implements VOSpaa
{
    private long idServicio;
    private int capacidad;
    public long getIdServicio() {
        return idServicio;
    }
    public Spaa() 
    {
        this.idServicio =0;
        this.capacidad =0;
    }
    
    public Spaa(long idServicio, int capacidad) {
        this.idServicio = idServicio;
        this.capacidad = capacidad;
    }


    public void setIdServicio(long idServicio) {
        this.idServicio = idServicio;
    }
    public int getCapacidad() {
        return capacidad;
    }
    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }
    @Override
    public String toString() {
        return "Spaa [capacidad=" + capacidad + ", idServicio=" + idServicio + "]";
    }

    
}