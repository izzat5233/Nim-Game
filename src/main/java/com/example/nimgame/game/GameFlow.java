package com.example.nimgame.game;

public class GameFlow {
    protected final Game game;

    public GameFlow(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    protected void play(State successor) {
        var status = game.getCurrentState().isFirstPlayerTurn() ?
                TurnStatus.SECOND_PLAYER_TURN : TurnStatus.FIRST_PLAYER_TURN;

        var nextSuccessors = successor.getAllSuccessors();
        if (nextSuccessors.isEmpty() && status == TurnStatus.FIRST_PLAYER_TURN)
            status = TurnStatus.FIRST_PLAYER_WON;
        if (nextSuccessors.isEmpty() && status == TurnStatus.SECOND_PLAYER_TURN)
            status = TurnStatus.SECOND_PLAYER_WON;

        game.setGameVariables(successor, nextSuccessors, status);
    }

    public void play(int row, int removeAmount) {
        var current = game.getCurrentState();
        var successor = current.getSuccessor(row, removeAmount);
        if (successor.isPresent()) play(successor.get());
        else game.setGameVariables(current, game.getCurrentSuccessors(), TurnStatus.MOVE_NOT_ALLOWED);
    }
}
