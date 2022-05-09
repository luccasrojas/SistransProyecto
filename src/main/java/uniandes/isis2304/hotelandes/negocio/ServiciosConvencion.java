package uniandes.isis2304.hotelandes.negocio;

public class ServiciosConvencion implements VOServiciosConvencion {
    
    /* ****************************************************************
    * 			Atributos
    *****************************************************************/

    private long idConvencion;
    private long idServicio;

    /* ****************************************************************
    * 			Métodos
    *****************************************************************/

    /**
     * Constructor por defecto
     */
    public ServiciosConvencion() {
        this.idConvencion = 0;
        this.idServicio = 0;
    }

    /**
     * Constructor con valores
     */
    public ServiciosConvencion(long idConvencion, long idServicio) {
        this.idConvencion = idConvencion;
        this.idServicio = idServicio;
    }

    public long getIdConvencion() {
        return idConvencion;
    }

    public void setIdConvencion(long idConvencion) {
        this.idConvencion = idConvencion;
    }

    public long getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(long idServicio) {
        this.idServicio = idServicio;
    }

    @Override
    public String toString() {
        return "ServiciosConvencion [idConvencion=" + idConvencion + ", idServicio=" + idServicio + "]";
    }
}
