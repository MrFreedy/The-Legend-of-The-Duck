package org.azal.view;

import org.azal.model.PrimModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;

public class PrimView extends JPanel {

    private PrimModel model;

    private Color color = Color.BLACK;

    public PrimView (PrimModel model) {
        super();
        this.model = model;
        setBackground(color);

        KeyListener keyListener = new KeyListener() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent e) {
            }

            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                if(e.getKeyCode() == java.awt.event.KeyEvent.VK_ESCAPE) {
                    System.exit(0);
                }else{
                    repaint();
                }
            }

            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
            }
        };

        addKeyListener(keyListener);
    }

    @Override
    protected void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
        model.draw((Graphics2D) model.image.getGraphics());
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(model.image, 0, 0, getWidth(), getHeight(), null);
    }



}
