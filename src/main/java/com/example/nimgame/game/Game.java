package com.example.nimgame.game;

import com.example.nimgame.game.position.Position;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private Position position;

    private List<Position> successors;

    private Status status = Status.GAME_START;

    private boolean frozen = false;

    protected final ArrayList<StatusListener> statusListeners = new ArrayList<>();

    public Game(Position initialPosition) {
        set(initialPosition, Status.FIRST_PLAYER_TURN);
    }

    protected void notifyStatusListeners() {
        statusListeners.forEach(i -> i.onStatusChange(status));
    }

    public void subscribe(StatusListener listener) {
        statusListeners.add(listener);
    }

    public void unsubscribe(StatusListener listener) {
        statusListeners.remove(listener);
    }

    public Position getPosition() {
        return position;
    }

    public List<Position> getSuccessors() {
        return successors;
    }

    public Status getStatus() {
        return status;
    }

    public boolean isEndOfGame() {
        return successors.isEmpty();
    }

    public void freeze() {
        frozen = true;
    }

    public void unfreeze() {
        frozen = false;
    }

    public void set(Position newPosition, Status newStatus) {
        if (frozen) return;
        if (position != newPosition) {
            position = newPosition;
            successors = newPosition.getAllSuccessors();
        }
        if (status != newStatus) {
            status = newStatus;
            notifyStatusListeners();
        }
    }
}
