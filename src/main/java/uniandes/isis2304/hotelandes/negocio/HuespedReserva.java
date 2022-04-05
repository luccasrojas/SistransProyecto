package uniandes.isis2304.hotelandes.negocio;
public class HuespedReserva implements VOHuespedReserva
{
    private long idReservaHabitacion;
    private TipoDocumento tipoDocumento;
    private int numeroDocumento;
    private int acompanante;
    public HuespedReserva()
    {
        this.idReservaHabitacion =0;
        this.tipoDocumento = TipoDocumento.CC;
        this.numeroDocumento =0;
        this.acompanante=0;
    }
    


    public HuespedReserva(long idReservaHabitacion, TipoDocumento tipoDocumento, int numeroDocumento, int acompanante) {
        this.idReservaHabitacion = idReservaHabitacion;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.acompanante = acompanante;
    }




    public long getIdReservaHabitacion() {
        return idReservaHabitacion;
    }



    public void setIdReservaHabitacion(long idReservaHabitacion) {
        this.idReservaHabitacion = idReservaHabitacion;
    }



    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }



    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }



    public int getNumeroDocumento() {
        return numeroDocumento;
    }



    public void setNumeroDocumento(int numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }



    public int getAcompanante() {
        return acompanante;
    }



    public void setAcompanante(int acompanante) {
        this.acompanante = acompanante;
    }
    public boolean getAcompananteB(){
        if(this.acompanante==0)
        {
            return false;
        }
        else{
            return true;
        }
    }
    public void setAcompananteB(boolean acompanante){
        if (acompanante)
        {this.acompanante =1;}
        else{this.acompanante =0;}
    }



    @Override
    public String toString() {
        return "HuespedReserva [acompanante=" + acompanante + ", idReservaHabitacion=" + idReservaHabitacion
                + ", numeroDocumento=" + numeroDocumento + ", tipoDocumento=" + tipoDocumento + "]";
    }
}