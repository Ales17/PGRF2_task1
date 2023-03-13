package view;

import control.Controller3D;
import render.Renderer;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    private final Panel panel;
    private final JLabel label;


    public Window() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("UHK FIM PGRF");

        panel = new Panel();
        label = new JLabel("Keyboard controls in README");



        add(panel, "Center");

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
