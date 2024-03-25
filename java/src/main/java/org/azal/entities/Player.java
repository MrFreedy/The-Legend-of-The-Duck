package org.azal.entities;

import java.awt.*;

public class Player {
    private Point position;
    private boolean visible;

    public Player(char letter, Point position) {
        this.position = position;
        this.visible = true;
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
