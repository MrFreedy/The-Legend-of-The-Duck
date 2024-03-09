package org.azal.controller;

import org.azal.model.PrimModel;
import org.azal.view.PrimView;

import javax.swing.*;
import java.awt.*;

public class PrimController{
    private PrimModel model;
    private PrimView view;

    public PrimController(PrimModel model, PrimView view) {
        this.model = model;
        this.view = view;
    }
}
