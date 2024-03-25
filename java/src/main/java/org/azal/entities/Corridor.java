package org.azal.entities;

public class Corridor {
    private Room room1;
    private Room room2;

    public Corridor(Room room1, Room room2) {
        this.room1 = room1;
        this.room2 = room2;
    }

    public Room getRoom1() {
        return room1;
    }

    public Room getRoom2() {
        return room2;
    }
}