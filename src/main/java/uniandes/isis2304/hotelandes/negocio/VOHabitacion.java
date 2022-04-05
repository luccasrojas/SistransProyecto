package uniandes.isis2304.hotelandes.negocio;

public interface VOHabitacion {

	/* ****************************************************************
	 * 			M�todos 
	 *****************************************************************/
	public String getNombreHotel();
	
	public int getNumero();
	
	public String getTipoHabitacion();
	
	public double getCostoPorNoche();
	
	public String getDescripcion();
	
	public int getCapacidadMin();
	
	public int getCapacidadMax();
	
	@Override
	public String toString();
	
}
