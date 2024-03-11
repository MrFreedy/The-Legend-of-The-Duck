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
