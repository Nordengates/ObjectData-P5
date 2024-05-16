package ObjectData_app.ObjectData_model.ObjectData_Hibernate;
import jakarta.persistence.*;

@Entity
@Table(name = "federacion") 
public class FederacionHib {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "nombre")
    private String nombre;

   //Constructor vacio 
    public FederacionHib() {
    }

    // Constructor con parámetros
    public FederacionHib(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

        @Override
    public String toString() {
        return nombre;
    }

}
