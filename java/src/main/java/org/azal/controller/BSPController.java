package org.azal.controller;

import org.azal.model.BSPModel;
import org.azal.utils.XMLReader;
import org.azal.view.BSPView;

import javax.swing.JFrame;
import javax.swing.JButton;

/**
 * The BSPController class serves as a controller in the Model-View-Controller (MVC) pattern for managing
 * the interaction between the BSPModel and BSPView components. It facilitates communication and control
 * flow between the model and view, and it may be associated with a JFrame for the BSP algorithm visualization.
 */
public class BSPController {
    /** The BSPModel instance responsible for managing the data and business logic of the BSP algorithm. */
    private BSPModel model;
    /** The BSPView instance responsible for displaying the graphical user interface of the BSP algorithm. */
    private BSPView view;
    /** The JFrame instance associated with the BSP algorithm visualization, if applicable. */
    private JFrame frame;

    /**
     * Constructs a new BSPController instance with the specified model, view, and frame.
     *
     * @param model The BSPModel instance responsible for managing the data and business logic of the BSP algorithm.
     * @param view The BSPView instance responsible for displaying the graphical user interface of the BSP algorithm.
     * @param frame The JFrame instance associated with the BSP algorithm visualization, if applicable.
     */
    public BSPController(final BSPModel model, final BSPView view, final JFrame frame) {
        this.model = model;
        this.view = view;
        this.frame = frame;

        XMLReader xmlReaderConfig = new XMLReader("src/data/config.xml");
        XMLReader xmlReaderMessages = new XMLReader(String.format("src/data/language/%s.xml", xmlReaderConfig.getValue("language")));

        final String regenerateTextButton = xmlReaderMessages.getValue("button", "regenerate");
        final String exitTextButton = xmlReaderMessages.getValue("button", "exit");

        JButton generateB = new JButton(regenerateTextButton);
        generateB.addActionListener(e -> {
            model.generateNewBSP();
            view.repaint();
        });

        JButton exitB = new JButton(exitTextButton);
        exitB.addActionListener(e -> {
            frame.dispose();
        });

        view.add(generateB);
        view.add(exitB);
    }
}
