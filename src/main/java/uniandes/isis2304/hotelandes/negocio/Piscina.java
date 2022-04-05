package uniandes.isis2304.hotelandes.negocio;

public class Piscina implements VOPiscina{

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	private long idServicio;
	private int capacidad;
	private double profundidad;
	private double costo;
	
	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
	public Piscina()
	{
		this.idServicio = 0L;
		this.capacidad = 1;
		this.profundidad = 0.0;
		this.costo = 0.0;
	}
	
	public Piscina(long idServicio, int capacidad, double profundidad, double costo)
	{
		this.idServicio = idServicio;
		this.capacidad = capacidad;
		this.profundidad = profundidad;
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

	public double getProfundidad() {
		return profundidad;
	}

	public void setProfundidad(double profundidad) {
		this.profundidad = profundidad;
	}

	public double getCosto() {
		return costo;
	}

	public void setCosto(double costo) {
		this.costo = costo;
	}
	
	@Override
	public String toString ()
	{
		return "Piscina [idServicio=" + idServicio + ", capacidad=" + capacidad + ", profundidad=" + profundidad + 
				", costo=" + costo + "]";
	}
	
}
