package com.example.nimgame.game.player;

public enum Difficulty {
    NONE,
    EASY,
    MEDIUM,
    HARD,
    PERFECT;

    private Player player;

    synchronized Player defaultPlayer() {
        if (player == null) player = createPlayer();
        return player;
    }

    private Player createPlayer() {
        return switch (this) {
            case NONE -> null;
            case EASY -> new RandomPlayer();
            case MEDIUM -> new AiPlayer(new AiParameters(0, 400));
            case HARD -> new AiPlayer(new AiParameters(4, 600));
            case PERFECT -> new AiPlayer(new AiParameters(-1, 1000));
        };
    }
}
