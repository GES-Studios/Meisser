package com.ges.meisser.server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class ControlPanel {
    private static final JFrame FRAME = new JFrame("Meisser Server - Control Panel");

    private static final JLabel
            HOST_LABEL = new JLabel(),
            PORT_LABEL = new JLabel(),
            BACKLOG_LABEL = new JLabel();
    private static final JButton EXIT = new JButton("Exit");

    static void init() {
        FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        FRAME.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Main.quit(0);
            }
        });
        FRAME.setMinimumSize(new Dimension(500, 400));

        EXIT.addActionListener(e -> Main.quit(0));
        EXIT.setAlignmentX(Component.CENTER_ALIGNMENT);

        draw();
        FRAME.pack();
        FRAME.setLocationRelativeTo(null);
    }

    private static void draw() {
        Container container = FRAME.getContentPane();
        GridLayout layout = new GridLayout(1, 2);
        container.setLayout(layout);

        JPanel leftBox = new JPanel();
        leftBox.setLayout(new BoxLayout(leftBox, BoxLayout.Y_AXIS));
        leftBox.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        HOST_LABEL.setText("Host: " + Server.getAddress());
        PORT_LABEL.setText("Port: " + Server.getPort());
        BACKLOG_LABEL.setText("Backlog: " + Server.getBacklog());

        leftBox.add(HOST_LABEL);
        leftBox.add(Box.createVerticalStrut(10));
        leftBox.add(PORT_LABEL);
        leftBox.add(Box.createVerticalStrut(10));
        leftBox.add(BACKLOG_LABEL);

        JPanel rightBox = new JPanel();
        rightBox.setLayout(new BoxLayout(rightBox, BoxLayout.Y_AXIS));
        rightBox.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        rightBox.add(EXIT);

        container.add(leftBox);
        container.add(rightBox);
    }

    static void display() {
        FRAME.setVisible(true);
    }
}
