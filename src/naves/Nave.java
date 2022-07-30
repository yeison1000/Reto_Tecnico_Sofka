package naves;

public abstract class Nave {

    private String nombre;
    private String empuje;
    private String objetivo;
    private String fecha;

    public Nave (){}

    public Nave(String nombre, String empuje, String objetivo, String fecha) {
        this.nombre = nombre;
        this.empuje = empuje;
        this.objetivo = objetivo;
        this.fecha = fecha;
    }

    public abstract Object elegirNave(Object tipoNave);

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmpuje() {
        return empuje;
    }

    public void setEmpuje(String empuje) {
        this.empuje = empuje;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
