package com.ges.meisser.server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ControlPanel {
    private static JFrame frame;

    public static void init() {
        frame = new JFrame("Meisser Server - Control Panel");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Main.quit(0);
            }
        });
        frame.setMinimumSize(new Dimension(500, 400));
        draw();
        frame.pack();
        frame.setLocationRelativeTo(null);
    }

    private static void draw() {
        Container container = frame.getContentPane();
        GridLayout layout = new GridLayout(1, 2);
        container.setLayout(layout);

        JPanel leftBox = new JPanel();
        leftBox.setLayout(new BoxLayout(leftBox, BoxLayout.Y_AXIS));
        leftBox.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        leftBox.add(new JLabel("Host: " + Server.getAddress()));
        leftBox.add(Box.createVerticalStrut(10));
        leftBox.add(new JLabel("Port: " + Server.getPort()));
        leftBox.add(Box.createVerticalStrut(10));
        leftBox.add(new JLabel("Backlog: " + Server.getBacklog()));


        JPanel rightBox = new JPanel();
        rightBox.setLayout(new BoxLayout(rightBox, BoxLayout.Y_AXIS));
        rightBox.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        rightBox.add(button("Exit", e -> Main.quit(0)));

        container.add(leftBox);
        container.add(rightBox);
    }

    private static JButton button(String title, ActionListener listener) {
        JButton button = new JButton(title);
        button.addActionListener(listener);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        return button;
    }

    public static void display() {
        frame.setVisible(true);
    }
}
