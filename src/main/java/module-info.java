module com.example.nimgame {
    requires javafx.controls;
    requires javafx.fxml;


    exports com.example.nimgame;
    exports com.example.nimgame.fxml;
    exports com.example.nimgame.game;
    exports com.example.nimgame.game.player;
    exports com.example.nimgame.game.position;
    exports com.example.nimgame.game.flow;
    opens com.example.nimgame to javafx.fxml;
    opens com.example.nimgame.fxml to javafx.fxml;
    exports com.example.nimgame.fxml.object;
    opens com.example.nimgame.fxml.object to javafx.fxml;
}