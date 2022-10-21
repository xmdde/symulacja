module com.example.symulacja {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.symulacja to javafx.fxml;
    exports com.example.symulacja;
}