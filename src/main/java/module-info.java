module group.password_generator {
    requires javafx.controls;
    requires javafx.fxml;


    opens group.password_generator to javafx.fxml;
    exports group.password_generator;
}