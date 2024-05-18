package ObjectData_app.ObjectData_controller;

import ObjectData_app.ObjectData_model.ExcursionModel;
import ObjectData_app.ObjectData_view.NotificacionView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.text.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class ExcursionController {
    @FXML
    private TextField tfNombreExcursion;
    @FXML
    private DatePicker tfFecha;
    @FXML
    private TextField tfHora;
    @FXML
    private TextField tfNumDias;
    @FXML
    private TextField tfPrecioInscripcion;
    @FXML
    private Button btCrear;

    @FXML
    private DatePicker tfFechaInicioExcursion;
    @FXML
    private DatePicker tfFechaFinExcursion;
    @FXML
    private Text tInfo;
    @FXML
    private TableView<ExcursionModel> taResultadoExcursion;
    @FXML
    private TableColumn<ExcursionModel, Integer> taNumeroExcursion;
    @FXML
    private TableColumn<ExcursionModel, String> taDescripcion;
    @FXML
    private TableColumn<ExcursionModel, Date> taFecha;
    @FXML
    private TableColumn<ExcursionModel, Double> taPrecio;
    @FXML
    private TableColumn<ExcursionModel, Integer> taNumDias;
    @FXML
    private Button bnBuscarMostrarExcursion;

    // Método para crear una ID aleatoria de 8 dígitos
    public static int generarID() {
        Random rand = new Random();
        int id = 0;
        for (int i = 0; i < 8; i++) {
            id = id * 10 + rand.nextInt(10);
        }
        return id;
    }

    // Método para crear una nueva excursión
    @FXML
    public void crearExcursion() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date fecha = null;
        int numeroDias = 0;
        double precio = 0.0;
        String respuesta;
        // Comprobaciones
        if (tfNombreExcursion.getText().isEmpty() ||
            tfFecha.getValue() == null ||
            tfHora.getText().isEmpty() ||
            tfNumDias.getText().isEmpty() ||
            tfPrecioInscripcion.getText().isEmpty()) {
            NotificacionView.Notificacion("WARNING", "Campos Vacíos", "Por favor, completa todos los campos.");
            return;
        }
        String descripcionExcursion = tfNombreExcursion.getText();
        try {
            String retorno = tfFecha.getValue().toString();
            retorno += " " + tfHora.getText().trim();
            if (!retorno.matches("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}$")) {
                NotificacionView.Notificacion("WARNING", "Formato Incorrecto",
                        "El formato de la fecha debe ser yyyy-MM-dd HH:mm.");
                return;
            }
            fecha = sdf.parse(retorno);
        } catch (ParseException e) {
            NotificacionView.Notificacion("WARNING", "Fecha Incorrecta",
                    "Error al parsear la fecha: " + e.getMessage());
            return;
        }
        try {
            String retorno = tfNumDias.getText().trim();
            numeroDias = Integer.parseInt(retorno);
        } catch (NumberFormatException e) {
            NotificacionView.Notificacion("WARNING", "Formato Incorrecto",
                    "El número de días debe ser un número entero.");
            return;
        }
        try {
            String retorno = tfPrecioInscripcion.getText().trim();
            precio = Double.parseDouble(retorno);
        } catch (NumberFormatException e) {
            NotificacionView.Notificacion("WARNING", "Formato Incorrecto",
                    "El precio debe ser un número válido.");
            return;
        }
        int numeroExcursion = generarID();
        NotificacionView.Notificacion("INFORMATION", "Número de excursión generado", String.valueOf(numeroExcursion));
        ExcursionModel excursion = new ExcursionModel(numeroExcursion, descripcionExcursion, fecha, numeroDias, precio);
        try {
            respuesta = excursion.crearExcursionModel(excursion);
            NotificacionView.Notificacion("INFORMATION", "Excursión Creada!", respuesta);
        } catch (Exception e) {
            NotificacionView.Notificacion("ERROR", "Error creando excursión: ", e.getMessage());
        }
    }

    @FXML
    private void buscarExcursiones() {
        
        taNumeroExcursion.setCellValueFactory(new PropertyValueFactory<>("numeroExcursion"));
        taDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        taFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        taPrecio.setCellValueFactory(new PropertyValueFactory<>("precioInscripcion"));
        taNumDias.setCellValueFactory(new PropertyValueFactory<>("numeroDias"));

        taResultadoExcursion.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Integer contenidoCelda = newSelection.getNumeroExcursion();
                final Clipboard clipboard = Clipboard.getSystemClipboard();
                final ClipboardContent content = new ClipboardContent();
                content.putString(contenidoCelda.toString());
                clipboard.setContent(content);
                NotificacionView.Notificacion("INFORMATION", "Copiado al portapapeles","Se copio al portapapeles el número de excursión: " + contenidoCelda);
            }
        });

        tInfo.setText("Buscando datos ...");
        LocalDate fechaInicioSeleccionada = tfFechaInicioExcursion.getValue();
        LocalDate fechaFinSeleccionada = tfFechaFinExcursion.getValue();
        if (fechaInicioSeleccionada == null || fechaFinSeleccionada == null) {
            NotificacionView.Notificacion("error", "Campos vacíos", "Debes rellenar los campos.");
            tInfo.setText("");
            return;
        }
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() {
                try {
                    Date fechaInicio = Date.from(fechaInicioSeleccionada.atStartOfDay(ZoneId.systemDefault()).toInstant());
                    Date fechaFin = Date.from(fechaFinSeleccionada.atStartOfDay(ZoneId.systemDefault()).toInstant());
                    if (fechaInicio.after(fechaFin)) {
                        Platform.runLater(() -> NotificacionView.Notificacion("WARNING", "Fechas incorrectas", "La fecha de inicio no puede ser posterior a la fecha de fin."));
                        return null;
                    }
                    ArrayList<ExcursionModel> excursiones = ExcursionModel.objetoListaExcursion(fechaInicio, fechaFin);
                    ObservableList<ExcursionModel> data = FXCollections.observableArrayList(excursiones);
                    Platform.runLater(() -> taResultadoExcursion.setItems(data));
                } catch (Exception e) {
                    Platform.runLater(() -> NotificacionView.Notificacion("error", "Error en el controlador", "Error en el controlador: " + e.getMessage()));
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

