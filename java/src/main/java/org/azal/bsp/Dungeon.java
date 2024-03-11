package org.azal.bsp;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/** The Dungeon class represents a graphical user interface component for displaying a dungeon layout.
 *  It extends JPanel and includes features such as rooms, corridors, doors, and a boss position.
 */
class Dungeon extends JPanel {
    /** The width of the dungeon panel. */
    static final int WIDTH = 800;

    /** The height of the dungeon panel. */
    static final int HEIGHT = 800;

    /** List of rectangles representing partitions in the dungeon. */
    private List<Rectangle> partitions;

    /** The color of the rooms in the dungeon (blue). */
    private final Color roomColor = Color.BLUE;

    /** The color of the corridors in the dungeon (red). */
    private final Color corridorColor = Color.RED;

    /** The color of the doors in the dungeon (green). */
    private final Color doorColor = Color.GREEN;

    /** List of rectangles representing doors in the dungeon. */
    private List<Rectangle> doors;

    /** The size of the doors in the dungeon. */
    private static final int DOOR_SIZE = 7;

    /** The position of the boss in the dungeon. */
    private Point bossPosition;

    public Dungeon() {
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

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH, HEIGHT);
    }

    @Override
    protected void paintComponent(Graphics g) {
        for (int i = 0; i < partitions.size() / 2; i++) {
            Graphics2D g2 = (Graphics2D) g;
            Rectangle corridor = partitions.get(i);
            g.setColor(corridorColor);
            g.fillRect(corridor.x, corridor.y, corridor.width, corridor.height);
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(10));
            g2.drawRect(corridor.x, corridor.y, corridor.width, corridor.height);
        }
        for (int i = partitions.size() / 2; i < partitions.size(); i++) {
            Rectangle room = partitions.get(i);
            g.setColor(roomColor);
            g.fillRect(room.x, room.y, room.width, room.height);
        }

        for (Rectangle door : doors) {
            g.setColor(doorColor);
            g.fillRect(door.x, door.y, door.width, door.height);
        }

        g.setColor(Color.YELLOW);
        g.fillOval(bossPosition.x, bossPosition.y, 20, 20);
    }

    private int randomWithRange(int min, int max) {
        if (min >= max) {
            return max;
        } else {
            Random r = new Random();
            return r.nextInt((max - min) + 1) + min;
        }
    }
}
