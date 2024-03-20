package org.azal.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BSPModel {
    static final int WIDTH = 800, HEIGHT = 800;
    private List<Rectangle> partitions;
    private List<Rectangle> doors;
    private static final int DOOR_SIZE = 7;
    private Point bossPosition;
    private Point keyPosition;
    private final Color roomColor = Color.BLUE;
    private final Color corridorColor = Color.RED;
    private final Color doorColor = Color.GREEN;

    public BSPModel(){
        doors = new ArrayList<>();
        partitions = new ArrayList<>();
        partitions.add(new Rectangle(60, 60, WIDTH - 120, HEIGHT - 120));
        splitPartitions(partitions, 5);
        createCorridors(partitions);
        addDoorsToRooms(partitions);

        Rectangle bossRoom = partitions.get(new Random().nextInt(partitions.size() / 2));
        bossPosition = new Point(
                bossRoom.x + bossRoom.width / 2,
                bossRoom.y + bossRoom.height / 2);
        Rectangle keyRoom = partitions.get(new Random().nextInt(partitions.size()/2));
        keyPosition = new Point(
                keyRoom.x + keyRoom.width / 2,
                keyRoom.y + keyRoom.height / 2);
    }

    public void generateNewBSP(){
        partitions.clear();
        doors.clear();
        partitions.add(new Rectangle(60,50,WIDTH-120,HEIGHT-120));

        splitPartitions(partitions, 5);
        createCorridors(partitions);
        addDoorsToRooms(partitions);
        Rectangle bossRoom = partitions.get(new Random().nextInt(partitions.size() / 2));
        bossPosition = new Point(
                bossRoom.x + bossRoom.width / 2,
                bossRoom.y + bossRoom.height / 2);

        Rectangle keyRoom;
        do {
            keyRoom = partitions.get(new Random().nextInt(partitions.size() / 2));
        } while (keyRoom == bossRoom);

        keyPosition = new Point(
                keyRoom.x + keyRoom.width / 2,
                keyRoom.y + keyRoom.height / 2);
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

    private void addDoorsToRooms(List<Rectangle> partitions) {
        Random r = new Random();
        doors = new ArrayList<>();
        for (int i = 0; i < partitions.size() / 2; i++) {
            Rectangle room = partitions.get(i);
            int wall = r.nextInt(4);
            if (wall == 0) {
                doors.add(new Rectangle(
                        room.x + room.width / 2 - DOOR_SIZE / 2,
                        room.y + 5,
                        DOOR_SIZE, DOOR_SIZE)
                );
            } else if (wall == 1) {
                doors.add(new Rectangle(
                        room.x + 5,
                        room.y + room.height / 2 - DOOR_SIZE / 2,
                        DOOR_SIZE, DOOR_SIZE)
                );
            } else if (wall == 2) {
                doors.add(new Rectangle(
                        room.x + room.width / 2 - DOOR_SIZE / 2,
                        room.y + room.height - DOOR_SIZE - 5,
                        DOOR_SIZE, DOOR_SIZE)
                );
            } else {
                doors.add(new Rectangle(
                        room.x + room.width - DOOR_SIZE - 5,
                        room.y + room.height / 2 - DOOR_SIZE / 2,
                        DOOR_SIZE, DOOR_SIZE)
                );
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

    public List<Rectangle> getDoors() {
        return doors;
    }

    public Point getBossPosition() {
        return bossPosition;
    }

    public Point getKeyPosition() {
        return keyPosition;
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


}
