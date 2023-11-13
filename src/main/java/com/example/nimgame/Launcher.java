package com.example.nimgame;

import com.example.nimgame.game.State;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Launcher extends Application {
    private static Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        Launcher.stage = stage;
        var loader = new FXMLLoader(Launcher.class.getResource("fxml/menu.fxml"));
        var scene = new Scene(loader.load(), 783, 573);
        stage.setTitle("Nim Game");
        stage.setScene(scene);
        stage.show();
    }

    public static Stage getStage() {
        return stage;
    }

    public static void main(String[] args) {
        launch();
    }
}