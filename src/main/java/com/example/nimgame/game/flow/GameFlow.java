package com.example.nimgame.game.flow;

import com.example.nimgame.game.Game;
import com.example.nimgame.game.Status;
import com.example.nimgame.game.Position;

public class GameFlow {
    protected final Game game;

    public GameFlow(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    protected void play(Position successor) {
        var status = game.getPosition().isFirstPlayerTurn() ?
                Status.SECOND_PLAYER_TURN : Status.FIRST_PLAYER_TURN;

        var nextSuccessors = successor.getAllSuccessors();
        if (nextSuccessors.isEmpty() && status == Status.FIRST_PLAYER_TURN)
            status = Status.FIRST_PLAYER_WON;
        if (nextSuccessors.isEmpty() && status == Status.SECOND_PLAYER_TURN)
            status = Status.SECOND_PLAYER_WON;

        game.move(successor, status);
    }

    public void play(int row, int removeAmount) {
        var current = game.getPosition();
        var successor = current.getSuccessor(row, removeAmount);
        if (successor.isPresent()) play(successor.get());
        else game.move(current, Status.MOVE_NOT_ALLOWED);
    }
}
