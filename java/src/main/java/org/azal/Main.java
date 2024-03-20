package org.azal;

import org.azal.controller.MenuController;
import org.azal.model.MenuModel;
import org.azal.view.MenuView;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        int width=800;
        int height=600;
        MenuModel model = new MenuModel();
        MenuView view = new MenuView(model);
        MenuController controller = new MenuController(model, view);
        JFrame frame = new JFrame();
        frame.add(view);
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
