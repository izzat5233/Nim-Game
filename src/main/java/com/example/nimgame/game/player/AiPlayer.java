package com.example.nimgame.game.player;

import com.example.nimgame.game.position.Position;

import java.util.*;

public class AiPlayer
        implements Player {

    private final HashMap<Position, Integer> memo = new HashMap<>();

    private final AiParameters parameters;

    AiPlayer(AiParameters parameters) {
        this.parameters = parameters;
    }

    public AiParameters parameters() {
        return parameters;
    }

    @Override
    public Position getSuccessor(Position position) {
        int best = Integer.MIN_VALUE;
        Position successor = null;
        for (var i : position.getAllSuccessors()) {
            int v = alphaBeta(i, Integer.MIN_VALUE, Integer.MAX_VALUE, parameters().searchDepth(), false);
            if (v > best) {
                best = v;
                successor = i;
            }
        }
        return successor;
    }

    @Override
    public long getMinimumDelayTime() {
        return parameters.minimumDelayTime();
    }

    private int alphaBeta(Position position, int a, int b, int depth, boolean maximizing) {
        if (memo.containsKey(position)) return memo.get(position);
        var successors = position.getAllSuccessors();
        if (depth == 0 || successors.isEmpty()) return position.getHeuristicValue(maximizing);

        int best;
        if (maximizing) {
            best = Integer.MIN_VALUE;
            // successors.sort(Comparator.comparingInt(o -> o.getHeuristicValue(false)));
            for (var i : successors) {
                best = Math.max(best, alphaBeta(i, a, b, depth - 1, false));
                a = Math.max(a, best);
                if (b <= a) break;
            }
        } else {
            best = Integer.MAX_VALUE;
            // successors.sort(Comparator.comparingInt(o -> o.getHeuristicValue(true)));
            for (var i : successors) {
                best = Math.min(best, alphaBeta(i, a, b, depth - 1, true));
                b = Math.min(b, best);
                if (b <= a) break;
            }
        }

        memo.put(position, best);
        return best;
    }
}
