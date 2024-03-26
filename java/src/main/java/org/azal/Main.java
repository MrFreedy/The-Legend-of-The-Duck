package org.azal;

import org.azal.controller.MenuController;
import org.azal.model.MenuModel;
import org.azal.utils.XMLReader;
import org.azal.view.MenuView;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        XMLReader xmlReaderConfig = new XMLReader("src/data/config.xml");
        final int width = Integer.parseInt(xmlReaderConfig.getValue("menu","width"));
        final int height = Integer.parseInt(xmlReaderConfig.getValue("menu","height"));
        
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
