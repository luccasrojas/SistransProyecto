package uniandes.isis2304.hotelandes.negocio;

public interface VOEstablecimiento {

	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
	public long getIdServicio();
	
	public String getNombre();
	
	public int getCapacidad();
	
	public String getEstiloTipo();
	
	public double getDescuentoPlan();
	
	@Override
	public String toString();
	
}
