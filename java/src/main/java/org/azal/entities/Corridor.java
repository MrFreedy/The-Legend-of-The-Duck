package org.azal.entities;

import java.awt.Color;

/**
 * Represents a corridor between two rooms.
 */
public class Corridor {
    /** The first room of the corridor. */
    private Room room1;
    /** The second room of the corridor. */
    private Room room2;
    /** The color of the corridor. */
    private Color color;

    /**
     * Constructs a new Corridor instance with the specified rooms and color.
     *
     * @param room1 The first room of the corridor.
     * @param room2 The second room of the corridor.
     * @param color The color of the corridor.
     */
    public Corridor(final Room room1, final Room room2, final Color color) {
        this.room1 = room1;
        this.room2 = room2;
        this.color = color;
    }

    /**
     * Retrieves the first room of the corridor.
     *
     * @return The first room of the corridor.
     */
    public Room getRoom1() {
        return room1;
    }

    /**
     * Retrieves the second room of the corridor.
     *
     * @return The second room of the corridor.
     */
    public Room getRoom2() {
        return room2;
    }

    /**
     * Retrieves the color of the corridor.
     *
     * @return The color of the corridor.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets the color of the corridor.
     *
     * @param color The color of the corridor.
     */
    public void setColor(final Color color) {
        this.color = color;
    }
}
