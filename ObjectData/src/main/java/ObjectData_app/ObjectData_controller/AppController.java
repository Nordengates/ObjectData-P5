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
            Parent root = FXMLLoader.load(getClass().getResource("/ObjectData_app/ObjectData_view/AppWindowsView.fxml"));
            this.stage.setScene(new Scene(root));
            this.stage.setTitle("ObjectData Aplicación V1.0");
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
        FXMLLoader("/ObjectData_app/ObjectData_view/SocioView/nuevoSocioInfantilFXMLLoader.fxml");
    }

    @FXML
    public void mostrarSociosFXMLLoader() {
        FXMLLoader("/ObjectData_app/ObjectData_view/SocioView/mostrarSociosFXMLLoader.fxml");
    }

    @FXML
    public void mostrarSocioEstandarFXMLLoader() {
        FXMLLoader("/ObjectData_app/ObjectData_view/SocioView/mostrarSocioEstandarFXMLLoader.fxml");
    }

    @FXML
    public void mostrarSocioFederadoFXMLLoader() {
        FXMLLoader("/ObjectData_app/ObjectData_view/SocioView/mostrarSocioFederadoFXMLLoader.fxml");
    }

    @FXML
    public void mostrarSocioInfantilFXMLLoader() {
        FXMLLoader("/ObjectData_app/ObjectData_view/SocioView/mostrarSocioInfantilFXMLLoader.fxml");
    }

    @FXML
    public void eliminarSocioFXMLLoader() {
        FXMLLoader("/ObjectData_app/ObjectData_view/SocioView/eliminarSocioFXMLLoader.fxml");
    }

    @FXML
    public void modificarSeguroFXMLLoader() {
        FXMLLoader("/ObjectData_app/ObjectData_view/SocioView/modificarSeguroFXMLLoader.fxml");
    }

    @FXML
    public void mostrarFacturacionSocioFXMLLoader() {
        FXMLLoader("/ObjectData_app/ObjectData_view/SocioView/mostrarFacturacionSocioFXMLLoader.fxml");
    }

    // Estos metodos cargan los FXML cuyo controlador es InscripcionController
    // (Gestión Inscripciones)
    @FXML
    public void nuevaInscripcionFXMLLoader() {
        FXMLLoader("/ObjectData_app/ObjectData_view/InscripcionView/nuevaInscripcionFXMLLoader.fxml");
    }

    @FXML
    public void mostrarInscripcionFechaFXMLLoader() {
        FXMLLoader("/ObjectData_app/ObjectData_view/InscripcionView/mostrarInscripcionFechaFXMLLoader.fxml");
    }

    @FXML
    public void mostrarInscripcionSocioFXMLLoader() {
        FXMLLoader("/ObjectData_app/ObjectData_view/InscripcionView/mostrarInscripcionSocioFXMLLoader.fxml");
    }

    @FXML
    public void eliminarInscripcionFXMLLoader() {
        FXMLLoader("/ObjectData_app/ObjectData_view/InscripcionView/eliminarInscripcionFXMLLoader.fxml");
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
