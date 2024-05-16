package ObjectData_app.ObjectData_model.ObjectData_Hibernate;

import jakarta.persistence.*;

//Establecemos la tabla
@Entity
@Table(name = "socioEstandar")
public class socioEstandarHib {
    // Mapeo de columnas de la tabla
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "numeroSocio")
    private int numeroSocio;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "NIF")
    private String NIF;
    @Column(name = "seguro")
    private String seguro;

    // Constructor por defecto
    public socioEstandarHib() {
    }

    // Contructor de mapeo
    public socioEstandarHib(int numeroSocio, String nombre, String NIF, String seguro) {
        this.numeroSocio = numeroSocio;
        this.nombre = nombre;
        this.NIF = NIF;
        this.seguro = seguro;
    }

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

    public String getNIF() {
        return NIF;
    }

    public void setNIF(String nIF) {
        NIF = nIF;
    }

    public String getSeguro() {
        return seguro;
    }

    public void setSeguro(String seguro) {
        this.seguro = seguro;
    }
} 