package uniandes.isis2304.hotelandes.negocio;

public interface VOParticipantesConvencion {
    
    public long getIdConvencion();
    public String getTipoDocumento();
    public int getNumeroDocumento();

    @Override
    public String toString();
}
