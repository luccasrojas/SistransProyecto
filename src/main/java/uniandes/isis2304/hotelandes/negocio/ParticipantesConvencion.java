package uniandes.isis2304.hotelandes.negocio;

public class ParticipantesConvencion implements VOParticipantesConvencion{
    /* ****************************************************************
    * 			Atributos
    *****************************************************************/

    private long idConvencion;
    private String tipoDocumento;
    private int numeroDocumento;

    /* ****************************************************************
    * 			Métodos
    *****************************************************************/

    /**
     * Constructor por defecto
     * */
    public ParticipantesConvencion() {
        this.idConvencion = 0;
        this.tipoDocumento = "";
        this.numeroDocumento = 0;
    }

    /**
     * Constructor con valores
     * @param idConvencion
     * @param tipoDocumento
     * @param numeroDocumento
     * */
    public ParticipantesConvencion(long idConvencion, String tipoDocumento, int numeroDocumento) {
        this.idConvencion = idConvencion;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
    }

    public long getIdConvencion() {
        return idConvencion;
    }

    public void setIdConvencion(long idConvencion) {
        this.idConvencion = idConvencion;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public int getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(int numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    @Override
    public String toString() {
        return "ParticipantesConvencion [idConvencion=" + idConvencion + ", tipoDocumento=" + tipoDocumento + ", numeroDocumento=" + numeroDocumento + "]";
    }
}
