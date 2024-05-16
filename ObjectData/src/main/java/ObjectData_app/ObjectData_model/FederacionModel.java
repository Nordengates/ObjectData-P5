package ObjectData_app.ObjectData_model;

import ObjectData_app.ObjectData_model.ObjectData_Hibernate.FederacionHib;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class FederacionModel {
    /////////////////// Propiedades de Hibernete
    private static SessionFactory sessionFactory;
    private static Session session;

    /////////////// Propiedades de la clase
    String codigo;
    String nombre;

    // Constructor
    public FederacionModel(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    /////////////////// Metodo para crear la session de hibernate
    private static void crearSessionHib() {
        sessionFactory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(FederacionHib.class)
                .buildSessionFactory();
        session = sessionFactory.openSession();
    }

    /////////////////// Método para obtener todas las federaciones de la base de
    /////////////////// datos
    public static String[] obtenerListadoFederacion() {
        // Crear sesion hibernete e inicializacion
        crearSessionHib();
        // Propiedades del metodo
        List<FederacionHib> listaFederaciones;
        try {
            // Iniciar transacción
            session.beginTransaction();
            // Consulta para obtener las federaciones y guardarlas en la lista
            listaFederaciones = session.createQuery("FROM FederacionHib", FederacionHib.class).list();
        } catch (Exception e) {// Captura posibles excepciones
            throw e;
        } finally {
            session.close(); // Cerrar la sesión
            sessionFactory.close(); // Cerrar fábrica de sesiones de Hibernate
        }
        String listado = "";
        int contador = 0;
        for (FederacionHib federacion : listaFederaciones) {
            contador++;
            listado += "\n    - " + contador + ". " + federacion.toString();
        }
        if (contador == 0) {
            listado = "- Sin datos.";
        }
        return new String[] { listado, String.valueOf(contador) };
    }

    public static FederacionModel obtenerFederacion(int seleccion) {
        // Crear sesión de Hibernate e inicializar
        crearSessionHib();
        // Propiedades del metodo
        List<FederacionHib> listaFederaciones;
        try {
            // Iniciar transacción
            session.beginTransaction();
            // Consulta para obtener las federaciones y guardarlas en la lista
            listaFederaciones = session.createQuery("FROM FederacionHib", FederacionHib.class).list();
        } catch (Exception e) {// Captura posibles excepciones
            throw e;
        } finally {
            session.close(); // Cerrar la sesión
            sessionFactory.close(); // Cerrar fábrica de sesiones de Hibernate
        }
        int contador = 0;
        for (FederacionHib federacion : listaFederaciones) {
            contador++;
            if(contador == seleccion){
                return new FederacionModel(federacion.getCodigo(), federacion.getNombre());
            }
        }
        return null;
    }

    // Getters
    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    // Setters
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
