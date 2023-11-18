package com.example.nimgame.fxml;

import com.example.nimgame.ResourceFactory;
import com.example.nimgame.game.Status;
import com.example.nimgame.game.StatusListener;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashMap;

public class StatusController
        implements StatusListener {

    private final HashMap<String, Image> images = new HashMap<>();

    private final ImageView statusImageView;

    private boolean isMultiPlayer;

    public StatusController(ImageView statusImageView, boolean isMultiPlayer) {
        this.statusImageView = statusImageView;
        this.isMultiPlayer = isMultiPlayer;
    }

    public void setMultiPlayer(boolean isMultiPlayer) {
        this.isMultiPlayer = isMultiPlayer;
    }

    @Override
    public void onStatusChange(Status status) {
        setStatus(status);
    }

    public void setStatus(Status status) {
        var key = imageName(status);
        images.putIfAbsent(key, ResourceFactory.getTextImage(key));
        this.statusImageView.setImage(images.get(key));
    }

    private String imageName(Status status) {
        return switch (status) {
            case FIRST_PLAYER_TURN -> isMultiPlayer ? "Player-1-Turn" : "Your-Turn";
            case SECOND_PLAYER_TURN -> isMultiPlayer ? "Player-2-Turn" : "Computers-Turn";
            case FIRST_PLAYER_WON -> isMultiPlayer ? "Player-1-Wins" : "You-Win";
            case SECOND_PLAYER_WON -> isMultiPlayer ? "Player-2-Wins" : "Computer-Wins";
            case MOVE_NOT_ALLOWED -> "Not-Allowed";
        } + ".png";
    }
}
