package com.example.nimgame.fxml;

import com.example.nimgame.Launcher;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;



public class MenuController {
    @FXML
    public ImageView imageButtonSinglePlayer, imageButtonMultiPlayer;

    @FXML
    public void onMouseClickButtonSinglePlayer() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("map.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 843, 434);
        Stage stage = new Stage();
        stage.setTitle("Games");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void onMouseClickButtonMultiPlayer() throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("Names.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 468, 346);
            Stage stage = new Stage();
            stage.setTitle("Players Name");
            stage.setScene(scene);
            stage.show();
    }

}
