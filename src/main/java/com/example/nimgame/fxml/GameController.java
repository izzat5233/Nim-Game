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
import com.example.nimgame.game.flow.GameFlow;
import com.example.nimgame.game.position.ClassicPosition;
import com.example.nimgame.game.position.MiserePosition;
import com.example.nimgame.game.position.Position;
import com.example.nimgame.game.position.Version;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class GameController
        implements PileSelectionListener, StatusListener {

    private static final Image ITEM_IMAGE =
            new Image(String.valueOf(Launcher.class.getResource("assets/items/stone1.png")));

    private static final int misereGameRows = 4;

    private final Pane pileContainer;

    private Difficulty difficulty;

    private Version version;

    private GameFlow gameFlow;

    private ArrayList<Pile> piles;

    public GameController(Pane pileContainer, Difficulty difficulty, Version version) {
        this.pileContainer = pileContainer;
        this.difficulty = difficulty;
        this.version = version;
        refresh();
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
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
        var pos = version == Version.MISERE ? getMiserePosition() : getClassicPosition();
        var game = new Game(pos);
        game.subscribe(this);
        if (difficulty == Difficulty.NONE) {
            gameFlow = new GameFlow(game);
        } else {
            gameFlow = new AiGameFlow(game, new AiPlayer(difficulty));
        }
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

    private Position getMiserePosition() {
        var counts = new ArrayList<Integer>();
        for (int i = 0; i < misereGameRows; i++) counts.add(2 * i + 1);
        return new MiserePosition(counts);
    }

    private Position getClassicPosition() {
        var counts = new ArrayList<>(List.of(15));
        return new ClassicPosition(counts);
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
