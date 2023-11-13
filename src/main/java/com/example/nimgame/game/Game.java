package com.example.nimgame.game;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private State currentState;

    private List<State> currentSuccessors;

    private TurnStatus currentTurnStatus = TurnStatus.GAME_START;

    private boolean freeze = false;

    protected final ArrayList<TurnStatusListener> turnStatusListeners = new ArrayList<>();

    public Game(int rows) {
        var counts = new ArrayList<Integer>();
        for (int i = 0; i < rows; i++) counts.add(2 * i + 1);
        var state = new State(counts);
        setGameVariables(state, state.getAllSuccessors(), TurnStatus.FIRST_PLAYER_TURN);
    }

    void setGameVariables(State state, List<State> successors, TurnStatus status) {
        if (freeze) return;
        currentState = state;
        currentSuccessors = successors;
        if (currentTurnStatus != status) {
            currentTurnStatus = status;
            notifyTurnStatusListeners();
        }
    }

    protected void notifyTurnStatusListeners() {
        turnStatusListeners.forEach(i -> i.onTurnStatusChange(currentTurnStatus));
    }

    public void subscribeTurnStatus(TurnStatusListener listener) {
        turnStatusListeners.add(listener);
    }

    public void unsubscribeTurnStatus(TurnStatusListener listener) {
        turnStatusListeners.remove(listener);
    }

    public State getCurrentState() {
        return currentState;
    }

    public List<State> getCurrentSuccessors() {
        return currentSuccessors;
    }

    public TurnStatus getCurrentTurnStatus() {
        return currentTurnStatus;
    }

    public boolean isEndOfGame() {
        return currentSuccessors.isEmpty();
    }

    public void freeze() {
        freeze = true;
    }

    public void unfreeze() {
        freeze = false;
    }
}
