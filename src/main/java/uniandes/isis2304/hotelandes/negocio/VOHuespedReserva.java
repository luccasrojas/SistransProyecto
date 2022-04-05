package uniandes.isis2304.hotelandes.negocio;

public interface VOHuespedReserva {
    public long getIdReservaHabitacion();
    public TipoDocumento getTipoDocumento();
    public int getNumeroDocumento();
    public int getAcompanante();

    @Override
	public String toString();
    
}
