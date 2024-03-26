package org.azal.entities;

import java.awt.Point;

public class Player {
    /** The position of the player. */
    private Point position;
    /** The visibility of the player. */
    private boolean visible;

    /**
     * Constructs a new Player instance with the specified letter and position.
     *
     * @param letter The letter of the player.
     * @param position The position of the player.
     */
    public Player(final char letter, final Point position) {
        this.position = position;
        this.visible = true;
    }
    /**
     * Retrieves the position of the player.
     *
     * @return The position of the player.
     */
    public Point getPosition() {
        return position;
    }

    /**
     * Retrieves the visibility of the player.
     *
     * @return The visibility of the player.
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Sets the position of the player.
     *
     * @param position The position of the player.
     */
    public void setPosition(final Point position) {
        this.position = position;
    }

    /**
     * Sets the visibility of the player.
     *
     * @param visible The visibility of the player.
     */
    public void setVisible(final boolean visible) {
        this.visible = visible;
    }
}
