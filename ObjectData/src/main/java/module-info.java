// Improved module-info.java file
module ObjectData_app {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires javafx.graphics;
    exports ObjectData_app.ObjectData_controller;
    exports ObjectData_app.ObjectData_model;
    opens ObjectData_app.ObjectData_controller to javafx.fxml;
    opens ObjectData_app.ObjectData_model.ObjectData_Hibernate to org.hibernate.orm.core;
}