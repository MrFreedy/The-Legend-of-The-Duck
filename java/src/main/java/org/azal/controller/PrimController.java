package org.azal.controller;

import org.azal.model.PrimModel;
import org.azal.view.PrimView;

import javax.swing.*;

public class PrimController{
    private PrimModel model;
    private PrimView view;
    private JFrame frame;

    public PrimController(PrimModel model, PrimView view, JFrame frame) {
        this.model = model;
        this.view = view;
        this.frame = frame;

        JButton generateB = new JButton("Regénérer");
        generateB.addActionListener(e -> {
            view.repaint();
        });

        JButton exitB = new JButton("Quitter");
        exitB.addActionListener(e -> {
            frame.dispose();
        });

        view.add(generateB);
        view.add(exitB);
    }
}
