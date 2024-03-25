package org.azal.controller;

import org.azal.model.PrimModel;
import org.azal.view.PrimView;
import javax.swing.JFrame;
import javax.swing.JButton;

/**
 * The PrimController class serves as a controller in the Model-View-Controller (MVC) pattern for managing
 * the interaction between the PrimModel and PrimView components. It facilitates communication and control
 * flow between the model and view, and it may be associated with a JFrame for the Prim algorithm visualization.
 */
public class PrimController {
    /** The PrimModel instance responsible for managing the data and business logic of the Prim algorithm. */
    private PrimModel model;

    /** The PrimView instance responsible for displaying the graphical user interface of the Prim algorithm. */
    private PrimView view;

    /** The JFrame instance associated with the Prim algorithm visualization, if applicable. */
    private JFrame frame;

    public boolean isGettingKey = false;

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

        JButton GetKeyButton = new JButton("Obtenir la clé");
        GetKeyButton.addActionListener(e -> {
            isGettingKey = true;
            model.getKey(isGettingKey);
            view.repaint();
        });

        view.add(GenerateButton);
        view.add(ExitButton);
        view.add(GetKeyButton);
    }
}
