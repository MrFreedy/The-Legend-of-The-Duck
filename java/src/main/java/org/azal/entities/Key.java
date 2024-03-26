package org.azal.entities;

import java.awt.Point;

/**
 * Represents a key in the game.
 */
public class Key {
    /** The letter of the key. */
    private char letter;
    /** The position of the key. */
    private Point position;
    /** The visibility of the key. */
    private boolean visible;

    /**
     * Constructs a new Key instance with the specified letter and position.
     *
     * @param letter The letter of the key.
     * @param position The position of the key.
     */
    public Key(final char letter, final Point position) {
        this.letter = letter;
        this.position = position;
        this.visible = true;
    }

    /**
     * Retrieves the letter of the key.
     *
     * @return The letter of the key.
     */
    public char getLetter() {
        return letter;
    }

    /**
     * Retrieves the position of the key.
     *
     * @return The position of the key.
     */
    public Point getPosition() {
        return position;
    }

    /**
     * Retrieves the visibility of the key.
     *
     * @return The visibility of the key.
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Sets the position of the key.
     *
     * @param position The position of the key.
     */
    public void setPosition(final Point position) {
        this.position = position;
    }

    /**
     * Sets the visibility of the key.
     *
     * @param visible The visibility of the key.
     */
    public void setVisible(final boolean visible) {
        this.visible = visible;
    }
}
