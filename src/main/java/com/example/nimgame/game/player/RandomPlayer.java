package com.example.nimgame.game.player;

import com.example.nimgame.game.position.Position;

import java.util.Random;

public class RandomPlayer
        implements Player {

    private Random random = null;

    RandomPlayer() {
    }

    @Override
    public Position getSuccessor(Position position) {
        if (random == null) random = new Random(System.nanoTime());
        var successors = position.getAllSuccessors();
        return successors.get(random.nextInt(successors.size()));
    }

    @Override
    public long getMinimumDelayTime() {
        return 100;
    }
}
