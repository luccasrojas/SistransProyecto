package uniandes.isis2304.hotelandes.negocio;

public interface VOPiscina {
	
	/* ****************************************************************
	 * 			M�todos 
	 *****************************************************************/
	public long getIdServicio();
	
	public int getCapacidad();
	
	public double getProfundidad();
	
	public double getCosto();

	@Override
	public String toString();

}
