package com.example.nimgame.game.position;

import java.util.ArrayList;
import java.util.List;

public interface PositionFactory {

    int MISERE_GAME_ROWS = 5;

    static Position getInitialPosition(Version version) {
        return version == Version.CLASSIC ? getClassicPosition() : getMiserePosition();
    }

    static private Position getMiserePosition() {
        var counts = new ArrayList<Integer>();
        for (int i = 0; i < MISERE_GAME_ROWS; i++) counts.add(2 * i + 1);
        return new MiserePosition(counts);
    }

    static private Position getClassicPosition() {
        var counts = new ArrayList<>(List.of(15));
        return new ClassicPosition(counts);
    }
}
