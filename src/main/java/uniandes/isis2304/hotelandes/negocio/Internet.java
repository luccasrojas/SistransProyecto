package uniandes.isis2304.hotelandes.negocio;

public class Internet implements VOInternet{

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	private long idServicio;
	private double capacidad;
	private double costoPorDia;
	
	/* ****************************************************************
	 * 			M�todos 
	 *****************************************************************/
	public Internet()
	{
		this.idServicio = 0L;
		this.capacidad = 0;
		this.costoPorDia = 0.0;
	}
	
	public Internet(long idServicio, double capacidad, double costoPorDia)
	{
		this.idServicio = idServicio;
		this.capacidad = capacidad;
		this.costoPorDia = costoPorDia;
	}

	public long getIdServicio() {
		return idServicio;
	}

	public void setIdServicio(long idServicio) {
		this.idServicio = idServicio;
	}

	public double getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	public double getCostoPorDia() {
		return costoPorDia;
	}

	public void setCostoPorDia(double costoPorDia) {
		this.costoPorDia = costoPorDia;
	}
	
	@Override
	public String toString() 
	{
		return "Internet [idServicio=" + idServicio + ", capacidad=" + capacidad + ", costoPorDia=" + costoPorDia + "]";
	}
	
}
