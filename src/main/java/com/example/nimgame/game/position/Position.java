package com.example.nimgame.game.position;

import com.example.nimgame.game.Move;

import java.util.List;
import java.util.Optional;

public abstract class Position {
    protected final List<Integer> counts;

    protected final boolean firstPlayerTurn;

    protected Position(List<Integer> counts, boolean firstPlayerTurn) {
        this.counts = counts;
        this.firstPlayerTurn = firstPlayerTurn;
    }

    protected Position(List<Integer> counts) {
        this(counts, true);
    }

    public boolean isFirstPlayerTurn() {
        return firstPlayerTurn;
    }

    public List<Integer> getCounts() {
        return counts;
    }

    public abstract boolean isAllowed(Move move);

    public abstract Optional<Position> getSuccessor(Move move);

    public abstract List<Position> getAllSuccessors();

    public abstract int getHeuristicValue(boolean firstPlayerIsMaximum);
}
