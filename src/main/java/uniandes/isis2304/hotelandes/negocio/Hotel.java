package uniandes.isis2304.hotelandes.negocio;

public class Hotel implements VOHotel{
	
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	private String nombreCadena;
	
	private String nombreHotel;
	
	private String pais;
	
	private String ciudad;
	
	private String direccion;
	
	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
	public Hotel () 
	{
		this.nombreCadena = "";
		this.nombreHotel = "";
		this.pais = "";
		this.ciudad = "";
		this.direccion = "";
	}
	
	public Hotel (String nombreCadena, String nombreHotel, String pais, String ciudad, String direccion) 
	{
		this.nombreCadena = nombreCadena;
		this.nombreHotel = nombreHotel;
		this.pais = pais;
		this.ciudad = ciudad;
		this.direccion = direccion;
	}

	public String getNombreCadena() {
		return nombreCadena;
	}

	public void setNombreCadena(String nombreCadena) {
		this.nombreCadena = nombreCadena;
	}

	public String getNombreHotel() {
		return nombreHotel;
	}

	public void setNombreHotel(String nombreHotel) {
		this.nombreHotel = nombreHotel;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
	@Override
	public String toString()
	{
		return "Hotel [nombreCadena="+ nombreCadena + ", nombreHotel=" + nombreHotel + 
				", pais=" + pais + ", ciudad=" + ciudad + ", direccion=" + direccion +"]";
	}
	
}
