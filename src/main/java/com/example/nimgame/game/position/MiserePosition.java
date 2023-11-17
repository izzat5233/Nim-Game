package com.example.nimgame.game.position;

import com.example.nimgame.game.Move;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MiserePosition extends Position {

    protected MiserePosition(List<Integer> counts, boolean firstPlayerTurn) {
        super(counts, firstPlayerTurn);
    }

    public MiserePosition(List<Integer> counts) {
        super(counts);
    }

    @Override
    public boolean isAllowed(Move move) {
        if (move.row() < 0 || move.row() >= counts.size()) return false;
        return move.amount() > 0 && move.amount() <= counts.get(move.row());
    }

    @Override
    public Optional<Position> getSuccessor(Move move) {
        if (!isAllowed(move)) return Optional.empty();
        var newCounts = new ArrayList<>(counts);
        var count = newCounts.get(move.row());
        newCounts.set(move.row(), count - move.amount());
        return Optional.of(new MiserePosition(newCounts, !firstPlayerTurn));
    }

    @Override
    public List<Position> getAllSuccessors() {
        var successors = new ArrayList<Position>();
        for (int i = 0; i < counts.size(); i++) {
            int count = counts.get(i);
            for (int j = 1; j <= count; j++) {
                var newCounts = new ArrayList<>(counts);
                newCounts.set(i, count - j);
                successors.add(new MiserePosition(newCounts, !firstPlayerTurn));
            }
        }
        return successors;
    }

    @Override
    public int getHeuristicValue(boolean maximizing) {
        int h = maximizing ? 1 : -1;
        if (counts.stream().allMatch(i -> i == 0)) return 2 * h;

        boolean isEndgame = counts.stream().filter(c -> c > 1).count() <= 1;
        if (!isEndgame) return nimSum() == 0 ? -h : h;

        int singlePiles = (int) counts.stream().filter(c -> c == 1).count();
        return singlePiles % 2 == 0 ? -h : h;
    }
}
