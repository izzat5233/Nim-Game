package com.example.nimgame.object;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.function.Function;

public class Pile
        implements ItemSelectionListener {

    private final Pane pane;

    private final Image itemImage;

    private final ArrayList<PileSelectionListener> listeners = new ArrayList<>();

    public Pile(Pane pane, Image itemImage, int initialAmount) {
        this.pane = pane;
        this.itemImage = itemImage;
        for (int i = 0; i < initialAmount; i++) {
            increment();
        }
    }

    @Override
    public void onItemSelection(Item item) {
        if (item.isSelected()) {
            notifyListenersOfIncrement();
        } else {
            notifyListenersOfDecrement();
        }
    }

    public void subscribe(PileSelectionListener listener) {
        listeners.add(listener);
    }

    public void unsubscribe(PileSelectionListener listener) {
        listeners.remove(listener);
    }

    private void notifyListenersOfIncrement() {
        listeners.forEach(i -> i.onPileSelectionIncrement(this));
    }

    private void notifyListenersOfDecrement() {
        listeners.forEach(i -> i.onPileSelectionDecrement(this));
    }

    public Pane getPane() {
        return pane;
    }

    public Image getItemImage() {
        return itemImage;
    }

    public void increment() {
        var item = new Item(itemImage, this);
        item.setFitWidth(40);
        item.setFitHeight(30);
        pane.getChildren().add(item);
    }

    public void decrement() {
        if (getAmount() > 0) {
            var item = (Item) pane.getChildren().remove(getAmount() - 1);
            if (item.isSelected()) {
                notifyListenersOfDecrement();
            }
        }
    }

    public int getAmount() {
        return pane.getChildren().size();
    }

    public int getSelectedAmount() {
        var count = 0;
        for (var i : pane.getChildren()) {
            count += ((Item) i).isSelected() ? 1 : 0;
        }
        return count;
    }

    public void deselectAll() {
        if (getSelectedAmount() > 0) {
            pane.getChildren().forEach(i -> ((Item) i).setSelected(false));
            notifyListenersOfDecrement();
        }
    }

    public void removeSelected() {
        if (pane.getChildren().removeIf(i -> ((Item) i).isSelected())) {
            notifyListenersOfDecrement();
        }
    }

    public void forEachSelected(Function<Item, Void> function) {
        for (var i : pane.getChildren()) {
            var item = (Item) i;
            if (item.isSelected()) {
                function.apply(item);
            }
        }
    }
}
