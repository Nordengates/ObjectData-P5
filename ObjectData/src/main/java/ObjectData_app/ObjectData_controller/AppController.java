package ObjectData_app.ObjectData_controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.IOException;

public class AppController {

    @FXML
    private BorderPane mainContainer;
    private Stage stage;

    @FXML
    public void AppWindowsView() {
        try {
            this.stage = new Stage();
            Parent root = FXMLLoader
                    .load(getClass().getResource("/ObjectData_app/ObjectData_view/AppWindowsView.fxml"));
            this.stage.setScene(new Scene(root));
            this.stage.setTitle("ObjectData Aplicación V1.0");
            this.stage.setResizable(false);
            this.stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void FXMLLoader(String file) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(file));
            Parent formulario = loader.load();
            mainContainer.setCenter(formulario);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Estos metodos cargan los FXML cuyo controlador es SocioController (Gestión
    // Socios)
    @FXML
    public void nuevoSocioEstandarFXMLLoader() {
        FXMLLoader("/ObjectData_app/ObjectData_view/SocioView/nuevoSocioEstandarFXMLLoader.fxml");
    }

    @FXML
    public void nuevoSocioFederadoFXMLLoader() {
        FXMLLoader("/ObjectData_app/ObjectData_view/SocioView/nuevoSocioFederadoFXMLLoader.fxml");
    }

    @FXML
    public void nuevoSocioInfantilFXMLLoader() {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/ObjectData_app/ObjectData_view/SocioView/nuevoSocioInfantilFXMLLoader.fxml"));
        try {
            Parent formulario = loader.load();
            mainContainer.setCenter(formulario);
            // Obtener el controlador del formulario cargado
            SocioController controller = loader.getController();
            // Llamar al método cargarLosSociosEnTabla() en el controlador para cargar los
            // socios
            controller.cargarLosSociosEnTabla();
            // Llamar al método filtrarSocioPorNumeroEnTabla() en el controlador
            controller.filtrarSocioPorNumeroEnTabla();
            //controller.crearSocioInfantil();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void mostrarSociosFXMLLoader() {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/ObjectData_app/ObjectData_view/SocioView/mostrarSociosFXMLLoader.fxml"));
        try {
            Parent formulario = loader.load();
            mainContainer.setCenter(formulario);
            // Obtener el controlador del formulario cargado
            SocioController controller = loader.getController();
            // Llamar al método cargarLosSociosEnTabla() en el controlador para cargar los
            // socios
            controller.cargarLosSociosEnTabla();
            // Llamar al método filtrarSocioPorTipoEnTabla() en el controlador para cargar
            // los filtros
            controller.filtrarSocioPorTipoEnTabla();
            // Mostrar el formulario
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void eliminarSocioFXMLLoader() {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/ObjectData_app/ObjectData_view/SocioView/eliminarSocioFXMLLoader.fxml"));
        try {
            Parent formulario = loader.load();
            mainContainer.setCenter(formulario);
            // Obtener el controlador del formulario cargado
            SocioController controller = loader.getController();
            // Llamar al método cargarLosSociosEnTabla() en el controlador para cargar los
            // socios
            controller.cargarLosSociosEnTabla();
            // Llamar al método filtrarSocioPorNumeroEnTabla() en el controlador
            controller.filtrarSocioPorNumeroEnTabla();
            // Funcion eliminar socio:
            controller.accionEliminarSocio();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void modificarSeguroFXMLLoader() {
        FXMLLoader("/ObjectData_app/ObjectData_view/SocioView/modificarSeguroFXMLLoader.fxml");
    }

    @FXML
    public void mostrarFacturacionSocioFXMLLoader() {
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/ObjectData_app/ObjectData_view/SocioView/mostrarFacturacionSocioFXMLLoader.fxml"));
        try {
            Parent formulario = loader.load();
            mainContainer.setCenter(formulario);
            // Obtener el controlador del formulario cargado
            SocioController controller = loader.getController();
            // Llamar al método cargarLosSociosEnTabla() en el controlador para cargar los socios
            controller.cargarLosSociosEnTabla();
            // Llamar al método filtrarSocioPorNumeroEnTabla() en el controlador
            controller.filtrarSocioPorNumeroEnTabla();
            // Metodo para facturación
            controller.facturaMensualSocio();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Estos metodos cargan los FXML cuyo controlador es InscripcionController
    // (Gestión Inscripciones)
    @FXML
    public void nuevaInscripcionFXMLLoader() {
        // Cargar el archivo FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ObjectData_app/ObjectData_view/InscripcionView/nuevaInscripcionFXMLLoader.fxml"));
        try {
            Parent root = loader.load();
            // Obtener el controlador del formulario cargado
            InscripcionController inscripcionController = loader.getController();
            //Cargamos los socios en la tabla
            inscripcionController.cargarLosSociosEnTabla();
            //Cargamos el filtro
            inscripcionController.filtrarSocioPorNumeroEnTabla();
            // Llamar al método crearInscripcion() en el controlador
            inscripcionController.crearInscripcion();
            // Establecer el contenido cargado en el centro del BorderPane
            mainContainer.setCenter(root);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void mostrarInscripcionFechaFXMLLoader() {
        FXMLLoader("/ObjectData_app/ObjectData_view/InscripcionView/mostrarInscripcionFechaFXMLLoader.fxml");
    }

    @FXML
    public void mostrarInscripcionSocioFXMLLoader() {
        // Cargar el archivo FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ObjectData_app/ObjectData_view/InscripcionView/mostrarInscripcionSocioFXMLLoader.fxml"));
        try {
            Parent root = loader.load();
            // Establecer el contenido cargado en el centro del BorderPane
            mainContainer.setCenter(root);
            // Obtener el controlador del formulario cargado
            InscripcionController inscripcionController = loader.getController();
            //Cargamos los socios en la tabla
            inscripcionController.cargarLosSociosEnTabla();
            //Cargamos el filtro
            inscripcionController.filtrarSocioPorNumeroEnTabla();
            //Funcion para cargar inscripciones
            inscripcionController.obtenerInscripciones();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void eliminarInscripcionFXMLLoader() {
        FXMLLoader("/ObjectData_app/ObjectData_view/InscripcionView/eliminarInscripcionFXMLLoader.fxml");
        // Cargar el archivo FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ObjectData_app/ObjectData_view/InscripcionView/mostrarInscripcionSocioFXMLLoader.fxml"));
        try {
            Parent root = loader.load();
            // Establecer el contenido cargado en el centro del BorderPane
            mainContainer.setCenter(root);
            // Obtener el controlador del formulario cargado
            InscripcionController inscripcionController = loader.getController();
            //Cargamos los socios en la tabla
            inscripcionController.cargarLosSociosEnTabla();
            //Cargamos el filtro
            inscripcionController.filtrarSocioPorNumeroEnTabla();
            //Funcion para cargar inscripciones
            inscripcionController.obtenerInscripciones();
        } catch (IOException e) {
            e.printStackTrace();
        }
        actionEliminarInscripcion();
    }

    // Estos metodos cargan los FXML cuyo controlador es ExcursionController
    // (Gestión Excursiones)
    @FXML
    public void nuevaExcursionFXMLLoader() {
        FXMLLoader("/ObjectData_app/ObjectData_view/ExcursionView/nuevaExcursionFXMLLoader.fxml");
    }

    @FXML
    public void mostrarExcursionFechaFXMLLoader() {
        FXMLLoader("/ObjectData_app/ObjectData_view/ExcursionView/mostrarExcursionFechaFXMLLoader.fxml");
    }

    // Cierre de la app usando el menu Salir
    @FXML
    public void exitApp() {
        System.exit(0);
    }
}
