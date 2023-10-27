module com.example.nimgame {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.nimgame to javafx.fxml;
    exports com.example.nimgame;
}