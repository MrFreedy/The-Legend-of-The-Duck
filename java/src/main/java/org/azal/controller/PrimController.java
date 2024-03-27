package org.azal.controller;

import org.azal.model.EnigmaModel;
import org.azal.model.PrimModel;
import org.azal.utils.XMLReader;
import org.azal.view.EnigmaView;
import org.azal.view.PrimView;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JOptionPane;

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

    /** The boolean value indicating whether the player is getting the key. */
    private boolean isGettingKey = false;
    /** The boolean value indicating whether the player is fighting the boss. */
    private boolean isFight = false;
    /** The boolean value indicating whether the player has gotten the key. */
    private boolean keyGetted = false;
    /** The button for generating a new Prim algorithm. */
    private JButton generateButton;
    /** The button for exiting the Prim algorithm. */
    private JButton exitButton;
    /** The button for getting the key. */
    private JButton getKeyButton;
    /** The button for fighting the boss. */
    private JButton fightButton;
    /** The button for finishing the level. */
    private JButton finishButton;

    /**
     * Constructs a new PrimController instance with the specified model, view, and frame.
     *
     * @param model The PrimModel instance responsible for managing the data and business logic of the Prim algorithm.
     * @param view The PrimView instance responsible for displaying the graphical user interface of the Prim algorithm.
     * @param frame The JFrame instance associated with the Prim algorithm visualization, if applicable.
     */
    public PrimController(final PrimModel model, final PrimView view, final JFrame frame) {
        this.model = model;
        this.view = view;
        this.frame = frame;

        XMLReader xmlReaderConfig = new XMLReader("src/data/config.xml");
        XMLReader xmlReaderMessages = new XMLReader(String.format("src/data/language/%s.xml", xmlReaderConfig.getValue("language")));

        //Load config settings from the XML file
        final int enigmaWidth = Integer.parseInt(xmlReaderConfig.getValue("enigma", "width"));
        final int enigmaHeight = Integer.parseInt(xmlReaderConfig.getValue("enigma", "height"));

        // Load all the buttons and messages from the XML file
        final String regenerateTextButton = xmlReaderMessages.getValue("button", "regenerate");
        final String exitTextButton = xmlReaderMessages.getValue("button", "exit");
        final String getKeyTextButton = xmlReaderMessages.getValue("button", "getKey");
        final String fightTextButton = xmlReaderMessages.getValue("button", "fight");
        final String finishTextButton = xmlReaderMessages.getValue("button", "finish");

        final String successGetKeyMessage = xmlReaderMessages.getValue("success", "getKey");
        final String errorGetKeyMessage = xmlReaderMessages.getValue("error", "getKey");
        final String errorBossFightMessage = xmlReaderMessages.getValue("error", "fightBoss");
        final String alreadyFightMessage = xmlReaderMessages.getValue("error", "alreadyFight");
        final String successFinishMessage = xmlReaderMessages.getValue("success", "finish");
        final String errorFinishMessage = xmlReaderMessages.getValue("error", "finish");


        generateButton = new JButton(regenerateTextButton);
        generateButton.addActionListener(e -> {
            model.setBuilding(true);
            model.setBossDead(false);
            keyGetted = false;
            view.repaint();
        });

        exitButton = new JButton(exitTextButton);
        exitButton.addActionListener(e -> {
            frame.dispose();
        });

        getKeyButton = new JButton(getKeyTextButton);
        getKeyButton.addActionListener(e -> {
            if (!keyGetted) {
                isGettingKey = true;
                model.setGettingKey(isGettingKey);
                view.repaint();
                keyGetted = true;

                JOptionPane.showMessageDialog(null, successGetKeyMessage);
            } else {
                JOptionPane.showMessageDialog(null, errorGetKeyMessage);
            }

        });

        fightButton = new JButton(fightTextButton);
        fightButton.addActionListener(e -> {
            if (!keyGetted) {
                JOptionPane.showMessageDialog(null, errorBossFightMessage);
            } else if (model.isBossDead()) {
                JOptionPane.showMessageDialog(null, alreadyFightMessage);
            } else {
                isFight = true;

                EnigmaModel enigmaModel = new EnigmaModel();
                EnigmaView enigmaView = new EnigmaView(enigmaModel);
                JFrame enigmaFrame = new JFrame();
                EnigmaController enigmaController = new EnigmaController(enigmaModel, enigmaView, enigmaFrame, view, model);

                enigmaFrame.setSize(enigmaWidth, enigmaHeight);
                enigmaFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                enigmaFrame.add(enigmaView);
                enigmaFrame.setVisible(true);

            }
        });

        finishButton = new JButton(finishTextButton);
        finishButton.addActionListener(e -> {
            if (model.isBossDead()) {
                JOptionPane.showMessageDialog(null, successFinishMessage);
                frame.dispose();
            } else {
                JOptionPane.showMessageDialog(null, errorFinishMessage);
            }
        });


        view.add(generateButton);
        view.add(exitButton);
        view.add(getKeyButton);
        view.add(fightButton);
        view.add(finishButton);
    }

    /**
     * Retrieves the button for generating a new Prim algorithm.
     *
     * @return The button for generating a new Prim algorithm.
     */
    public JButton getGenerateButton() {
        return generateButton;
    }

    /**
     * Sets the button for generating a new Prim algorithm.
     *
     * @param generateButton The button for generating a new Prim algorithm.
     */
    public void setGenerateButton(final JButton generateButton) {
        this.generateButton = generateButton;
    }

    /**
     * Retrieves the button for exiting the Prim algorithm.
     *
     * @return The button for exiting the Prim algorithm.
     */
    public JButton getExitButton() {
        return exitButton;
    }

    /**
     * Sets the button for exiting the Prim algorithm.
     *
     * @param exitButton The button for exiting the Prim algorithm.
     */
    public void setExitButton(final JButton exitButton) {
        this.exitButton = exitButton;
    }

    /**
     * Retrieves the button for getting the key.
     *
     * @return The button for getting the key.
     */
    public JButton getGetKeyButton() {
        return getKeyButton;
    }

    /**
     * Sets the button for getting the key.
     *
     * @param getKeyButton The button for getting the key.
     */
    public void setGetKeyButton(final JButton getKeyButton) {
        this.getKeyButton = getKeyButton;
    }
}
