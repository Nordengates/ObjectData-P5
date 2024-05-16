package ObjectData_app.ObjectData_model.ObjectData_Hibernate;

import jakarta.persistence.*;

@Entity
@Table(name = "socioFederado")
public class SocioFederadoHib {
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
    @Column(name = "codigoFederacion")
    private String federacion;
    // Constructor por defecto
    public SocioFederadoHib() {
    }

    // Contructor de mapeo
    public SocioFederadoHib(int numeroSocio, String nombre, String NIF, String federacion) {
        this.numeroSocio = numeroSocio;
        this.nombre = nombre;
        this.NIF = NIF;
        this.federacion = federacion;
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

    public String getFederacion() {
        return federacion;
    }

    public void setFederacion(String federacion) {
        this.federacion = federacion;
    }

    

}
