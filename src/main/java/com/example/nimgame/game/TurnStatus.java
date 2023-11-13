package com.example.nimgame.game;

public enum TurnStatus {
    GAME_START("Game Start"),
    FIRST_PLAYER_TURN("P1 Turn"),
    SECOND_PLAYER_TURN("P2 Turn"),
    MOVE_NOT_ALLOWED("Not Allowed!"),
    FIRST_PLAYER_WON("P1 Won!"),
    SECOND_PLAYER_WON("P2 Won!"),
    ;

    private final String string;

    TurnStatus(String string) {
        this.string = string;
    }

    @Override
    public String toString() {
        return string;
    }
}
