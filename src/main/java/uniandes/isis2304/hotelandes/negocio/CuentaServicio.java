package uniandes.isis2304.hotelandes.negocio;
import java.sql.Timestamp;

public class CuentaServicio implements VOCuentaServicio
{
    private long idCuenta;
    private double costo;
    private String concepto;
    private Timestamp fecha;
    private long idServicio;
    private long idReservaHabitacion;
    private TipoDocumento registradoPorTipoDocumento;
    private int registradoPorNumeroDocumento;

    public CuentaServicio()
    {
        this.idCuenta =0;
        this.costo=0;
        this.concepto="";
        this.fecha= new Timestamp(0);
        this.idServicio =0;
        this.idReservaHabitacion=0;
        this.registradoPorNumeroDocumento=0;
        this.registradoPorTipoDocumento=TipoDocumento.CC;
    }
    public CuentaServicio(long idCuenta, double costo, String concepto,
            Timestamp fecha, long idServicio, long idReservaHabitacion,TipoDocumento tipoDoc,int doc) {
        this.idCuenta = idCuenta;
        this.costo = costo;
        this.concepto = concepto;
        this.fecha = fecha;
        this.idServicio = idServicio;
        this.idReservaHabitacion = idReservaHabitacion;
        this.registradoPorTipoDocumento = tipoDoc;
        this.registradoPorNumeroDocumento=doc;
    }
    public long getIdCuenta() {
        return idCuenta;
    }
    public void setIdCuenta(long idCuenta) {
        this.idCuenta = idCuenta;
    }
    public double getCosto() {
        return costo;
    }
    public void setCosto(double costo) {
        this.costo = costo;
    }
    public String getConcepto() {
        return concepto;
    }
    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }
    public Timestamp getFecha() {
        return fecha;
    }
    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }
    public long getIdServicio() {
        return idServicio;
    }
    public void setIdServicio(long idServicio) {
        this.idServicio = idServicio;
    }
    public long getIdReservaHabitacion() {
        return idReservaHabitacion;
    }
    public void setIdReservaHabitacion(long idReservaHabitacion) {
        this.idReservaHabitacion = idReservaHabitacion;
    }
    public TipoDocumento getRegistradoPorTipoDocumento() {
        return registradoPorTipoDocumento;
    }
    public void setRegistradoPorTipoDocumento(TipoDocumento registradoPorTipoDocumento) {
        this.registradoPorTipoDocumento = registradoPorTipoDocumento;
    }
    public int getRegistradoPorNumeroDocumento() {
        return registradoPorNumeroDocumento;
    }
    public void setRegistradoPorNumeroDocumento(int registradoPorNumeroDocumento) {
        this.registradoPorNumeroDocumento = registradoPorNumeroDocumento;
    }
    @Override
    public String toString() {
        return "CuentaServicio [concepto=" + concepto + ", costo=" + costo + ", fecha=" + fecha + ", idCuenta="
                + idCuenta + ", idReservaHabitacion=" + idReservaHabitacion + ", idServicio=" + idServicio
                + ", registradoPorDocumento=" + registradoPorNumeroDocumento + ", registradoPorTipoDocumento="
                + registradoPorTipoDocumento + "]";
    }

}