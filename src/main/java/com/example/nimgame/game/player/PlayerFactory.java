package com.example.nimgame.game.player;

import com.example.nimgame.game.position.Version;

import java.util.HashMap;

public final class PlayerFactory {

    private PlayerFactory() {
    }

    private record PlayerKey(
            Difficulty difficulty,
            Version version
    ) {
    }

    private static final HashMap<PlayerKey, Player> players = new HashMap<>();

    public static Player usePlayer(Difficulty difficulty, Version version) {
        var key = new PlayerKey(difficulty, version);
        players.putIfAbsent(key, difficulty.getDefaultPlayer());
        return players.get(key);
    }

    public static AiPlayer newAiPlayer(AiParameters parameters) {
        return new AiPlayer(parameters);
    }

    public static RandomPlayer newRandomPlayer() {
        return new RandomPlayer();
    }
}