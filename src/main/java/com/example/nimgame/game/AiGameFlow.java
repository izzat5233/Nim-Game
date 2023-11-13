package com.example.nimgame.game;

import com.example.nimgame.game.ai.AiPlayer;
import javafx.concurrent.Task;

public class AiGameFlow extends GameFlow
        implements TurnStatusListener {

    private static final long MINIMUM_DELAY_TIME = 1000;

    private final AiPlayer player;

    public AiGameFlow(Game game, AiPlayer player) {
        super(game);
        this.player = player;
        game.subscribeTurnStatus(this);
    }

    public Task<State> createAiTask() {
        return new Task<>() {
            @Override
            protected com.example.nimgame.game.State call() {
                long t1 = System.currentTimeMillis();
                var successor = player.getSuccessor(game.getCurrentState());
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
    public void onTurnStatusChange(TurnStatus status) {
        if (status == TurnStatus.SECOND_PLAYER_TURN) {
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
