package uniandes.isis2304.hotelandes.negocio;
import java.sql.Timestamp;

public interface VOReservaHabitacion {
    public long getIdReservaHabitacion();
    public Timestamp getFechaIn();
    public Timestamp getFechaOut();
    public int getNumPersonas();
    public double getCuentaMinibar();
    public String getNombreHotel();
    public int getNumeroHabitacion();
    public long getIdPlanConsumo();

    @Override
    public String toString();
    
}
