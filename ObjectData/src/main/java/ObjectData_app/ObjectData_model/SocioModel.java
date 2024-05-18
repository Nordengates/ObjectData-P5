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

    ////////////// Método para devover un socio existe mediante el numeroSocio
    public static SocioModel obtenerSocioPorNumeroSocio(int numeroSocio) {
        // Comprobar en la lista de socios estándar
        SocioModel socio = SocioEstandarModel.getSocioPorNumeroSocio(numeroSocio);
        if (socio != null) {
            return socio;
        }
        // Comprobar en la lista de socios federados
        socio = SocioFederadoModel.getSocioPorNumeroSocio(numeroSocio);
        if (socio != null) {
            return socio;
        }
        // Comprobar en la lista de socios infantiles
        socio = SocioInfantilModel.getSocioPorNumeroSocio(numeroSocio);
        if (socio != null) {
            return socio;
        }
        // Si no se encuentra en ninguna lista, devolver null
        return null;
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