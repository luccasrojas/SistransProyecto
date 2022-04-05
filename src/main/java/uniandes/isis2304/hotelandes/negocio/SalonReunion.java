package uniandes.isis2304.hotelandes.negocio;

public class SalonReunion implements VOSalonReunion {
    private long idServicio;
    private int numero;
    private double costoPorHora;
    private double costoAdicional;

    public SalonReunion(){
        this.idServicio =0;
        this.numero =0;
        this.costoPorHora =0;
        this.costoAdicional =0;
    } 
    public SalonReunion(long idServicio, int numero, double costoPorHora, double costoAdicional) {
        this.idServicio = idServicio;
        this.numero = numero;
        this.costoPorHora = costoPorHora;
        this.costoAdicional = costoAdicional;
    }
    public long getIdServicio() {
        return idServicio;
    }
    public void setIdServicio(long idServicio) {
        this.idServicio = idServicio;
    }
    public int getNumero() {
        return numero;
    }
    public void setNumero(int numero) {
        this.numero = numero;
    }
    public double getCostoPorHora() {
        return costoPorHora;
    }
    public void setCostoPorHora(double costoPorHora) {
        this.costoPorHora = costoPorHora;
    }
    public double getCostoAdicional() {
        return costoAdicional;
    }
    public void setCostoAdicional(double costoAdicional) {
        this.costoAdicional = costoAdicional;
    }
    @Override
    public String toString() {
        return "SalonReunion [costoAdicional=" + costoAdicional + ", costoPorHora=" + costoPorHora + ", idServicio="
                + idServicio + ", numero=" + numero + "]";
    }
    
}
