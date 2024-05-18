package ObjectData_app.ObjectData_controller;

//Se añade la vista principal
import ObjectData_app.ObjectData_model.InscripcionModel;
import ObjectData_app.ObjectData_model.SocioEstandarModel;
import ObjectData_app.ObjectData_model.SocioModel;
import ObjectData_app.ObjectData_view.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ObjectData_app.ObjectData_model.ExcursionModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class InscripcionController {
     @FXML
    private BorderPane mainContainer;
    @FXML
    private Label label;

    @FXML
    private TextField tfNumSocio;

    @FXML
    private Label label1;

    @FXML
    private TextField tfNumExc;

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
                tfNumExc.setText(contenidoCelda.toString());
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
                tfNumSocio.setText(contenidoCelda.toString());
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
        

        // Genera un número de inscripción aleatorio
        
        //RespView.respuestaControllerView("- Número de inscripción generado: " + numeroInscripcion);
// Obtener el texto de los TextField
String textoNumSocio = tfNumSocio.getText();
String textoNumExc = tfNumExc.getText();
// Verificar si las cadenas no están vacías antes de continuar

// Crear un objeto InscripcionModel, convirtiendo los textos a números
//InscripcionModel inscripcion = new InscripcionModel(numeroInscripcion,
  //                                                  Integer.parseInt(textoNumSocio),
    //                                                Integer.parseInt(textoNumExc),
      //                                              new Date());
       // try {
         //   String respuest = InscripcionModel.crearInscripcion(inscripcion);
          //  RespView.respuestaControllerView(respuest);
        //} catch (Exception e) {
           // RespView.excepcionesControllerView(e.getMessage());
        //}
    }
  @FXML
private void handleCrearInscripcion(ActionEvent event) {
    // Obtener el texto de los campos de texto
    String textoNumSocio = tfNumSocio.getText();
    String textoNumExc = tfNumExc.getText();
    int numeroInscripcion = Integer.parseInt("9" + generarID());

    // Verificar si los campos no están vacíos
    if (!textoNumSocio.isEmpty() && !textoNumExc.isEmpty()) {
        try {
            // Convertir los textos a números
            int numeroSocio = Integer.parseInt(textoNumSocio);
            int numeroExcursion = Integer.parseInt(textoNumExc);

            // Crear un objeto InscripcionModel
            InscripcionModel inscripcion = new InscripcionModel(numeroInscripcion, numeroSocio, numeroExcursion, new Date());

            // Llamar al método para crear la inscripción
            String respuesta = InscripcionModel.crearInscripcion(inscripcion);

            // Mostrar mensaje de éxito
            NotificacionView.Notificacion("INFORMATION", "Éxito", "La inscripción se ha creado correctamente.");
            // Cargar el formulario deseado en el mainContainer del contenedor principal
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ObjectData_app/ObjectData_view/AppWindowsView.fxml"));
            Parent root = loader.load();
            mainContainer.setCenter(root);
            return;
            
        } catch (NumberFormatException e) {
            // Mostrar mensaje de error
            NotificacionView.Notificacion("ERROR", "Error", "Los campos deben contener valores numéricos.");
        } catch (Exception e) {
            // Mostrar mensaje de error con la excepción
            NotificacionView.Notificacion("ERROR", "Error", "Ha ocurrido un error: " + e.getMessage());
        }
    } else {
        // Mostrar mensaje de error
        NotificacionView.Notificacion("ERROR", "Error", "Los campos no pueden estar vacíos.");
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