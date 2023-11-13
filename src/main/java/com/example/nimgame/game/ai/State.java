package com.example.nimgame.game.ai;

import com.example.nimgame.game.Position;

public record State(Position position, boolean maximizing) {
}
