package uniandes.isis2304.hotelandes.negocio;

import java.sql.Timestamp;

public class Convencion implements VOConvencion {
    /* ****************************************************************
	 * 			Atributos
	 *****************************************************************/

	private long idConvencion;
    private Timestamp fechaIn;
    private Timestamp fechaOut;
    private Long idPlanConsumo;
    private String hotel;
	
	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
	/**
	 * Constructor por defecto
	 */
	public Convencion() 
	{
		this.idConvencion = 0;
		this.fechaIn = new Timestamp(0);
		this.fechaOut = new Timestamp(0);
        this.idPlanConsumo = 0L;
        this.hotel = "";
	}

	/**
	 * Constructor con valores
	 */
	public Convencion(long idConvencion, Timestamp fechaIn, Timestamp fechaOut, Long idPlanConsumo, String hotel)
	{
		this.idConvencion = idConvencion;
		this.fechaIn = fechaIn;
		this.fechaOut = fechaOut;
        this.idPlanConsumo = idPlanConsumo;
        this.hotel = hotel;
	}

	public long getIdConvencion() {
        return idConvencion;
    }

    public void setIdConvencion(long idConvencion) {
        this.idConvencion = idConvencion;
    }

    public Timestamp getFechaIn() {
        return fechaIn;
    }

    public void setFechaIn(Timestamp fechaIn) {
        this.fechaIn = fechaIn;
    }

    public Timestamp getFechaOut() {
        return fechaOut;
    }

    public void setFechaOut(Timestamp fechaOut) {
        this.fechaOut = fechaOut;
    }

    public Long getIdPlanConsumo() {
        return idPlanConsumo;
    }

    public void setIdPlanConsumo(Long idPlanConsumo) {
        this.idPlanConsumo = idPlanConsumo;
    }

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    @Override
    public String toString() {
        return "Convencion [fechaIn=" + fechaIn + ", fechaOut=" + fechaOut + ", hotel=" + hotel + ", idConvencion="
                + idConvencion + ", idPlanConsumo=" + idPlanConsumo + "]";
    }

}
