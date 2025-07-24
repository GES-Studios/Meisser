package com.ges.meisser.util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionOccurrenceWindow extends JFrame {
    private final JPanel panel;

    private final JLabel errorLabel = new JLabel();
    private final JTextArea errorLog = new JTextArea();
    private final JButton moreButton = new JButton("More");
    private final JButton okButton = new JButton("OK");

    public ExceptionOccurrenceWindow(Throwable throwable, Runnable onExit) {
        this.setTitle("Error occurred");
        this.setMinimumSize(new Dimension(300, 200));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onExit.run();
            }
        });

        this.panel = new JPanel(new BorderLayout());

        errorLabel.setText("<html> <font color='red'> " + throwable.getMessage() + " </font> </html>");
        errorLog.setText(getError(throwable));
        errorLog.setEditable(false);

        moreButton.addActionListener(e -> {
            ExceptionOccurrenceWindow.this.panel.remove(errorLabel);
            ExceptionOccurrenceWindow.this.panel.add(errorLog, BorderLayout.CENTER);
            ExceptionOccurrenceWindow.this.pack();
            ExceptionOccurrenceWindow.this.setLocationRelativeTo(null);
        });
        okButton.addActionListener(e -> {
            ExceptionOccurrenceWindow.this.dispose();
            onExit.run();
        });

        draw();

        this.pack();
        this.setLocationRelativeTo(null);
    }

    private void draw() {
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JPanel buttons = new JPanel(new GridLayout(1, 2));
        buttons.add(moreButton);
        buttons.add(okButton);

        panel.add(errorLabel, BorderLayout.CENTER);
        panel.add(buttons, BorderLayout.SOUTH);

        this.getContentPane().add(panel);
    }

    public static String getError(Throwable throwable) {
        StringWriter sw = new StringWriter();
        throwable.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
}
