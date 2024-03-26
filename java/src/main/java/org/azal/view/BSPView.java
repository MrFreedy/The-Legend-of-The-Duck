package org.azal.view;

import org.azal.model.BSPModel;
import org.azal.utils.XMLReader;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.BasicStroke;

public class BSPView extends JPanel {
    /** The BSPModel instance associated with this view for managing BSP algorithm-related data and logic. */
    private BSPModel model;
    /** The width of the entity. */
    private final int entityWidth;
    /** The height of the entity. */
    private final int entityHeight;
    /** The stroke width of the entity. */
    private final int strokeWidth;

    /**
     * Constructs a new BSPView instance associated with the specified BSPModel.
     *
     * @param model The BSPModel instance associated with this view for managing BSP algorithm-related data and logic.
     */
    public BSPView(final BSPModel model) {
        super();
        this.model = model;
        Color color = Color.WHITE;
        setBackground(color);

        XMLReader xmlReaderConfig = new XMLReader("src/data/config.xml");
        entityWidth = Integer.parseInt(xmlReaderConfig.getValue("bsp", "entityWidth"));
        entityHeight = Integer.parseInt(xmlReaderConfig.getValue("bsp", "entityHeight"));
        strokeWidth = Integer.parseInt(xmlReaderConfig.getValue("bsp", "strokeWidth"));
    }

    /**
     * Paints the graphical user interface of the BSP algorithm visualization on the JPanel.
     *
     * @param g The Graphics object used for rendering the visualization.
     */
    @Override
    protected void paintComponent(final Graphics g) {
        for (int i = 0; i < model.getPartitions().size() / 2; i++) {
            Graphics2D g2 = (Graphics2D) g;
            Rectangle corridor = model.getPartitions().get(i);
            g.setColor(model.getCorridorColor());
            g.fillRect(corridor.x, corridor.y, corridor.width, corridor.height);
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(strokeWidth));
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
        g.fillOval(model.getBossPosition().x, model.getBossPosition().y, entityWidth, entityHeight);

        g.setColor(Color.ORANGE);
        g.fillOval(model.getKeyPosition().x, model.getKeyPosition().y, entityWidth, entityHeight);

        g.setColor(Color.GREEN);
        g.fillOval(model.getSpawnPosition().x, model.getSpawnPosition().y, entityWidth, entityHeight);
    }
}
