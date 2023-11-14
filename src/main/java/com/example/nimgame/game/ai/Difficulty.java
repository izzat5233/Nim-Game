package com.example.nimgame.game.ai;

public enum Difficulty {
    EASY(true, 0),
    MEDIUM(false, 0),
    HARD(false, 4),
    PERFECT(false, -1),
    ;

    private final boolean random;

    private final int searchDepth;

    Difficulty(boolean random, int searchDepth) {
        this.random = random;
        this.searchDepth = searchDepth;
    }

    public boolean isRandom() {
        return random;
    }

    public int getSearchDepth() {
        return searchDepth;
    }
}
