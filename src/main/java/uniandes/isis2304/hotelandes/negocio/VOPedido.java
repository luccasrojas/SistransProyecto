package uniandes.isis2304.hotelandes.negocio;

public interface VOPedido {

    public long getIdPedido();
    public int getPagoHabitacion();
    public long getIdCuenta();

    @Override
	public String toString();
}
