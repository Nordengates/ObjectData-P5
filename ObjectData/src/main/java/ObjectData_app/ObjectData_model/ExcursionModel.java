package ObjectData_app.ObjectData_model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import ObjectData_app.ObjectData_model.ObjectData_Hibernate.ExcursionModelHib;

public class ExcursionModel {
    static SessionFactory sessionFactory;
    static Session session;

    // Propiedades de clase
    private int numeroExcursion;
    private String descripcion;
    private Date fecha;
    private int numeroDias;
    private double precioInscripcion;

    // Constructor
    public ExcursionModel(int numeroExcursion, String descripcion, Date fecha, int numeroDias, double precioInscripcion) {
        this.numeroExcursion = numeroExcursion;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.numeroDias = numeroDias;
        this.precioInscripcion = precioInscripcion;
    }

    private static void crearSessionHib() {
        sessionFactory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(ExcursionModelHib.class)
                .buildSessionFactory();
        session = sessionFactory.openSession();
    }

    // Método para crear una excursion
    public String crearExcursionModel(ExcursionModel excursion) {
        crearSessionHib();
        try {
            session.beginTransaction();
            ExcursionModelHib excursionHib = new ExcursionModelHib(
                    excursion.getNumeroExcursion(),
                    excursion.getDescripcion(),
                    excursion.getFecha(),
                    excursion.getNumeroDias(),
                    excursion.getPrecioInscripcion());
            session.persist(excursionHib);
            // Confirmamos la transacción
            session.getTransaction().commit();
        } catch (Exception e) {
            // En caso de error, realizamos un rollback de la transacción
            session.getTransaction().rollback();
            // Devolvemos el error aguas arriba en las clases
            throw e;
        } finally {
            // Finalmente cerramos la sesión y el objeto de fábrica de sesiones
            session.close();
            // Cerramos la fábrica de sesiones de Hibernate para liberar recursos
            sessionFactory.close();
        }
        return "Se creo la excursión correctamente.";
    }

    public static ExcursionModel obtenerExcursionPorNumeroExcursion(int numeroExcursion) {
        crearSessionHib();
        ExcursionModelHib excursion = null;
        try {
            session.beginTransaction();
            excursion = session
                    .createQuery("FROM ExcursionModelHib WHERE numeroExcursion = :numeroExcursion",
                            ExcursionModelHib.class)
                    .setParameter("numeroExcursion", numeroExcursion)
                    .uniqueResult();
        } catch (Exception e) {
            // Devolvemos el error aguas arriba en las clases
            throw e;
        } finally {
            // Finalmente cerramos la sesión y el objeto de fábrica de sesiones
            session.close();
            // Cerramos la fábrica de sesiones de Hibernate para liberar recursos
            sessionFactory.close();
        }
        return new ExcursionModel(excursion.getNumeroExcursion(), excursion.getDescripcion(), excursion.getFecha(),
                excursion.getNumeroDias(), excursion.getPrecioInscripcion());
    }

    public static double obtenerPrecioExcursion(int numeroExcursion) {
        crearSessionHib();
        ExcursionModelHib excursion = null;
        try {
            session.beginTransaction();
            excursion = session
                    .createQuery("FROM ExcursionModelHib WHERE numeroExcursion = :numeroExcursion",
                            ExcursionModelHib.class)
                    .setParameter("numeroExcursion", numeroExcursion)
                    .uniqueResult();
        } catch (Exception e) {
            // Devolvemos el error aguas arriba en las clases
            throw e;
        } finally {
            // Finalmente cerramos la sesión y el objeto de fábrica de sesiones
            session.close();
            // Cerramos la fábrica de sesiones de Hibernate para liberar recursos
            sessionFactory.close();
        }
        // Uso de operador ternario: condición ? valorSiVerdadero : valorSiFalso;
        return excursion != null ? excursion.getPrecioInscripcion() : 0;
    }

    public static ArrayList<ExcursionModel> objetoListaExcursion(Date fechaInicio, Date fechaFin) {
        crearSessionHib();
        List<ExcursionModelHib> excursionesHib = null;
        ArrayList<ExcursionModel> excursiones = new ArrayList<>();
        try {
            session.beginTransaction();
            excursionesHib = session
                    .createQuery("FROM ExcursionModelHib WHERE fecha BETWEEN :start AND :end", ExcursionModelHib.class)
                    .setParameter("start", fechaInicio)
                    .setParameter("end", fechaFin)
                    .list();
            session.getTransaction().commit();
            for (ExcursionModelHib excursion : excursionesHib) {
                excursiones.add(new ExcursionModel(
                    excursion.getNumeroExcursion(), 
                    excursion.getDescripcion(), 
                    excursion.getFecha(), 
                    excursion.getNumeroDias(), 
                    excursion.getPrecioInscripcion()));
            }
        } catch (Exception e) {
            // Devolvemos el error aguas arriba en las clases
            throw e;
        } finally {
            // Finalmente cerramos la sesión y el objeto de fábrica de sesiones
            session.close();
            // Cerramos la fábrica de sesiones de Hibernate para liberar recursos
            sessionFactory.close();
        }
        return excursiones;
    }

    // Metodo paras listar los socios estandar.
    public static ArrayList<ExcursionModel> obtenerListadoExcursiones() {
        // Creamos una sesión de Hibernate y la iniciamos
        crearSessionHib();
        List<ExcursionModelHib> excursionesHib = null;
        ArrayList<ExcursionModel> excursiones = new ArrayList<>();
        try {
            // Iniciamos una transacción en la sesión
            session.beginTransaction();
            excursionesHib = session.createQuery("FROM ExcursionModelHib", ExcursionModelHib.class).list();
        } catch (Exception e) {
            // Devolvemos el error aguas arriba en las clases
            throw e;
        } finally {
            // Finalmente cerramos la sesión y el objeto de fábrica de sesiones
            session.close();
            // Cerramos la fábrica de sesiones de Hibernate para liberar recursos
            sessionFactory.close();
        }
        for (ExcursionModelHib excursion : excursionesHib) {
            excursiones.add(new ExcursionModel(
                excursion.getNumeroExcursion(), 
                excursion.getDescripcion(), 
                excursion.getFecha(), 
                excursion.getNumeroDias(), 
                excursion.getPrecioInscripcion()));
        }
        return excursiones;
    }

    // Getters y Setters
    public int getNumeroExcursion() {
        return numeroExcursion;
    }

    public void setNumeroExcursion(int numeroExcursion) {
        this.numeroExcursion = numeroExcursion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getNumeroDias() {
        return numeroDias;
    }

    public void setNumeroDias(int numeroDias) {
        this.numeroDias = numeroDias;
    }

    public double getPrecioInscripcion() {
        return precioInscripcion;
    }

    public void setPrecioInscripcion(double precioInscripcion) {
        this.precioInscripcion = precioInscripcion;
    }
}
