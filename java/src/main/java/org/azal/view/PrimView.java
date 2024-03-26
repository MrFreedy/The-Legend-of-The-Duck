package org.azal.view;

import org.azal.model.PrimModel;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;

/**
 * The PrimView class represents a JPanel for displaying the graphical user interface of the Prim algorithm
 * in conjunction with the Model-View-Controller (MVC) pattern. It is associated with a PrimModel to visualize
 * and interact with the data and functionalities related to the Prim algorithm.
 */
public class PrimView extends JPanel {
    /** The PrimModel instance associated with this view for managing Prim algorithm-related data and logic. */
    private PrimModel model;

    /** The Color used for rendering the visualization, defaulting to black. */
    private Color color = Color.BLACK;
    /**
     * Constructs a new PrimView instance associated with the specified PrimModel.
     *
     * @param model The PrimModel instance associated with this view for managing Prim algorithm-related data and logic.
     */
    public PrimView(final PrimModel model) {
        super();
        this.model = model;
        setBackground(color);
    }

    /**
     * Paints the graphical user interface of the Prim algorithm visualization on the JPanel.
     *
     * @param graphics The Graphics object used for rendering the visualization.
     */
    @Override
    protected void paintComponent(final Graphics graphics) {
        super.paintComponent(graphics);
        model.draw((Graphics2D) model.getImage().getGraphics());
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.drawImage(model.getImage(), 0, 0, getWidth(), getHeight(), null);
        model.setBuilding(false);
    }



}
