package uniandes.isis2304.hotelandes.negocio;

public class PrestamoUtensilio implements VOPrestamoUtensilio {
    private long idServicio;
    private String nombreUtensilio;

    public PrestamoUtensilio() {
        this.idServicio=0;
        this.nombreUtensilio="";
    }
    public PrestamoUtensilio(long idServicio, String nombreUtensilio) {
        this.idServicio = idServicio;
        this.nombreUtensilio = nombreUtensilio;
    }
    public long getIdServicio() {
        return idServicio;
    }
    public void setIdServicio(long idServicio) {
        this.idServicio = idServicio;
    }
    public String getNombreUtensilio() {
        return nombreUtensilio;
    }
    public void setNombreUtensilio(String nombreUtensilio) {
        this.nombreUtensilio = nombreUtensilio;
    }
    @Override
    public String toString() {
        return "PrestamoUtensilio [idServicio=" + idServicio + ", nombreUtensilio=" + nombreUtensilio + "]";
    }

    
}
