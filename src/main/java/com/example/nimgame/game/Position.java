package com.example.nimgame.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Position {
    private final boolean firstPlayerTurn;

    private final List<Integer> counts;

    private Position(List<Integer> counts, boolean firstPlayerTurn) {
        this.counts = counts;
        this.firstPlayerTurn = firstPlayerTurn;
    }

    Position(List<Integer> counts) {
        this(counts, true);
    }

    public boolean isFirstPlayerTurn() {
        return firstPlayerTurn;
    }

    public List<Integer> getCounts() {
        return counts;
    }

    public boolean isAllowed(int row, int removeAmount) {
        return counts.get(row) >= removeAmount;
    }

    public Optional<Position> getSuccessor(int row, int removeAmount) {
        if (counts.get(row) < removeAmount) return Optional.empty();
        var newCounts = new ArrayList<>(counts);
        newCounts.set(row, newCounts.get(row) - removeAmount);
        return Optional.of(new Position(newCounts, !firstPlayerTurn));
    }

    public List<Position> getAllSuccessors() {
        var successors = new ArrayList<Position>();

        for (int i = 0; i < counts.size(); i++) {
            int count = counts.get(i);
            for (int j = 1; j <= count; j++) {
                var newCounts = new ArrayList<>(counts);
                newCounts.set(i, count - j);
                successors.add(new Position(newCounts, !firstPlayerTurn));
            }
        }

        return successors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var state = (Position) o;
        return firstPlayerTurn == state.firstPlayerTurn &&
                counts.equals(state.counts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstPlayerTurn, counts);
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("rows: " + counts.size() + '\n');
        for (int i = 0; i < counts.size(); i++) {
            string.append(i).append(": ").append(counts.get(i)).append('\n');
        }
        return string.toString();
    }
}
