package org.azal.view;

import org.azal.model.MenuModel;
import javax.swing.JPanel;
import java.awt.Color;

public class MenuView extends JPanel {
    private MenuModel model;
    public MenuView(MenuModel model) {
        super();
        this.model=model;
        setBackground(Color.WHITE);
    }
}
