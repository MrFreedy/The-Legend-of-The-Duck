package org.azal.view;

import org.azal.model.MenuModel;
import javax.swing.JPanel;
import java.awt.Color;

/**
 * The MenuView class represents a JPanel for displaying the graphical user interface of a menu in
 * conjunction with the Model-View-Controller (MVC) pattern. It is associated with a MenuModel to
 * visualize and interact with menu-related data and functionalities.
 */
public class MenuView extends JPanel {
    /** The MenuModel instance associated with this view for managing menu-related data and logic. */
    private MenuModel model;

    public MenuView(MenuModel model) {
        super();
        this.model=model;
        setBackground(Color.WHITE);
    }
}
