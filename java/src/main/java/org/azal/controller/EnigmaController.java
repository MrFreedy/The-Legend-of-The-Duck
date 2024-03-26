package org.azal.controller;

import org.azal.model.EnigmaModel;
import org.azal.model.PrimModel;
import org.azal.utils.XMLReader;
import org.azal.view.EnigmaView;
import org.azal.view.PrimView;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.BoxLayout;
import java.awt.Color;
import java.util.Locale;

/**
 * The EnigmaController class serves as a controller in the Model-View-Controller (MVC) pattern for managing
 * the interaction between the EnigmaModel and EnigmaView components. It facilitates communication and control
 * flow between the model and view, and it may be associated with a JFrame for the enigma visualization.
 */
public class EnigmaController {
    /**
     * The EnigmaModel instance responsible for managing the data and business logic of the enigma.
     */
    private EnigmaModel model;
    /**
     * The EnigmaView instance responsible for displaying the graphical user interface of the enigma.
     */
    private EnigmaView view;
    /**
     * The JFrame instance associated with the enigma visualization, if applicable.
     */
    private JFrame frame;
    /**
     * The PrimView instance responsible for displaying the graphical user interface of the Prim algorithm.
     */
    private PrimView primView;
    /**
     * The PrimModel instance responsible for managing the data and business logic of the Prim algorithm.
     */
    private PrimModel primModel;

    /**
     * Constructs a new EnigmaController instance with the specified model, view, frame, primView, and primModel.
     *
     * @param model The EnigmaModel instance responsible for managing the data and business logic of the enigma.
     * @param view The EnigmaView instance responsible for displaying the graphical user interface of the enigma.
     * @param frame The JFrame instance associated with the enigma visualization, if applicable.
     * @param primView The PrimView instance responsible for displaying the graphical user interface of the Prim algorithm.
     * @param primModel The PrimModel instance responsible for managing the data and business logic of the Prim algorithm.
     */
    public EnigmaController(final EnigmaModel model, final EnigmaView view, final JFrame frame, final PrimView primView, final PrimModel primModel) {
        this.model = model;
        this.view = view;
        this.primView = primView;
        this.primModel = primModel;
        this.frame = frame;

        XMLReader xmlReaderConfig = new XMLReader("src/data/config.xml");
        XMLReader xmlReaderMessages = new XMLReader(String.format("src/data/language/%s.xml", xmlReaderConfig.getValue("language")));

        final String enigmaTitle = xmlReaderMessages.getValue("enigma", "title");
        final String confirmTextButton = xmlReaderMessages.getValue("button", "confirm");
        final String successAnswerMessage = xmlReaderMessages.getValue("success", "answer");
        final String errorAnswerMessage = xmlReaderMessages.getValue("error", "answer");

        String randomEnigma = model.getRandomEnigma();
        String[] parts = randomEnigma.split(";");
        String question = parts[0];
        String answer = parts[1];

        JPanel panelV = new JPanel();
        BoxLayout boxLayoutV = new BoxLayout(panelV, BoxLayout.Y_AXIS);
        panelV.setBackground(Color.WHITE);
        panelV.setLayout(boxLayoutV);



        JLabel label = new JLabel(enigmaTitle);
        label.setBounds(50, 50, 100, 30);
        panelV.add(label);

        JLabel labelQuestion = new JLabel(question);
        labelQuestion.setBounds(50, 75, 100, 30);
        labelQuestion.setText(question);
        panelV.add(labelQuestion);

        JTextField textField = new JTextField();
        textField.setBounds(50, 100, 100, 30);
        panelV.add(textField);

        JButton button = new JButton(confirmTextButton);
        button.setBounds(50, 150, 100, 30);
        button.addActionListener(e -> {
            String text = textField.getText();
            if (text.equals(answer.toUpperCase(Locale.FRANCE))) {
                JOptionPane.showMessageDialog(null, successAnswerMessage);
                primModel.setFighting(true);
                primView.repaint();
            } else {
                JOptionPane.showMessageDialog(null, errorAnswerMessage);
            }
            frame.dispose();
        });
        panelV.add(button);

        view.add(panelV);
    }

    /**
     * Starts the enigma controller.
     */
    public void start() {
        view.setVisible(true);
    }
}
