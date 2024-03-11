package org.azal.controller;

import org.azal.model.MenuModel;
import org.azal.model.PrimModel;
import org.azal.view.MenuView;
import org.azal.view.PrimView;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Objects;

/**
 * The MenuController class serves as a controller in the Model-View-Controller (MVC) pattern for managing
 * the interaction between the MenuModel and MenuView components. It facilitates communication and control
 * flow between the model and view of a menu-related application.
 */
public class MenuController {
    /** The MenuModel instance responsible for managing the data and business logic of the menu. */
    private MenuModel model;

    /** The MenuView instance responsible for displaying the graphical user interface of the menu. */
    private MenuView view;

    Dimension comboSize = new Dimension(200, 30);

    public MenuController(MenuModel model, MenuView view) {
        this.model = model;
        this.view = view;

        JPanel panelV = new JPanel();
        BoxLayout boxLayoutV = new BoxLayout(panelV, BoxLayout.Y_AXIS);
        panelV.setBackground(Color.WHITE);
        panelV.setLayout(boxLayoutV);

        JLabel label = new JLabel("Quel type d'algorithme de génération souhaitez-vous utiliser ?");

        JComboBox<String> comboBox = new JComboBox<>(
            new String[]{"Algorithme de Prim", "Algorithme BSP"}
        );
        comboBox.setPreferredSize(comboSize);
        comboBox.setMaximumSize(comboSize);

        JPanel panelH = new JPanel();
        BoxLayout boxLayoutH = new BoxLayout(panelH, BoxLayout.X_AXIS);
        panelH.setBackground(Color.WHITE);
        panelH.setLayout(boxLayoutH);

        JButton validateB = new JButton("Valider");
        validateB.addActionListener(e -> {
            if(Objects.equals(comboBox.getSelectedItem(), "Algorithme de Prim")){
                PrimModel modelPrim = new PrimModel();
                PrimView viewPrim = new PrimView(modelPrim);
                JFrame frame = new JFrame();
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setUndecorated(true);
                frame.setVisible(true);
                frame.add(viewPrim);
                viewPrim.requestFocus();
                PrimController controller = new PrimController(modelPrim, viewPrim,frame);
            }
        });

        JButton exitB = new JButton("Quitter");
        exitB.addActionListener(e -> {
            System.exit(0);
        });

        panelV.add(label);
        panelV.add(comboBox);
        panelV.add(panelH);
        panelH.add(validateB);
        panelH.add(exitB);

        view.add(panelV);

    }
}
