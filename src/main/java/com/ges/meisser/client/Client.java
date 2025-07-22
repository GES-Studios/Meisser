package com.ges.meisser.client;

import javax.swing.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

final class Client {
    private static Socket client;
    private static String username;


    static void connect(String host, int port) throws IOException {
        if (client == null) client = new Socket(host, port);
        System.out.printf("Client connected to %s:%d\n", host, port);
    }

    static void startup() throws Exception {
        SwingUtilities.invokeLater(() -> {
            RegistrationPanel.init();
            RegistrationPanel.display();
        });
        RegistrationPanel.waitTillUserInput();
        connect(RegistrationPanel.getHost(), RegistrationPanel.getPort());
        Client.username = RegistrationPanel.getUsername();
        RegistrationPanel.close();

        SwingUtilities.invokeLater(() -> {
            UserInterface.init();
            UserInterface.display();
        });
    }

    static InetAddress getAddress() {
        return client.getInetAddress();
    }
    static String getUsername() {
        return username;
    }

    static void close() throws Exception {
        if (!client.isClosed()) client.close();
        System.out.println("Client was closed");
    }
}
