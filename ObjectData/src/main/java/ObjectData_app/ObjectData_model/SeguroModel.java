package ObjectData_app.ObjectData_model;

public class SeguroModel {
    // Enumeración para los tipos de seguro
    public enum TipoSeguro {
        BASICO,
        COMPLETO
    }
    private static final Double basico = 25.50, completo = 45.00;
    private TipoSeguro tipo;
    private double precio;

    // Constructor
    public SeguroModel(TipoSeguro tipo) {
        this.tipo = tipo;
        switch (tipo) {
            case BASICO:
                this.precio = basico;
                break;
            case COMPLETO:
                this.precio = completo;
                break;
        }
    }

    // Getters
    public TipoSeguro getTipo() {
        return tipo;
    }

    public double getPrecio() {
        return precio;
    }

    // Setters
    public void setTipo(TipoSeguro tipo) {
        this.tipo = tipo;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    // Método toString
    @Override
    public String toString() {
        return "Seguro{" +
                "tipo=" + tipo +
                ", precio=" + precio +
                '}';
    }
}