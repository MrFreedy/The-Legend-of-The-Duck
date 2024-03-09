package org.azal;

import org.azal.controller.PrimController;
import org.azal.model.PrimModel;
import org.azal.view.PrimView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        PrimModel model = new PrimModel();
        PrimView view = new PrimView(model);
        PrimController controller = new PrimController(model, view);
        JFrame frame = new JFrame();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
        frame.setVisible(true);
        frame.add(view);
        view.requestFocus();
    }
}