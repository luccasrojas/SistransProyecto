package uniandes.isis2304.hotelandes.negocio;

public interface VOPlanConsumo {
    public String getNombreHotel();
    public long getIdPlanConsumo();
    public String getNombre();
    public int getDuracionMin();
    public double getDescuento();

    @Override
    public String toString();
}
