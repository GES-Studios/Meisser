package com.ges.meisser.client;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicBoolean;

public final class RegistrationPanel {
    private static final JFrame FRAME = new JFrame("Meisser - Registration");

    private static final JLabel
            HOST_LABEL = new JLabel("Host: "),
            PORT_LABEL = new JLabel("Port: "),
            USERNAME_LABEL = new JLabel("Username: ");
    private static final JTextField
            HOST_FIELD = new JTextField(),
            PORT_FIELD = new JTextField(),
            USERNAME_FIELD = new JTextField();
    private static final JButton CONNECT_BUTTON = new JButton("Connect");
    private static final JTextArea LOG_AREA = new JTextArea();


    private static final AtomicBoolean DONE = new AtomicBoolean(false);

    static void waitTillUserInput() throws InterruptedException {
        while (!DONE.get()) Thread.sleep(250);
    }


    static String getHost() {
        return HOST_FIELD.getText();
    }
    static String getUsername() {
        return USERNAME_FIELD.getText();
    }
    static int getPort() {
        return Integer.parseInt(PORT_FIELD.getText());
    }


    static void init() {
        FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        FRAME.setMinimumSize(new Dimension(500, 400));

        LOG_AREA.setEnabled(false);
        LOG_AREA.setDisabledTextColor(Color.RED);

        CONNECT_BUTTON.addActionListener(e -> {
            try {
                RegistrationPanel.isValidPort(PORT_FIELD.getText());
            } catch (Exception ex) {
                LOG_AREA.setText("Enter a valid port\n" + ex.getMessage());
                PORT_FIELD.setText("");
                return;
            }

            DONE.set(true);
            CONNECT_BUTTON.setText("...");
        });

        draw();

        FRAME.pack();
        FRAME.setLocationRelativeTo(null);
    }

    private static void draw() {
        Container container = FRAME.getContentPane();
        GroupLayout layout = new GroupLayout(container);
        container.setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(HOST_LABEL)
                                .addComponent(PORT_LABEL)
                                .addComponent(USERNAME_LABEL)
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(HOST_FIELD)
                                .addComponent(PORT_FIELD)
                                .addComponent(USERNAME_FIELD)
                        )
                )
                .addComponent(CONNECT_BUTTON)
                .addComponent(LOG_AREA)
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(HOST_LABEL)
                                .addComponent(HOST_FIELD)
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(PORT_LABEL)
                                .addComponent(PORT_FIELD)
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(USERNAME_LABEL)
                                .addComponent(USERNAME_FIELD)
                        )
                )
                .addComponent(CONNECT_BUTTON)
                .addComponent(LOG_AREA)
        );
    }

    private static void isValidPort(String portStr) throws NumberFormatException, IllegalStateException {
        int port = Integer.parseInt(portStr);
        if (port < 0 || port > 65535) throw new IllegalStateException("Port must be in range 0-65535. You gave " + portStr);
    }

    static void display() {
        FRAME.setVisible(true);
    }
    static void close() {
        FRAME.setVisible(false);
    }

}
