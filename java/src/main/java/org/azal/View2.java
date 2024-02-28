package org.azal;

import jdk.jshell.execution.Util;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class View2 extends JPanel implements KeyListener, MouseListener {
    private TexturePaint stoneTexture;
    private TexturePaint dirtTexture;
    private final BufferedImage image 
        = new BufferedImage(80, 80, BufferedImage.TYPE_INT_RGB);

    // number of rooms
    private static final int NUMBER_OF_POINTS = 8;
    private final List<Point> points = new ArrayList<>();
    
    private final List<Point> tree = new ArrayList<>();
    
    private static final int ROOM_MAX_SIZE = 30;
    private static final int ROOM_MIN_SIZE = 20;
    
    // minimum distance between 2 rooms
    private static final int ROOM_MIN_DISTANCE = 2;
    
    private final List<Rectangle> rooms = new ArrayList<>();
    
    public View2() {
        try {
            // load the image from the file
            BufferedImage stoneImage = ImageIO.read(new File("data/tiles/stone.png"));
            BufferedImage dirtImage = ImageIO.read(new File("data/tiles/dirt.png"));
            // create a TexturePaint with your image
            stoneTexture = new TexturePaint(stoneImage,
                    new Rectangle(0, 0, stoneImage.getWidth(), stoneImage.getHeight()));
            dirtTexture = new TexturePaint(dirtImage,
                    new Rectangle(0, 0, dirtImage.getWidth(), dirtImage.getHeight()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        addKeyListener(this);
        addMouseListener(this);

    }

   @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw((Graphics2D) image.getGraphics());
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(image, 0, 0, getWidth(), getHeight(), null);
    }

    /*@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.clearRect(0, 0, getWidth(), getHeight());
        g2d.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        drawFromFile(g2d);
    }*/

    private void writeToFile(String type, Rectangle room) {
        try {
            FileWriter writer = new FileWriter("output.txt", true);
            writer.write(type + ", X: " + room.x + ", Y: " + room.y + ", Width: " + room.width + ", Height: " + room.height + "\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }



    private void draw(Graphics2D g) {

        g.clearRect(0, 0, image.getWidth(), image.getHeight());

        Set<Integer> xs = new HashSet<>();
        Set<Integer> ys = new HashSet<>();

        points.clear();
        tree.clear();
        rooms.clear();

        g.setColor(Color.WHITE);

        outer:
        for (int i = 0; i < NUMBER_OF_POINTS; i++) {
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

            writeToFile("Room", ra);

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

            //writeToFile("Path", a, b);
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

    public static void main(String[] args) throws IOException {
        SwingUtilities.invokeLater(() -> {
            View2 view = new View2();
            JFrame frame = new JFrame();
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setUndecorated(true);
            frame.setVisible(true);
            frame.add(view);
            view.requestFocus();
            view.start();
        });
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        repaint();

        if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        //print coordinates
        System.out.println("X: " + e.getX() + " Y: " + e.getY());
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
