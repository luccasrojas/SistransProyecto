package uniandes.isis2304.hotelandes.negocio;

public class Servicio implements VOServicio{

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	private String nombreHotel;
	private long idServicio;
	private double horaInicio;
	private double horaFin;
	private String tipoServicio;
	
	/* ****************************************************************
	 * 			M�todos 
	 *****************************************************************/
	public Servicio() 
	{
		this.nombreHotel = "";
		this.idServicio = 0L;
		this.horaInicio = 0.0;
		this.horaFin = 0.0;
		this.tipoServicio = "PLANCHADO";
	}
	
	public Servicio(String nombreHotel, long idServicio, double horaInicio, double horaFin,
			String tipoServicio)
	{
		this.nombreHotel = nombreHotel;
		this.idServicio = idServicio;
		this.horaInicio = horaInicio;
		this.horaFin = horaFin;
		this.tipoServicio = tipoServicio;
	}

	public String getNombreHotel() {
		return nombreHotel;
	}

	public void setNombreHotel(String nombreHotel) {
		this.nombreHotel = nombreHotel;
	}

	public long getIdServicio() {
		return idServicio;
	}

	public void setIdServicio(long idServicio) {
		this.idServicio = idServicio;
	}

	public double getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(double horaInicio) {
		this.horaInicio = horaInicio;
	}

	public double getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(double horaFin) {
		this.horaFin = horaFin;
	}

	public String getTipoServicio() {
		return tipoServicio;
	}

	public void setTipoServicio(String tipoServicio) {
		this.tipoServicio = tipoServicio;
	}
	
	@Override
	public String toString() 
	{
		return "Servicio [nombreHotel=" + nombreHotel + ", idServicio=" + idServicio + 
				", horaInicio=" + horadoubleToString(horaInicio) + 
				", horaFin=" + horadoubleToString(horaFin) +  
				", tipoServicio=" + tipoServicio + "]";
	}
	
	private String horadoubleToString(double hora)
	{
		int hour = ((int) hora) < 13 ? ((int) hora) : ((int) hora) - 12;
		int minute = (int) (hora-((int) hora))*60;
		String fmat = ((int) hora) < 12 ? " am" : " pm";
		return hour + ":" + (minute < 10 ? "0"+minute : minute) + fmat;
	}
}
