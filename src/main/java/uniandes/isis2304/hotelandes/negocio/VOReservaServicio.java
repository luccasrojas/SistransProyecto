package uniandes.isis2304.hotelandes.negocio;
import java.sql.Timestamp;

public interface VOReservaServicio {

	/* ****************************************************************
	 * 			M�todos 
	 *****************************************************************/
     /**
	 * @return El nombre de la cadena hotelera
	 */
	public long getIdReservaServicio();
    public long getIdServicio();
    public long getIdReservaHabitacion();
    public Timestamp getFecha();
    public int getDuracion();
	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos del bar
	 */
	public String toString();

}
