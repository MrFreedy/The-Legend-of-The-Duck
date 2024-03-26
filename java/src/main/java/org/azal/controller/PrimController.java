package org.azal.controller;

import org.azal.model.EnigmaModel;
import org.azal.model.PrimModel;
import org.azal.utils.XMLReader;
import org.azal.view.EnigmaView;
import org.azal.view.PrimView;

import javax.swing.*;

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
    public boolean isFight = false;
    private boolean keyGetted = false;
    private JButton GenerateButton;
    private JButton ExitButton;
    private JButton GetKeyButton;
    private JButton FightButton;
    private JButton FinishButton;

    public PrimController(PrimModel model, PrimView view, JFrame frame) {
        this.model = model;
        this.view = view;
        this.frame = frame;

        XMLReader xmlReaderConfig = new XMLReader("src/data/config.xml");
        XMLReader xmlReaderMessages = new XMLReader(String.format("src/data/language/%s.xml", xmlReaderConfig.getValue("language")));

        //Load config settings from the XML file
        final int ENIGMA_WIDTH = Integer.parseInt(xmlReaderConfig.getValue("enigma","width"));
        final int ENIGMA_HEIGHT = Integer.parseInt(xmlReaderConfig.getValue("enigma","height"));

        // Load all the buttons and messages from the XML file
        final String regenerateTextButton = xmlReaderMessages.getValue("button","regenerate");
        final String exitTextButton = xmlReaderMessages.getValue("button","exit");
        final String getKeyTextButton = xmlReaderMessages.getValue("button","getKey");
        final String fightTextButton = xmlReaderMessages.getValue("button","fight");
        final String finishTextButton = xmlReaderMessages.getValue("button","finish");

        final String successGetKeyMessage = xmlReaderMessages.getValue("success","getKey");
        final String errorGetKeyMessage = xmlReaderMessages.getValue("error","getKey");
        final String errorBossFightMessage = xmlReaderMessages.getValue("error","fightBoss");
        final String successFinishMessage = xmlReaderMessages.getValue("success","finish");


        GenerateButton = new JButton(regenerateTextButton);
        GenerateButton.addActionListener(e -> {
            model.setBuilding(true);
            model.setBossDead(false);
            keyGetted = false;
            view.repaint();
        });

        ExitButton = new JButton(exitTextButton);
        ExitButton.addActionListener(e -> {
            frame.dispose();
        });

        GetKeyButton = new JButton(getKeyTextButton);
        GetKeyButton.addActionListener(e -> {
            if(!keyGetted){
                isGettingKey = true;
                model.getKey(isGettingKey);
                view.repaint();
                keyGetted = true;

                JOptionPane.showMessageDialog(null, successGetKeyMessage);
            }else{
                JOptionPane.showMessageDialog(null, errorGetKeyMessage);
            }

        });

        FightButton = new JButton(fightTextButton);
        FightButton.addActionListener(e -> {
            if(!keyGetted){
                JOptionPane.showMessageDialog(null, errorBossFightMessage);
            }else{
                isFight = true;

                EnigmaModel enigmaModel = new EnigmaModel();
                EnigmaView enigmaView = new EnigmaView(enigmaModel);
                JFrame enigmaFrame = new JFrame();
                EnigmaController enigmaController = new EnigmaController(enigmaModel, enigmaView,enigmaFrame, view,model);

                enigmaFrame.setSize(ENIGMA_WIDTH, ENIGMA_HEIGHT);
                enigmaFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                enigmaFrame.add(enigmaView);
                enigmaFrame.setVisible(true);

            }
        });

        FinishButton = new JButton(finishTextButton);
        FinishButton.addActionListener(e -> {
            if(model.isBossDead()){
                JOptionPane.showMessageDialog(null, successFinishMessage);
                frame.dispose();
            }else{
                JOptionPane.showMessageDialog(null, "");
            }
        });


        view.add(GenerateButton);
        view.add(ExitButton);
        view.add(GetKeyButton);
        view.add(FightButton);
        view.add(FinishButton);
    }

    public JButton getGenerateButton() {
        return GenerateButton;
    }

    public void setGenerateButton(JButton generateButton) {
        GenerateButton = generateButton;
    }

    public JButton getExitButton() {
        return ExitButton;
    }

    public void setExitButton(JButton exitButton) {
        ExitButton = exitButton;
    }

    public JButton getGetKeyButton() {
        return GetKeyButton;
    }

    public void setGetKeyButton(JButton getKeyButton) {
        GetKeyButton = getKeyButton;
    }
}
