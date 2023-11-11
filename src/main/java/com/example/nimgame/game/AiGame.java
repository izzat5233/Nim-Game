package com.example.nimgame.game;

public class AiGame extends Game {
    public AiGame(int rows) {
        super(rows);
    }

    @Override
    public TurnStatus play(int row, int removeAmount) {
        System.out.println(getCurrentState());
        var result = super.play(row, removeAmount);
        if (result != TurnStatus.HAS_MOVED) {
            return result;
        }

        int best = Integer.MIN_VALUE;
        State successor = null;
        for (var i : getCurrentSuccessors()) {
            int v = alphaBeta(i, Integer.MIN_VALUE, Integer.MAX_VALUE, 5, false);
            if (v > best) {
                best = v;
                successor = i;
            }
            if (v == 1) {
                break;
            }
        }

        System.out.println(successor);
        return super.play(successor);
    }

    private static int alphaBeta(State state, int a, int b, int depth, boolean maximizing) {
        var successors = state.getAllSuccessors();
        if (depth == 0 || successors.isEmpty()) {
            return state.getHeuristicValue();
        }

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
        return best;
    }
}
