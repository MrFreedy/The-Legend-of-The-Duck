package org.azal.controller;

import org.azal.model.BSPModel;
import org.azal.model.MenuModel;
import org.azal.model.PrimModel;
import org.azal.utils.XMLReader;
import org.azal.view.BSPView;
import org.azal.view.MenuView;
import org.azal.view.PrimView;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class MenuController {
    private MenuModel model;
    private MenuView view;

    public MenuController(MenuModel model, MenuView view) {
        this.model = model;
        this.view = view;

        XMLReader xmlReaderConfig = new XMLReader("src/data/config.xml");
        XMLReader xmlReaderMessages = new XMLReader(String.format("src/data/language/%s.xml", xmlReaderConfig.getValue("language")));

        final String confirmTextButton = xmlReaderMessages.getValue("button","confirm");
        final String exitTextButton = xmlReaderMessages.getValue("button","exit");

        final int COMBO_WIDTH =Integer.parseInt(xmlReaderConfig.getValue("combo","width"));
        final int COMBO_HEIGHT =Integer.parseInt(xmlReaderConfig.getValue("combo","height"));
        Dimension comboSize = new Dimension(COMBO_WIDTH, COMBO_HEIGHT);

        final String menuQuestion = xmlReaderMessages.getValue("menu","question");
        final String primChoice = xmlReaderMessages.getValue("menu","prim");
        final String bspChoice = xmlReaderMessages.getValue("menu","bsp");

        JPanel panelV = new JPanel();
        BoxLayout boxLayoutV = new BoxLayout(panelV, BoxLayout.Y_AXIS);
        panelV.setBackground(Color.WHITE);
        panelV.setLayout(boxLayoutV);

        JLabel AlgorithmChoice = new JLabel(menuQuestion);

        JComboBox<String> comboBox = new JComboBox<>(
            new String[]{primChoice, bspChoice}
        );
        comboBox.setPreferredSize(comboSize);
        comboBox.setMaximumSize(comboSize);

        JPanel panelH = new JPanel();
        BoxLayout boxLayoutH = new BoxLayout(panelH, BoxLayout.X_AXIS);
        panelH.setBackground(Color.WHITE);
        panelH.setLayout(boxLayoutH);

        JButton ValidateButton = new JButton(confirmTextButton);
        ValidateButton.addActionListener(e -> {
            if(Objects.equals(comboBox.getSelectedItem(), primChoice)){
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
            }else if (Objects.equals(comboBox.getSelectedItem(), bspChoice)){
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

        JButton ExitButton = new JButton(exitTextButton);
        ExitButton.addActionListener(e -> {
            System.exit(0);
        });

        panelV.add(AlgorithmChoice);
        panelV.add(comboBox);
        panelV.add(panelH);
        panelH.add(ValidateButton);
        panelH.add(ExitButton);

        view.add(panelV);

    }
}
