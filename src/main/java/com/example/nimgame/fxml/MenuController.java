package com.example.nimgame.fxml;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class MenuController {
    @FXML
    public ImageView imageButtonSinglePlayer, imageButtonMultiPlayer;

    @FXML
    public void onMouseClickButtonSinglePlayer() {
        System.out.println("Single Player");
    }

    @FXML
    public void onMouseClickButtonMultiPlayer() {
        System.out.println("Multiplayer");
    }
}
