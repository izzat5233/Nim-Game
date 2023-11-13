package com.example.nimgame.fxml.object;

public interface PileSelectionListener {
    default void onPileSelectionChange(Pile pile) {
    }

    default void onPileSelectionIncrement(Pile pile) {
        onPileSelectionChange(pile);
    }

    default void onPileSelectionDecrement(Pile pile) {
        onPileSelectionChange(pile);
    }
}
