package org.azal.controller;

import org.azal.model.EnigmaModel;
import org.azal.model.MenuModel;
import org.azal.model.PrimModel;
import org.azal.view.EnigmaView;
import org.azal.view.MenuView;
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

    public PrimController(PrimModel model, PrimView view, JFrame frame) {
        this.model = model;
        this.view = view;
        this.frame = frame;

        GenerateButton = new JButton("Regénérer");
        GenerateButton.addActionListener(e -> {
            view.repaint();
        });

        ExitButton = new JButton("Quitter");
        ExitButton.addActionListener(e -> {
            frame.dispose();
        });

        GetKeyButton = new JButton("Obtenir la clé");
        GetKeyButton.addActionListener(e -> {
            isGettingKey = true;
            model.getKey(isGettingKey);
            view.repaint();
            keyGetted = true;

            JOptionPane.showMessageDialog(null, "Vous avez ramassé la clé");
        });

        FightButton = new JButton("Combattre");
        FightButton.addActionListener(e -> {
            if(!keyGetted){
                JOptionPane.showMessageDialog(null, "Vous devez obtenir la clé avant de combattre le boss final !");
            }else{
                isFight = true;

                EnigmaModel enigmaModel = new EnigmaModel();
                EnigmaView enigmaView = new EnigmaView(enigmaModel);
                JFrame enigmaFrame = new JFrame();
                EnigmaController enigmaController = new EnigmaController(enigmaModel, enigmaView,enigmaFrame, view,model);

                enigmaFrame.setSize(800, 200);
                enigmaFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                enigmaFrame.add(enigmaView);
                enigmaFrame.setVisible(true);

            }
        });

        view.add(GenerateButton);
        view.add(ExitButton);
        view.add(GetKeyButton);
        view.add(FightButton);
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
