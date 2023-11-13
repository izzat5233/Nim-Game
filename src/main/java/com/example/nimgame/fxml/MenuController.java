package com.example.nimgame.fxml;

import com.example.nimgame.Launcher;
import com.example.nimgame.game.State;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class MenuController
        implements Initializable {
    private final FXMLLoader loader = new FXMLLoader();

    private Parent mapRoot = null, multiplayerRoot = null;

    private MapController mapController;

    private MultiplayerController multiplayerController;

    @FXML
    public AnchorPane menuRoot;

    @FXML
    public ImageView imageButtonSinglePlayer, imageButtonMultiPlayer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imageButtonSinglePlayer.setOnMouseClicked(e -> startSinglePlayer());
        imageButtonMultiPlayer.setOnMouseClicked(e -> startMultiPlayer());
    }

    private void loadMapController() throws IOException {
        if (mapRoot == null) {
            loader.setLocation(getClass().getResource("map.fxml"));
            mapRoot = loader.load();
            mapController = loader.getController();
        }
    }

    private void loadMultiPlayerController() throws IOException {
        if (multiplayerRoot == null) {
            loader.setLocation(getClass().getResource("Multiplayer.fxml"));
            multiplayerRoot = loader.load();
            multiplayerController = loader.getController();
        }
    }

    public void startSinglePlayer() {
        try {
            loadMapController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Launcher.getStage().setScene(new Scene(mapRoot));
    }

    public void startMultiPlayer() {
        try {
            loadMultiPlayerController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Launcher.getStage().setScene(new Scene(multiplayerRoot));
    }
}
