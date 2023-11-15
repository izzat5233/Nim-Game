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
import com.example.nimgame.game.position.MiserePosition;
import com.example.nimgame.game.position.Position;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
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
    public RadioButton radioButtonEasy, radioButtonMedium, radioButtonHard, radioButtonPerfect;

    ArrayList<Row> rows;

    Image stoneImage;

    AiGameFlow gameFlow;

    Difficulty difficulty;

    int gameRows;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        stoneImage = new Image(String.valueOf(Launcher.class.getResource("assets/items/stone1.png")));
        playButton.setOnAction(e -> play());
        restartButton.setOnAction(e -> restart());
        pilesContainer.setFillWidth(true);
        pilesContainer.setSpacing(20);
        // System.out.println("Rows:");
        // gameRows = new Scanner(System.in).nextInt();
        gameRows = 5;
        restart();
    }

    private void play() {
        for (int i = 0; i < rows.size(); i++) {
            var pile = rows.get(i).pile;
            if (pile.getSelectedAmount() > 0) {
                gameFlow.play(new Move(i, pile.getSelectedAmount()));
                break;
            }
        }
    }

    private Position position() {
        var counts = new ArrayList<Integer>();
        for (int i = 0; i < gameRows; i++) counts.add(2 * i + 1);
        return new MiserePosition(counts);
    }

    private AiGameFlow gameFlow() {
        return new AiGameFlow(new Game(position()), new AiPlayer(difficulty));
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

    private void displayGame() {
        rows = new ArrayList<>();
        var position = gameFlow.getGame().getPosition();
        for (var count : position.getCounts()) {
            var pile = new Pile(new FlowPane(), stoneImage, count);
            pile.subscribe(this);
            var row = new Row(pile);
            rows.add(row);
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

    static class Row extends HBox
            implements PileSelectionListener {
        final Pile pile;
        final Label counter;

        Row(Pile pile) {
            this.pile = pile;
            this.pile.subscribe(this);
            this.counter = new Label("Selected: 0");
            getChildren().addAll(pile.getPane());
        }

        @Override
        public void onPileSelectionChange(Pile pile) {
            counter.setText("Selected: " + pile.getSelectedAmount());
        }
    }
}
