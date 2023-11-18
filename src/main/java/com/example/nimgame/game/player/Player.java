package com.example.nimgame.game.player;

import com.example.nimgame.game.position.Position;

public interface Player {
    Position getSuccessor(Position position);

    long getMinimumDelayTime();
}
