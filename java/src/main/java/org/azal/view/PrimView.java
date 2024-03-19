package org.azal.view;

import org.azal.model.PrimModel;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics2D;

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

    public PrimView (PrimModel model) {
        super();
        this.model = model;
        setBackground(color);
    }

    @Override
    protected void paintComponent(java.awt.Graphics graphics) {
        super.paintComponent(graphics);
        model.draw((Graphics2D) model.image.getGraphics());
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.drawImage(model.image, 0, 0, getWidth(), getHeight(), null);
    }



}
