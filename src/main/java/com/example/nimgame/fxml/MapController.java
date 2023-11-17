package com.example.nimgame.fxml;

import com.example.nimgame.game.ai.Difficulty;
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

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MapController
        implements Initializable {

    @FXML
    public AnchorPane
            mapRoot,
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
            ibExit,
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
        gameController = new GameController(pilesContainer, Difficulty.EASY, Version.CLASSIC);
        ibStart.setOnMouseClicked(e -> gameController.play());
        ibExit.setOnMouseClicked(e -> Platform.exit());
        difficultyGroup = new RadioGroup(e -> setDifficulty(), abFreePlay, abEasy, abMedium, abHard, abPerfect);
        versionGroup = new RadioGroup(e -> setVersion(), abClassic, abMisere);
    }

    private void setDifficulty() {
        var selected = difficultyGroup.selected;
        Difficulty difficulty;
        if (selected == abEasy) {
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
        Version version;
        if (selected == abClassic) {
            version = Version.CLASSIC;
        } else {
            version = Version.MISERE;
        }
        gameController.setVersion(version);
    }

    static class RadioGroup {
        final ArrayList<Node> buttons = new ArrayList<>();

        final ColorAdjust brighter = new ColorAdjust(), darker = new ColorAdjust();

        final ToggleGroup group = new ToggleGroup();

        AnchorPane selected = null;

        RadioGroup(EventHandler<MouseEvent> onSelection, AnchorPane... buttons) {
            this.buttons.addAll(List.of(buttons));
            brighter.setBrightness(0);
            darker.setBrightness(-0.5);
            for (var pane : buttons) {
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
    }
}
