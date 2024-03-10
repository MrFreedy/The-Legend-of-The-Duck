package org.azal.view;

import org.azal.model.MenuModel;

import javax.swing.*;
import java.awt.*;

public class MenuView extends JPanel {
    private MenuModel model;
    public MenuView(MenuModel model) {
        super();
        this.model=model;
        setBackground(Color.WHITE);
    }
}
