package com.example.nimgame.game.position;

import com.example.nimgame.game.Move;

import java.util.*;

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

    protected int nimSum() {
        return counts.stream().reduce(0, (a, b) -> a ^ b);
    }

    public abstract boolean isAllowed(Move move);

    public abstract Optional<Position> getSuccessor(Move move);

    public abstract List<Position> getAllSuccessors();

    public abstract int getHeuristicValue(boolean maximizing);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position that)) return false;
        return that.firstPlayerTurn == firstPlayerTurn && that.counts.equals(counts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(counts, firstPlayerTurn);
    }
}
