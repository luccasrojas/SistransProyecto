package uniandes.isis2304.hotelandes.negocio;

public class TipoServicio implements VOTipoServicio{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	private String tipoServicio;
	
	/* ****************************************************************
	 * 			M�todos 
	 *****************************************************************/
	public TipoServicio() {
		this.tipoServicio = "";
	}

	public TipoServicio(String tipoServicio) {
		this.tipoServicio = tipoServicio;
	}

	public String getTipoServicio() {
		return tipoServicio;
	}

	public void setTipoServicio(String tipoServicio) {
		this.tipoServicio = tipoServicio;
	}

	@Override
	public String toString() {
		return "TipoServicio [tipoServicio=" + tipoServicio + "]";
	}
	
}
