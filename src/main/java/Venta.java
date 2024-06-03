
import java.util.ArrayList;
import java.util.Date;

public class Venta {
    private int idVenta;
    private Cliente cliente;
    private ArrayList<Producto> productos = new ArrayList<>();
    private double precioVentaTotal;
    private Date fechaVenta;

    public Venta() {
    }

    public Venta(Cliente cliente, double precioVentaTotal, ArrayList<Producto> productos, int idVenta, Date fechaVenta) {
        this.cliente = cliente;
        this.precioVentaTotal = precioVentaTotal;
        this.productos = productos;
        this.idVenta = idVenta;
        this.fechaVenta = fechaVenta;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Date getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public double getPrecioVentaTotal() {
        return precioVentaTotal;
    }

    public void setPrecioVentaTotal(double precioVentaTotal) {
        this.precioVentaTotal = precioVentaTotal;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public void setProductos(ArrayList<Producto> productos) {
        this.productos = productos;
    }
}
