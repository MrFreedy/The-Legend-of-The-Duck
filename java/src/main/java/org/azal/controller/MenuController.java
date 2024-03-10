package org.azal.controller;

import org.azal.model.BSPModel;
import org.azal.model.MenuModel;
import org.azal.model.PrimModel;
import org.azal.view.BSPView;
import org.azal.view.MenuView;
import org.azal.view.PrimView;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class MenuController {
    private MenuModel model;
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
            }else if (Objects.equals(comboBox.getSelectedItem(),"Algorithme BSP")){
                BSPModel modelBSP = new BSPModel();
                BSPView viewBSP = new BSPView(modelBSP);
                JFrame frame = new JFrame();
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setUndecorated(true);
                frame.setVisible(true);
                frame.add(viewBSP);
                viewBSP.requestFocus();
                BSPController controller = new BSPController(modelBSP,viewBSP,frame);
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
