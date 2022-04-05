package uniandes.isis2304.hotelandes.negocio;

public interface VOInternet {

	/* ****************************************************************
	 * 			M�todos 
	 *****************************************************************/
	public long getIdServicio();
	
	public double getCapacidad();
	
	public double getCostoPorDia();
	
	@Override
	public String toString();
}
