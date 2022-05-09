package uniandes.isis2304.hotelandes.negocio;
import java.sql.Timestamp;

public interface VOConvencion {
    
    public long getIdConvencion();
    public Timestamp getFechaIn();
    public Timestamp getFechaOut();
    public Long getIdPlanConsumo();
    public String getHotel();

    @Override
	public String toString();
}
