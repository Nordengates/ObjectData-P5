package ObjectData_app.ObjectData_controller;

import ObjectData_app.ObjectData_model.ExcursionModel;
import ObjectData_app.ObjectData_controller.ExcursionController;
import ObjectData_app.ObjectData_view.MensajeControllerView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class ExcursionController {

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
    @FXML
    Button btCancelar;

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
        RespView.tituloDeLaFuncion("-- FORMULARIO PARA CREAR EXCURSIONES --");

        // Pedimos la descripcion de la excursión
        String descripcionExcursion = tfNombreExcursion.getText();
        if (descripcionExcursion.isEmpty()) {
            RespView.respuestaControllerView("Operación cancelada.");
            // AppController.gestionExcursiones();
        }
        // Pedimos la fecha de excursión y comprobamos el dato.
        do {
            String retorno = tfFechaExcursion.getText();
            if (retorno.isEmpty()) {
                RespView.respuestaControllerView("Operación cancelada.");
                // AppController.gestionExcursiones();
            } else if (!retorno.matches("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}$")) {
                RespView.excepcionesControllerView(
                        "Las fechas y horas introducidas no son válidas. Formato esperado: yyyy-MM-dd HH:mm");
                continue;
            }
            try {
                fecha = sdf.parse(retorno);
                comprobadoOk = true;
            } catch (ParseException e) {
                RespView.excepcionesControllerView("Error al parsear la fecha: " + e.getMessage());
                continue;
            }
        } while (!comprobadoOk);
        comprobadoOk = false;
        // Pedimos el numero de dias y comprobamos el dato.
        do {
            String retorno = tfNumDias.getText();
            if (retorno.isEmpty()) {
                RespView.respuestaControllerView("Operación cancelada.");
                // AppController.gestionExcursiones();
            } else if (retorno.matches("\\d+")) { // Verifica si el retorno es un número entero
                numeroDias = Integer.parseInt(retorno);
                comprobadoOk = true;
                continue;
            } else {
                // Si no es un número entero, muestra un mensaje de error
                RespView.excepcionesControllerView("El número de excursión debe ser un numero entero.");
                continue;
            }
        } while (!comprobadoOk);
        comprobadoOk = false;
        do {
            String retorno = tfPrecioInscripcion.getText();
            if (retorno.isEmpty()) {
                RespView.respuestaControllerView("Operación cancelada.");
                // AppController.gestionExcursiones();
            } else if (retorno.matches("\\d+(\\.\\d+)?")) { // Verifica si el retorno es un número entero o double
                precio = Double.parseDouble(retorno);
                comprobadoOk = true;
                continue;
            } else {
                // Si no es un número entero, muestra un mensaje de error
                RespView.excepcionesControllerView("El precio debe ser un número válido.");
                continue;
            }
        } while (!comprobadoOk);
        comprobadoOk = false;
        // Método para generar un numeroExcursion aleatorio
        int numeroExcursion = Integer.parseInt("1" + generarID()); // numeroExcursion
        // Mandamos el numeroExcursion a la pantalla:
        // RespView.respuestaControllerView("- Número de excursion generada: " +
        // numeroExcursion);
        // Se genera el conjunto de en la variable excursion
        ExcursionModel excursion = new ExcursionModel(numeroExcursion, descripcionExcursion, fecha, numeroDias, precio);
        // Se llama al metodo crearExcursion del modelo ExcursionModel, se pasa tanto la
        // instancia como el objeto creado
        try {
            respuesta = excursion.crearExcursionModel(excursion);
        } catch (Exception e) {
            RespView.excepcionesControllerView(e.getMessage());
        }
        // Devuelvo la respuesta del modelo y la imprimo en la vista
        // RespView.respuestaControllerView(respuesta);
        // Al finalizar vuelvo al menu de excursiones
        // AppController.gestionExcursiones();
    }

    public static void mostrarExcursionFecha() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        boolean finalizar = false;
        do {
            String[] retorno = ExcuView.menuMostarExcursionFechaView();
            // Se comprueba si el usuario queire salir
            if (retorno[0].equals("") || retorno[1].equals("")) {
                RespView.respuestaControllerView("Operación cancelada.");
                // AppController.gestionExcursiones();
                break;
            }
            // Se intenta transformar las fechas y se printan.
            try {
                Date fechaInicio = sdf.parse(retorno[0]);
                Date fechaFin = sdf.parse(retorno[1]);
                String respuesta = ExcursionModel.mostrarExcursiones(fechaInicio, fechaFin);
                RespView.respuestaControllerView(respuesta);
                finalizar = true;
            } catch (Exception e) {
                RespView.excepcionesControllerView("Formato de fecha incorrecto. Debe ser yyyy-MM-dd");
            }
        } while (!finalizar);
        // Al finalizar vuelvo al menu de excursiones
        // AppController.gestionExcursiones();
    }
}