package view;

import control.Controller3D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window extends JFrame {

    private final Panel panel;




    public Window() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("UHK FIM PGRF");

       panel = new Panel();
//        Controller3D controller3D = new Controller3D(panel);
//        JCheckBox checkBox = new JCheckBox("Wireframe mode");
//       JPanel controlPanel = new JPanel();
//       controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
//         controlPanel.add(checkBox);
//
//        add(controlPanel, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);

//       checkBox.addActionListener(e -> {
//           boolean isWireframe = checkBox.isSelected();
//           System.out.println("Wireframe mode: " + isWireframe);
//           controller3D.getRenderer().setWireframe(isWireframe);
//
//       });


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
