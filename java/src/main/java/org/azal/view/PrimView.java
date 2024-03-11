package org.azal.view;

import org.azal.model.PrimModel;

import javax.swing.*;
import java.awt.*;

public class PrimView extends JPanel {

    private PrimModel model;

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
