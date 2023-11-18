package com.example.nimgame.game;

import com.example.nimgame.game.flow.AutoGameFlow;
import com.example.nimgame.game.flow.GameFlow;
import com.example.nimgame.game.player.Difficulty;
import com.example.nimgame.game.player.PlayerFactory;
import com.example.nimgame.game.position.PositionFactory;
import com.example.nimgame.game.position.Version;

public interface GameFactory {
    static Game createGame(Version version, StatusListener... listeners) {
        var position = PositionFactory.getInitialPosition(version);
        var game = new Game(position);
        for (var i : listeners) game.subscribe(i);
        return game;
    }

    static GameFlow start(Difficulty difficulty, Game game) {
        if (difficulty == Difficulty.NONE) {
            return new GameFlow(game);
        } else {
            var player = PlayerFactory.usePlayer(difficulty);
            return new AutoGameFlow(game, player);
        }
    }

    static GameFlow start(Difficulty difficulty, Version version, StatusListener... listeners) {
        var game = createGame(version, listeners);
        return start(difficulty, game);
    }
}
