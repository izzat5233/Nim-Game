package com.example.nimgame.game.ai;

import com.example.nimgame.game.Position;

import java.util.HashMap;
import java.util.Random;

public class AiPlayer {
    private final HashMap<State, Integer> memo = new HashMap<>();

    private final Difficulty difficulty;

    public AiPlayer(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    private static int getHeuristicValue(State state) {
        var maximizing = state.maximizing();
        var counts = state.position().getCounts();

        int res = maximizing ? 1 : -1;
        if (counts.stream().allMatch(i -> i == 0)) {
            return res;
        }

        int pilesWithMoreThanOne = (int) counts.stream().filter(i -> i > 1).count();
        // Endgame
        if (pilesWithMoreThanOne == 1) {
            return state.position().isFirstPlayerTurn() ? -res : res;
        }

        // Mid-game
        int oddCountPiles = (int) counts.stream().filter(i -> i % 2 == 1).count();
        return oddCountPiles % 2 == 0 ? -res : res;
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
        var pair = new State(position, maximizing);
        var successors = position.getAllSuccessors();

        if (memo.containsKey(pair)) return memo.get(pair);
        if (depth == 0 || successors.isEmpty()) return getHeuristicValue(pair);

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

        memo.put(pair, best);
        return best;
    }
}
