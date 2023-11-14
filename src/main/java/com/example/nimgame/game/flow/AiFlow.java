package com.example.nimgame.game.flow;

import com.example.nimgame.game.Game;
import com.example.nimgame.game.Status;
import com.example.nimgame.game.StatusListener;
import com.example.nimgame.game.Position;
import com.example.nimgame.game.ai.AiPlayer;
import javafx.concurrent.Task;

public class AiFlow extends GameFlow
        implements StatusListener {

    private static final long MINIMUM_DELAY_TIME = 1000;

    private final AiPlayer player;

    public AiFlow(Game game, AiPlayer player) {
        super(game);
        this.player = player;
        game.subscribe(this);
    }

    public Task<Position> createAiTask() {
        return new Task<>() {
            @Override
            protected Position call() {
                long t1 = System.currentTimeMillis();
                var successor = player.getSuccessor(game.getPosition());
                long t2 = System.currentTimeMillis() - t1;

                try {
                    Thread.sleep(Math.max(MINIMUM_DELAY_TIME - t2, 0));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                return successor;
            }
        };
    }

    @Override
    public void onStatusChange(Status status) {
        if (status == Status.SECOND_PLAYER_TURN) {
            game.freeze();
            var task = createAiTask();
            task.setOnSucceeded(e -> {
                var successor = task.getValue();
                game.unfreeze();
                super.play(successor);
            });
            new Thread(task).start();
        }
    }
}
