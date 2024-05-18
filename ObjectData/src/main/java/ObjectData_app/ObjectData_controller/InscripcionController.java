package ObjectData_app.ObjectData_controller;

//Se añade la vista principal
import ObjectData_app.ObjectData_model.InscripcionModel;
import ObjectData_app.ObjectData_model.SocioEstandarModel;
import ObjectData_app.ObjectData_model.SocioModel;
import ObjectData_app.ObjectData_view.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import ObjectData_app.ObjectData_model.ExcursionModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class InscripcionController {
    @FXML
    private Label label;

    @FXML
    private TextField tfNombreExcursion;

    @FXML
    private Label label1;

    @FXML
    private TextField tfNumDias;

    @FXML
    private Button btCrear;

    @FXML
    private Button btCrear2;

    @FXML
    private Button btCrear3;

    @FXML
    private TableView<SocioEstandarModel> taSocios;

    @FXML
    private TableColumn<SocioEstandarModel,Integer> taN;

    @FXML
    private TableColumn<SocioEstandarModel,String> taD;

    @FXML
    private TableView<ExcursionModel> taExcursion;

    @FXML
    private TableColumn<ExcursionModel, Integer> taNumero;

    @FXML
    private TableColumn<ExcursionModel, String> taDesc;

    // Se inicializan las vistas necasias.

    // Metodo para crear ID de inscripcion dinamicos
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

    public void inicializarTabla() {
        taNumero.setCellValueFactory(new PropertyValueFactory<>("numeroExcursion"));
        taDesc.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        
        taExcursion.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Integer contenidoCelda = newSelection.getNumeroExcursion();
                final Clipboard clipboard = Clipboard.getSystemClipboard();
                final ClipboardContent content = new ClipboardContent();
                content.putString(contenidoCelda.toString());
                clipboard.setContent(content);
                NotificacionView.Notificacion("INFORMATION", "Copiado al portapapeles","Se copio al portapapeles el número de excursión: " + contenidoCelda);
            }
        });
    }

    public void inicializarTabla2()
    {
        taN.setCellValueFactory(new PropertyValueFactory<>("numeroSocio"));
        taD.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        
        taSocios.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Integer contenidoCelda = newSelection.getNumeroSocio();
                final Clipboard clipboard = Clipboard.getSystemClipboard();
                final ClipboardContent content = new ClipboardContent();
                content.putString(contenidoCelda.toString());
                clipboard.setContent(content);
                NotificacionView.Notificacion("INFORMATION", "Copiado al portapapeles","Se copio al portapapeles el número de excursión: " + contenidoCelda);
            }
        });
    }


    @FXML
    // Metodo para crear una Inscripcion
    public void crearInscripcion() {
        inicializarTabla();
        inicializarTabla2();
        List<SocioEstandarModel> socios = SocioEstandarModel.obtenerSocios();
        List<ExcursionModel> excursiones = ExcursionModel.obtenerListadoExcursiones();
        
        // Convertir la lista a una ObservableList
        ObservableList<ExcursionModel> observableList = FXCollections.observableArrayList(excursiones);
        ObservableList<SocioEstandarModel> observableList2 = FXCollections.observableArrayList(socios);

        // Asignar la lista al TableView
        taExcursion.setItems(observableList);
        taSocios.setItems(observableList2);
        int numeroSocio = 0;
        //String respuesta = InscView.formCrearInscripcionView();
        int numeroExcursion = 0;
        //if (respuesta == null || respuesta.isEmpty()) {
          //  RespView.respuestaControllerView("Operación cancelada.");
            //return;
        //} else if (respuesta.equals("N")) {
          //  SocioController.crearNuevoSocio();
           // return;
        //} else if (respuesta.equals("S")) {
          //  String retorno = InscView.formSeguirCrearInscripcionView();
            //if (retorno.isEmpty()) {
              //  RespView.respuestaControllerView("Número de socio vacío. Operación cancelada.");
                //return;
           // }

           // numeroSocio = Integer.parseInt(retorno);

       // } else {
         //   RespView.excepcionesControllerView("Debes introducir 'S' o 'N'. Operación cancelada.");
           // return;
        //}

        // Si se llega aquí, significa que el usuario indicó que el socio existe y se le
        // pide ingresar el número de socio
        // Comprueba si el socio existe
        try {
            if (!SocioModel.comprobarSocioPorNumeroSocio(numeroSocio)) {
                return;
            }
        } catch (Exception e) {
            //RespView.excepcionesControllerView(e.getMessage());
        }

        // Obtiene y muestra el listado de excursiones
        List<ExcursionModel> listadoExcursiones = ExcursionModel.obtenerListadoExcursiones();
        //String retornoExcursion = InscView.formListadoExcursionesView(listadoExcursiones[0]);
       // if (retornoExcursion.matches("\\d+")) {
         //   int opcion = Integer.parseInt(retornoExcursion);
           // numeroExcursion = ExcursionModel.obtenerExcursionDesdeLista(opcion).getNumeroExcursion();
       // } else {
         //   RespView.excepcionesControllerView("Debes introducir un valor númerico.");
          //  return;
        //}

        // Comprueba si la excursión existe
        try {
            if (ExcursionModel.obtenerExcursionPorNumeroExcursion(numeroExcursion) == null) {
                //RespView.excepcionesControllerView("Excursión no encontrada.");
                return;
            }
        } catch (Exception e) {
           // RespView.excepcionesControllerView(e.getMessage());
        }

        // Genera un número de inscripción aleatorio
        int numeroInscripcion = Integer.parseInt("9" + generarID());
        //RespView.respuestaControllerView("- Número de inscripción generado: " + numeroInscripcion);

        // Crea la inscripción
        InscripcionModel inscripcion = new InscripcionModel(numeroInscripcion, numeroSocio, numeroExcursion,
                new Date());
        try {
            String respuest = InscripcionModel.crearInscripcion(inscripcion);
          //  RespView.respuestaControllerView(respuest);
        } catch (Exception e) {
           // RespView.excepcionesControllerView(e.getMessage());
        }
    }

    public static void mostrarInscripcion() {
        boolean valoresComprobados = false;
        int opcion = 0;
        do {
            String retorno = InscView.formMostrarInscripcionView();
            if (retorno.matches("\\d+")) {
                opcion = Integer.parseInt(retorno);
            } else {
                RespView.excepcionesControllerView("Debes introducir un valor númerico.");
                continue;
            }

            if (opcion == 0) {
                break;
            } else if (opcion == 1 || opcion == 2) {
                valoresComprobados = true;
            } else {
                RespView.excepcionesControllerView("Debes selecciona una opcion valida.");
                continue;
            }
        } while (!valoresComprobados);
        switch (opcion) {
            case 1:
                mostrarInscripcionPorSocio();
                break;
            case 2:
                mostrarInscripcionPorFecha();
                break;
        }
    }

    public static void mostrarInscripcionPorSocio() {
        String[] retorno = InscView.formFiltrarPorSocio();
        if (retorno != null && retorno.length > 0) {
            String numSocio = retorno[0];
            if (!numSocio.isEmpty() && numSocio.matches("\\d+")) { // Verifica si la cadena no está vacía y contiene
                                                                   // solo dígitos
                int numeroSocio = Integer.parseInt(numSocio);
                try {
                    RespView.respuestaControllerView("Listado de todas las inscripciones para el socio seleccionado: "
                            + InscripcionModel.obtenerInscripcionesByNumSocio(numeroSocio)[0]);
                } catch (Exception e) {
                    RespView.excepcionesControllerView(
                            "Error. No se ha podido obtener las inscripciones." + e.getMessage());
                }
            } else {
                RespView.excepcionesControllerView("El número de socio ingresado no es válido.");
            }
        }
    }

    public static void eliminarInscripcion() {
        String listadoInscripciones = "";
        boolean inscripcionEliminada = false;

        try {
            listadoInscripciones = InscripcionModel.obtenerListadoInscripciones();
        } catch (Exception e) {
            RespView.excepcionesControllerView(e.getMessage());
        }
        RespView.respuestaControllerView(listadoInscripciones);
        String retorno = InscView.formEliminarInscripcionView(listadoInscripciones);
        int num;
        // Manejar el caso en que el usuario no ingrese un número válido
        if (retorno.matches("\\d+")) {
            num = Integer.parseInt(retorno);
        } else {
            RespView.excepcionesControllerView("El número de inscripción ingresado no es válido.");
            return;
        }

        try {
            inscripcionEliminada = InscripcionModel.eliminarInscripcionNumero(num);
        } catch (Exception e) {
            RespView.excepcionesControllerView(e.getMessage());
        }

        if (inscripcionEliminada) {
            RespView.respuestaControllerView("La inscripción ha sido eliminada exitosamente.");
        } else {
            RespView.excepcionesControllerView(
                    "No se pudo eliminar la inscripción. Verifique el número de inscripción y asegúrese de que la fecha de inscripción sea anterior a la fecha de la excursión.");
        }
    }

    public static void mostrarInscripcionPorFecha() {
        String[] inscripciones = null;
        String[] retorno = InscView.formFiltrarPorFechas();
        if (retorno != null && retorno.length == 2) {
            String fechaInicio = retorno[0];
            String fechaFin = retorno[1];
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            Date fechaI = null, fechaF = null;
            if (!fechaInicio.matches("\\d{4}/\\d{2}/\\d{2}") || !fechaFin.matches("\\d{4}/\\d{2}/\\d{2}")) {
                RespView.excepcionesControllerView("Formato de fecha inválido. Se esperaba yyyy/MM/dd.");
            } else {
                try { // Parsear las fechas de inicio y fin
                    fechaI = sdf.parse(fechaInicio);
                    fechaF = sdf.parse(fechaFin);
                } catch (ParseException e) {
                    RespView.excepcionesControllerView("No se han podido parsear las fechas.");
                }
            }

            try {
                inscripciones = InscripcionModel.listarInscripcionesFecha(fechaI, fechaF);
            } catch (Exception e) {
                RespView.excepcionesControllerView("No se ha podido listar las Inscripciones.");

            }
            if (inscripciones.length > 0) {
                RespView.respuestaControllerView("Listado de inscripciones por rango de fechas: " + inscripciones[0]);
            } else {
                RespView.excepcionesControllerView(
                        "No se encontraron inscripciones para el rango de fechas especificado.");
            }
        } else {
            RespView.excepcionesControllerView("Problema al obtener las fechas de filtrado.");
        }
    }
}