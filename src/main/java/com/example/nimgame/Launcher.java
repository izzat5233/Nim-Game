package com.example.nimgame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Launcher extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        var loader = new FXMLLoader(Launcher.class.getResource("fxml/menu.fxml"));
        var scene = new Scene(loader.load(), 783, 573);
        stage.setTitle("Nim Game");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}