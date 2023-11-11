package com.example.nimgame.fxml;

import com.example.nimgame.Launcher;
import com.example.nimgame.game.AiGame;
import com.example.nimgame.game.ai.AiAgent;
import com.example.nimgame.game.ai.Difficulty;
import com.example.nimgame.object.Pile;
import com.example.nimgame.object.PileSelectionListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class MapController
        implements Initializable, PileSelectionListener {

    @FXML
    public AnchorPane mapRoot;

    @FXML
    public VBox pilesContainer;

    @FXML
    public Button playButton, restartButton;

    ArrayList<Row> rows;

    Image stoneImage;

    AiGame game;

    int gameRows;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        stoneImage = new Image(String.valueOf(Launcher.class.getResource("assets/items/stone1.png")));
        playButton.setOnAction(e -> play());
        restartButton.setOnAction(e -> restart());
        System.out.println("Rows:");
        gameRows = new Scanner(System.in).nextInt();
        restart();
    }

    private void displayGame() {
        rows = new ArrayList<>();
        var state = game.getCurrentState();
        for (var count : state.getCounts()) {
            var pile = new Pile(new HBox(), stoneImage, count);
            pile.subscribe(this);
            var row = new Row(pile);
            rows.add(row);
        }
        pilesContainer.getChildren().setAll(rows);
    }

    @Override
    public void onPileSelectionIncrement(Pile pile) {
        rows.forEach(i -> {
            if (i.pile != pile) {
                i.pile.deselectAll();
            }
        });
    }

    static class Row extends HBox
            implements PileSelectionListener {
        final Pile pile;
        final Label counter;

        Row(Pile pile) {
            this.pile = pile;
            this.pile.subscribe(this);
            this.counter = new Label("Selected: 0");
            getChildren().addAll(pile.getPane(), counter);
        }

        @Override
        public void onPileSelectionChange(Pile pile) {
            counter.setText("Selected: " + pile.getSelectedAmount());
        }
    }

    void play() {
        for (int i = 0; i < rows.size(); i++) {
            var pile = rows.get(i).pile;
            if (pile.getSelectedAmount() > 0) {
                System.out.println(game.play(i, pile.getSelectedAmount()));
                break;
            }
        }
        displayGame();
    }

    void restart() {
        game = new AiGame(gameRows, new AiAgent(Difficulty.HARD));
        displayGame();
    }
}
