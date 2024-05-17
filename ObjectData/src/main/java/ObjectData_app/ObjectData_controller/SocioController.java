package ObjectData_app.ObjectData_controller;

import ObjectData_app.ObjectData_model.SocioEstandarModel;
import ObjectData_app.ObjectData_model.SocioFederadoModel;
import ObjectData_app.ObjectData_model.SocioInfantilModel;
import ObjectData_app.ObjectData_model.SocioModel;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import ObjectData_app.ObjectData_view.NotificacionView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.Random;

import ObjectData_app.ObjectData_model.ExcursionModel;
import ObjectData_app.ObjectData_model.FederacionModel;
import ObjectData_app.ObjectData_model.InscripcionModel;
import ObjectData_app.ObjectData_model.SeguroModel;
import ObjectData_app.ObjectData_model.SeguroModel.TipoSeguro;
//Se añaden las vistas necesarias.
import ObjectData_app.ObjectData_controller.SocioController;

public class SocioController {
    @FXML
    private Text taInfo;
    @FXML
    private TableView<Object> taTodosLosSocios;
    @FXML
    private TableColumn<Object, Integer> taNumeroSocio;
    @FXML
    private TableColumn<Object, String> taNombre;
    @FXML
    private TableColumn<Object, String> taTipoSocio;

    public void mostrarTodosLosSocios() {
        //Iniciamos la tabla
        taNumeroSocio.setCellValueFactory(cellData -> {
            Object item = cellData.getValue();
            if (item instanceof SocioEstandarModel) {
                SocioEstandarModel socio = (SocioEstandarModel) item;
                return new SimpleIntegerProperty(socio.getNumeroSocio()).asObject();
            } else if (item instanceof SocioFederadoModel) {
                SocioFederadoModel socio = (SocioFederadoModel) item;
                return new SimpleIntegerProperty(socio.getNumeroSocio()).asObject();
            } else if (item instanceof SocioInfantilModel) {
                SocioInfantilModel socio = (SocioInfantilModel) item;
                return new SimpleIntegerProperty(socio.getNumeroSocio()).asObject();
            }
            return null;
        });

        taNombre.setCellValueFactory(cellData -> {
            Object item = cellData.getValue();
            if (item instanceof SocioEstandarModel) {
                SocioEstandarModel socio = (SocioEstandarModel) item;
                return new SimpleStringProperty(socio.getNombre());
            } else if (item instanceof SocioFederadoModel) {
                SocioFederadoModel socio = (SocioFederadoModel) item;
                return new SimpleStringProperty(socio.getNombre());
            } else if (item instanceof SocioInfantilModel) {
                SocioInfantilModel socio = (SocioInfantilModel) item;
                return new SimpleStringProperty(socio.getNombre());
            }
            return null;
        });

        taTipoSocio.setCellValueFactory(cellData -> {
            Object item = cellData.getValue();
            if (item instanceof SocioEstandarModel) {
                return new SimpleStringProperty("Estandar");
            } else if (item instanceof SocioFederadoModel) {
                return new SimpleStringProperty("Federado");
            } else if (item instanceof SocioInfantilModel) {
                return new SimpleStringProperty("Infantil");
            }
            return null;
        });

        //Obtenemos los objetos de los socios
        ArrayList<SocioEstandarModel> socioEstandarModels = SocioEstandarModel.obtenerSocios();
        ArrayList<SocioFederadoModel> socioFederadoModels = SocioFederadoModel.obtenerSocios();
        ArrayList<SocioInfantilModel> socioInfantilModels = SocioInfantilModel.obtenerSocios();

        //Añadimos los socios a la tabla
        taTodosLosSocios.getItems().addAll(socioEstandarModels);
        taTodosLosSocios.getItems().addAll(socioFederadoModels);
        taTodosLosSocios.getItems().addAll(socioInfantilModels);
    }
    //SocioEstandar: 

    @FXML
    private TextField tfNombreSocioEstandar;

    @FXML
    private TextField tfDniSocioEstandar;

    @FXML
    private ChoiceBox<String> cbTipoSeguro;

    @FXML
    private Button btCrear;

    @FXML
    public void initialize() {
        cbTipoSeguro.getItems().addAll("1 - Básico", "2 - Completo");
    }

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

    // Metodo para añadir un socio estandar
    @FXML
    public void crearSocioEstandar() {
        // Atributos
        String nombre = tfNombreSocioEstandar.getText();
        String NIF = tfDniSocioEstandar.getText();
        String tipoSeguroSeleccionado = cbTipoSeguro.getValue();

        if (nombre.isEmpty() || NIF.isEmpty() || tipoSeguroSeleccionado == null) {
            NotificacionView.Notificacion("WARNING", "Campos Vacíos", "Por favor, completa todos los campos.");
            return;
        }
        int numeroSocio;
        boolean todoOk = false;

        //RespView.tituloDeLaFuncion("-- FORMULARIO PARA CREAR UN SOCIO ESTANDAR --");
        
        do {
            numeroSocio = Integer.parseInt("5" + generarID());

            SeguroModel seguroModel = seguroSocio();
            if (seguroModel == null) {
                NotificacionView.Notificacion("ERROR", "Tipo de Seguro Inválido", "Por favor, selecciona un tipo de seguro válido.");
                break;
            }

            SocioEstandarModel socioModel = new SocioEstandarModel(numeroSocio, nombre, NIF, seguroModel);

            try {
                SocioEstandarModel.crearSocioEstandar(socioModel);
                NotificacionView.Notificacion("INFORMATION", "Éxito", "Se ha creado el socio estándar correctamente.");
                todoOk = true;
            } catch (Exception e) {
                NotificacionView.Notificacion("ERROR", "Error en la Creación", "Hubo un error al crear el socio estándar: " + e.getMessage());
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
   @FXML
   private SeguroModel seguroSocio() {
       // Tratamiento del seguro
       TipoSeguro tipoSeguro = null;
       String retornoSeguro = cbTipoSeguro.getValue();

       if (retornoSeguro.startsWith("1")) {
           tipoSeguro = TipoSeguro.BASICO;
       } else if (retornoSeguro.startsWith("2")) {
           tipoSeguro = TipoSeguro.COMPLETO;
       } else {
           return null;
       }

       return new SeguroModel(tipoSeguro);
   }
}