
public class Plato {
    private int idPlato;
    private String nombrePlato;
    private double precioPlato;

    public Plato() {
    }

    public Plato(int idPlato, String nombrePlato, double precioPlato) {
        this.idPlato = idPlato;
        this.nombrePlato = nombrePlato;
        this.precioPlato = precioPlato;
    }

    // Getters and Setters
    public int getIdPlato() {
        return idPlato;
    }

    public void setIdPlato(int idPlato) {
        this.idPlato = idPlato;
    }

    public String getNombrePlato() {
        return nombrePlato;
    }

    public void setNombrePlato(String nombrePlato) {
        this.nombrePlato = nombrePlato;
    }

    public double getPrecioPlato() {
        return precioPlato;
    }

    public void setPrecioPlato(double precioPlato) {
        this.precioPlato = precioPlato;
    }
}
