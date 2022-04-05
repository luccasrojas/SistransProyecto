package uniandes.isis2304.hotelandes.negocio;

public interface VOSalonConferencia {
    public long getIdServicio();
    public int getNumero();
    public int getCapacidad();
    public double getCostoPorHora();

    @Override
    public String toString();
}
