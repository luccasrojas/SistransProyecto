package uniandes.isis2304.hotelandes.negocio;

public class RolUsuario implements VORolUsuario{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	private String rol;
	
	/* ****************************************************************
	 * 			M�todos 
	 *****************************************************************/
	public RolUsuario() {
		this.rol = "";
	}

	public RolUsuario(String rol) {
		this.rol = rol;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	@Override
	public String toString() {
		return "RolUsuario [rol=" + rol + "]";
	}

}
