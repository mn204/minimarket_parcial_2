

import java.util.ArrayList;
import java.util.Date;

public class Comanda {
    private int idComanda;
    private ArrayList<Plato> platos;
    private int comandaMesa;
    private Date fechaComanda;
    private double precioComanda;
    private boolean comandaPagado;

    public Comanda() {
    }

    public Comanda(int comandaMesa, boolean comandaPagado, Date fechaComanda, int idComanda, ArrayList<Plato> platos, double precioComanda) {
        this.comandaMesa = comandaMesa;
        this.comandaPagado = comandaPagado;
        this.fechaComanda = fechaComanda;
        this.idComanda = idComanda;
        this.platos = platos;
        this.precioComanda = precioComanda;
    }

    public int getComandaMesa() {
        return comandaMesa;
    }

    public void setComandaMesa(int comandaMesa) {
        this.comandaMesa = comandaMesa;
    }

    public boolean isComandaPagado() {
        return comandaPagado;
    }

    public void setComandaPagado(boolean comandaPagado) {
        this.comandaPagado = comandaPagado;
    }

    public Date getFechaComanda() {
        return fechaComanda;
    }

    public void setFechaComanda(Date fechaComanda) {
        this.fechaComanda = fechaComanda;
    }

    public int getIdComanda() {
        return idComanda;
    }

    public void setIdComanda(int idComanda) {
        this.idComanda = idComanda;
    }

    public ArrayList<Plato> getPlatos() {
        return platos;
    }

    public void setPlatos(ArrayList<Plato> platos) {
        this.platos = platos;
    }

    public double getPrecioComanda() {
        return precioComanda;
    }

    public void setPrecioComanda(double precioComanda) {
        this.precioComanda = precioComanda;
    }
}
