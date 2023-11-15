package com.example.nimgame.game.flow;

import com.example.nimgame.game.Game;
import com.example.nimgame.game.Status;
import com.example.nimgame.game.StatusListener;
import com.example.nimgame.game.position.Position;
import com.example.nimgame.game.ai.AiPlayer;
import javafx.concurrent.Task;

public class AiGameFlow extends GameFlow
        implements StatusListener {

    private final AiPlayer player;

    private final Status playOnStatus;

    public AiGameFlow(Game game, AiPlayer player, boolean isAiSecondPlayer) {
        super(game);
        game.subscribe(this);
        this.player = player;
        this.playOnStatus = isAiSecondPlayer ?
                Status.SECOND_PLAYER_TURN : Status.FIRST_PLAYER_TURN;
    }

    public AiGameFlow(Game game, AiPlayer player) {
        this(game, player, true);
    }

    public Task<Position> createAiTask() {
        return new Task<>() {
            @Override
            protected Position call() {
                long t1 = System.currentTimeMillis();
                var successor = player.getSuccessor(game.getPosition());
                long t2 = System.currentTimeMillis() - t1;

                try {
                    var delay = player.getDifficulty().getMinimumDelayTime();
                    Thread.sleep(Math.max(delay - t2, 0));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                return successor;
            }
        };
    }

    @Override
    public void onStatusChange(Status status) {
        if (status == playOnStatus) {
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
