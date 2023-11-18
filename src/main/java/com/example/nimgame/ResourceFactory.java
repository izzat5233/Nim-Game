package com.example.nimgame;

import javafx.scene.image.Image;

public interface ResourceFactory {

    private static Image getImage(String fullName) {
        return new Image(String.valueOf(ResourceFactory.class.getResource(fullName)));
    }

    static Image getIconImage(String name) {
        return getImage("assets/icons/" + name);
    }

    static Image getItemImage(String name) {
        return getImage("assets/items/" + name);
    }

    static Image getTextImage(String name) {
        return getImage("assets/texts/" + name);
    }
}
