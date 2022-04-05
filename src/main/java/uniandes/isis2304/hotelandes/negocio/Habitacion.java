package uniandes.isis2304.hotelandes.negocio;

public class Habitacion implements VOHabitacion{
	
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	private String nombreHotel;
	private int numero;
	private String tipoHabitacion;
	private double costoPorNoche;
	private String descripcion;
	private int capacidadMin;
	private int capacidadMax;
	
	/* ****************************************************************
	 * 			M�todos 
	 *****************************************************************/
	public Habitacion() 
	{
		this.nombreHotel = "";
		this.numero = 0;
		this.tipoHabitacion = "FAMILIAR";
		this.costoPorNoche = 1.0;
		this.descripcion = "";
		this.capacidadMin = 1;
		this.capacidadMax = 2;
	}
	
	public Habitacion(String nombreHotel, int numero, String tipoHabitacion, 
			double costoPorNoche, String descripcion, int capacidadMin, int capacidadMax) 
	{
		this.nombreHotel = nombreHotel;
		this.numero = numero;
		this.tipoHabitacion = tipoHabitacion;
		this.costoPorNoche = costoPorNoche;
		this.descripcion = descripcion;
		this.capacidadMin = capacidadMin;
		this.capacidadMax = capacidadMax;
	}

	public String getNombreHotel() {
		return nombreHotel;
	}

	public void setNombreHotel(String nombreHotel) {
		this.nombreHotel = nombreHotel;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getTipoHabitacion() {
		return tipoHabitacion;
	}

	public void setTipoHabitacion(String tipoHabitacion) {
		this.tipoHabitacion = tipoHabitacion;
	}

	public double getCostoPorNoche() {
		return costoPorNoche;
	}

	public void setCostoPorNoche(double costoPorNoche) {
		this.costoPorNoche = costoPorNoche;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getCapacidadMin() {
		return capacidadMin;
	}

	public void setCapacidadMin(int capacidadMin) {
		this.capacidadMin = capacidadMin;
	}

	public int getCapacidadMax() {
		return capacidadMax;
	}

	public void setCapacidadMax(int capacidadMax) {
		this.capacidadMax = capacidadMax;
	}
	
	@Override
	public String toString() 
	{
		return "Habitacion [nombreHotel=" + nombreHotel + ", numero=" + numero + ", tipoHabitacion=" + tipoHabitacion + 
				", costoPorNoche=" + costoPorNoche + ", descripcion=" + descripcion + ", capacidadMin=" + capacidadMin + 
				", capacidadMax=" + capacidadMax +"]";
	}
	
}
