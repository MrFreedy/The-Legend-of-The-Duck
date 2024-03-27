package org.azal.model;

import org.azal.entities.Door;
import org.azal.entities.Room;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class BSPModel {
    static final int WIDTH = 800, HEIGHT = 800;
    private List<Rectangle> partitions;
    private static final int DOOR_SIZE = 7;
    private Point bossPosition;
    private Point keyPosition;
    private Point spawnPosition;
    private boolean isGettingKey = false;
    private boolean isFight = false;
    private boolean isRegenerating = false;
    private final Color roomColor = Color.BLUE;
    private final Color corridorColor = Color.RED;
    private final Color doorColor = Color.GREEN;
    private Door keyDoor;
    private Door playerDoor;
    private Door bossDoor;
    private boolean isBossDead = false;
    private Color spawnColor = Color.GREEN;
    private Color keyColor = Color.ORANGE;
    private Color bossColor = Color.RED;
    private HashMap<Room, Door> rooms;
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

    private void createCorridors(List<Rectangle> partitions) {
        List<Rectangle> originalPartitions = new ArrayList<>(partitions);
        for (Rectangle partition : originalPartitions) {
            if (partition.width - 40 <= 0 || partition.height - 40 <= 0) continue;
            Rectangle smallerRect = new Rectangle(partition.x + 10, partition.y + 10, partition.width - 20, partition.height - 20);
            partitions.add(smallerRect);
        }
    }

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

    private int randomWithRange(int min, int max) {
        if (min >= max) {
            return max;
        } else {
            Random r = new Random();
            return r.nextInt((max - min) + 1) + min;
        }
    }

    public List<Rectangle> getPartitions() {
        return partitions;
    }

    public HashMap<Room, Door> getRooms() {
        return rooms;
    }

    public Point getBossPosition() {
        return bossPosition;
    }

    public Point getKeyPosition() {
        return keyPosition;
    }

    public Point getSpawnPosition() {
        return spawnPosition;
    }
    public Color getRoomColor() {
        return roomColor;
    }

    public Color getCorridorColor() {
        return corridorColor;
    }

    public Color getDoorColor() {
        return doorColor;
    }

    public Door getKeyDoor() {
        return keyDoor;
    }

    public Door getPlayerDoor() {
        return playerDoor;
    }

    public Door getBossDoor() {
        return bossDoor;
    }

    public boolean isGettingKey() {
        return isGettingKey;
    }

    public void setGettingKey(boolean gettingKey) {
        isGettingKey = gettingKey;
    }

    public boolean isFight() {
        return isFight;
    }

    public void setFight(boolean fight) {
        isFight = fight;
    }

    public boolean isRegenerating() {
        return isRegenerating;
    }

    public void setRegenerating(boolean regenerating) {
        isRegenerating = regenerating;
    }

    public boolean isBossDead() {
        return isBossDead;
    }

    public void setBossDead(boolean bossDead) {
        isBossDead = bossDead;
    }
    public Color getSpawnColor() {
        return spawnColor;
    }

    public void setSpawnColor(Color spawnColor) {
        this.spawnColor = spawnColor;
    }

    public Color getKeyColor() {
        return keyColor;
    }

    public void setKeyColor(Color keyColor) {
        this.keyColor = keyColor;
    }

    public Color getBossColor() {
        return bossColor;
    }

    public void setBossColor(Color bossColor) {
        this.bossColor = bossColor;
    }

}
