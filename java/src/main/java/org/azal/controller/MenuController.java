package org.azal.controller;

import org.azal.model.BSPModel;
import org.azal.model.MenuModel;
import org.azal.model.PrimModel;
import org.azal.utils.XMLReader;
import org.azal.view.BSPView;
import org.azal.view.MenuView;
import org.azal.view.PrimView;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.BoxLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Objects;

/**
 * The MenuController class serves as a controller in the Model-View-Controller (MVC) pattern for managing
 * the interaction between the MenuModel and MenuView components. It facilitates communication and control
 * flow between the model and view, and it may be associated with a JFrame for the menu visualization.
 */
public class MenuController {
    /** The MenuModel instance responsible for managing the data and business logic of the menu. */
    private MenuModel model;
    /** The MenuView instance responsible for displaying the graphical user interface of the menu. */
    private MenuView view;

    /**
     * Constructs a new MenuController instance with the specified model and view.
     *
     * @param model The MenuModel instance responsible for managing the data and business logic of the menu.
     * @param view The MenuView instance responsible for displaying the graphical user interface of the menu.
     */
    public MenuController(final MenuModel model, final MenuView view) {
        this.model = model;
        this.view = view;

        XMLReader xmlReaderConfig = new XMLReader("src/data/config.xml");
        XMLReader xmlReaderMessages = new XMLReader(String.format("src/data/language/%s.xml", xmlReaderConfig.getValue("language")));

        final String confirmTextButton = xmlReaderMessages.getValue("button", "confirm");
        final String exitTextButton = xmlReaderMessages.getValue("button", "exit");

        final int comboWidth = Integer.parseInt(xmlReaderConfig.getValue("combo", "width"));
        final int comboHeight = Integer.parseInt(xmlReaderConfig.getValue("combo", "height"));
        Dimension comboSize = new Dimension(comboWidth, comboHeight);

        final String menuQuestion = xmlReaderMessages.getValue("menu", "question");
        final String primChoice = xmlReaderMessages.getValue("menu", "prim");
        final String bspChoice = xmlReaderMessages.getValue("menu", "bsp");

        JPanel panelV = new JPanel();
        BoxLayout boxLayoutV = new BoxLayout(panelV, BoxLayout.Y_AXIS);
        panelV.setBackground(Color.WHITE);
        panelV.setLayout(boxLayoutV);

        JLabel algorithmChoice = new JLabel(menuQuestion);

        JComboBox<String> comboBox = new JComboBox<>(
            new String[]{primChoice, bspChoice}
        );
        comboBox.setPreferredSize(comboSize);
        comboBox.setMaximumSize(comboSize);

        JPanel panelH = new JPanel();
        BoxLayout boxLayoutH = new BoxLayout(panelH, BoxLayout.X_AXIS);
        panelH.setBackground(Color.WHITE);
        panelH.setLayout(boxLayoutH);

        JButton validateButton = new JButton(confirmTextButton);
        validateButton.addActionListener(e -> {
            if (Objects.equals(comboBox.getSelectedItem(), primChoice)) {
                PrimModel modelPrim = new PrimModel();
                PrimView viewPrim = new PrimView(modelPrim);
                JFrame frame = new JFrame();
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setUndecorated(true);
                frame.setVisible(true);
                frame.add(viewPrim);
                viewPrim.requestFocus();
                PrimController controller = new PrimController(modelPrim, viewPrim, frame);
            } else if (Objects.equals(comboBox.getSelectedItem(), bspChoice)) {
                BSPModel modelBSP = new BSPModel();
                BSPView viewBSP = new BSPView(modelBSP);
                JFrame frame = new JFrame();
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setUndecorated(true);
                frame.setVisible(true);
                frame.add(viewBSP);
                viewBSP.requestFocus();
                BSPController controller = new BSPController(modelBSP, viewBSP, frame);
            }
        });

        JButton exitButton = new JButton(exitTextButton);
        exitButton.addActionListener(e -> {
            System.exit(0);
        });

        panelV.add(algorithmChoice);
        panelV.add(comboBox);
        panelV.add(panelH);
        panelH.add(validateButton);
        panelH.add(exitButton);

        view.add(panelV);

    }
}
