module com.example.nimgame {
    requires javafx.controls;
    requires javafx.fxml;


    exports com.example.nimgame;
    exports com.example.nimgame.fxml;
    opens com.example.nimgame to javafx.fxml;
    opens com.example.nimgame.fxml to javafx.fxml;
    exports com.example.nimgame.object;
    opens com.example.nimgame.object to javafx.fxml;
}