package org.azal.entities;

import java.awt.Rectangle;

/**
 * The Room class represents a room in a building with a rectangular shape and an associated object.
 */
public class Room {
    /** The rectangular shape of the room. */
    private Rectangle rectangle;
    /** The object associated with the room. */
    private Object associatedObject;

    /**
     * Constructs a new Room instance with the specified rectangular shape.
     *
     * @param rectangle The rectangular shape of the room.
     */
    public Room(final Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    /**
     * Retrieves the rectangular shape of the room.
     *
     * @return The rectangular shape of the room.
     */
    public Rectangle getRectangle() {
        return rectangle;
    }

    /**
     * Sets the rectangular shape of the room.
     *
     * @param rectangle The rectangular shape of the room.
     */
    public void setRectangle(final Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    /**
     * Retrieves the object associated with the room.
     *
     * @return The object associated with the room.
     */
    public Object getAssociatedObject() {
        return associatedObject;
    }

    /**
     * Sets the object associated with the room.
     *
     * @param associatedObject The object associated with the room.
     */
    public void setAssociatedObject(final Object associatedObject) {
        this.associatedObject = associatedObject;
    }
}
