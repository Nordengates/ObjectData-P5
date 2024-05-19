//Este archivo es el que busca el programa al iniciar la aplicacion
//por primera vez, en este archivo tenemos la clase Main que contiene
//la llamada al metodo menuInicio del controlador.
package ObjectData_app.ObjectData_controller;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        AppController app = new AppController();
        app.AppWindowsView();
    }

    public static void main(String[] args) {
        launch(args);
    }
}