package view;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    private final Panel panel;
    private final JLabel label;
    private String rotatedObject;

    public Window() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("UHK FIM PGRF : " + this.getClass().getName());

        panel = new Panel();
        label = new JLabel("Keyboard controls in README" + rotatedObject);
        add(panel, BorderLayout.CENTER);
        add(label, "North");
        setVisible(true);
        pack();

        setLocationRelativeTo(null);

        panel.setFocusable(true);
        panel.grabFocus();
    }

    public Panel getPanel() {
        return panel;
    }


}
