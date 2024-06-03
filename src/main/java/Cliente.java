import java.util.ArrayList;

public class Cliente {
    private int idCliente;
    private String nombreCliente;
    private String apellidoCliente;
    private boolean habitualCliente;
    private ArrayList<Venta> ventasCliente;
    private int cantidadComprasCliente;
    private ArrayList<Comanda> comandasCliente;

    public Cliente() {
    }

    public Cliente(String apellidoCliente, int cantidadComprasCliente, ArrayList<Comanda> comandasCliente, boolean habitualCliente, int idCliente, String nombreCliente, ArrayList<Venta> ventasCliente) {
        this.apellidoCliente = apellidoCliente;
        this.cantidadComprasCliente = cantidadComprasCliente;
        this.comandasCliente = comandasCliente;
        this.habitualCliente = habitualCliente;
        this.idCliente = idCliente;
        this.nombreCliente = nombreCliente;
        this.ventasCliente = ventasCliente;
    }

    public String getApellidoCliente() {
        return apellidoCliente;
    }

    public void setApellidoCliente(String apellidoCliente) {
        this.apellidoCliente = apellidoCliente;
    }

    public int getCantidadComprasCliente() {
        return cantidadComprasCliente;
    }

    public void setCantidadComprasCliente(int cantidadComprasCliente) {
        this.cantidadComprasCliente = cantidadComprasCliente;
    }

    public ArrayList<Comanda> getComandasCliente() {
        return comandasCliente;
    }

    public void setComandasCliente(ArrayList<Comanda> comandasCliente) {
        this.comandasCliente = comandasCliente;
    }

    public boolean isHabitualCliente() {
        return habitualCliente;
    }

    public void setHabitualCliente(boolean habitualCliente) {
        this.habitualCliente = habitualCliente;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public ArrayList<Venta> getVentasCliente() {
        return ventasCliente;
    }

    public void setVentasCliente(ArrayList<Venta> ventasCliente) {
        this.ventasCliente = ventasCliente;
    }
}

