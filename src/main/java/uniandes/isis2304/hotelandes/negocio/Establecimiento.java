package uniandes.isis2304.hotelandes.negocio;

public class Establecimiento implements VOEstablecimiento{

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	private long idServicio;
	private String nombre;
	private int capacidad;
	private String estiloTipo;
	private double descuentoPlan;
	
	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
	public Establecimiento()
	{
		this.idServicio = 0L;
		this.nombre = "";
		this.capacidad = 1;
		this.estiloTipo = "";
		this.descuentoPlan = 0.0;
	}
	
	public Establecimiento(long idServicio, String nombre, int capacidad, String estiloTipo, double descuentoPlan)
	{
		this.idServicio = idServicio;
		this.nombre = nombre;
		this.capacidad = capacidad;
		this.estiloTipo = estiloTipo;
		this.descuentoPlan = descuentoPlan;
	}

	public long getIdServicio() {
		return idServicio;
	}

	public void setIdServicio(long idServicio) {
		this.idServicio = idServicio;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	public String getEstiloTipo() {
		return estiloTipo;
	}

	public void setEstiloTipo(String estiloTipo) {
		this.estiloTipo = estiloTipo;
	}

	public double getDescuentoPlan() {
		return descuentoPlan;
	}

	public void setDescuentoPlan(double descuentoPlan) {
		this.descuentoPlan = descuentoPlan;
	}

	@Override
	public String toString() {
		return "Establecimiento [idServicio=" + idServicio + ", nombre=" + nombre + ", capacidad=" + capacidad
				+ ", estiloTipo=" + estiloTipo + ", descuentoPlan=" + descuentoPlan + "]";
	}
	
}
