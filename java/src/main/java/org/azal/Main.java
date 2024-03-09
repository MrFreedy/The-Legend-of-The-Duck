package org.azal;

import org.azal.controller.MenuController;
import org.azal.controller.PrimController;
import org.azal.model.MenuModel;
import org.azal.model.PrimModel;
import org.azal.view.MenuView;
import org.azal.view.PrimView;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        MenuModel model = new MenuModel();
        MenuView view = new MenuView(model);
        MenuController controller = new MenuController(model, view);
        JFrame frame = new JFrame();
        frame.add(view);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}