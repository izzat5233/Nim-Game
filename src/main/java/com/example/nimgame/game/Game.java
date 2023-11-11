package com.example.nimgame.game;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private State currentState;

    private List<State> currentSuccessors;

    public Game(int rows) {
        var counts = new ArrayList<Integer>();
        for (int i = 0; i < rows; i++) {
            counts.add(2 * i + 1);
        }
        setCurrentState(new State(counts));
    }

    private void setCurrentState(State state) {
        currentState = state;
        currentSuccessors = currentState.getAllSuccessors();
    }

    public State getCurrentState() {
        return currentState;
    }

    public List<State> getCurrentSuccessors() {
        return currentSuccessors;
    }

    public boolean isEndOfGame() {
        return currentSuccessors.isEmpty();
    }

    protected TurnStatus play(State successor) {
        setCurrentState(successor);
        if (isEndOfGame()) {
            return (currentState.isFirstPlayerTurn() ?
                    TurnStatus.FIRST_PLAYER_WON : TurnStatus.SECOND_PLAYER_WON);
        }
        return TurnStatus.HAS_MOVED;
    }

    public TurnStatus play(int row, int removeAmount) {
        var successor = currentState.getSuccessor(row, removeAmount);
        if (successor.isEmpty()) {
            return TurnStatus.MOVE_NOT_ALLOWED;
        }
        return play(successor.get());
    }
}
