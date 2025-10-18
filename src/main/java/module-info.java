module org.example.eventregapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.eventregapp to javafx.fxml;
    exports org.example.eventregapp;
}