package org.azal.view;

import org.azal.model.EnigmaModel;

import javax.swing.*;
import java.awt.Color;

public class EnigmaView  extends JPanel {
    private EnigmaModel model;

    public EnigmaView(EnigmaModel model) {
        super();
        this.model=model;
        setBackground(Color.WHITE);
    }
}
