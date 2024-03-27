package org.azal.controller;

import org.azal.model.BSPModel;
import org.azal.model.EnigmaModel;
import org.azal.utils.XMLReader;
import org.azal.view.BSPView;
import org.azal.view.EnigmaView;

import javax.swing.*;

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

    private boolean keyGetted = false;
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

        final int enigmaWidth = Integer.parseInt(xmlReaderConfig.getValue("enigma", "width"));
        final int enigmaHeight = Integer.parseInt(xmlReaderConfig.getValue("enigma", "height"));

        // Load all the buttons and messages from the XML file
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

        JButton exitB = new JButton(exitTextButton);
        exitB.addActionListener(e -> {
            frame.dispose();
        });

        JButton getKeyButton = new JButton(getKeyTextButton);
        getKeyButton.addActionListener(e -> {
            if (!keyGetted) {
                model.setGettingKey(true);
                view.repaint();
                keyGetted = true;
                view.repaint();
                JOptionPane.showMessageDialog(null, successGetKeyMessage);
            } else {
                JOptionPane.showMessageDialog(null, errorGetKeyMessage);
            }
        });

        JButton fightButton = new JButton(fightTextButton);
        fightButton.addActionListener(e -> {
            if (!keyGetted) {
                JOptionPane.showMessageDialog(null, errorBossFightMessage);
            } else if (model.isBossDead()) {
                JOptionPane.showMessageDialog(null, alreadyFightMessage);
            } else {
                model.setFight(true);

                EnigmaModel enigmaModel = new EnigmaModel();
                EnigmaView enigmaView = new EnigmaView(enigmaModel);
                JFrame enigmaFrame = new JFrame();
                enigmaFrame.setLocationRelativeTo(null);
                EnigmaController enigmaController = new EnigmaController(enigmaModel, enigmaView, enigmaFrame, view, model);

                enigmaFrame.setSize(enigmaWidth, enigmaHeight);
                enigmaFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                enigmaFrame.add(enigmaView);
                enigmaFrame.setVisible(true);
            }
        });

        JButton finishButton = new JButton(finishTextButton);
        finishButton.addActionListener(e -> {
            if (model.isBossDead()) {
                view.repaint();
                JOptionPane.showMessageDialog(null, successFinishMessage);
                frame.dispose();
            } else {
                JOptionPane.showMessageDialog(null, errorFinishMessage);
            }
        });

        view.add(exitB);
        view.add(getKeyButton);
        view.add(fightButton);
        view.add(finishButton);
    }
}
