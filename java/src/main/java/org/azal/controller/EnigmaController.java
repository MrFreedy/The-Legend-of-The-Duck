package org.azal.controller;

import org.azal.model.EnigmaModel;
import org.azal.model.PrimModel;
import org.azal.view.EnigmaView;
import org.azal.view.PrimView;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;

public class EnigmaController {
    private EnigmaModel model;
    private EnigmaView view;
    private JFrame frame;
    private PrimView primView;
    private PrimModel primModel;

    private final int widthDimension=200;
    private final int heightDimension=30;

    public EnigmaController(EnigmaModel model, EnigmaView view, JFrame frame, PrimView primView, PrimModel primModel) {
        this.model = model;
        this.view = view;
        this.primView = primView;
        this.primModel = primModel;
        this.frame = frame;

        String randomEnigma = model.getRandomEnigma();
        String[] parts = randomEnigma.split(";");
        String question = parts[0];
        String answer = parts[1];

        JPanel panelV = new JPanel();
        BoxLayout boxLayoutV = new BoxLayout(panelV, BoxLayout.Y_AXIS);
        panelV.setBackground(Color.WHITE);
        panelV.setLayout(boxLayoutV);


        JLabel label = new JLabel("Enigme");
        label.setBounds(50, 50, 100, 30);
        panelV.add(label);

        JLabel labelQuestion = new JLabel(question);
        labelQuestion.setBounds(50, 75, 100, 30);
        labelQuestion.setText(question);
        panelV.add(labelQuestion);

        JTextField textField = new JTextField();
        textField.setBounds(50, 100, 100, 30);
        panelV.add(textField);

        JButton button = new JButton("Valider");
        button.setBounds(50, 150, 100, 30);
        button.addActionListener(e -> {
            String text = textField.getText();
            if (text.equals(answer.toUpperCase(Locale.FRANCE))) {
                JOptionPane.showMessageDialog(null, "Bravo ! Vous avez vaincu le boss final !");
                primModel.fight(true);
                primView.repaint();
            } else {
                JOptionPane.showMessageDialog(null, "Dommage ! Vous avez perdu !");
            }
            frame.dispose();
        });
        panelV.add(button);

        view.add(panelV);
    }

    public void start() {
        view.setVisible(true);
    }
}
