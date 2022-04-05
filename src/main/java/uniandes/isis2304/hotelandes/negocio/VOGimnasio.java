package uniandes.isis2304.hotelandes.negocio;

public interface VOGimnasio {

	/* ****************************************************************
	 * 			M�todos 
	 *****************************************************************/
	public long getIdServicio();
	
	public int getCapacidad();
	
	public String getDescripcion();
	
	public double getCosto();

	@Override
	public String toString();
	
}
