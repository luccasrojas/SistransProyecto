package uniandes.isis2304.hotelandes.negocio;

public class Producto implements VOProducto{
    private long idServicio;
    private long idProducto;
    private String nombre;
    private double costo;
    private int planTodoIncluido;
    private int duracion;

    public Producto()
    {
        this.idServicio =0;
        this.idProducto =0;
        this.nombre="";
        this.costo =0;
        this.planTodoIncluido =0;
        this.duracion =0;
    }
    
    public Producto(long idServicio, long idProducto, String nombre, double costo, int planTodoIncluido, int duracion) {
        this.idServicio = idServicio;
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.costo = costo;
        this.planTodoIncluido = planTodoIncluido;
        this.duracion = duracion;
    }
    public long getIdServicio() {
        return idServicio;
    }
    public void setIdServicio(long idServicio) {
        this.idServicio = idServicio;
    }
    public long getIdProducto() {
        return idProducto;
    }
    public void setIdProducto(long idProducto) {
        this.idProducto = idProducto;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public double getCosto() {
        return costo;
    }
    public void setCosto(double costo) {
        this.costo = costo;
    }


    public boolean getPlanTodoIncluidoB() {
        if (this.planTodoIncluido==0)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    public void setPlanTodoIncluidoB(boolean planTodoIncluido) {
        if (planTodoIncluido)
        {
            this.planTodoIncluido = 1;
        }
        else
        {
            this.planTodoIncluido =0;
        }
    }
    public int getPlanTodoIncluido() {
        return this.planTodoIncluido;
    }
    public void setPlanTodoIncluido(int planTodoIncluido)
    {
        this.planTodoIncluido =planTodoIncluido;
    }



    public int getDuracion() {
        return duracion;
    }
    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    @Override
    public String toString() {
        return "Producto [costo=" + costo + ", duracion=" + duracion + ", idProducto=" + idProducto + ", idServicio="
                + idServicio + ", nombre=" + nombre + ", planTodoIncluido=" + planTodoIncluido + "]";
    }
    
    
}
