package org.azal.entities;

import java.awt.*;

public class Corridor {
    private Room room1;
    private Room room2;
    private Color color;

    public Corridor(Room room1, Room room2, Color color) {
        this.room1 = room1;
        this.room2 = room2;
        this.color = color;
    }

    public Room getRoom1() {
        return room1;
    }

    public Room getRoom2() {
        return room2;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}