package com.example.nimgame.game.ai;

import com.example.nimgame.game.position.Position;

import java.util.HashMap;
import java.util.Random;

public class AiPlayer {
    private final HashMap<Position, Integer> memo = new HashMap<>();

    private final Difficulty difficulty;

    public AiPlayer(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Position getSuccessor(Position position) {
        if (difficulty.isRandom()) {
            return randomSuccessor(position);
        }

        memo.clear();
        int best = Integer.MIN_VALUE;
        Position successor = null;
        for (var i : position.getAllSuccessors()) {
            int v = alphaBeta(i, Integer.MIN_VALUE, Integer.MAX_VALUE, difficulty.getSearchDepth(), false);
            if (v > best) {
                best = v;
                successor = i;
            }
            if (v == 1) break;
        }
        memo.clear();
        return successor;
    }

    private Position randomSuccessor(Position position) {
        var random = new Random(System.nanoTime());
        var successors = position.getAllSuccessors();
        return successors.get(random.nextInt(successors.size()));
    }

    private int alphaBeta(Position position, int a, int b, int depth, boolean maximizing) {
        var successors = position.getAllSuccessors();

        if (memo.containsKey(position)) return memo.get(position);
        if (depth == 0 || successors.isEmpty()) return position.getHeuristicValue(maximizing);

        int best;
        if (maximizing) {
            best = Integer.MIN_VALUE;
            for (var i : successors) {
                best = Math.max(best, alphaBeta(i, a, b, depth - 1, false));
                a = Math.max(a, best);
                if (b <= a) {
                    break;
                }
            }
        } else {
            best = Integer.MAX_VALUE;
            for (var i : successors) {
                best = Math.min(best, alphaBeta(i, a, b, depth - 1, true));
                b = Math.min(b, best);
                if (b <= a) {
                    break;
                }
            }
        }

        memo.put(position, best);
        return best;
    }
}
