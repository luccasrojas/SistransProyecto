package uniandes.isis2304.hotelandes.negocio;
public class PlanConsumo implements VOPlanConsumo
{
    private long idPlanConsumo;
    private String nombreHotel;
    private String nombre;
    private int duracionMin;
    private double descuento;
    
    public PlanConsumo(){
        this.idPlanConsumo =0;
        this.nombreHotel ="";
        this.nombre ="";
        this.duracionMin =0;
        this.descuento=0;
    }
    public PlanConsumo( String nombreHotel,long idPlanConsumo, String nombre, int duracionMin, double descuento) {
        this.nombreHotel = nombreHotel;
        this.idPlanConsumo = idPlanConsumo;
        this.nombre = nombre;
        this.duracionMin = duracionMin;
        this.descuento = descuento;
    }
    public long getIdPlanConsumo() {
        return idPlanConsumo;
    }
    public void setIdPlanConsumo(long idPlanConsumo) {
        this.idPlanConsumo = idPlanConsumo;
    }
    public String getNombreHotel() {
        return nombreHotel;
    }
    public void setNombreHotel(String nombreHotel) {
        this.nombreHotel = nombreHotel;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public int getDuracionMin() {
        return duracionMin;
    }
    public void setDuracionMin(int duracionMin) {
        this.duracionMin = duracionMin;
    }
    public double getDescuento() {
        return descuento;
    }
    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }
    @Override
    public String toString() {
        return "PlanConsumo [descuento=" + descuento + ", duracionMin=" + duracionMin + ", idPlanConsumo="
                + idPlanConsumo + ", nombre=" + nombre + ", nombreHotel=" + nombreHotel + "]";
    }
    
    
}