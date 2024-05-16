package ObjectData_app.ObjectData_view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;

//Eventos posibles para la alerta ERROR INFORMATION WARNING CONFIRMATION

public class NotificacionView {
    @FXML
    public static void Notificacion(String tipo, String titulo, String contenido) {
        Alert.AlertType alertType = Alert.AlertType.valueOf(tipo.toUpperCase());
        Alert alert = new Alert(alertType);
        alert.setHeaderText(null);
        alert.setTitle(titulo);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}
