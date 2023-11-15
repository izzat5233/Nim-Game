package com.example.nimgame.fxml;

import com.example.nimgame.Launcher;
import com.example.nimgame.fxml.object.Pile;
import com.example.nimgame.fxml.object.PileSelectionListener;
import com.example.nimgame.game.Game;
import com.example.nimgame.game.Move;
import com.example.nimgame.game.Status;
import com.example.nimgame.game.StatusListener;
import com.example.nimgame.game.ai.AiPlayer;
import com.example.nimgame.game.ai.Difficulty;
import com.example.nimgame.game.flow.AiGameFlow;
import com.example.nimgame.game.position.ClassicPosition;
import com.example.nimgame.game.position.MiserePosition;
import com.example.nimgame.game.position.Position;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class MapController
        implements Initializable, PileSelectionListener, StatusListener {

    @FXML
    public AnchorPane mapRoot;

    @FXML
    public VBox pilesContainer;

    @FXML
    public Button playButton, restartButton;

    @FXML
    public Label labelPlayerTurn;

    @FXML
    public RadioButton
            radioButtonEasy,
            radioButtonMedium,
            radioButtonHard,
            radioButtonPerfect,
            radioButtonClassic,
            radioButtonMisere;

    ArrayList<Row> rows;

    Image stoneImage;

    AiGameFlow gameFlow;

    Difficulty difficulty;

    int misereGameRows = 7;

    HashMap<Integer, Integer> countMap, rowMap;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        stoneImage = new Image(String.valueOf(Launcher.class.getResource("assets/items/stone1.png")));
        playButton.setOnAction(e -> play());
        restartButton.setOnAction(e -> restart());
        pilesContainer.setFillWidth(true);
        pilesContainer.setSpacing(20);
        radioButtonEasy.setOnAction(e -> restart());
        radioButtonMedium.setOnAction(e -> restart());
        radioButtonHard.setOnAction(e -> restart());
        radioButtonPerfect.setOnAction(e -> restart());
        radioButtonClassic.setOnAction(e -> restart());
        radioButtonMisere.setOnAction(e -> restart());
        restart();
    }

    private void play() {
        for (var row : rows) {
            var i = row.pile.getSelectedAmount();
            if (i > 0) {
                var j = rowMap.get(row.pile.getAmount());
                gameFlow.play(new Move(j, i));
                break;
            }
        }
    }

    private Position miserePosition() {
        var counts = new ArrayList<Integer>();
        for (int i = 0; i < misereGameRows; i++) counts.add(2 * i + 1);
        return new MiserePosition(counts);
    }

    private Position classicPosition() {
        var counts = new ArrayList<>(List.of(15));
        return new ClassicPosition(counts);
    }

    private AiGameFlow gameFlow() {
        var position = radioButtonClassic.isSelected() ? classicPosition() : miserePosition();
        return new AiGameFlow(new Game(position), new AiPlayer(difficulty));
    }

    private void restart() {
        setDifficulty();
        gameFlow = gameFlow();
        gameFlow.getGame().subscribe(this);
        displayGame();
    }

    private void setDifficulty() {
        if (radioButtonEasy.isSelected()) {
            difficulty = Difficulty.EASY;
        } else if (radioButtonMedium.isSelected()) {
            difficulty = Difficulty.MEDIUM;
        } else if (radioButtonHard.isSelected()) {
            difficulty = Difficulty.HARD;
        } else {
            difficulty = Difficulty.PERFECT;
        }
    }

    private void processGame() {
        countMap = new HashMap<>();
        rowMap = new HashMap<>();
        var counts = gameFlow.getGame().getPosition().getCounts();
        for (int i = 0; i < counts.size(); i++) {
            var pile = counts.get(i);
            rowMap.putIfAbsent(pile, i);
            countMap.put(pile, countMap.getOrDefault(pile, 0) + 1);
        }
    }

    private void displayGame() {
        processGame();
        rows = new ArrayList<>();
        for (var i : countMap.keySet()) {
            var pile = new Pile(new FlowPane(), stoneImage, i);
            pile.subscribe(this);
            rows.add(new Row(pile, countMap.get(i)));
        }
        pilesContainer.getChildren().setAll(rows);
    }

    @Override
    public void onPileSelectionIncrement(Pile pile) {
        rows.forEach(i -> {
            if (i.pile != pile) i.pile.deselectAll();
        });
    }

    @Override
    public void onStatusChange(Status status) {
        labelPlayerTurn.setText(status.toString());
        displayGame();
    }

    static class Row extends HBox {
        final Pile pile;
        final Label label = new Label();

        Row(Pile pile, int count) {
            this.pile = pile;
            label.setStyle("-fx-font-weight: bold; -fx-label-padding: 5; -fx-text-fill: black");
            label.setMaxWidth(Double.MAX_VALUE);
            label.setFont(Font.font("Cambria", 20));
            if (count > 1) label.setText(String.valueOf(count));
            getChildren().addAll(label, pile.getPane());
            HBox.setHgrow(label, Priority.ALWAYS);
        }
    }
}
