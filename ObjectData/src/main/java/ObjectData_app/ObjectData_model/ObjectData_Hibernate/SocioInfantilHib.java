package ObjectData_app.ObjectData_model.ObjectData_Hibernate;

import jakarta.persistence.*;

// Clase de entidad socio infantil 
@Entity
@Table(name = "socioInfantil")
public class SocioInfantilHib {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Column(name = "id")
     private int id;

     @Column(name = "numeroSocio")
     private int numeroSocio;

     @Column(name = "nombre")
     private String nombre;

     @Column(name = "numeroSocioTutorLegal")
     private int numeroSocioTutorLegal;

     // Constructor por defecto
    public SocioInfantilHib() {
    }

     // Constructor de mapeo
     public SocioInfantilHib(int numeroSocio, String nombre, int numeroSocioTutorLegal) {
        this.numeroSocio = numeroSocio;
        this.nombre = nombre;
        this.numeroSocioTutorLegal = numeroSocioTutorLegal;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumeroSocio() {
        return numeroSocio;
    }

    public void setNumeroSocio(int numeroSocio) {
        this.numeroSocio = numeroSocio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumeroSocioTutorLegal() {
        return numeroSocioTutorLegal;
    }

    public void setNumeroSocioTutorLegal(int numeroSocioTutorLegal) {
        this.numeroSocioTutorLegal = numeroSocioTutorLegal;
    }
}