package com.example.nimgame.game;

import com.example.nimgame.game.ai.AiAgent;

public class AiGame extends Game {
    private final AiAgent agent;

    public AiGame(int rows, AiAgent agent) {
        super(rows);
        this.agent = agent;
    }

    @Override
    public TurnStatus play(int row, int removeAmount) {
        System.out.println(getCurrentState());
        var result = super.play(row, removeAmount);
        if (result != TurnStatus.HAS_MOVED) {
            return result;
        }
        var successor = agent.bestSuccessor(getCurrentState());
        System.out.println(successor);
        return super.play(successor);
    }
}
