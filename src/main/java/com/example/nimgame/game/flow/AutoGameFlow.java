package com.example.nimgame.game.flow;

import com.example.nimgame.game.Game;
import com.example.nimgame.game.Status;
import com.example.nimgame.game.StatusListener;
import com.example.nimgame.game.player.Player;
import com.example.nimgame.game.position.Position;
import javafx.concurrent.Task;

public class AutoGameFlow extends GameFlow
        implements StatusListener {

    private final Player player;

    private final Status playOnStatus;

    public AutoGameFlow(Game game, Player player, boolean isAutoPlayerSecondPlayer) {
        super(game);
        game.subscribe(this);
        this.player = player;
        this.playOnStatus = isAutoPlayerSecondPlayer ?
                Status.SECOND_PLAYER_TURN : Status.FIRST_PLAYER_TURN;
    }

    public AutoGameFlow(Game game, Player player) {
        this(game, player, true);
    }

    public Task<Position> createTask() {
        return new Task<>() {
            @Override
            protected Position call() {
                long t1 = System.currentTimeMillis();
                var successor = player.getSuccessor(game.getPosition());
                long t2 = System.currentTimeMillis() - t1;
                System.out.println("Move calculation time: " + t2 + " ms");

                try {
                    var delay = player.getMinimumDelayTime();
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
            var task = createTask();
            task.setOnSucceeded(e -> {
                var successor = task.getValue();
                game.unfreeze();
                super.play(successor);
            });
            new Thread(task).start();
        }
    }
}
