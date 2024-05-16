package ObjectData_app.ObjectData_model;

import ObjectData_app.ObjectData_model.ObjectData_Hibernate.socioEstandarHib;


import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SocioEstandarModel extends SocioModel {
    /////////////////// Propiedades de Hibernate
    static SessionFactory sessionFactory;
    static Session session;
    /////////////////// Propiedades del constructor
    String NIF;
    SeguroModel seguro;

    /////////////////// Constructor
    public SocioEstandarModel(int numeroSocio, String nombre, String NIF, SeguroModel seguro) {
        super(numeroSocio, nombre);
        this.NIF = NIF;
        this.seguro = seguro;
    }

    /////////////////// Metodo para crear la session de hibernate
    private static void crearSessionHib() {
        sessionFactory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(socioEstandarHib.class).buildSessionFactory();
        session = sessionFactory.openSession();
    }

    /////////////////// Método para agregar un socio estandar a la lista en Datos.
    public static void crearSocioEstandar(SocioEstandarModel socioEstandar) {
        // Creamos una sesión de Hibernate y la iniciamos
        crearSessionHib();
        try {
            // Iniciamos una transacción en la sesión
            session.beginTransaction();
            socioEstandarHib socio = new socioEstandarHib(
                    socioEstandar.getNumeroSocio(), // Obtenemos el numero de socio desde el objeto socioEstandar
                                                    // //Columna numeroSocio
                    socioEstandar.getNombre(), // Obtenemos el nombre de socio desde el objeto socioEstandar //Columna
                                               // nombre
                    socioEstandar.getNIF(), // Obtenemos el NIF de socio desde el objeto socioEstandar //Columna NIF
                    socioEstandar.getSeguro().getTipo().toString() // Obtenemos el nombre del tipo de seguro del seguro
                                                                   // del objeto de socioEstandar //Columna seguro
            );
            // Lanzamos el comando para insertar el objeto en la base de datos
            session.persist(socio);
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
    }

    /////////////////// Metodo para buscar por numero de socio y devolver el objeto.
    public static SocioEstandarModel getSocioPorNumeroSocio(int numeroSocio) {
        // Creamos una sesión de Hibernate y la iniciamos
        crearSessionHib();
        socioEstandarHib socio = null;
        try {
            // Iniciamos una transacción en la sesión
            session.beginTransaction();
            // Obtenemos el socio por numero de socio
            socio = session
                    .createQuery("FROM socioEstandarHib WHERE numeroSocio = :numeroSocio", socioEstandarHib.class)
                    .setParameter("numeroSocio", numeroSocio)
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
        // Si todo a ido bien, creamos el objeto de tipo socioEstandarModel y lo
        // devolvemos
        if (socio != null) {
            return new SocioEstandarModel(socio.getNumeroSocio(), socio.getNombre(), socio.getNIF(),
                    new SeguroModel(SeguroModel.TipoSeguro.valueOf(socio.getSeguro())));
        } else {
            return null;
        }
    }

    /////////////////// Metodo para actualizar el seguro del socio:
    public void actualizarSeguroSocioEstandar(SeguroModel seguro, SocioEstandarModel socio) {
        // Creamos una sesión de Hibernate y la iniciamos
        crearSessionHib();
        try {
            // Iniciamos una transacción en la sesión
            session.beginTransaction();
            // Creamos una consulta de mutación para actualizar el seguro del socio estándar
            // en la base de datos,
            // pasando el tipo de seguro y el número de socio como parámetros
            session.createMutationQuery("UPDATE SocioEstandarHib SET seguro = :seguro WHERE numeroSocio = :numeroSocio")
                    .setParameter("seguro", seguro.getTipo().toString())
                    .setParameter("numeroSocio", socio.getNumeroSocio())
                    .executeUpdate();
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
    }

    /////////////////// Metodo para obtener el precio del seguro.
    public static double obtenerPrecioSeguroPorNumeroSocio(int numeroSocio) {
        // Creamos una sesión de Hibernate y la iniciamos
        crearSessionHib();
        socioEstandarHib socio = null;
        try {
            // Iniciamos una transacción en la sesión
            session.beginTransaction();
            socio = session
                    .createQuery("FROM socioEstandarHib WHERE numeroSocio = :numeroSocio", socioEstandarHib.class)
                    .setParameter("numeroSocio", numeroSocio)
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
        return new SeguroModel(SeguroModel.TipoSeguro.valueOf(socio.getSeguro())).getPrecio();
    }

    /////////////////// Método para eliminar socio estandar de la base de datos
    public static void eliminarSocioModel(int numeroSocio) {
        // Creamos una sesión de Hibernate y la iniciamos
        crearSessionHib();
        try {
            // Iniciamos una transacción en la sesión
            session.beginTransaction();
            // Creamos una consulta DELETE utilizando createMutationQuery()
            session.createMutationQuery("DELETE FROM SocioEstandarHib WHERE numeroSocio = :numeroSocio")
                    .setParameter("numeroSocio", numeroSocio)
                    .executeUpdate();
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
    }

    // Metodo paras listar los socios estandar.
    public static String[] listarSocios(int valorInicialContador) {
        // Creamos una sesión de Hibernate y la iniciamos
        crearSessionHib();
        List<socioEstandarHib> sociosEstandar = null;
        try {
            // Iniciamos una transacción en la sesión
            session.beginTransaction();
            sociosEstandar = session.createQuery("FROM socioEstandarHib", socioEstandarHib.class).list();
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
        int contador = valorInicialContador;
        for (socioEstandarHib socio : sociosEstandar) {
            contador++;
            listado.append("\n- ").append(contador).append(". Numero Socio: ").append(socio.getNumeroSocio())
                    .append(" | Nombre: ").append(socio.getNombre()).append(" | NIF: ").append(socio.getNIF())
                    .append(" | Seguro: ").append(socio.getSeguro());
        }
        if (contador == 0) {
            listado.append("\n  - Sin datos de socios Estandar.");
        }
        return new String[] { listado.toString(), String.valueOf(contador) };
    }

    /////////////////// Getters y Setters
    public String getNIF() {
        return NIF;
    }

    public void setNIF(String NIF) {
        this.NIF = NIF;
    }

    public SeguroModel getSeguro() {
        return seguro;
    }

    public void setSeguro(SeguroModel seguro) {
        this.seguro = seguro;
    }

    @Override
    public String toString() {
        return "SocioEstandarModel{" +
                "NIF='" + NIF + '\'' +
                ", seguro=" + seguro +
                '}';
    }

}
