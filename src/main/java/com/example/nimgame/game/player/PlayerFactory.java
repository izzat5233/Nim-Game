package com.example.nimgame.game.player;

public interface PlayerFactory {

    static Player usePlayer(Difficulty difficulty) {
        return difficulty.defaultPlayer();
    }

    static Player customAiPlayer(AiParameters parameters) {
        return new AiPlayer(parameters);
    }
}
