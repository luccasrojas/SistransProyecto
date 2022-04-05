package uniandes.isis2304.hotelandes.negocio;

public class Usuario implements VOUsuario{
	
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	private String nombre;
	private TipoDocumento tipoDocumento;
	private int numeroDocumento;
	private String correo;
	private String contrasena;
	private String rolUsuario;
	
	/* ****************************************************************
	 * 			M�todos 
	 *****************************************************************/
	public Usuario ()
	{
		this.nombre = "";
		this.tipoDocumento = TipoDocumento.CC;
		this.numeroDocumento = 0;
		this.correo = "";
		this.contrasena = "000000";
		this.rolUsuario = "CLIENTE";
	}
	
	public Usuario (String nombre, TipoDocumento tipoDocumento, int numeroDocumento,
			String correo, String contrasena, String rolUsuario)
	{
		this.nombre = nombre;
		this.tipoDocumento = tipoDocumento;
		this.numeroDocumento = numeroDocumento;
		this.correo = correo;
		this.contrasena = contrasena;
		this.rolUsuario = rolUsuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public int getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(int numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getRolUsuario() {
		return rolUsuario;
	}

	public void setRolUsuario(String rolUsuario) {
		this.rolUsuario = rolUsuario;
	}
	
	@Override
	public String toString() 
	{
		return "Usuario [nombre=" + nombre + ", tipoDocumento=" + tipoDocumento + 
				", numeroDocumento=" + numeroDocumento + ", correo=" + correo + ", contrasena=" + contrasena +
				",, rolUsuario=" + rolUsuario + "]";
	}
	
}
