package com.example.nimgame.game.player;

public enum Difficulty {
    NONE,
    EASY,
    MEDIUM,
    HARD,
    PERFECT;

    Player getDefaultPlayer() {
        return switch (this) {
            case NONE -> null;
            case EASY -> new RandomPlayer();
            case MEDIUM -> new AiPlayer(new AiParameters(0, 400));
            case HARD -> new AiPlayer(new AiParameters(4, 600));
            case PERFECT -> new AiPlayer(new AiParameters(-1, 1000));
        };
    }
}
