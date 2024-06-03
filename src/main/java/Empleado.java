

public class Empleado {
    private int idEmpleado;
    private String nombreEmpleado;
    private String apellidoEmpleado;
    private double sueldoEmpleado;

    public Empleado() {
    }

    public Empleado(String apellidoEmpleado, int idEmpleado, String nombreEmpleado, double sueldoEmpleado) {
        this.apellidoEmpleado = apellidoEmpleado;
        this.idEmpleado = idEmpleado;
        this.nombreEmpleado = nombreEmpleado;
        this.sueldoEmpleado = sueldoEmpleado;
    }

    // Getters and Setters
    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public String getApellidoEmpleado() {
        return apellidoEmpleado;
    }

    public void setApellidoEmpleado(String apellidoEmpleado) {
        this.apellidoEmpleado = apellidoEmpleado;
    }

    public double getSueldoEmpleado() {
        return sueldoEmpleado;
    }

    public void setSueldoEmpleado(double sueldoEmpleado) {
        this.sueldoEmpleado = sueldoEmpleado;
    }
}

