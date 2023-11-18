package com.example.nimgame.game.position;

import java.util.ArrayList;

public final class PositionFactory {

    private PositionFactory() {
    }

    private static int rows = 5;

    public static void setRows(int rows) {
        PositionFactory.rows = rows;
    }

    public static Position getInitialPosition(Version version) {
        return version == Version.CLASSIC ? getClassicPosition() : getMiserePosition();
    }

    private static Position getMiserePosition() {
        var counts = new ArrayList<Integer>();
        for (int i = 0; i < rows; i++) counts.add(2 * i + 1);
        return new MiserePosition(counts);
    }

    private static Position getClassicPosition() {
        var counts = new ArrayList<Integer>();
        var limit = Math.clamp(rows, 1, 4);
        for (int i = 0; i < limit; i++) counts.add(24 / rows);
        return new ClassicPosition(counts);
    }
}
