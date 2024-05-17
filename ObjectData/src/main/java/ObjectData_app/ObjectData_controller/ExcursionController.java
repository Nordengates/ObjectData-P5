package ObjectData_app.ObjectData_controller;

import ObjectData_app.ObjectData_model.ExcursionModel;
import ObjectData_app.ObjectData_controller.ExcursionController;
import ObjectData_app.ObjectData_view.MensajeControllerView;
import ObjectData_app.ObjectData_view.NotificacionView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Random;

import org.hibernate.mapping.List;

public class ExcursionController {
    //Crear excursion
    @FXML
    TextField tfNombreExcursion;
    @FXML
    TextField tfFechaExcursion;
    @FXML
    TextField tfNumDias;
    @FXML
    TextField tfPrecioInscripcion;

    @FXML
    Button btCrear;

     //MostrarExcursion
    @FXML
    DatePicker tfFechaInicioExcursion;
    @FXML
    DatePicker tfFechaFinExcursion;

    @FXML
    TextArea taResultadoExcursion;

    @FXML
    Button bnBuscarMostrarExcursion;



   
    // Se inicializan las vistas necasias.

    // Metodo para crear una ID ramdon de 8 digitos
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

    // Esta función sirve para crear una nueva excursión (Debemos importar , que
    // se inicializó al arranque de APP en main)
    @FXML
    public void crearExcursion() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        boolean comprobadoOk = false;
        Date fecha = null;
        int numeroDias = 0;
        double precio = 0.0;
        String respuesta = null;

        // Comprobaciones
        if (tfNombreExcursion.getText().isEmpty() || 
            tfFechaExcursion.getText().isEmpty() || 
            tfNumDias.getText().isEmpty() || 
            tfPrecioInscripcion.getText().isEmpty()) {
        
            // Mostrar notificación de campos vacíos
            NotificacionView.Notificacion("WARNING", "Campos Vacíos", "Por favor, completa todos los campos.");
            return; 
        }

        // Pedimos la descripcion de la excursión
        String descripcionExcursion = tfNombreExcursion.getText();

        // Pedimos la fecha de excursión y comprobamos el dato.
        do {
            String retorno = tfFechaExcursion.getText().trim();
            if (retorno.isEmpty()) {
                NotificacionView.Notificacion("WARNING", "Fecha Vacía", "La fecha de la excursión no puede estar vacía.");
                return;
            } else if (!retorno.matches("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}$")) {
                NotificacionView.Notificacion("WARNING", "Formato Incorrecto", "El formato de la fecha debe ser yyyy/MM/dd HH:mm.");
                return;
            }
            try {
                fecha = sdf.parse(retorno);
                comprobadoOk = true;
            } catch (ParseException e) {
                NotificacionView.Notificacion("INFORMATION", "Fecha Incorrecta", "Error al parsear la fecha: " + e.getMessage()); // Cambio aquí
                return;
            }
        } while (!comprobadoOk);

        comprobadoOk = false;
        // Pedimos el numero de dias y comprobamos el dato.
        do {
            String retorno = tfNumDias.getText().trim();
            if (retorno.isEmpty()) {
                NotificacionView.Notificacion("WARNING", "Días Vacíos", "El número de días no puede estar vacío.");
                return;
            } else if (retorno.matches("\\d+")) { // Verifica si el retorno es un número entero
                numeroDias = Integer.parseInt(retorno);
                comprobadoOk = true;
            } else {
                NotificacionView.Notificacion("WARNING", "Formato Incorrecto", "El número de días debe ser un número entero.");
                return;
            }
        } while (!comprobadoOk);

        comprobadoOk = false;
        do {
            String retorno = tfPrecioInscripcion.getText().trim();
            if (retorno.isEmpty()) {
                NotificacionView.Notificacion("WARNING", "Precio Vacío", "El precio no puede estar vacío.");
                return;
            } else if (retorno.matches("\\d+(\\.\\d+)?")) { // Verifica si el retorno es un número entero o double
                precio = Double.parseDouble(retorno);
                comprobadoOk = true;
            } else {
                NotificacionView.Notificacion("WARNING", "Formato Incorrecto", "El precio debe ser un número válido.");
                return;
            }
        } while (!comprobadoOk);

        // Método para generar un numeroExcursion aleatorio
        int numeroExcursion = Integer.parseInt("1" + generarID()); // numeroExcursion
        // Se genera el conjunto de en la variable excursion
        ExcursionModel excursion = new ExcursionModel(numeroExcursion, descripcionExcursion, fecha, numeroDias, precio);

        // Se llama al metodo crearExcursion del modelo ExcursionModel, se pasa tanto la instancia como el objeto creado
        try {
            respuesta = excursion.crearExcursionModel(excursion);
            NotificacionView.Notificacion("SUCCESS", "Excursión Creada!", respuesta);
        } catch (Exception e) {
            NotificacionView.Notificacion("ERROR", "Error", e.getMessage());
        }
    }

    @FXML
    private void buscarExcursiones() {
        LocalDate fechaInicioSeleccionada = tfFechaInicioExcursion.getValue();
        LocalDate fechaFinSeleccionada = tfFechaFinExcursion.getValue();
    
        // Se comprueba si el usuario quiere salir
        if (fechaInicioSeleccionada == null || fechaFinSeleccionada == null) {
            taResultadoExcursion.setText("Operación cancelada.");
            return;
        }
    
        // Se intenta transformar las fechas y se muestran los resultados.
        try {
            Date fechaInicio = Date.from(fechaInicioSeleccionada.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date fechaFin = Date.from(fechaFinSeleccionada.atStartOfDay(ZoneId.systemDefault()).toInstant());
    
            // Verificación adicional para fechas válidas
            if (fechaInicio.after(fechaFin)) {
                taResultadoExcursion.setText("La fecha de inicio no puede ser posterior a la fecha de fin.");
                return;
            }
    
            String respuesta = ExcursionModel.mostrarExcursiones(fechaInicio, fechaFin);
            taResultadoExcursion.setText(respuesta);
        } catch (Exception e) {
            taResultadoExcursion.setText("Error al procesar las fechas: " + e.getMessage());
        }
    }
}