package com.example.nimgame.object;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Item extends ImageView {
    private boolean selected = false;

    private final ColorAdjust colorAdjust = new ColorAdjust();

    private final Pile pile;

    public Item(Image itemImage, Pile pile) {
        super(itemImage);
        this.pile = pile;
        super.setOnMouseClicked((e) -> setSelected(!selected));
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        colorAdjust.setBrightness(selected ? -0.5 : 0);
        super.setEffect(colorAdjust);
        notifyPile();
    }

    public boolean isSelected() {
        return selected;
    }

    private void notifyPile() {
        pile.onItemSelection(this);
    }
}
