package com.example.nimgame.game;

public class AiGame extends Game {
    public AiGame(int rows) {
        super(rows);
    }

    @Override
    public TurnStatus play(int row, int removeAmount) {
        System.out.println(getCurrentState());
        var result = super.play(row, removeAmount);
        if (result != TurnStatus.HAS_MOVED) {
            return result;
        }

        // the algorithm should be implemented here:
        var someSuccessor = getCurrentSuccessors().get(0);

        System.out.println(someSuccessor);
        return super.play(someSuccessor);
    }
}
