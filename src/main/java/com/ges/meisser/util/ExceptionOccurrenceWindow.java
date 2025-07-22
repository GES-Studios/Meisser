package com.ges.meisser.util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionOccurrenceWindow extends JFrame {
    private final JLabel errorLabel = new JLabel();
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

        setError(throwable.toString(), true);
        moreButton.addActionListener(e -> {
            setError(getError(throwable), false);
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

    private void setError(String content, boolean doRed) {
        content = content.replaceAll("\n", "<br>");
        errorLabel.setText(String.format("<html> %s %s %s </html>", doRed ? "<font color='red'>" : "",
                content, doRed ? "</font>" : ""));
    }
    public static String getError(Throwable throwable) {
        StringWriter sw = new StringWriter();
        throwable.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }

    private void draw() {
        Container container = this.getContentPane();
        container.setLayout(new BorderLayout());

        JPanel buttons = new JPanel(new GridLayout(1, 2));
        buttons.add(moreButton);
        buttons.add(okButton);

        container.add(errorLabel, BorderLayout.CENTER);
        container.add(buttons, BorderLayout.SOUTH);
    }
}
