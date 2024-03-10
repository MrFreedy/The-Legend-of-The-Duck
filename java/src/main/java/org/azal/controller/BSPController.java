package org.azal.controller;

import org.azal.model.BSPModel;
import org.azal.view.BSPView;

import javax.swing.*;

public class BSPController {
    private BSPModel model;
    private BSPView view;
    private JFrame frame;

    public BSPController(BSPModel model, BSPView view, JFrame frame) {
        this.model = model;
        this.view = view;
        this.frame = frame;

        JButton generateB = new JButton("Regénérer");
        generateB.addActionListener(e -> {
            model.generateNewBSP();
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
