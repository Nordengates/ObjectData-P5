package ObjectData_app.ObjectData_controller;

import ObjectData_app.ObjectData_model.SocioEstandarModel;
import ObjectData_app.ObjectData_model.SocioFederadoModel;
import ObjectData_app.ObjectData_model.SocioInfantilModel;
import ObjectData_app.ObjectData_model.SocioModel;

import java.util.Random;

import ObjectData_app.ObjectData_model.FederacionModel;
import ObjectData_app.ObjectData_model.InscripcionModel;
import ObjectData_app.ObjectData_model.SeguroModel;
import ObjectData_app.ObjectData_model.SeguroModel.TipoSeguro;
import ObjectData_app.ObjectData_view.MensajeControllerView;
//Se añaden las vistas necesarias.
import ObjectData_app.ObjectData_controller.SocioController;

public class SocioController {
    // Se inicializan las vistas necesasias.
    static MensajeControllerView RespView = new MensajeControllerView();

    // Método para generar un número de socio aleatorio
    public static int generarID() {
        Random rand = new Random();
        int id = 0;
        for (int i = 0; i < 8; i++) {
            id = id * 10 + rand.nextInt(9) + 1;
        }
        if (id < 0) {
            return id * -1;
        }
        return id;
    }

    // Metodo para elegir el tipo de socio a crear
    public static void crearNuevoSocio() {
        // Excepcion para la conversion del numero de tipo String a Int
        int opcion = 0;
        boolean opcionValida = false;
        do {
            String retorno = SociView.crearNuevoSocioView();
            if (retorno.matches("\\d+")) {
                opcion = Integer.parseInt(retorno);
            } else {
                RespView.excepcionesControllerView("Debe insertar un valor numerico valido.");
                continue;
            }

            if (opcion >= 1 && opcion <= 4) {
                opcionValida = true;
            } else {
                RespView.excepcionesControllerView("Debe seleccionar una opción valida.");
            }

        } while (!opcionValida);
        // Se carga el formulario de registro de un nuevo socio.
        switch (opcion) {
            case 1:
                crearSocioEstandar();
                break;
            case 2:
                crearSocioFederado();
                break;
            case 3:
                crearSocioInfantil();
                break;
            case 4:
            default:
        }
    }

    // Metodo para añadir un socio estandar
    public static void crearSocioEstandar() {
        // Atributos
        String nombre; // El primer parametro del array sera el nombre
        String NIF; // El segundo parametro del array es el DNI
        int numeroSocio; // Para almacenar el numero de socio.
        boolean todoOk = false;
        // Imprimimos el titulo de la función.
        RespView.tituloDeLaFuncion("-- FORMULARIO PARA CREAR UN SOCIO ESTANDAR --");
        // Creamos el bucle para el metodo.
        do {
            // Pedimos el nombre del socio.
            nombre = SociView.obtenerNombreSocio();
            // Si nombre esta vacio salimos.
            if (nombre.isEmpty()) {
                RespView.respuestaControllerView("Operación cancelada.");
            }
            // Pedimos el NIF
            NIF = SociView.obtenerDNISocio();
            // Si nombre esta vacio salimos.
            if (NIF.isEmpty()) {
                RespView.respuestaControllerView("Operación cancelada.");
            }
            // Obtenemos el numero de socio.
            numeroSocio = Integer.parseInt("5" + generarID()); // Número de socio
            RespView.respuestaControllerView("# Numero de socio generado: " + numeroSocio); // Mandamos el numero de
                                                                                            // socio a la pantalla
            // Pido el seguro
            SeguroModel seguroModel = seguroSocio();
            // Creamos un objeto de tipo llamado socioModel con los datos correctos
            SocioEstandarModel socioModel = new SocioEstandarModel(numeroSocio, nombre, NIF, seguroModel);
            // Enviamos la información al modelo para que añada el socio a la BBDD
            // Al intentar crear datos en un medio exteno, en este caso la BBDD, debemos
            // usar try para comprobar posibles excepciones.
            try {
                SocioEstandarModel.crearSocioEstandar(socioModel);
                RespView.respuestaControllerView("Se ha creado el socio estandar correctamente.");
                todoOk = true;
            } catch (Exception e) {
                RespView.excepcionesControllerView(e.getMessage());
            }
        } while (!todoOk);
    }

    public static void modificarSeguroSocioEstandar() {
        // Atributos
        String retorno;
        int numeroSocio = 0;
        SocioEstandarModel socio = null;
        boolean todoOk = false;
        // Imprimitos el titulo de la función.
        RespView.tituloDeLaFuncion("-- FORMULARIO PARA MODIFICAR EL TIPO DE SEGURO --");
        // Bucle de logica para comprobar datos.
        do {
            // Pedimos el nombre del socio.
            retorno = SociView.obtenerNumeroSocio();
            // Si retorno esta vacio salimos.
            if (retorno.isEmpty()) {
                RespView.respuestaControllerView("Operación cancelada.");
            } else if (retorno.matches("\\d+")) { // Verifica si el retorno es un número entero
                numeroSocio = Integer.parseInt(retorno);
                todoOk = true;
            } else {
                // Si no es un número entero, muestra un mensaje de error
                RespView.excepcionesControllerView("El número de socio debe ser un numero.");
                continue;
            }
            // Obtenemos el objeto socio estandar (si existe)
            try {
                socio = SocioEstandarModel.getSocioPorNumeroSocio(numeroSocio);
            } catch (Exception e) {
                RespView.excepcionesControllerView(e.getMessage());
            }
            if (socio != null) {
                SeguroModel seguroModel = seguroSocio();
                try {
                    socio.actualizarSeguroSocioEstandar(seguroModel, socio);
                    RespView.respuestaControllerView("El socio se ha actualizado correctamente.");
                    todoOk = true;
                } catch (Exception e) {
                    RespView.respuestaControllerView(e.getMessage());
                }
            } else {
                RespView.excepcionesControllerView("No se ha podido encontrar el socio.");
                continue;
            }
        } while (!todoOk);
    }

    public static void crearSocioFederado() {
        // Atributos
        String nombre; // El primer parametro del array sera el nombre
        String NIF; // El segundo parametro del array es el DNI
        int numeroSocio; // Para almacenar el numero de socio.
        boolean todoOk = false;
        String[] listaFederaciones = null;
        FederacionModel federacion = null;
        // Imprimitos el titulo de la función.
        RespView.tituloDeLaFuncion("-- FORMULARIO PARA CREAR UN SOCIO FEDERADO --");
        // Creamos el bucle para el metodo.
        do {
            // Pedimos el nombre del socio.
            nombre = SociView.obtenerNombreSocio();
            // Si nombre esta vacio salimos.
            if (nombre.isEmpty()) {
                RespView.respuestaControllerView("Operación cancelada.");
            }
            // Pedimos el NIF
            NIF = SociView.obtenerDNISocio();
            // Si NIF esta vacio salimos.
            if (NIF.isEmpty()) {
                RespView.respuestaControllerView("Operación cancelada.");
            }
            // Pido el listado de federaciones y el numero de federaciones disponibles;
            try {
                listaFederaciones = FederacionModel.obtenerListadoFederacion();
            } catch (Exception e) {
                RespView.excepcionesControllerView(e.getMessage());
            }
            int opcionesDiponibles = Integer.parseInt(listaFederaciones[1]);
            // Se genera el control de excepcion para opcion seleccionada no valida.
            int seleccion = 0;
            boolean opcionOk = false;
            do {
                String opcion = SociView.selectorFederacionesView(listaFederaciones[0]);
                // Si la opcion esta vacia salimos.
                if (opcion.isEmpty()) {
                    RespView.respuestaControllerView("Operación cancelada.");
                    // No se agrega al socio, así que simplemente salimos del bucle.
                    break;
                } else if (opcion.matches("\\d+")) { // Verifica si la opción es un número entero
                    seleccion = Integer.parseInt(opcion);
                } else {
                    // Si no es un número entero, muestra un mensaje de error
                    RespView.excepcionesControllerView("Opcion no valida, debe introducir un valor numerico.");
                    continue;
                }
                if (seleccion <= 0 || seleccion >= opcionesDiponibles) {
                    RespView.excepcionesControllerView("Opcion no valida, seleccione una opción disponible.");
                    continue;
                } else {
                    opcionOk = true;
                    continue;
                }
            } while (!opcionOk);
            // Método para generar un número de socio aleatorio
            numeroSocio = Integer.parseInt("6" + generarID()); // Número de socio
            RespView.respuestaControllerView("# Numero de socio generado: " + numeroSocio);
            // Con este metodo del modelo obtengo el objeto seleccionado por el usuario
            try {
                federacion = FederacionModel.obtenerFederacion(seleccion);

            } catch (Exception e) {
                RespView.excepcionesControllerView(e.getMessage());
            }
            String codigoFederacion = federacion.getCodigo();
            // Creamos el objeto con los datos recolectados.
            SocioFederadoModel socio = new SocioFederadoModel(numeroSocio, nombre, NIF, codigoFederacion);
            // Enviamos la información al modelo para que añada el socio a la
            try {
                socio.crearSocioFederado(socio);
                RespView.respuestaControllerView("Se ha creado el socio federado correctamente.");
                todoOk = true;
            } catch (Exception e) {
                RespView.excepcionesControllerView(e.getMessage());
            }
        } while (!todoOk);
    }

    public static void crearSocioInfantil() {
        // Atributos
        String retorno;
        int numeroSocioTutorLegal = 0; // Varialbe para almacenar numeroSocioTutorLegal.
        String nombre; // El primer parametro del array sera el nombre
        int numeroSocio; // Para almacenar el numero de socio.
        boolean todoOk = false;
        // Imprimimos el titulo de la función.
        RespView.tituloDeLaFuncion("-- FORMULARIO PARA CREAR UN SOCIO INFANTIL --");
        // Bucle para la logica de comprobacion de datos.
        do {
            // Pedimos el nombre del socio.
            nombre = SociView.obtenerNombreSocio();
            // Si nombre esta vacio salimos.
            if (nombre.isEmpty()) {
                RespView.respuestaControllerView("Operación cancelada.");
            }
            todoOk = true;
        } while (!todoOk);
        todoOk = false;
        do {
            // Pedimos el codigo del tutor legal
            retorno = SociView.numeroSocioParentalView();
            // Creamos la excepción para verificar el tipo de dato introducido.
            if (retorno.isEmpty()) {
                RespView.respuestaControllerView("Operación cancelada.");
            } else if (retorno.matches("\\d+")) { // Verifica si la opción es un número entero
                numeroSocioTutorLegal = Integer.parseInt(retorno);
                todoOk = true;
            } else {
                // Si no es un número entero, muestra un mensaje de error
                RespView.excepcionesControllerView("Opcion no valida, debe introducir un valor numerico.");
                continue;
            }
        } while (!todoOk);
        // Método para generar un número de socio aleatorio
        numeroSocio = Integer.parseInt("7" + generarID()); // Número de socio
        RespView.respuestaControllerView("# Numero de socio generado: " + numeroSocio);
        // Creamos el objeto con los datos recolectados.
        SocioInfantilModel socio = new SocioInfantilModel(numeroSocio, nombre, numeroSocioTutorLegal);
        // Enviamos la información al modelo para que añada el socio a la BBDD
        try {
            socio.crearSocioInfantil(socio);
            RespView.respuestaControllerView("Se ha creado el socio infantil correctamente.");
        } catch (Exception e) {
            RespView.excepcionesControllerView(e.getMessage());
        }
    }

    public static void eliminarSocio() {
        // Atributos
        String numeroSocioReturn = null;
        String tipoSocio = null;
        int numeroSocio = 0;
        boolean inscritoEnExcursion = false;
        Boolean todoOk = false;
        // Imprimo en pantalla el titulo del metodo
        RespView.tituloDeLaFuncion("-- ELIMINAR UN SOCIO --");
        // Comienza el bucle para la logica del metodo
        do {
            numeroSocioReturn = SociView.obtenerNumeroSocio();
            // Verificar si la cadena numeroSocio está vacía
            if (numeroSocioReturn.isEmpty()) {
                RespView.respuestaControllerView("Operación cancelada.");
            } else if (numeroSocioReturn.matches("\\d+")) { // Comprueba que el valor introducido es un numero entero.
                numeroSocio = Integer.parseInt(numeroSocioReturn);
                todoOk = true;
            } else {
                RespView.excepcionesControllerView("El número de usuario debe ser un entero.");
                continue;
            }
        } while (!todoOk);
        // Verificar si el socio está inscrito en alguna excursión
        try {
            inscritoEnExcursion = InscripcionModel.comprobarSocioInscrito(numeroSocio);
        } catch (Exception e) {
            RespView.excepcionesControllerView(e.getMessage());
        }
        if (inscritoEnExcursion) {
            // Mostrar mensaje de que el socio está inscrito en una excursión y no puede ser
            // eliminado
            RespView.respuestaControllerView("El socio con número de socio " + numeroSocio
                    + " está inscrito en una excursión y no puede ser eliminado.");
        }
        // Verificar el tipo de socio y llamar al método eliminar correspondiente
        try {
            tipoSocio = SocioModel.obtenerTipoSocioPorNumeroSocio(numeroSocio);
        } catch (Exception e) {
            RespView.excepcionesControllerView(e.getMessage());
        }
        if (tipoSocio == "Estandar") {
            try {
                SocioEstandarModel.eliminarSocioModel(numeroSocio);
                RespView.respuestaControllerView(
                        "El socio con número de socio " + numeroSocio + " ha sido eliminado correctamente.");
            } catch (Exception e) {
                RespView.excepcionesControllerView(e.getMessage());
            }
        } else if (tipoSocio.equals("Federado")) {
            try {
                SocioFederadoModel.eliminarSocioModel(numeroSocio);
                RespView.respuestaControllerView(
                        "El socio con número de socio " + numeroSocio + " ha sido eliminado correctamente.");
            } catch (Exception e) {
                RespView.excepcionesControllerView(e.getMessage());
            }
        } else if (tipoSocio.equals("Infantil")) {
            try {
                SocioInfantilModel.eliminarSocioModel(numeroSocio);
                RespView.respuestaControllerView(
                        "El socio con número de socio " + numeroSocio + " ha sido eliminado correctamente.");
            } catch (Exception e) {
                RespView.excepcionesControllerView(e.getMessage());
            }
        } else {
            RespView.excepcionesControllerView("No se ha podido identificar el tipo de socio.");
        }
    }

    public static void mostrarSocio() {
        // Excepcion para la conversion del numero de tipo String a Int y opcion
        // seleccionada valida
        int opcion = 0;
        boolean opcionValida = false;
        do {
            String retorno = SociView.listadoSociosView();
            // Verifica si el retorno es un número entero
            if (retorno.matches("\\d+")) {
                opcion = Integer.parseInt(retorno);
            } else {
                // Si no es un número entero, muestra un mensaje de error
                RespView.excepcionesControllerView("Debe insertar un valor numerico valido.");
                continue;
            }
            if (opcion >= 1 && opcion <= 5) {
                opcionValida = true;
            } else {
                RespView.excepcionesControllerView("Debe seleccionar una opción valida.");
            }
        } while (!opcionValida);
        // Se listan los socios.
        try {
            switch (opcion) {
                case 1:
                    RespView.respuestaControllerView(
                            "Listado de todos los socios: " + SocioModel.listarSocios()[0]);
                    break;
                case 2:
                    RespView.respuestaControllerView(
                            "\nListado de socios estandar: " + SocioEstandarModel.listarSocios(0)[0]);
                    break;
                case 3:
                    RespView.respuestaControllerView(
                            "\nListado de socios federados: " + SocioFederadoModel.listarSocios(0)[0]);
                    break;
                case 4:
                    RespView.respuestaControllerView(
                            "\nListado de socios infantiles: " + SocioInfantilModel.listarSocios(0)[0]);
                    break;
                case 5:
                    break;
                default:
            }
        } catch (Exception e) {
            RespView.excepcionesControllerView(e.getMessage());
        }
    }

    public static void facturaMensualSocio() {
        // Atributos
        int numeroSocio = 0;
        boolean valoresComprobados = false;
        String tipoSocio = "";
        Double precioSeguro = 0.0;
        Double facturacion = 0.0;
        final Double cuotaMensual = 10.00;
        String[] retornoArray = null;
        String respuesta = "\nFacturación del socio: ";
        // Imprimo en pantalla el titulo del metodo
        RespView.tituloDeLaFuncion("-- MUESTRA LA FACTURACIÓN DE UN SOCIO --");
        // Comprobación de datos
        do {
            // Se muestran la vista y se piden datos.
            String retorno = SociView.obtenerNumeroSocio();
            // Verificar si la cadena retorno está vacía
            if (retorno.isEmpty()) {
                RespView.respuestaControllerView("Operación cancelada.");
            } else if (retorno.matches("\\d+")) { // Verifica si el retorno es un número entero
                numeroSocio = Integer.parseInt(retorno);
            } else {
                // Si no es un número entero, muestra un mensaje de error
                RespView.excepcionesControllerView("Debe insertar un valor númerico válido.");
                continue;
            }
            // Se comprueba que el usuario no quiere salir del método y se comprueban datos
            if (numeroSocio == 0) {
                break;
            } else {
                try {
                    if (SocioModel.comprobarSocioPorNumeroSocio(numeroSocio)) {
                        try {
                            tipoSocio = SocioModel.obtenerTipoSocioPorNumeroSocio(numeroSocio);
                            valoresComprobados = true;
                        } catch (Exception e) {
                            RespView.excepcionesControllerView(e.getMessage());
                            continue;
                        }
                    } else {
                        RespView.excepcionesControllerView("No se ha podido encontrar el socio.");
                        continue;
                    }
                } catch (Exception e) {
                    RespView.excepcionesControllerView(e.getMessage());
                }
            }
        } while (!valoresComprobados);
        if (tipoSocio.equals("Estandar")) {
            // Coste de la cuota
            respuesta += "\n    - Coste de la cuota: " + cuotaMensual + " euros.";
            // Obtenemos el precio del seguro contratado desde el modelo.
            // Al intentar obtener datos de un medio exteno, en este caso la BBDD, debemos
            // usar try para comprobar posibles excepciones.
            try {
                precioSeguro = SocioEstandarModel.getSocioPorNumeroSocio(numeroSocio).getSeguro().getPrecio();
            } catch (Exception e) {
                RespView.excepcionesControllerView(e.getMessage());
            }
            // Obtener listado de excursiones y precio:
            // Al intentar obtener datos de un medio exteno, en este caso la BBDD, debemos
            // usar try para comprobar posibles excepciones.
            try {
                retornoArray = InscripcionModel.obtenerInscripcionesByNumSocio(numeroSocio);
            } catch (Exception e) {
                RespView.excepcionesControllerView(e.getMessage());
            }
            respuesta += retornoArray[0];
            // Precio del seguro.
            respuesta += "\n    - Coste del seguro: " + precioSeguro + " euros.";
            // Se genera el precio final de facturación
            facturacion = cuotaMensual + precioSeguro + Double.parseDouble(retornoArray[1]);
            // Se manda el resultado a la vista
            respuesta += "\n El socio factura " + facturacion + " euros mensuales.";
        } else if (tipoSocio.equals("Federado")) {
            // Aplicamos un despues de la cuota mensual 5%
            Double precioCuotaDescuento = cuotaMensual - (cuotaMensual * 5 / 100);
            // Obtener listado de escursiones y precio:
            // Al intentar obtener datos de un medio exteno, en este caso la BBDD, debemos
            // usar try para comprobar posibles excepciones.
            try {
                retornoArray = InscripcionModel.obtenerInscripcionesByNumSocio(numeroSocio);
            } catch (Exception e) {
                RespView.excepcionesControllerView(e.getMessage());
            }
            respuesta += retornoArray[0];
            // Se calcula el descuento de las escursiones de este socio. 10%
            Double descuentoExcursiones = Double.parseDouble(retornoArray[1])
                    - (Double.parseDouble(retornoArray[1]) * 10 / 100);
            // Se genera el precio final de facturación
            facturacion = precioCuotaDescuento + descuentoExcursiones;
            // Se manda el resultado a la vista
            respuesta += "\n El socio factura " + facturacion
                    + " euros mensuales. (10% de dto. incluido en el precio final.)";
        } else if (tipoSocio.equals("Infantil")) {
            // Aplicamos un descuento de la cuota mensual 50%
            Double precioCuotaDescuento = cuotaMensual - (cuotaMensual * 50 / 100);
            respuesta += "\n    - Coste de la cuota: " + precioCuotaDescuento + " euros.";
            // Obtener listado de escursiones y precio:
            // Al intentar obtener datos de un medio exteno, en este caso la BBDD, debemos
            // usar try para comprobar posibles excepciones.
            try {
                retornoArray = InscripcionModel.obtenerInscripcionesByNumSocio(numeroSocio);
            } catch (Exception e) {
                RespView.excepcionesControllerView(e.getMessage());
            }
            respuesta += retornoArray[0];
            // Se genera el precio final de facturación
            facturacion = precioCuotaDescuento + Double.parseDouble(retornoArray[1]);
            // Se manda el resultado a la vista
            respuesta += "\n El socio factura " + facturacion + " euros mensuales.";
        }
        RespView.respuestaControllerView(respuesta);
        // Volvemos al menu principal de la gestión de los socios.
    }

    // Metodo para seleccionar el seguro de un socio estandar.
    public static SeguroModel seguroSocio() {
        // Tratamiento del seguro
        TipoSeguro tipoSeguro = null;
        boolean comprobarTipoSeguro = false;
        // Excepciones de tipo de seguro
        do {
            // Se piden los datos del seguro mediante la vista seleccionarSeguroView
            String retornoSeguro = SociView.seleccionarSeguroView();
            // Se hace el tratamiento de los datos retornados desde la vista.
            if (retornoSeguro.isEmpty()) {
                RespView.respuestaControllerView("Operación cancelada.");
                break;
            } else if (retornoSeguro.equals("1")) {
                tipoSeguro = TipoSeguro.BASICO;
                comprobarTipoSeguro = true;
            } else if (retornoSeguro.equals("2")) {
                tipoSeguro = TipoSeguro.COMPLETO;
                comprobarTipoSeguro = true;
            } else {
                RespView.excepcionesControllerView("No se ha podido comprobar el tipo de seguro.");
                continue;
            }
        } while (!comprobarTipoSeguro);
        // Genero el objeto de tipo seguro
        SeguroModel seguro = new SeguroModel(tipoSeguro);
        // Retorno el seguro
        return seguro;
    }
}