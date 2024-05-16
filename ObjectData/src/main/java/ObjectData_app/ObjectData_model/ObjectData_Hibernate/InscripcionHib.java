package ObjectData_app.ObjectData_model.ObjectData_Hibernate;
import java.util.Date;
import jakarta.persistence.*;
@Entity
@Table(name = "inscripcion")
public class InscripcionHib {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "numeroInscripcion")
    private int numeroInscripcion;

    @Column(name = "numeroSocio")
    private int numeroSocio;

    @Column(name = "numeroExcursion")
    private int numeroExcursion;

    @Column(name = "fechaInscripcion")
    private Date fechaInscripcion;   
    //Constructor vacio.
    public InscripcionHib()
    {

    }
    // Constructor
    public InscripcionHib(int numeroInscripcion, int numeroSocio, int numeroExcursion, Date fechaInscripcion) {
        this.numeroInscripcion = numeroInscripcion;
        this.numeroSocio = numeroSocio;
        this.numeroExcursion = numeroExcursion;
        this.fechaInscripcion = fechaInscripcion;
    }
    // Getters
    public int getNumeroInscripcion() {
        return numeroInscripcion;
    }

    public int getNumeroSocio() {
        return numeroSocio;
    }

    public int getNumeroExcursion() {
        return numeroExcursion;
    }

    public Date getFechaInscripcion() {
        return fechaInscripcion;
    }

    // Setters
    public void setNumeroInscripcion(int numeroInscripcion) {
        this.numeroInscripcion = numeroInscripcion;
    }

    public void setNumeroSocio(int numeroSocio) {
        this.numeroSocio = numeroSocio;
    }

    public void setNumeroExcursion(int numeroExcursion) {
        this.numeroExcursion = numeroExcursion;
    }

    public void setFechaInscripcion(Date fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    // Método toString
    @Override
    public String toString() {
        return "ID Inscripción: " + numeroInscripcion +
                " | ID Socio: " + numeroSocio +
                " | ID Escursión: " + numeroExcursion +
                " | Fecha Inscripción: " + fechaInscripcion;
    }    

}
