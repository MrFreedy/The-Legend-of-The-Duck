package org.azal.model;

import org.azal.entities.Door;
import org.azal.entities.Room;

import java.awt.Point;
import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * The BSPModel class represents the model component of the Binary Space Partitioning (BSP) algorithm in the
 * Model-View-Controller (MVC) pattern. It is responsible for managing the data and business logic of the BSP
 * algorithm, including the generation of rooms, corridors, and doors, as well as the placement of the player,
 * key, and boss entities within the generated map.
 */
public class BSPModel {
    /** The width of the map. */
    static final int WIDTH = 800, HEIGHT = 800;
    /** The list of partitions in the map. */
    private List<Rectangle> partitions;
    /** The size of the door. */
    private static final int DOOR_SIZE = 7;
    /** The position of the boss entity. */
    private Point bossPosition;
    /** The position of the key entity. */
    private Point keyPosition;
    /** The position of the player entity. */
    private Point spawnPosition;
    /** The flag indicating whether the player is getting the key. */
    private boolean isGettingKey = false;
    /** The flag indicating whether the player is fighting the boss. */
    private boolean isFight = false;
    /** The color of the room. */
    private final Color roomColor = Color.BLUE;
    /** The color of the wall. */
    private final Color wallColor = Color.RED;
    /** The color of the door. */
    private final Color doorColor = Color.GREEN;
    /** The door for the key entity. */
    private Door keyDoor;
    /** The door for the player entity. */
    private Door playerDoor;
    /** The door for the boss entity. */
    private Door bossDoor;
    /** The flag indicating whether the boss entity is dead. */
    private boolean isBossDead = false;
    /** The color of the spawn entity. */
    private Color spawnColor = Color.GREEN;
    /** The color of the key entity. */
    private Color keyColor = Color.ORANGE;
    /** The color of the boss entity. */
    private Color bossColor = Color.RED;
    /** The map of rooms and doors. */
    private HashMap<Room, Door> rooms;

    /**
     * Constructs a new BSPModel instance for managing the data and business logic of the BSP algorithm.
     */
    public BSPModel(){
        rooms = new HashMap<>();
        partitions = new ArrayList<>();
        partitions.add(new Rectangle(60, 60, WIDTH - 120, HEIGHT - 120));
        splitPartitions(partitions, 5);
        createCorridors(partitions);

        Rectangle bossRoom;
        Rectangle keyRoom = partitions.get(new Random().nextInt(partitions.size()/2));
        Rectangle spawnRoom = partitions.get(new Random().nextInt(partitions.size()/2));

        do {
            bossRoom = partitions.get(new Random().nextInt(partitions.size() / 2));
        } while (bossRoom.equals(keyRoom) || bossRoom.equals(spawnRoom));

        bossPosition = new Point(
                bossRoom.x + bossRoom.width / 2,
                bossRoom.y + bossRoom.height / 2
        );
        keyPosition = new Point(
                keyRoom.x + keyRoom.width / 2,
                keyRoom.y + keyRoom.height / 2);
        spawnPosition = new Point(
                spawnRoom.x + spawnRoom.width / 2,
                spawnRoom.y + spawnRoom.height / 2);

        if (spawnPosition.equals(keyPosition)) {
            spawnPosition = new Point(
                    spawnRoom.x + spawnRoom.width / 2 + 50,
                    spawnRoom.y + spawnRoom.height / 2 + 50);
        }

        addDoorsToRooms(partitions, keyRoom, spawnRoom, bossRoom);

    }
    /**
     * Splits the partitions in the map into smaller partitions.
     * @param partitions The list of partitions in the map.
     * @param maxRooms The maximum number of rooms to generate.
     */
    private void splitPartitions(List<Rectangle> partitions, int maxRooms) {
        List<Rectangle> newPartitions = new ArrayList<>();

        for (Rectangle partition : partitions) {
            if (partition.width > partition.height) {
                if (partition.width > 100) {
                    int split = randomWithRange(80, partition.width - 80);
                    if (newPartitions.size() < maxRooms) {
                        newPartitions.add(new Rectangle(partition.x, partition.y, split, partition.height));
                    }
                    if (newPartitions.size() < maxRooms) {
                        newPartitions.add(new Rectangle(partition.x + split, partition.y, partition.width - split, partition.height));
                    }
                } else if (newPartitions.size() < maxRooms) {
                    newPartitions.add(partition);
                }
            } else {
                if (partition.height > 100) {
                    int split = randomWithRange(80, partition.height - 80);
                    if (newPartitions.size() < maxRooms) {
                        newPartitions.add(new Rectangle(partition.x, partition.y, partition.width, split));
                    }
                    if (newPartitions.size() < maxRooms) {
                        newPartitions.add(new Rectangle(partition.x, partition.y + split, partition.width, partition.height - split));
                    }
                } else if (newPartitions.size() < maxRooms) {
                    newPartitions.add(partition);
                }
            }
            if (newPartitions.size() >= maxRooms) {
                break;
            }
        }

        partitions.clear();
        partitions.addAll(newPartitions);

        if (partitions.size() < maxRooms) {
            splitPartitions(partitions, maxRooms);
        }
    }

    /**
     * Creates corridors between the rooms in the map.
     * @param partitions The list of partitions in the map.
     */
    private void createCorridors(List<Rectangle> partitions) {
        List<Rectangle> originalPartitions = new ArrayList<>(partitions);
        for (Rectangle partition : originalPartitions) {
            if (partition.width - 40 <= 0 || partition.height - 40 <= 0) continue;
            Rectangle smallerRect = new Rectangle(partition.x + 10, partition.y + 10, partition.width - 20, partition.height - 20);
            partitions.add(smallerRect);
        }
    }

    /**
     * Adds doors to the rooms in the map.
     * @param partitions The list of partitions in the map.
     * @param keyRoom The room containing the key entity.
     * @param spawnRoom The room containing the player entity.
     * @param bossRoom The room containing the boss entity.
     */
    private void addDoorsToRooms(List<Rectangle> partitions, Rectangle keyRoom, Rectangle spawnRoom, Rectangle bossRoom) {
        Random r = new Random();
        rooms = new HashMap<>();
        for (int i = 0; i < partitions.size() / 2; i++) {
            Room room = new Room(partitions.get(i));

            Rectangle doorRectangle;
            int wall = r.nextInt(4);
            if (wall == 0) {
                doorRectangle = new Rectangle(
                        room.getRectangle().x + room.getRectangle().width / 2 - DOOR_SIZE / 2,
                        room.getRectangle().y + 5,
                        DOOR_SIZE, DOOR_SIZE);
            } else if (wall == 1) {
                doorRectangle = new Rectangle(
                        room.getRectangle().x + 5,
                        room.getRectangle().y + room.getRectangle().height / 2 - DOOR_SIZE / 2,
                        DOOR_SIZE, DOOR_SIZE);
            } else if (wall == 2) {
                doorRectangle = new Rectangle(
                        room.getRectangle().x + room.getRectangle().width / 2 - DOOR_SIZE / 2,
                        room.getRectangle().y + room.getRectangle().height - DOOR_SIZE - 5,
                        DOOR_SIZE, DOOR_SIZE);
            } else {
                doorRectangle = new Rectangle(
                        room.getRectangle().x + room.getRectangle().width - DOOR_SIZE - 5,
                        room.getRectangle().y + room.getRectangle().height / 2 - DOOR_SIZE / 2,
                        DOOR_SIZE, DOOR_SIZE);
            }
            Door door = new Door(doorRectangle);
            rooms.put(room, door);
            if (room.getRectangle().equals(keyRoom)) {
                keyDoor = door;
            } else if (room.getRectangle().equals(spawnRoom)) {
                playerDoor = door;
            } else if (room.getRectangle().equals(bossRoom)) {
                bossDoor = door;
            }
        }
    }
    /**
     * Generates a random number within the specified range.
     * @param min The minimum value of the range.
     * @param max The maximum value of the range.
     * @return The random number generated within the specified range.
     */
    private int randomWithRange(final int min, final int max) {
        if (min >= max) {
            return max;
        } else {
            Random r = new Random();
            return r.nextInt((max - min) + 1) + min;
        }
    }

    /**
     * Returns the list of partitions in the map.
     * @return The list of partitions in the map.
     */
    public List<Rectangle> getPartitions() {
        return partitions;
    }
/**
     * Returns the map of rooms and doors.
     * @return The map of rooms and doors.
     */
    public HashMap<Room, Door> getRooms() {
        return rooms;
    }
    /**
     * Returns the position of the boss entity.
     * @return The position of the boss entity.
     */
    public Point getBossPosition() {
        return bossPosition;
    }
    /**
     * Returns the position of the key entity.
     * @return The position of the key entity.
     */
    public Point getKeyPosition() {
        return keyPosition;
    }
    /**
     * Returns the position of the player entity.
     * @return The position of the player entity.
     */
    public Point getSpawnPosition() {
        return spawnPosition;
    }
    /**
     * Returns the color of the room.
     * @return The color of the room.
     */
    public Color getRoomColor() {
        return roomColor;
    }
    /**
     * Returns the color of the wall.
     * @return The color of the wall.
     */
    public Color getWallColor() {
        return wallColor;
    }
    /**
     * Returns the color of the door.
     * @return The color of the door.
     */
    public Color getDoorColor() {
        return doorColor;
    }
    /**
     * Returns the door for the key entity.
     * @return The door for the key entity.
     */
    public Door getKeyDoor() {
        return keyDoor;
    }
    /**
     * Returns the door for the player entity.
     * @return The door for the player entity.
     */
    public Door getPlayerDoor() {
        return playerDoor;
    }
    /**
     * Returns the door for the boss entity.
     * @return The door for the boss entity.
     */
    public Door getBossDoor() {
        return bossDoor;
    }
    /**
     * Returns the flag indicating whether the player is getting the key.
     * @return The flag indicating whether the player is getting the key.
     */
    public boolean isGettingKey() {
        return isGettingKey;
    }
    /**
     * Sets the flag indicating whether the player is getting the key.
     * @param gettingKey The flag indicating whether the player is getting the key.
     */
    public void setGettingKey(final boolean gettingKey) {
        isGettingKey = gettingKey;
    }
    /**
     * Returns the flag indicating whether the player is fighting the boss.
     * @return The flag indicating whether the player is fighting the boss.
     */
    public boolean isFight() {
        return isFight;
    }
    /**
     * Sets the flag indicating whether the player is fighting the boss.
     * @param fight The flag indicating whether the player is fighting the boss.
     */
    public void setFight(final boolean fight) {
        isFight = fight;
    }
    /**
     * Returns the flag indicating whether the boss entity is dead.
     * @return The flag indicating whether the boss entity is dead.
     */
    public boolean isBossDead() {
        return isBossDead;
    }
    /**
     * Sets the flag indicating whether the boss entity is dead.
     * @param bossDead The flag indicating whether the boss entity is dead.
     */
    public void setBossDead(final boolean bossDead) {
        isBossDead = bossDead;
    }
    /**
     * Returns the color of the player entity.
     * @return The color of the player entity.
     */
    public Color getSpawnColor() {
        return spawnColor;
    }
    /**
     * Sets the color of the player entity.
     * @param spawnColor The color of the player entity.
     */
    public void setSpawnColor(final Color spawnColor) {
        this.spawnColor = spawnColor;
    }

    /**
     * Returns the color of the key entity.
     * @return The color of the key entity.
     */
    public Color getKeyColor() {
        return keyColor;
    }

    /**
     * Sets the color of the key entity.
     * @param keyColor The color of the key entity.
     */
    public void setKeyColor(final Color keyColor) {
        this.keyColor = keyColor;
    }

    /**
     * Returns the color of the boss entity.
     * @return The color of the boss entity.
     */
    public Color getBossColor() {
        return bossColor;
    }
    /**
     * Sets the color of the boss entity.
     * @param bossColor The color of the boss entity.
     */
    public void setBossColor(final Color bossColor) {
        this.bossColor = bossColor;
    }

}
