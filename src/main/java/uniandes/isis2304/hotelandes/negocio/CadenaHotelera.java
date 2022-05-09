package uniandes.isis2304.hotelandes.negocio;

public class CadenaHotelera implements VOCadenaHotelera{
	
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El nombre de cada cadena hotelera (�NICO)
	 */
	private String nombre;
	
	/* ****************************************************************
	 * 			M�todos 
	 *****************************************************************/
	/**
	 * Constructor por defecto
	 */
	public CadenaHotelera() 
	{
		this.nombre = "";
	}

	/**
	 * Constructor con valores
	 */
	public CadenaHotelera(String nombre)
	{
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@Override
	public String toString() 
	{
		return "Cadena Hotelera [nombre=" + nombre + "]";
	}
}
