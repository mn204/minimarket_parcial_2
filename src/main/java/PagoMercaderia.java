import java.util.Date;

public class PagoMercaderia {
    private int idPagoMercaderia;
    private double costoTotalMercaderia;
    private Date fechaDePagoMercaderia;
    private boolean estaPagado;
    private Proveedor proveedor;

    public PagoMercaderia() {
    }

    public PagoMercaderia(double costoTotalMercaderia, boolean estaPagado, Date fechaDePagoMercaderia, int idPagoMercaderia, Proveedor proveedor) {
        this.costoTotalMercaderia = costoTotalMercaderia;
        this.estaPagado = estaPagado;
        this.fechaDePagoMercaderia = fechaDePagoMercaderia;
        this.idPagoMercaderia = idPagoMercaderia;
        this.proveedor = proveedor;
    }

    public double getCostoTotalMercaderia() {
        return costoTotalMercaderia;
    }

    public void setCostoTotalMercaderia(double costoTotalMercaderia) {
        this.costoTotalMercaderia = costoTotalMercaderia;
    }

    public boolean isEstaPagado() {
        return estaPagado;
    }

    public void setEstaPagado(boolean estaPagado) {
        this.estaPagado = estaPagado;
    }

    public Date getFechaDePagoMercaderia() {
        return fechaDePagoMercaderia;
    }

    public void setFechaDePagoMercaderia(Date fechaDePagoMercaderia) {
        this.fechaDePagoMercaderia = fechaDePagoMercaderia;
    }

    public int getIdPagoMercaderia() {
        return idPagoMercaderia;
    }

    public void setIdPagoMercaderia(int idPagoMercaderia) {
        this.idPagoMercaderia = idPagoMercaderia;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }
}
