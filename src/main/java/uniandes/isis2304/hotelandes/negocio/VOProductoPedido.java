package uniandes.isis2304.hotelandes.negocio;

public interface VOProductoPedido {

	/* ****************************************************************
	 * 			M�todos 
	 *****************************************************************/
    public long getIdPedido();
    public long getIdServicio();
    public long getIdProducto();
    public int getCantidad();
	
	@Override
	public String toString();

	
}
