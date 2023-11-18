package com.example.nimgame.fxml;

import com.example.nimgame.game.player.Difficulty;
import com.example.nimgame.game.position.Version;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MapController
        implements Initializable {

    @FXML
    public AnchorPane
            rootPane,
            titlePane,
            difficultyPane,
            versionPane,
            abFreePlay,
            abEasy,
            abMedium,
            abHard,
            abPerfect,
            abClassic,
            abMisere;

    @FXML
    public ImageView
            ibStart,
            ibPlay,
            ibBack,
            statusImageView,
            abiFreePlay,
            abiEasy,
            abiMedium,
            abiHard,
            abiPerfect,
            abiClassic,
            abiMisere;

    @FXML
    public VBox pilesContainer;

    RadioGroup difficultyGroup, versionGroup;

    GameController gameController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pilesContainer.setFillWidth(true);
        pilesContainer.setSpacing(20);
        difficultyGroup = new RadioGroup(e -> setDifficulty(), abFreePlay, abEasy, abMedium, abHard, abPerfect);
        versionGroup = new RadioGroup(e -> setVersion(), abClassic, abMisere);
        difficultyGroup.select(abFreePlay);
        versionGroup.select(abClassic);
        var statusController = new StatusController(statusImageView, true);
        gameController = new GameController(pilesContainer, statusController, Difficulty.NONE, Version.CLASSIC);
        ibStart.setOnMouseClicked(e -> start());
        ibPlay.setOnMouseClicked(e -> play());
        ibBack.setOnMouseClicked(e -> back());
        setGameVisible(false);
    }

    private void setGameVisible(boolean visible) {
        statusImageView.setVisible(visible);
        pilesContainer.setVisible(visible);
        ibBack.setVisible(visible);
        ibPlay.setVisible(visible);
        ibStart.setVisible(!visible);
        titlePane.setVisible(!visible);
        difficultyPane.setVisible(!visible);
        versionPane.setVisible(!visible);
    }

    private void start() {
        setGameVisible(true);
    }

    private void play() {
        gameController.play();
    }

    private void back() {
        setGameVisible(false);
    }

    private void setDifficulty() {
        var selected = difficultyGroup.selected;
        Difficulty difficulty;
        if (selected == abFreePlay) {
            difficulty = Difficulty.NONE;
        } else if (selected == abEasy) {
            difficulty = Difficulty.EASY;
        } else if (selected == abMedium) {
            difficulty = Difficulty.MEDIUM;
        } else if (selected == abHard) {
            difficulty = Difficulty.HARD;
        } else {
            difficulty = Difficulty.PERFECT;
        }
        gameController.setDifficulty(difficulty);
    }

    private void setVersion() {
        var selected = versionGroup.selected;
        gameController.setVersion(selected == abClassic ? Version.CLASSIC : Version.MISERE);
    }

    static class RadioGroup {
        final ArrayList<Node> panes = new ArrayList<>();

        final ColorAdjust brighter = new ColorAdjust(), darker = new ColorAdjust();

        final ToggleGroup group = new ToggleGroup();

        AnchorPane selected = null;

        RadioGroup(EventHandler<MouseEvent> onSelection, AnchorPane... panes) {
            this.panes.addAll(List.of(panes));
            brighter.setBrightness(0);
            darker.setBrightness(-0.5);
            for (var pane : panes) {
                var button = new ToggleButton();
                button.setToggleGroup(group);
                button.setVisible(false);
                pane.effectProperty().bind(Bindings
                        .when(button.selectedProperty())
                        .then(darker).otherwise(brighter));
                pane.getChildren().add(button);
                pane.setOnMouseClicked(e -> {
                    button.setSelected(true);
                    selected = pane;
                    onSelection.handle(e);
                });
            }
        }

        void select(AnchorPane pane) {
            for (var i : pane.getChildren()) {
                if (i instanceof ToggleButton button) {
                    button.setSelected(true);
                    selected = pane;
                    break;
                }
            }
        }
    }
}
