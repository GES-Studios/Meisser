package com.ges.meisser.client;

import com.ges.meisser.util.ProtocolException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

class UserInterface {
    private static final JFrame FRAME = new JFrame();

    private static final JTextArea MESSAGE_LINE = new JTextArea();
    private static final JButton SEND_BUTTON = new JButton("Send");

    private static final JTextArea MESSAGES_DISPLAY = new JTextArea();

    static void init() {
        FRAME.setTitle("Meisser - [HOST=" + Client.getAddress() + "] - " + Client.getUsername());
        FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        FRAME.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Main.quit(0);
            }
        });
        FRAME.setMinimumSize(new Dimension(800, 600));

        MESSAGES_DISPLAY.setEditable(false);

        SEND_BUTTON.addActionListener(e -> {
            String line = MESSAGE_LINE.getText().trim();
            if (line.isEmpty()) return;
            try {
                Client.sendMessage(line);
                MESSAGE_LINE.setText("");
            } catch (IOException | ProtocolException ex) { throw new RuntimeException(ex); }
        });

        draw();

        FRAME.pack();
        FRAME.setLocationRelativeTo(null);
    }

    private static void draw() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        panel.add(new JScrollPane(MESSAGES_DISPLAY), BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setPreferredSize(new Dimension(800, 50));
        inputPanel.add(new JScrollPane(MESSAGE_LINE), BorderLayout.CENTER);
        inputPanel.add(SEND_BUTTON, BorderLayout.EAST);

        panel.add(inputPanel, BorderLayout.SOUTH);

        FRAME.getContentPane().add(panel);
    }

    static void appendToMessageDisplay(String text) {
        MESSAGES_DISPLAY.setText(MESSAGES_DISPLAY.getText() + text);
    }

    static void display() {
        FRAME.setVisible(true);
    }
}
