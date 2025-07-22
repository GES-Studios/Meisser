package com.ges.meisser.server;

import javax.swing.*;
import java.awt.*;

public class UserList extends JPanel {
    private final DefaultListModel<String> model;

    public UserList() {
        this.model = new DefaultListModel<>();
        this.add(new JScrollPane(new JList<>(model)));
    }

    public void addUsername(String username) {
        model.addElement(username);
    }
    public void removeUsername(String username) {
        model.removeElement(username);
    }
}
