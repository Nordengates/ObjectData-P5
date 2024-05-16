package ObjectData_app.ObjectData_model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.util.List;
import java.text.SimpleDateFormat;
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
    public ExcursionModel(int numeroExcursion, String descripcion, Date fecha, int numeroDias,
            double precioInscripcion) {
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

    public static String mostrarExcursiones(Date fechaInicio, Date fechaFin) {
        crearSessionHib();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        List<ExcursionModelHib> excursions = null;
        StringBuilder listado = new StringBuilder();
        try {
            session.beginTransaction();
            excursions = session
                    .createQuery("FROM ExcursionModelHib WHERE fecha BETWEEN :start AND :end", ExcursionModelHib.class)
                    .setParameter("start", fechaInicio)
                    .setParameter("end", fechaFin)
                    .list();
            session.getTransaction().commit();
            int contador = 0;
            for (ExcursionModelHib excursion : excursions) {
                contador++;
                listado.append("\n- ").append(contador).append(". Código: ").append(excursion.getNumeroExcursion())
                        .append(" | Descripción: ").append(excursion.getDescripcion())
                        .append(" | Fecha y hora: ").append(dateFormat.format(excursion.getFecha()))
                        .append(" | Número de días: ").append(excursion.getNumeroDias())
                        .append(" | Precio de la inscripción: ").append(excursion.getPrecioInscripcion());
            }
            if (contador == 0) {
                listado.append("\n  - Sin datos.");
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
        return listado.toString();
    }

    // Metodo paras listar los socios estandar.
    public static String[] obtenerListadoExcursiones() {
        // Creamos una sesión de Hibernate y la iniciamos
        crearSessionHib();
        List<ExcursionModelHib> excursionList = null;
        try {
            // Iniciamos una transacción en la sesión
            session.beginTransaction();
            excursionList = session.createQuery("FROM ExcursionModelHib", ExcursionModelHib.class).list();

        } catch (Exception e) {
            // Devolvemos el error aguas arriba en las clases
            throw e;
        } finally {
            // Finalmente cerramos la sesión y el objeto de fábrica de sesiones
            session.close();
            // Cerramos la fábrica de sesiones de Hibernate para liberar recursos
            sessionFactory.close();
        }
        // Comprobar en la lista de socios estándar
        StringBuilder listado = new StringBuilder();
        int contador = 0;
        for (ExcursionModelHib excursion : excursionList) {
            contador++;
            listado.append("\n- ").append(contador).append(". Descripción: ").append(excursion.getDescripcion())
                    .append(" | Precio: ").append(excursion.getPrecioInscripcion());
        }
        if (contador == 0) {
            listado.append("\n  - Sin datos de socios Estandar.");
        }
        return new String[] { listado.toString(), String.valueOf(contador) };
    }

    public static ExcursionModel obtenerExcursionDesdeLista(int seleccion) {
        // Creamos una sesión de Hibernate y la iniciamos
        crearSessionHib();
        List<ExcursionModelHib> excursionList = null;
        try {
            // Iniciamos una transacción en la sesión
            session.beginTransaction();
            excursionList = session.createQuery("FROM ExcursionModelHib", ExcursionModelHib.class).list();
        } catch (Exception e) {
            // Devolvemos el error aguas arriba en las clases
            throw e;
        } finally {
            // Finalmente cerramos la sesión y el objeto de fábrica de sesiones
            session.close();
            // Cerramos la fábrica de sesiones de Hibernate para liberar recursos
            sessionFactory.close();
        }
        // Comprobar en la lista de socios estándar
        int contador = 0;
        // Logica
        for (ExcursionModelHib excursion : excursionList) {
            contador++;
            if (contador == seleccion) {
                return new ExcursionModel(
                        excursion.getNumeroExcursion(),
                        excursion.getDescripcion(),
                        excursion.getFecha(),
                        excursion.getNumeroDias(),
                        excursion.getPrecioInscripcion());
            }
        }
        return null;
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
