package uniandes.isis2304.hotelandes.negocio;

public interface VOSalonReunion {
    public long getIdServicio();
    public int getNumero();
    public double getCostoPorHora();
    public double getCostoAdicional();

    @Override
    public String toString();
    
}
