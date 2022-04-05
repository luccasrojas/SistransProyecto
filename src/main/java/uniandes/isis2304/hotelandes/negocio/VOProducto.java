package uniandes.isis2304.hotelandes.negocio;

public interface VOProducto {
    public long getIdServicio();
    public long getIdProducto();
    public String getNombre();
    public double getCosto();
    public int getPlanTodoIncluido();
    public int getDuracion();
    
    @Override
    public String toString();
}
