package com.example.nimgame;

import com.example.nimgame.fxml.GameController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Launcher extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        var loader = new FXMLLoader(Launcher.class.getResource("fxml/map.fxml"));
        var scene = new Scene(loader.load());
        stage.getIcons().add(GameController.ITEM_IMAGE);
        stage.setTitle("Nim Game");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}