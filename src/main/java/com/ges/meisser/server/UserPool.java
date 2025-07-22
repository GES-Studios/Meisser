package com.ges.meisser.server;

import com.ges.meisser.util.InvalidDataException;

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

    public void add(Socket socket) throws InvalidDataException, IOException {
        User user = new User(socket);
        user.validate();
        clear();
        users.add(user);
        user.startThread();
    }

    public void iterate(Consumer<User> consumer) {
        users.forEach(consumer);
    }

    private void clear() {
        users.removeIf(user -> !user.isActive());
    }
}
