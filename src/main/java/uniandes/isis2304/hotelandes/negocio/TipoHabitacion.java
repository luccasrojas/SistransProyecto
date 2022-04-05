package uniandes.isis2304.hotelandes.negocio;

public class TipoHabitacion implements VOTipoHabitacion {

	/* ****************************************************************
	* 			Atributos
	 *****************************************************************/
	private String tipoHabitacion;
	
	/* ****************************************************************
	 * 			M�todos 
	 *****************************************************************/
	public TipoHabitacion() {
		this.tipoHabitacion = "";
	}

	public TipoHabitacion(String tipoHabitacion) {
		this.tipoHabitacion = tipoHabitacion;
	}

	public String getTipoHabitacion() {
		return tipoHabitacion;
	}

	public void setTipoHabitacion(String tipoHabitacion) {
		this.tipoHabitacion = tipoHabitacion;
	}

	@Override
	public String toString() {
		return "TipoHabitacion [tipoHabitacion=" + tipoHabitacion + "]";
	}

}
