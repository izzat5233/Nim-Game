package com.example.nimgame.game.ai;

import com.example.nimgame.game.position.Position;

import java.util.*;

public class AiPlayer {
    private Random random = null;

    private final HashMap<Position, Integer> memo = new HashMap<>();

    private final Difficulty difficulty;

    public AiPlayer(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public Position getSuccessor(Position position) {
        if (difficulty.isRandom()) return randomSuccessor(position);

        int best = Integer.MIN_VALUE;
        Position successor = null;
        for (var i : position.getAllSuccessors()) {
            int v = alphaBeta(i, Integer.MIN_VALUE, Integer.MAX_VALUE, difficulty.getSearchDepth(), false);
            if (v > best) {
                best = v;
                successor = i;
            }
        }
        return successor;
    }

    private Position randomSuccessor(Position position) {
        if (random == null) random = new Random(System.nanoTime());
        var successors = position.getAllSuccessors();
        return successors.get(random.nextInt(successors.size()));
    }

    private int alphaBeta(Position position, int a, int b, int depth, boolean maximizing) {
        var successors = new ArrayList<>(position.getAllSuccessors());

        if (memo.containsKey(position)) return memo.get(position);
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
