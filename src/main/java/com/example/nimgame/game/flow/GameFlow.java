package com.example.nimgame.game.flow;

import com.example.nimgame.game.Game;
import com.example.nimgame.game.Move;
import com.example.nimgame.game.Status;
import com.example.nimgame.game.position.Position;

public class GameFlow {
    protected final Game game;

    public GameFlow(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    protected void play(Position successor) {
        var isFirstPlayer = game.getPosition().isFirstPlayerTurn();
        var status = isFirstPlayer ? Status.SECOND_PLAYER_TURN : Status.FIRST_PLAYER_TURN;

        var nextSuccessors = successor.getAllSuccessors();
        if (nextSuccessors.isEmpty() && status == Status.FIRST_PLAYER_TURN)
            status = Status.FIRST_PLAYER_WON;
        if (nextSuccessors.isEmpty() && status == Status.SECOND_PLAYER_TURN)
            status = Status.SECOND_PLAYER_WON;

        game.set(successor, status);
    }

    public void play(Move move) {
        var current = game.getPosition();
        var successor = current.getSuccessor(move);
        if (successor.isPresent()) play(successor.get());
        else game.set(current, Status.MOVE_NOT_ALLOWED);
    }
}
