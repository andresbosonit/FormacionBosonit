module com.example.lotrjavafx {
    requires javafx.controls;
    requires javafx.fxml;
            
            requires com.dlsc.formsfx;
    requires lombok;

    opens com.example.lotrjavafx to javafx.fxml;
    exports com.example.lotrjavafx;
}