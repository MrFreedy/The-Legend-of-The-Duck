package org.azal.view;

import org.azal.model.BSPModel;

import javax.swing.*;
import java.awt.*;

public class BSPView extends JPanel {
    private BSPModel model;
    private Color color = Color.WHITE;

    public BSPView(BSPModel model){
        super();
        this.model = model;
        setBackground(color);
    }

    @Override
    protected void paintComponent(Graphics g) {
        for (int i = 0; i < model.getPartitions().size() / 2; i++) {
            Graphics2D g2 = (Graphics2D) g;
            Rectangle corridor = model.getPartitions().get(i);
            g.setColor(model.getCorridorColor());
            g.fillRect(corridor.x, corridor.y, corridor.width, corridor.height);
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(10));
            g2.drawRect(corridor.x, corridor.y, corridor.width, corridor.height);
        }
        for (int i = model.getPartitions().size() / 2; i < model.getPartitions().size(); i++) {
            Rectangle room = model.getPartitions().get(i);
            g.setColor(model.getRoomColor());
            g.fillRect(room.x, room.y, room.width, room.height);
        }

        for (Rectangle door : model.getDoors()) {
            g.setColor(model.getDoorColor());
            g.fillRect(door.x, door.y, door.width, door.height);
        }

        g.setColor(Color.RED);
        g.fillOval(model.getBossPosition().x, model.getBossPosition().y, 20, 20);

        g.setColor(Color.ORANGE);
        g.fillOval(model.getKeyPosition().x, model.getKeyPosition().y, 20, 20);

        g.setColor(Color.GREEN);
        g.fillOval(model.getSpawnPosition().x, model.getSpawnPosition().y, 20, 20);
    }
}
