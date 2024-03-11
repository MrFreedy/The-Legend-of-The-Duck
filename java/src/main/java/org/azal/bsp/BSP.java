package org.azal.bsp;

import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * The BSP class represents a JFrame for managing and displaying a dungeon generated using Binary Space Partitioning (BSP).
 * It contains a Dungeon instance for rendering the dungeon layout.
 */
public class BSP extends JFrame {
    /** The Dungeon instance for rendering the dungeon layout. */
    private Dungeon dungeon;

    public BSP() {
        replaceDungeon();
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                replaceDungeon();
            }
        });
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Dungeon.WIDTH, Dungeon.HEIGHT);
        setLayout(new BorderLayout());
        setVisible(true);
    }

    public static void main(String[] args) {
        new BSP();
    }

    public void replaceDungeon() {
        Dungeon dungeon = new Dungeon();
        getContentPane().removeAll();
        add(dungeon, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
}
