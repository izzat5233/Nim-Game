package com.example.nimgame.game;

public enum Status {
    GAME_START("Start Picking!"),
    FIRST_PLAYER_TURN("Player 1 Turn"),
    SECOND_PLAYER_TURN("Player 2 Turn"),
    MOVE_NOT_ALLOWED("Not Allowed!"),
    FIRST_PLAYER_WON("Player 1 Wins!"),
    SECOND_PLAYER_WON("Player 2 Wins!"),
    ;

    private final String string;

    Status(String string) {
        this.string = string;
    }

    @Override
    public String toString() {
        return string;
    }
}
