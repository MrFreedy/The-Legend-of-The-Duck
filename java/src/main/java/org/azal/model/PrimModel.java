package org.azal.model;

import org.azal.controller.PrimController;
import org.azal.entities.*;
import org.azal.view.PrimView;
import org.w3c.dom.css.Rect;

import javax.imageio.ImageIO;
import javax.swing.*;
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

    private final List<Room> rooms = new ArrayList<>();
    private HashMap<Room, Object> roomObjects = new HashMap<>();

    private HashMap<Corridor, List<Room>> corridorObjects = new HashMap<>();

    private Point bossPosition;
    private Point keyPosition;
    private Point spawnPosition;

    private boolean isGettingKey = false;
    private boolean isFight = false;
    private boolean bossDead = false;
    public boolean isBuilding = true;
    private final Color corridorClosed = Color.RED;

    public PrimModel() {
        try {
            int x = 0;
            int y = 0;
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
        int x = 0;
        int y = 0;
        if (isGettingKey) {
            for (Corridor corridor : corridorObjects.keySet()) {
                corridor.setColor(Color.GREEN);
                List<Room> corridorRooms = corridorObjects.get(corridor);
                graphics2D.setPaint(corridor.getColor());
                graphics2D.drawLine(
                        corridorRooms.get(0).getRectangle().x + corridorRooms.get(0).getRectangle().width / 2,
                        corridorRooms.get(0).getRectangle().y + corridorRooms.get(0).getRectangle().height / 2,
                        corridorRooms.get(1).getRectangle().x + corridorRooms.get(1).getRectangle().width / 2,
                        corridorRooms.get(1).getRectangle().y + corridorRooms.get(1).getRectangle().height / 2
                );
            }

            for (Room room : roomObjects.keySet()) {
                Rectangle rectangle = room.getRectangle();
                graphics2D.setPaint(stoneTexture);
                graphics2D.fill(rectangle);
                graphics2D.draw(rectangle);
                graphics2D.setPaint(dirtTexture);
                graphics2D.fillRect(rectangle.x + 1, rectangle.y + 1
                        , rectangle.width - ROOM_MIN_DISTANCE / 2
                        , rectangle.height - ROOM_MIN_DISTANCE / 2);
            }

            List<Player> players = roomObjects.values().stream()
                    .filter(object -> object instanceof Player)
                    .map(object -> (Player) object)
                    .toList();
            for (Player player : players) {
                graphics2D.setColor(Color.BLUE);
                graphics2D.fillOval(keyPosition.x, keyPosition.y, 5, 5);
                player.setPosition(keyPosition);
            }

            for(Boss boss : roomObjects.values().stream()
                    .filter(object -> object instanceof Boss)
                    .map(object -> (Boss) object)
                    .toList()) {
                graphics2D.setColor(Color.RED);
                graphics2D.fillOval(bossPosition.x, bossPosition.y, 5, 5);
            }

            isGettingKey = false;

        } else if(isFight) {
            List<Player> players = roomObjects.values().stream()
                    .filter(object -> object instanceof Player)
                    .map(object -> (Player) object)
                    .toList();
            for (Player player : players) {
                graphics2D.setColor(Color.BLUE);
                graphics2D.fillOval(bossPosition.x, bossPosition.y, 5, 5);
                graphics2D.setPaint(dirtTexture);
                graphics2D.fillRect(player.getPosition().x, player.getPosition().y, 5, 5);
                player.setPosition(bossPosition);
                isFight = false;
            }
            bossDead = true;
        }else if (isBuilding){
            corridorObjects.clear();
            roomObjects.clear();

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

                Room roomUn = new Room(new Rectangle(point.x - width / 2, point.y - height / 2, width, height));
                for (Room roomDeux : rooms) {
                    if (roomUn.getRectangle().intersects(roomDeux.getRectangle())) {
                        continue outer;
                    }
                }

                // ensure each room doesn't touch others
                Rectangle rectangleUn = roomUn.getRectangle();
                rectangleUn.x += ROOM_MIN_DISTANCE;
                rectangleUn.y += ROOM_MIN_DISTANCE;
                rectangleUn.width -= 2 * ROOM_MIN_DISTANCE;
                rectangleUn.height -= 2 * ROOM_MIN_DISTANCE;
                roomUn.setRectangle(rectangleUn);

                // ensure path doesn't collide with one of wall corners
                if (xs.contains(rectangleUn.x) || xs.contains(rectangleUn.x + rectangleUn.width / 2)
                        || xs.contains(rectangleUn.x + rectangleUn.width)
                        || ys.contains(rectangleUn.y) || ys.contains(rectangleUn.y + rectangleUn.height / 2)
                        || ys.contains(rectangleUn.y + rectangleUn.height)) {

                    continue;
                }

                // save wich
                for (int d = -1; d <= 1; d++) {
                    xs.add(rectangleUn.x + d);
                    xs.add(rectangleUn.x + rectangleUn.width / 2 + d);
                    xs.add(rectangleUn.x + rectangleUn.width + d);
                    ys.add(rectangleUn.y + d);
                    ys.add(rectangleUn.y + rectangleUn.height / 2 + d);
                    ys.add(rectangleUn.y + rectangleUn.height + d);
                }

                rooms.add(roomUn);

                graphics2D.setPaint(stoneTexture);
                graphics2D.fill(rectangleUn);
                graphics2D.draw(rectangleUn);
                graphics2D.setPaint(dirtTexture);
                graphics2D.fillRect(rectangleUn.x + 1, rectangleUn.y + 1
                        , rectangleUn.width - ROOM_MIN_DISTANCE / 2
                        , rectangleUn.height - ROOM_MIN_DISTANCE / 2);
                points.add(point);
            }

            tree.add(points.remove(0));

            for (int i = 0; i < rooms.size() - 1; i++) {
                Room room1 = rooms.get(i);
                Room room2 = rooms.get(i + 1);
                Corridor corridor = new Corridor(room1, room2, corridorClosed);
                List<Room> tempRooms = new ArrayList<>();
                tempRooms.add(room1);
                tempRooms.add(room2);
                corridorObjects.put(corridor, tempRooms);

                graphics2D.setPaint(Color.RED);
                graphics2D.drawLine(
                        room1.getRectangle().x + room1.getRectangle().width / 2,
                        room1.getRectangle().y + room1.getRectangle().height / 2,
                        room2.getRectangle().x + room2.getRectangle().width / 2,
                        room2.getRectangle().y + room2.getRectangle().height / 2
                );

            }

            if (!rooms.isEmpty()) {

                Boss boss = new Boss();
                Room bossRoom = rooms.get(new Random().nextInt(rooms.size()));
                roomObjects.put(bossRoom, boss);

                bossPosition.setLocation(
                        bossRoom.getRectangle().x + bossRoom.getRectangle().width / 2,
                        bossRoom.getRectangle().y + bossRoom.getRectangle().height / 2
                );

                Key key = new Key('A', new Point());
                Room keyRoom;
                do {
                    keyRoom = rooms.get(new Random().nextInt(rooms.size()));
                } while (roomObjects.containsKey(keyRoom));

                roomObjects.put(keyRoom, key);

                keyPosition.setLocation(
                        keyRoom.getRectangle().x + 2 + keyRoom.getRectangle().width / 2,
                        keyRoom.getRectangle().y + 2 + keyRoom.getRectangle().height / 2
                );

                Player player = new Player('P', new Point());
                Room spawnRoom;
                do {
                    spawnRoom = rooms.get(new Random().nextInt(rooms.size()));
                    player.setPosition(new Point(
                            spawnRoom.getRectangle().x + spawnRoom.getRectangle().width / 2,
                            spawnRoom.getRectangle().y + spawnRoom.getRectangle().height / 2
                    ));
                } while (spawnRoom == bossRoom || (spawnRoom == keyRoom && rooms.size() > 3));

                roomObjects.put(spawnRoom, player);
                spawnPosition.setLocation(
                        spawnRoom.getRectangle().x + spawnRoom.getRectangle().width / 2,
                        spawnRoom.getRectangle().y + spawnRoom.getRectangle().height / 2
                );

                List<Room> roomList = new ArrayList<>(roomObjects.keySet());
                for (int i = 0; i < roomList.size() - 1; i++) {
                    Room room1 = roomList.get(i);
                    Room room2 = roomList.get(i + 1);
                    Corridor corridor = new Corridor(room1, room2, corridorClosed);
                    List<Room> corridorRooms = new ArrayList<>();
                    corridorRooms.add(room1);
                    corridorRooms.add(room2);
                    corridorObjects.put(corridor, corridorRooms);

                    // Si le corridor relie la salle du joueur et la salle de la clé, changez sa couleur en vert
                    if ((roomObjects.get(room1) instanceof Player && roomObjects.get(room2) instanceof Key)
                            || (roomObjects.get(room1) instanceof Key && roomObjects.get(room2) instanceof Player)) {
                        graphics2D.setColor(Color.GREEN);
                        graphics2D.drawLine(
                                room1.getRectangle().x + room1.getRectangle().width / 2,
                                room1.getRectangle().y + room1.getRectangle().height / 2,
                                room2.getRectangle().x + room2.getRectangle().width / 2,
                                room2.getRectangle().y + room2.getRectangle().height / 2
                        );
                    }
                }

                for (Map.Entry<Room, Object> entry : roomObjects.entrySet()) {
                    if (entry.getValue() instanceof Key) {
                        keyRoom = entry.getKey();
                        break;
                    }
                }

                if (keyRoom != null) {
                    List<Corridor> keyCorridors = new ArrayList<>();
                    for (Map.Entry<Corridor, List<Room>> entry : corridorObjects.entrySet()) {
                        Corridor corridor = entry.getKey();
                        List<Room> corridorRooms = entry.getValue();
                        if (corridorRooms.contains(keyRoom)) {
                            keyCorridors.add(corridor);
                        }
                    }

                    boolean isConnectedToPlayer = false;
                    for (Corridor corridor : keyCorridors) {
                        List<Room> corridorRooms = corridorObjects.get(corridor);
                        for (Room room : corridorRooms) {
                            if (roomObjects.get(room) instanceof Player) {
                                isConnectedToPlayer = true;
                                break;
                            }
                        }
                        if (isConnectedToPlayer) {
                            break;
                        }
                    }

                    if (!isConnectedToPlayer) {
                        // Find the player's room
                        Room playerRoom = null;
                        for (Map.Entry<Room, Object> entry : roomObjects.entrySet()) {
                            if (entry.getValue() instanceof Player) {
                                playerRoom = entry.getKey();
                                break;
                            }
                        }

                        if (playerRoom != null) {
                            Corridor corridor = new Corridor(keyRoom, playerRoom, corridorClosed);
                            List<Room> corridorRooms = new ArrayList<>();
                            corridorRooms.add(keyRoom);
                            corridorRooms.add(playerRoom);
                            corridorObjects.put(corridor, corridorRooms);
                        }
                    }

                    for (Map.Entry<Corridor, List<Room>> entry : corridorObjects.entrySet()) {
                        Corridor corridor = entry.getKey();
                        List<Room> corridorRooms = entry.getValue();
                        if ((roomObjects.get(corridorRooms.get(0)) instanceof Player && roomObjects.get(corridorRooms.get(1)) instanceof Key)
                                || (roomObjects.get(corridorRooms.get(0)) instanceof Key && roomObjects.get(corridorRooms.get(1)) instanceof Player)) {
                            graphics2D.setColor(Color.GREEN);
                            graphics2D.drawLine(
                                    corridorRooms.get(0).getRectangle().x + corridorRooms.get(0).getRectangle().width / 2,
                                    corridorRooms.get(0).getRectangle().y + corridorRooms.get(0).getRectangle().height / 2,
                                    corridorRooms.get(1).getRectangle().x + corridorRooms.get(1).getRectangle().width / 2,
                                    corridorRooms.get(1).getRectangle().y + corridorRooms.get(1).getRectangle().height / 2
                            );
                        }
                    }
                }

                graphics2D.setColor(Color.RED); // Change this to the color of the boss
                graphics2D.fillOval(bossPosition.x, bossPosition.y, 5, 5); // Change the size as needed

                graphics2D.setColor(Color.ORANGE); // Change this to the color of the key
                graphics2D.fillOval(keyPosition.x, keyPosition.y, 5, 5); // Change the size as needed

                graphics2D.setColor(Color.BLUE); // Change this to the color of the player
                graphics2D.fillOval(spawnPosition.x, spawnPosition.y, 5, 5); // Change the size as needed
            }
        }
    }
    public void getKey(boolean isGettingKey) {
        this.isGettingKey = isGettingKey;
    }

    public void fight(boolean isFight) {
        this.isFight = isFight;
    }

    public void setBuilding(boolean building) {
        isBuilding = building;
    }

    public boolean isBossDead() {
        return bossDead;
    }

    public void setBossDead(boolean bossDead) {
        this.bossDead = bossDead;
    }
}
