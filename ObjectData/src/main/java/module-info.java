// Improved module-info.java file
module ObjectData_app {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires javafx.graphics;

    // Instead of exporting to javafx.fxml, export to all modules
    exports ObjectData_app.ObjectData_controller;
    exports ObjectData_app.ObjectData_model;

    // Open packages to javafx.fxml for reflection
    opens ObjectData_app.ObjectData_controller to javafx.fxml;
    
}