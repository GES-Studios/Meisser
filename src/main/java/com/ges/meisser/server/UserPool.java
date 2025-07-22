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
        clear();
        users.add(user);
        user.startThread();
        ControlPanel.addUser(user.getName());
    }

    public boolean isUnique(String username) {
        return users.stream().map(User::getName).noneMatch(name -> name.equals(username));
    }

    public void iterate(Consumer<User> consumer) {
        users.forEach(consumer);
    }

    private void clear() {
        users.removeIf(user -> !user.isActive());
    }
}
