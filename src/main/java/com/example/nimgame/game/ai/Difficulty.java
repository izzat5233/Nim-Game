package com.example.nimgame.game.ai;

public enum Difficulty {
    EASY(true, 0, 200),
    MEDIUM(false, 0, 400),
    HARD(false, 4, 600),
    PERFECT(false, -1, 1000),
    ;

    private final boolean random;

    private final int searchDepth;

    private final long minimumDelayTime;

    Difficulty(boolean random, int searchDepth, long minimumDelayTime) {
        this.random = random;
        this.searchDepth = searchDepth;
        this.minimumDelayTime = minimumDelayTime;
    }

    public boolean isRandom() {
        return random;
    }

    public int getSearchDepth() {
        return searchDepth;
    }

    public long getMinimumDelayTime() {
        return minimumDelayTime;
    }
}
