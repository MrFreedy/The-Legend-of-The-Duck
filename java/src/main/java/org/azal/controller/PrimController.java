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

        JButton GenerateButton = new JButton("Regénérer");
        GenerateButton.addActionListener(e -> {
            view.repaint();
        });

        JButton ExitButton = new JButton("Quitter");
        ExitButton.addActionListener(e -> {
            frame.dispose();
        });

        view.add(GenerateButton);
        view.add(ExitButton);
    }
}
