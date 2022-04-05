package uniandes.isis2304.hotelandes.negocio;
import java.sql.Timestamp;

public interface VOCuentaServicio {
    
    public long getIdCuenta();
    public double getCosto();
    public String getConcepto();
    public Timestamp getFecha();
    public long getIdServicio();
    public long getIdReservaHabitacion();
    public TipoDocumento getRegistradoPorTipoDocumento();
    public int getRegistradoPorNumeroDocumento(); 


    
    @Override
	public String toString();
}
