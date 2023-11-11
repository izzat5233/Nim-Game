package com.example.nimgame.game.ai;

public enum Difficulty {
    EASY(0),
    MEDIUM(2),
    HARD(-1);

    private final int depth;

    Difficulty(int depth) {
        this.depth = depth;
    }

    public int getDepth() {
        return depth;
    }
}
