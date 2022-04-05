package uniandes.isis2304.hotelandes.negocio;

public class SalonConferencia implements VOSalonConferencia{
    private long idServicio;
    private int numero;
    private int capacidad;
    private double costoPorHora;

    public SalonConferencia() 
    {
        this.idServicio = 0;
        this.numero = 0;
        this.capacidad = 0;
        this.costoPorHora = 0;
    }
    public SalonConferencia(long idServicio, int numero, int capacidad, double costoPorHora) {
        this.idServicio = idServicio;
        this.numero = numero;
        this.capacidad = capacidad;
        this.costoPorHora = costoPorHora;
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
    public int getCapacidad() {
        return capacidad;
    }
    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }
    public double getCostoPorHora() {
        return costoPorHora;
    }
    public void setCostoPorHora(double costoPorHora) {
        this.costoPorHora = costoPorHora;
    }
    @Override
    public String toString() {
        return "SalonConferencia [capacidad=" + capacidad + ", costoPorHora=" + costoPorHora + ", idServicio="
                + idServicio + ", numero=" + numero + "]";
    }
    
}
