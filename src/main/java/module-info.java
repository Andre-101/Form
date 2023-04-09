module com.example.demo.control {

    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires okhttp3;
    requires org.json;
    requires com.fasterxml.jackson.databind;
    requires javafx.graphics;


    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
    exports com.example.demo.control;
    opens com.example.demo.control to javafx.fxml;
}
