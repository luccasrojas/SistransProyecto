package uniandes.isis2304.hotelandes.negocio;

public class Gimnasio implements VOGimnasio{

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	private long idServicio;
	private int capacidad;
	private String descripcion;
	private double costo;
	
	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
	public Gimnasio()
	{
		this.idServicio = 0L;
		this.capacidad = 1;
		this.descripcion = "";
		this.costo = 0.0;
	}
	
	public Gimnasio(long idServicio, int capacidad, String descripcion, double costo)
	{
		this.idServicio = idServicio;
		this.capacidad = capacidad;
		this.descripcion = descripcion;
		this.costo = costo;
	}

	public long getIdServicio() {
		return idServicio;
	}

	public void setIdServicio(long idServicio) {
		this.idServicio = idServicio;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public double getCosto() {
		return costo;
	}

	public void setCosto(double costo) {
		this.costo = costo;
	}
	
	@Override
	public String toString() 
	{
		return "Gimnasio [idServicio=" + idServicio + ", capacidad=" + capacidad + ", descripcion=" + descripcion + 
				", costo=" + costo + "]";
	}
	
}
