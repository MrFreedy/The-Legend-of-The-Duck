package org.azal.entities;

import java.awt.*;

public class Key {
    private char letter;
    private Point position;
    private boolean visible;

    public Key(char letter, Point position) {
        this.letter = letter;
        this.position = position;
        this.visible = true;
    }

    public char getLetter() {
        return letter;
    }

    public Point getPosition() {
        return position;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}