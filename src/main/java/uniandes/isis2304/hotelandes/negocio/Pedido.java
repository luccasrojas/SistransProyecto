package uniandes.isis2304.hotelandes.negocio;

public class Pedido implements VOPedido{
    private long idPedido;
    private int pagoHabitacion;
    private long idCuenta;

    public Pedido()
    {
        this.idPedido =0;
        this.pagoHabitacion =0;
        this.idCuenta =0;
    }
    public Pedido(long idPedido, int pagoHabitacion, long idCuenta) {
        this.idPedido = idPedido;
        this.pagoHabitacion = pagoHabitacion;
        this.idCuenta = idCuenta;
    }


    public long getIdPedido() {
        return idPedido;
    }


    public void setIdPedido(long idPedido) {
        this.idPedido = idPedido;
    }


    public boolean getPagoHabitacionB() {
        if(this.pagoHabitacion==1)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public void setPagoHabitacionB(boolean pagoHabitacion) {
        if (pagoHabitacion)
        {
            this.pagoHabitacion=1;
        }
        else
        {
            this.pagoHabitacion=0;
        }
    }
    public int getPagoHabitacion() {
        return this.pagoHabitacion;
    }
    public void setPagoHabitacion(int pagoHabitacion)
    {
        this.pagoHabitacion=pagoHabitacion;
    }

    public long getIdCuenta() {
        return idCuenta;
    }


    public void setIdCuenta(long idCuenta) {
        this.idCuenta = idCuenta;
    }


    @Override
    public String toString() {
        return "Pedido [idCuenta=" + idCuenta + ", idPedido=" + idPedido + ", pagoHabitacion=" + pagoHabitacion + "]";
    }
    
    
}
