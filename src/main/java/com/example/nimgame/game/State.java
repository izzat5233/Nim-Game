package com.example.nimgame.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class State {
    private final boolean firstPlayerTurn;

    private final List<Integer> counts;

    private State(List<Integer> counts, boolean firstPlayerTurn) {
        this.counts = counts;
        this.firstPlayerTurn = firstPlayerTurn;
    }

    State(List<Integer> counts) {
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

    public Optional<State> getSuccessor(int row, int removeAmount) {
        if (counts.get(row) < removeAmount) {
            return Optional.empty();
        }
        var newCounts = new ArrayList<>(counts);
        newCounts.set(row, newCounts.get(row) - removeAmount);
        return Optional.of(new State(newCounts, !firstPlayerTurn));
    }

    public List<State> getAllSuccessors() {
        var successors = new ArrayList<State>();

        for (int i = 0; i < counts.size(); i++) {
            int count = counts.get(i);
            for (int j = 1; j <= count; j++) {
                var newCounts = new ArrayList<>(counts);
                newCounts.set(i, count - j);
                successors.add(new State(newCounts, !firstPlayerTurn));
            }
        }

        return successors;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("rows: " + counts.size() + '\n');
        for (int i = 0; i < counts.size(); i++) {
            string.append(i).append(": ").append(counts.get(i)).append('\n');
        }
        return string.toString();
    }

    int getHeuristicValue() {
        int pilesWithMoreThanOne = (int) counts.stream().filter(i -> i > 1).count();
        // Endgame
        if (pilesWithMoreThanOne == 1) {
            return firstPlayerTurn ? -1 : 1;
        }
        // Mid-game
        int oddCountPiles = (int) counts.stream().filter(i -> i % 2 == 1).count();
        return oddCountPiles % 2 == 0 ? -1 : 1;
    }
}
