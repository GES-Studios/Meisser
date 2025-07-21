package com.ges.meisser.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class UserInterface {
    private static final JFrame FRAME = new JFrame();

    static void init() {
        FRAME.setTitle("Meisser - [HOST=" + Client.getAddress() + "] - " + Client.getUsername());
        FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        FRAME.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Main.quit(0);
            }
        });
        FRAME.setMinimumSize(new Dimension(800, 700));
        FRAME.pack();
        FRAME.setLocationRelativeTo(null);
    }

    static void display() {
        FRAME.setVisible(true);
    }
}
