package org.azal.entities;

import java.awt.*;

public class Door {
    private Rectangle rectangle;

    public Door(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }
}
