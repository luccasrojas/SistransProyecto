package uniandes.isis2304.hotelandes.negocio;

public interface VOUsuario {

	/* ****************************************************************
	 * 			M�todos 
	 *****************************************************************/
	public String getNombre();
	
	public TipoDocumento getTipoDocumento();
	
	public int getNumeroDocumento();

	public String getCorreo();
	
	public String getContrasena();
	
	public String getRolUsuario();
	
	@Override
	public String toString();

	
}
