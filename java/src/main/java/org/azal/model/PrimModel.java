package org.azal.model;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The PrimModel class represents the model in the Model-View-Controller (MVC) pattern for managing
 * the data and business logic of the Prim algorithm. It includes the generation and management of
 * textures, points, trees, rooms, and other properties required for the algorithm.
 */
public class PrimModel {
    /** The BufferedImage used for rendering the Prim algorithm visualization. */
    public final BufferedImage image = new BufferedImage(80, 80, BufferedImage.TYPE_INT_RGB);

    /** The TexturePaint for the stone texture. */
    private TexturePaint stoneTexture;

    /** The TexturePaint for the dirt texture. */
    private TexturePaint dirtTexture;

    /** The number of points in the algorithm. */
    private static final int NUM_POINTS = 8;

    /** The list of points used in the Prim algorithm. */
    private final List<Point> points = new ArrayList<>();

    /** The list of points forming the tree in the Prim algorithm. */
    private final List<Point> tree = new ArrayList<>();

    /** The maximum size of a room in the dungeon. */
    private static final int ROOM_MAX_SIZE = 30;

    /** The minimum size of a room in the dungeon. */
    private static final int ROOM_MIN_SIZE = 20;

    /** The minimum distance between rooms in the dungeon. */
    private static final int ROOM_MIN_DISTANCE = 2;

    /** The list of rectangles representing rooms in the dungeon. */
    private final List<Rectangle> rooms = new ArrayList<>();

    public PrimModel() {
        try {
            // load the image from the file
            BufferedImage stoneImage = ImageIO.read(new File("src/data/tiles/stone.png"));
            BufferedImage dirtImage = ImageIO.read(new File("src/data/tiles/dirt.png"));
            // create a TexturePaint with your image
            stoneTexture = new TexturePaint(stoneImage,
                    new Rectangle(0, 0, stoneImage.getWidth(), stoneImage.getHeight()));
            dirtTexture = new TexturePaint(dirtImage,
                    new Rectangle(0, 0, dirtImage.getWidth(), dirtImage.getHeight()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g) {

        g.clearRect(0, 0, image.getWidth(), image.getHeight());

        Set<Integer> xs = new HashSet<>();
        Set<Integer> ys = new HashSet<>();

        points.clear();
        tree.clear();
        rooms.clear();

        g.setColor(Color.WHITE);

        outer:
        for (int i = 0; i < NUM_POINTS; i++) {
            int x = (int) (10 + (image.getWidth() - 20) * Math.random());
            int y = (int) (10 + (image.getHeight() - 20) * Math.random());
            Point p = new Point(x, y);

            int w = (int) (ROOM_MIN_SIZE
                    + ((ROOM_MAX_SIZE - ROOM_MIN_SIZE) * Math.random()));

            int h = (int) (ROOM_MIN_SIZE
                    + ((ROOM_MAX_SIZE - ROOM_MIN_SIZE) * Math.random()));

            Rectangle ra = new Rectangle(p.x - w / 2, p.y - h / 2, w, h);
            for (Rectangle rb : rooms) {
                if (ra.intersects(rb)) {
                    continue outer;
                }
            }

            // ensure each room doesn't touch others
            ra.x += ROOM_MIN_DISTANCE;
            ra.y += ROOM_MIN_DISTANCE;
            ra.width -= 2 * ROOM_MIN_DISTANCE;
            ra.height -= 2 * ROOM_MIN_DISTANCE;

            // ensure path doesn't collide with one of wall corners
            if (xs.contains(ra.x) || xs.contains(ra.x + ra.width / 2)
                    || xs.contains(ra.x + ra.width)
                    || ys.contains(ra.y) || ys.contains(ra.y + ra.height / 2)
                    || ys.contains(ra.y + ra.height)) {

                continue;
            }

            // save wich xs and ys can't be used for next room
            int d = 0;
            //for (int d = -1; d <= 1; d++) {
            xs.add(ra.x + d);
            xs.add(ra.x + ra.width / 2 + d);
            xs.add(ra.x + ra.width + d);
            ys.add(ra.y + d);
            ys.add(ra.y + ra.height / 2 + d);
            ys.add(ra.y + ra.height + d);
            //}

            rooms.add(ra);

            g.setPaint(stoneTexture);
            g.fill(ra);
            g.draw(ra);
            points.add(p);

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
            g.setColor(Color.RED);
            g.drawLine(a.x, a.y, a.x, b.y);
            g.drawLine(a.x, b.y, b.x, b.y);

        }

        // fill rooms
        g.setPaintMode();
        for (Rectangle room : rooms) {
            g.setPaint(dirtTexture);
            g.fillRect(room.x + 1, room.y + 1
                    , room.width - ROOM_MIN_DISTANCE / 2
                    , room.height - ROOM_MIN_DISTANCE / 2);
        }

    }
}
