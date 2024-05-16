package ObjectData_app.ObjectData_model;

public abstract class SocioModel {
    private int numeroSocio;
    private String nombre;

    // Constructor
    public SocioModel(int numeroSocio, String nombre) {
        this.numeroSocio = numeroSocio;
        this.nombre = nombre;
    }

    // Getters
    public int getNumeroSocio() {
        return numeroSocio;
    }

    public String getNombre() {
        return nombre;
    }

    // Setters
    public void setNumeroSocio(int numeroSocio) {
        this.numeroSocio = numeroSocio;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Método toString
    @Override
    public String toString() {
        return "Socio{" +
                "numeroSocio=" + numeroSocio +
                ", nombre='" + nombre + '\'' +
                '}';
    }

    ////////////// Método para comprobar si un socio existe mediante el numeroSocio
    public static boolean comprobarSocioPorNumeroSocio(int numeroSocio) {
        // Comprobar en la lista de socios estándar
        if (SocioEstandarModel.getSocioPorNumeroSocio(numeroSocio) != null) {
            return true;
        }
        // Comprobar en la lista de socios federados
        if (SocioFederadoModel.getSocioPorNumeroSocio(numeroSocio) != null) {
            return true;
        }
        // Comprobar en la lista de socios infantiles
        if (SocioInfantilModel.getSocioPorNumeroSocio(numeroSocio) != null) {
            return true;
        }
        // Si no se encuentra en ninguna lista, devolver false
        return false;
    }

    ////////////////// Metodo para listar todos los socios
    public static String[] listarSocios() {
        StringBuilder listado = new StringBuilder();
        int contador = 0;
        String[] datos = null;
        // Se obtienen los datos desde la tabla socio estandar.
        datos = SocioEstandarModel.listarSocios(contador);
        listado.append(datos[0]);
        contador = Integer.parseInt(datos[1]);
        // Se obtienen los datos desde la tabla socio federados.
        datos = SocioFederadoModel.listarSocios(contador);
        listado.append(datos[0]);
        contador = Integer.parseInt(datos[1]);
        // Se obtienen los datos desde la tabla socio estandar.
        datos = SocioInfantilModel.listarSocios(contador);
        listado.append(datos[0]);
        contador = Integer.parseInt(datos[1]);
        // Se devuelven los datos obtenidos
        return new String[] { listado.toString(), String.valueOf(contador) };
    }

    /////////////////// Metodo para obtener el tipo de socio por numero de socio.
    public static String obtenerTipoSocioPorNumeroSocio(int numeroSocio) {
        // Comprobar en la lista de socios estándar
        if (SocioEstandarModel.getSocioPorNumeroSocio(numeroSocio) != null) {
            return "Estandar";
        }
        // Comprobar en la lista de socios federados
        if (SocioFederadoModel.getSocioPorNumeroSocio(numeroSocio) != null) {
            return "Federado";
        }
        // Comprobar en la lista de socios infantiles
        if (SocioInfantilModel.getSocioPorNumeroSocio(numeroSocio) != null) {
            return "Infantil";
        }
        // Si no se encuentra en ninguna lista, devolver false
        return null;
    }
}