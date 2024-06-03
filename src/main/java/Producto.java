
public class Producto {
    private int idProducto;
    private String nombreProducto;
    private double precioProducto;
    private int stockProducto;

    public Producto(String nombreProducto, int idProducto, double precioProducto, int stockProducto) {
        this.nombreProducto = nombreProducto;
        this.idProducto = idProducto;
        this.precioProducto = precioProducto;
        this.stockProducto = stockProducto;
    }
    public Producto(){}
    // Getters and Setters
    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public double getPrecioProducto() {
        return precioProducto;
    }

    public void setPrecioProducto(double precioProducto) {
        this.precioProducto = precioProducto;
    }

    public int getStockProducto() {
        return stockProducto;
    }

    public void setStockProducto(int stockProducto) {
        this.stockProducto = stockProducto;
    }
}