package uniandes.isis2304.hotelandes.negocio;

public class ProductoPedido implements VOProductoPedido {
    
    private long idPedido;
    private long idServicio;
    private long idProducto;
    private int cantidad;

    public ProductoPedido() 
    {
        this.idPedido=0;
        this.idServicio=0;
        this.idProducto=0;
        this.cantidad=0;
    }
    public ProductoPedido(long idPedido, long idServicio, long idProducto, int cantidad) {
        this.idPedido = idPedido;
        this.idServicio = idServicio;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
    }

    public long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(long idPedido) {
        this.idPedido = idPedido;
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

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "ProductoPedido [cantidad=" + cantidad + ", idPedido=" + idPedido + ", idProducto=" + idProducto
                + ", idServicio=" + idServicio + "]";
    }
    

    
}
