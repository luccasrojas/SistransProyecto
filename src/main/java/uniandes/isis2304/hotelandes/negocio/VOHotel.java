package uniandes.isis2304.hotelandes.negocio;

public interface VOHotel {
	
	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
	public String getNombreCadena();
	
	public String getNombreHotel();
	
	public String getPais();
	
	public String getCiudad();
	
	public String getDireccion();
	
	@Override
	public String toString();
	
}
