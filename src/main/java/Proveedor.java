
public class Proveedor {
    private int idProveedor;
    private String nombreProveedor;
    private String apellidoProveedor;
    private String razonSocialProveedor;


    public Proveedor(String apellidoProveedor, int idProveedor, String nombreProveedor, String razonSocialProveedor) {
        this.apellidoProveedor = apellidoProveedor;
        this.idProveedor = idProveedor;
        this.nombreProveedor = nombreProveedor;
        this.razonSocialProveedor = razonSocialProveedor;

    }

    public Proveedor() {
    }

    public String getApellidoProveedor() {
        return apellidoProveedor;
    }

    public void setApellidoProveedor(String apellidoProveedor) {
        this.apellidoProveedor = apellidoProveedor;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public String getRazonSocialProveedor() {
        return razonSocialProveedor;
    }

    public void setRazonSocialProveedor(String razonSocialProveedor) {
        this.razonSocialProveedor = razonSocialProveedor;
    }
}

