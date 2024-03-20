package org.azal.model;

import org.w3c.dom.css.Rect;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class PrimModel {
    private final int WidthBuffer = 80;
    private final int HeightBuffer = 80;
    private static final int NUM_POINTS = 8;
    private static final int ROOM_MAX_SIZE = 30;
    private static final int ROOM_MIN_SIZE = 20;
    private static final int ROOM_MIN_DISTANCE = 2;
    public final BufferedImage image = new BufferedImage(WidthBuffer, HeightBuffer, BufferedImage.TYPE_INT_RGB);
    private TexturePaint stoneTexture;
    private TexturePaint dirtTexture;
    private final List<Point> points = new ArrayList<>();
    private final List<Point> tree = new ArrayList<>();
    private final List<Rectangle> rooms = new ArrayList<>();

    private Point bossPosition;
    private Point keyPosition;
    private Point spawnPosition;

    public PrimModel() {
        try {
            int x=0;
            int y=0;
            // load the image from the file
            BufferedImage stoneImage = ImageIO.read(new File("src/data/tiles/stone.png"));
            BufferedImage dirtImage = ImageIO.read(new File("src/data/tiles/dirt.png"));
            // create a TexturePaint with your image
            stoneTexture = new TexturePaint(stoneImage, new Rectangle(x, y, stoneImage.getWidth(), stoneImage.getHeight()));
            dirtTexture = new TexturePaint(dirtImage, new Rectangle(x, y, dirtImage.getWidth(), dirtImage.getHeight()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        bossPosition = new Point();
        keyPosition = new Point();
        spawnPosition = new Point();
    }

    public void draw(Graphics2D graphics2D) {
        int x=0;
        int y=0;

        graphics2D.clearRect(x, y, image.getWidth(), image.getHeight());

        Set<Integer> xs = new HashSet<>();
        Set<Integer> ys = new HashSet<>();

        points.clear();
        tree.clear();
        rooms.clear();

        graphics2D.setColor(Color.WHITE);

        outer:
        for (int i = 0; i < NUM_POINTS; i++) {
            x = (int) (10 + (image.getWidth() - 20) * Math.random());
            y = (int) (10 + (image.getHeight() - 20) * Math.random());
            Point point = new Point(x, y);

            int width = (int) (ROOM_MIN_SIZE + ((ROOM_MAX_SIZE - ROOM_MIN_SIZE) * Math.random()));

            int height = (int) (ROOM_MIN_SIZE + ((ROOM_MAX_SIZE - ROOM_MIN_SIZE) * Math.random()));

            Rectangle rectangleUn = new Rectangle(point.x - width / 2, point.y - height / 2, width, height);
            for (Rectangle rectangleDeux : rooms) {
                if (rectangleUn.intersects(rectangleDeux)) {
                    continue outer;
                }
            }

            // ensure each room doesn't touch others
            rectangleUn.x += ROOM_MIN_DISTANCE;
            rectangleUn.y += ROOM_MIN_DISTANCE;
            rectangleUn.width -= 2 * ROOM_MIN_DISTANCE;
            rectangleUn.height -= 2 * ROOM_MIN_DISTANCE;

            // ensure path doesn't collide with one of wall corners
            if (xs.contains(rectangleUn.x) || xs.contains(rectangleUn.x + rectangleUn.width / 2)
                    || xs.contains(rectangleUn.x + rectangleUn.width)
                    || ys.contains(rectangleUn.y) || ys.contains(rectangleUn.y + rectangleUn.height / 2)
                    || ys.contains(rectangleUn.y + rectangleUn.height)) {

                continue;
            }

            // save wich xs and ys can't be used for next room
            int d = 0;
            //for (int d = -1; d <= 1; d++) {
            xs.add(rectangleUn.x + d);
            xs.add(rectangleUn.x + rectangleUn.width / 2 + d);
            xs.add(rectangleUn.x + rectangleUn.width + d);
            ys.add(rectangleUn.y + d);
            ys.add(rectangleUn.y + rectangleUn.height / 2 + d);
            ys.add(rectangleUn.y + rectangleUn.height + d);
            //}

            rooms.add(rectangleUn);

            graphics2D.setPaint(stoneTexture);
            graphics2D.fill(rectangleUn);
            graphics2D.draw(rectangleUn);
            points.add(point);
        }

        tree.add(points.remove(0));

        Point a, b;
        double minDistance;
        double dx, dy, distance;
        while (!points.isEmpty()) {
            a = null;
            b = null;

            minDistance = Double.MAX_VALUE;

            for (Point p1 : tree) {
                for (Point p2 : points) {
                    dx = p2.x - p1.x;
                    dy = p2.y - p1.y;
                    distance = Math.sqrt(dx * dx + dy * dy);
                    if (distance < minDistance) {
                        minDistance = distance;
                        a = p1;
                        b = p2;
                    }
                }
            }

            if (a == null || b == null) {
                throw new RuntimeException("error ?");
            }

            points.remove(b);
            tree.add(b);

            // draw path between one room to another
            graphics2D.setColor(Color.RED);
            graphics2D.drawLine(a.x, a.y, a.x, b.y);
            graphics2D.drawLine(a.x, b.y, b.x, b.y);

        }

        // fill rooms
        graphics2D.setPaintMode();
        for (Rectangle room : rooms) {
            graphics2D.setPaint(dirtTexture);
            graphics2D.fillRect(room.x + 1, room.y + 1
                    , room.width - ROOM_MIN_DISTANCE / 2
                    , room.height - ROOM_MIN_DISTANCE / 2);
        }

        if (!rooms.isEmpty()) {
            // Generate random positions for the boss within the rooms
            Rectangle bossRoom = rooms.get(new Random().nextInt(rooms.size()));
            bossPosition.setLocation(
                    bossRoom.x + bossRoom.width / 2,
                    bossRoom.y + bossRoom.height / 2
            );

            Rectangle keyRoom;
            do {
                keyRoom = rooms.get(new Random().nextInt(rooms.size()));
            } while (keyRoom == bossRoom);

            keyPosition.setLocation(
                    keyRoom.x+2 + keyRoom.width / 2,//+2 to avoid the superposition with spawn position
                    keyRoom.y+2 + keyRoom.height / 2//+2 to avoid the superposition with spawn position
            );

            Rectangle spawnRoom;
            do {
                spawnRoom = rooms.get(new Random().nextInt(rooms.size()));
            } while (spawnRoom == bossRoom || (spawnRoom == keyRoom && rooms.size() > 3));

            spawnPosition.setLocation(
                    spawnRoom.x + spawnRoom.width / 2,
                    spawnRoom.y + spawnRoom.height / 2
            );

            // Draw the boss, the key and the player at their positions
            graphics2D.setColor(Color.RED); // Change this to the color of the boss
            graphics2D.fillOval(bossPosition.x, bossPosition.y, 5, 5); // Change the size as needed

            graphics2D.setColor(Color.ORANGE); // Change this to the color of the key
            graphics2D.fillOval(keyPosition.x, keyPosition.y, 5, 5); // Change the size as needed

            graphics2D.setColor(Color.GREEN); // Change this to the color of the player
            graphics2D.fillOval(spawnPosition.x, spawnPosition.y, 5, 5); // Change the size as needed
        }
    }
}
