package ObjectData_app.ObjectData_controller;

//Se añade la vista principal
import ObjectData_app.ObjectData_model.InscripcionModel;
import ObjectData_app.ObjectData_model.SocioEstandarModel;
import ObjectData_app.ObjectData_model.SocioFederadoModel;
import ObjectData_app.ObjectData_model.SocioInfantilModel;
import ObjectData_app.ObjectData_model.SocioModel;
import ObjectData_app.ObjectData_view.*;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import ObjectData_app.ObjectData_model.ExcursionModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.Random;

public class InscripcionController {
    @FXML
    private Button bnBuscarMostrarInscripcion;

    @FXML
    private DatePicker tfFechaInicioinscripcion;
    @FXML
    private DatePicker tfFechaFinInscripcion;

    // Tabla para mostrar todos los socios:
    @FXML
    private TableView<Object> taTodosLosSocios;
    @FXML
    private TableColumn<Object, Integer> taNumeroSocio;
    @FXML
    private TableColumn<Object, String> taNombre;

    // Filtro para la tabla
    @FXML
    private TextField tfNumeroSocio;
    @FXML
    private FilteredList<Object> filteredData;

    // Tabla para mostrar inscripciones en mostrar por socio:
    @FXML
    private TableView<Object> taTodasLasInscripciones;
    @FXML
    private TableColumn<Object, String> taNumInscripcion;
    @FXML
    private TableColumn<Object, String> taDescripcionExcursion;
    @FXML
    private TableColumn<Object, String> taFechaExcursion;

    // Declaracion de los ID de la tabla de resultados de inscripción en mostrar por
    // fecha
    @FXML
    private TableView<InscripcionModel> taResultadoInscripcion;
    @FXML
    private TableColumn<InscripcionModel, Integer> taNumeroInscripcion;
    @FXML
    private TableColumn<InscripcionModel, Integer> taNumeroSocio1;
    @FXML
    private TableColumn<InscripcionModel, Integer> taNumeroExcursion;
    @FXML
    private TableColumn<InscripcionModel, Date> taFecha;

    @FXML
    private Text tInfo;

    @FXML
    private Text tInfo1;

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
    private Button btBoton;

    @FXML
    private Button btBoton2;

    @FXML
    private Button btBoton3;

    @FXML
    private TableView<SocioEstandarModel> taSocios;

    @FXML
    private TableColumn<SocioEstandarModel, Integer> taN;

    @FXML
    private TableColumn<SocioEstandarModel, String> taD;

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

    @FXML
    // Metodo para crear una Inscripcion
    public void crearInscripcion() {
        taNumero.setCellValueFactory(new PropertyValueFactory<>("numeroExcursion"));
        taDesc.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        taExcursion.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Integer contenidoCelda = newSelection.getNumeroExcursion();
                tfNumExc.setText(contenidoCelda.toString());
            }
        });
        taTodosLosSocios.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Integer contenidoCelda = ((SocioModel) newSelection).getNumeroSocio();
                tfNumSocio.setText(contenidoCelda.toString());
            }
        });
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() {
                try {
                    ArrayList<ExcursionModel> excursiones = ExcursionModel.obtenerListadoExcursiones();
                    ObservableList<ExcursionModel> observableList = FXCollections.observableArrayList(excursiones);
                    taExcursion.setItems(observableList);
                } catch (Exception e) {
                }
                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
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
                InscripcionModel inscripcion = new InscripcionModel(numeroInscripcion, numeroSocio, numeroExcursion,
                        new Date());

                // Llamar al método para crear la inscripción
                String respuesta = InscripcionModel.crearInscripcion(inscripcion);

                // Mostrar mensaje de éxito
                NotificacionView.Notificacion("INFORMATION", "Éxito", "La inscripción se ha creado correctamente.");
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
            NotificacionView.Notificacion("WARNING", "Error", "Los campos no pueden estar vacíos.");
        }

    }

    public void obtenerInscripciones() {
        taTodosLosSocios.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                tInfo1.setText("Obteniendo inscripciones ...");
                // Inicializamos la tabla de facturación
                taNumInscripcion.setCellValueFactory(cellData -> {
                    Object item = cellData.getValue();
                    if (item instanceof String[]) {
                        String[] values = (String[]) item;
                        return new SimpleStringProperty(values[0]);
                    }
                    return null;
                });
                taDescripcionExcursion.setCellValueFactory(cellData -> {
                    Object item = cellData.getValue();
                    if (item instanceof String[]) {
                        String[] values = (String[]) item;
                        return new SimpleStringProperty(values[1]);
                    }
                    return null;
                });
                taFechaExcursion.setCellValueFactory(cellData -> {
                    Object item = cellData.getValue();
                    if (item instanceof String[]) {
                        String[] values = (String[]) item;
                        return new SimpleStringProperty(values[2]);
                    }
                    return null;
                });
                Task<Void> task = new Task<Void>() {
                    @Override
                    protected Void call() {
                        try {
                            String[] listaInscripciones = {};
                            ArrayList<String[]> listaInscripcionesArrayList = new ArrayList<>();
                            int numeroSocio = ((SocioModel) newSelection).getNumeroSocio();
                            ArrayList<InscripcionModel> inscripcionesSocioModel = InscripcionModel
                                    .obtenerInscripcionesByNumSocio(numeroSocio);
                            for (InscripcionModel inscripcion : inscripcionesSocioModel) {
                                // Obtenemos la excursión
                                ExcursionModel excursion = ExcursionModel
                                        .obtenerExcursionPorNumeroExcursion(inscripcion.getNumeroExcursion());
                                Integer numIns = inscripcion.getNumeroInscripcion();
                                Date fecha = excursion.getFecha();
                                listaInscripciones = new String[] { numIns.toString(), excursion.getDescripcion(),
                                        fecha.toString() };
                                listaInscripcionesArrayList.add(listaInscripciones);
                            }
                            taTodasLasInscripciones
                                    .setItems(FXCollections.observableArrayList(listaInscripcionesArrayList));
                            return null;
                        } catch (Exception e) {
                            Platform.runLater(() -> NotificacionView.Notificacion("error", "Error en el controlador",
                                    "Error en el controlador: " + e.getMessage()));
                        } finally {
                            Platform.runLater(() -> tInfo1.setText(""));
                        }
                        return null;
                    }
                };
                Thread thread = new Thread(task);
                thread.setDaemon(true);
                thread.start();
            }
        });
    }

    public void actionEliminarInscripcion() {
        taTodasLasInscripciones.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        String[] datos = (String[]) newSelection;
                        try {
                            Alert alertConfirmation = new Alert(AlertType.CONFIRMATION);
                            alertConfirmation.setTitle("Confirmación de Eliminación");
                            alertConfirmation.setHeaderText(null);
                            alertConfirmation.setContentText(
                                    "¿Estás seguro de que deseas eliminar la inscripción con número: "
                                            + datos[0] + "?");
                            Optional<ButtonType> result = alertConfirmation.showAndWait();
                            if (result.isPresent() && result.get() != ButtonType.OK) {
                                return;
                            }
                            InscripcionModel.eliminarInscripcionNumero(Integer.parseInt(datos[0]));
                            NotificacionView.Notificacion("INFORMATION", "Inscripción eliminada",
                                    "La inscripción se a eliminado con exito!!");
                        } catch (Exception e) {
                            NotificacionView.Notificacion("Error", "Error en el controlador",
                                    "Ha ocurido un error en la elimicación. Causa:" + e.getMessage());
                        }
                    }
                });
    }

    public void mostrarInscripcionPorFecha() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date fechaInicio = null, fechaFin = null;

        try {
            fechaInicio = java.sql.Date.valueOf(tfFechaInicioinscripcion.getValue());
            fechaFin = java.sql.Date.valueOf(tfFechaFinInscripcion.getValue());
        } catch (Exception e) {
            NotificacionView.Notificacion("ERROR", "Error al obtener las fechas de filtrado",
                    "Ha ocurrido un problema al intentar obtener las fechas de filtrado.");
            return;
        }

        if (fechaInicio.after(fechaFin)) {
            NotificacionView.Notificacion("ERROR", "La fecha de inicio debe ser anterior a la fecha de fin.",
                    "Ha ocurrido un problema al intentar obtener las fechas de filtrado.");
            return;
        }

        taNumeroInscripcion.setCellValueFactory(new PropertyValueFactory<>("numeroInscripcion"));
        taNumeroSocio1.setCellValueFactory(new PropertyValueFactory<>("numeroSocio"));
        taNumeroExcursion.setCellValueFactory(new PropertyValueFactory<>("numeroExcursion"));
        taFecha.setCellValueFactory(new PropertyValueFactory<>("fechaInscripcion"));
        taResultadoInscripcion.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        Integer contenidoCelda = newSelection.getNumeroExcursion();
                        final Clipboard clipboard = Clipboard.getSystemClipboard();
                        final ClipboardContent content = new ClipboardContent();
                        content.putString(contenidoCelda.toString());
                        clipboard.setContent(content);
                        NotificacionView.Notificacion("INFORMATION", "Copiado al portapapeles",
                                "Se copio al portapapeles el número de excursión: " + contenidoCelda);
                    }
                });
        // Supongamos que tienes dos variables para almacenar las fechas seleccionadas

        // Verificar si las fechas seleccionadas no son nulas
        if (fechaInicio != null && fechaFin != null) {
            // Supongamos que listarInscripcionesFecha retorna una lista de inscripciones
            ArrayList<InscripcionModel> inscripciones = InscripcionModel.objetoListaInscripcion(fechaInicio, fechaFin);

            // Convertir la lista a un ObservableList si es necesario
            ObservableList<InscripcionModel> observableInscripciones = FXCollections.observableArrayList(inscripciones);

            // Establecer los items de la tabla
            taResultadoInscripcion.setItems(observableInscripciones);
        }
    }

    // Este metodo permite filtrar usuarios en la tabla usando un TextField con
    // fx:id
    // tfNumeroSocio
    public void filtrarSocioPorNumeroEnTabla() {
        tfNumeroSocio.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(socio -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String numeroSocio = String.valueOf(((SocioModel) socio).getNumeroSocio());
                return numeroSocio.contains(newValue);
            });
        });
    }

    // Este metodo permite cargar usuarios en la tabla, el nombre de la tabla,
    // columnas y aviso de carga de datos son:
    // tabla-id:taTodosLosSocios // column-id:taNumeroSocio
    // column-id:taNombre // text-id:tInfo
    @FXML
    public void cargarLosSociosEnTabla() {
        tInfo.setText("Cargando datos ...");
        // Iniciamos la tabla
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
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() {
                try {
                    // Obtenemos los objetos de los socios
                    ArrayList<SocioEstandarModel> socioEstandarModels = SocioEstandarModel.obtenerSocios();
                    ArrayList<SocioFederadoModel> socioFederadoModels = SocioFederadoModel.obtenerSocios();
                    ArrayList<SocioInfantilModel> socioInfantilModels = SocioInfantilModel.obtenerSocios();
                    // Create an ObservableList and add the items to it
                    ObservableList<Object> allSocios = FXCollections.observableArrayList();
                    allSocios.addAll(socioEstandarModels);
                    allSocios.addAll(socioFederadoModels);
                    allSocios.addAll(socioInfantilModels);
                    // Objetos filtrados si es necesario
                    filteredData = new FilteredList<>(allSocios);
                    // Añadimos los socios a la tabla
                    taTodosLosSocios.setItems(filteredData);
                } catch (Exception e) {
                    Platform.runLater(() -> NotificacionView.Notificacion("error", "Error en el controlador",
                            "Error en el controlador: " + e.getMessage()));
                } finally {
                    Platform.runLater(() -> tInfo.setText(""));
                }
                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }
}
