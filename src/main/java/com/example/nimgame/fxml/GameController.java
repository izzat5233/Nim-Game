package com.example.nimgame.fxml;

import com.example.nimgame.Launcher;
import com.example.nimgame.fxml.object.Pile;
import com.example.nimgame.fxml.object.PileSelectionListener;
import com.example.nimgame.game.GameFactory;
import com.example.nimgame.game.Move;
import com.example.nimgame.game.Status;
import com.example.nimgame.game.StatusListener;
import com.example.nimgame.game.flow.GameFlow;
import com.example.nimgame.game.player.Difficulty;
import com.example.nimgame.game.position.Version;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class GameController
        implements PileSelectionListener, StatusListener {

    public static final Image ITEM_IMAGE =
            new Image(String.valueOf(Launcher.class.getResource("assets/items/stone1.png")));

    private final Pane pileContainer;

    private final StatusController statusController;

    private Difficulty difficulty;

    private Version version;

    private GameFlow gameFlow;

    private ArrayList<Pile> piles;

    public GameController(Pane pileContainer, StatusController statusController, Difficulty difficulty, Version version) {
        this.pileContainer = pileContainer;
        this.statusController = statusController;
        setDifficulty(difficulty);
        setVersion(version);
        refresh();
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
        statusController.setMultiPlayer(difficulty == Difficulty.NONE);
        refresh();
    }

    public void setVersion(Version version) {
        this.version = version;
        refresh();
    }

    public void refresh() {
        initialize();
        display();
    }

    private void initialize() {
        gameFlow = GameFactory.start(difficulty, version, this, statusController);
        statusController.setStatus(gameFlow.getGame().getStatus());
    }

    private void display() {
        piles = new ArrayList<>();
        pileContainer.getChildren().clear();
        for (var i : gameFlow.getGame().getPosition().getCounts()) {
            var pile = new Pile(new FlowPane(), ITEM_IMAGE, i);
            pile.subscribe(this);
            piles.add(pile);
            pileContainer.getChildren().add(pile.getPane());
        }
    }

    public void play() {
        for (int i = 0; i < piles.size(); i++) {
            var amount = piles.get(i).getSelectedAmount();
            if (amount > 0) {
                gameFlow.play(new Move(i, amount));
                break;
            }
        }
    }

    @Override
    public void onPileSelectionIncrement(Pile pile) {
        piles.forEach(i -> {
            if (i != pile) i.deselectAll();
        });
    }

    @Override
    public void onStatusChange(Status status) {
        display();
    }
}
