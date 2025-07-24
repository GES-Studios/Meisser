package com.ges.meisser.server;

import com.ges.meisser.util.ProtocolException;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

class UserPool {
    private final List<User> users;

    public UserPool() {
        this.users = Collections.synchronizedList(new ArrayList<>());
    }

    public void add(Socket socket) throws ProtocolException, IOException {
        User user = new User(socket);
        user.validate();
        users.add(user);
        user.startThread();
        ControlPanel.addUserToList(user.getName());
    }
    public void remove(User user) {
        this.users.remove(user);
    }

    public boolean hasUsername(String username) {
        return users.stream().map(User::getName).anyMatch(name -> name.equals(username));
    }

    public void iterate(Consumer<User> consumer) {
        //No default Java implementations for synchronized list iterations
        synchronized (users) {
            users.forEach(consumer);
        }
    }
}
