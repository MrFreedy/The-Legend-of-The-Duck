package org.azal.bsp;

import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class BSP extends JFrame {
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
