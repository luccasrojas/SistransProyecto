package uniandes.isis2304.hotelandes.negocio;

public interface VOServicio {

	/* ****************************************************************
	 * 			M�todos 
	 *****************************************************************/
	public String getNombreHotel();
	
	public long getIdServicio();
	
	public double getHoraInicio();
	
	public double getHoraFin();
	
	public String getTipoServicio();
	
	@Override 
	public String toString();
	
}
