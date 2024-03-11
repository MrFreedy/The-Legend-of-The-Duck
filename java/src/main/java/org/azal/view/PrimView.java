package org.azal.view;

import org.azal.model.PrimModel;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics2D;

public class PrimView extends JPanel {

    private PrimModel model;

    private Color color = Color.BLACK;

    public PrimView (PrimModel model) {
        super();
        this.model = model;
        setBackground(color);
    }

    @Override
    protected void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
        model.draw((Graphics2D) model.image.getGraphics());
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(model.image, 0, 0, getWidth(), getHeight(), null);
    }



}
