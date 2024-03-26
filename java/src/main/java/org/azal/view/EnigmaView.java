package org.azal.view;

import org.azal.model.EnigmaModel;

import javax.swing.JPanel;
import java.awt.Color;

public class EnigmaView  extends JPanel {
    /** The EnigmaModel instance associated with this view for managing Enigma-related data and logic. */
    private EnigmaModel model;

    /**
     * Constructs a new EnigmaView instance associated with the specified EnigmaModel.
     *
     * @param model The EnigmaModel instance associated with this view for managing Enigma-related data and logic.
     */
    public EnigmaView(final EnigmaModel model) {
        super();
        this.model = model;
        setBackground(Color.WHITE);
    }
}
