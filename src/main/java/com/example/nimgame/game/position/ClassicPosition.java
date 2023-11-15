package com.example.nimgame.game.position;

import com.example.nimgame.game.Move;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClassicPosition extends Position {

    private ClassicPosition(List<Integer> counts, boolean firstPlayerTurn) {
        super(counts, firstPlayerTurn);
    }

    public ClassicPosition(List<Integer> counts) {
        super(counts);
    }

    @Override
    public boolean isAllowed(Move move) {
        if (move.row() < 0 || move.row() >= counts.size()) return false;
        if (move.amount() <= 0) return false;
        return 2 * move.amount() != counts.get(move.row());
    }

    @Override
    public Optional<Position> getSuccessor(Move move) {
        if (!isAllowed(move)) return Optional.empty();
        var newCounts = new ArrayList<>(counts);
        var count = newCounts.get(move.row());
        newCounts.set(move.row(), count - move.amount());
        newCounts.add(move.row() + 1, move.amount());
        return Optional.of(new ClassicPosition(newCounts, !firstPlayerTurn));
    }

    @Override
    public List<Position> getAllSuccessors() {
        return null;
    }

    @Override
    public int getHeuristicValue(boolean firstPlayerIsMaximum) {
        return 0;
    }
}
