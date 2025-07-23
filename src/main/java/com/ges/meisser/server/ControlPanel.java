package com.ges.meisser.server;

import com.ges.meisser.util.Protocol;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class ControlPanel {
    private static final JFrame FRAME = new JFrame("Meisser Server - Control Panel");

    private static final JLabel
            HOST_LABEL = new JLabel(),
            PORT_LABEL = new JLabel(),
            BACKLOG_LABEL = new JLabel(),
            PROTOCOL_VERSION_LEVEL = new JLabel();
    private static final JButton EXIT = new JButton("Exit");

    private static final DefaultListModel<String> USER_LIST_MODEL = new DefaultListModel<>();
    private static final JList<String> USER_LIST = new JList<>(USER_LIST_MODEL);

    static void addUserToList(String name) {
        USER_LIST_MODEL.addElement(name);
    }
    static void removeUserFromList(String name) {
        USER_LIST_MODEL.removeElement(name);
    }


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
        container.setLayout(new GridLayout(1, 2));

        JPanel leftBox = new JPanel();
        leftBox.setLayout(new GridLayout(2, 1));
        leftBox.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        HOST_LABEL.setText("Host: " + Server.getAddress());
        PORT_LABEL.setText("Port: " + Server.getPort());
        BACKLOG_LABEL.setText("Backlog: " + Server.getBacklog());
        PROTOCOL_VERSION_LEVEL.setText("Protocol Version: " +
                Protocol.PROTOCOL_VERSION_MAJOR + "." + Protocol.PROTOCOL_VERSION_MINOR);

        infoPanel.add(HOST_LABEL);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(PORT_LABEL);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(BACKLOG_LABEL);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(PROTOCOL_VERSION_LEVEL);

        leftBox.add(infoPanel);
        leftBox.add(USER_LIST);

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
