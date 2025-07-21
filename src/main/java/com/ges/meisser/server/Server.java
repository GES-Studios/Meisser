package com.ges.meisser.server;

import com.ges.meisser.util.Protocol;

import javax.swing.*;
import java.io.IOException;
import java.net.*;

public final class Server {
    private static ServerSocket server;

    private static void bind() throws IOException {
        server = new ServerSocket(Protocol.DEFAULT_PORT, Protocol.DEFAULT_BACKLOG,
                new InetSocketAddress(Protocol.INADDR_ANY, Protocol.DEFAULT_PORT).getAddress());
        System.out.println("Server was bound to " + server.getInetAddress() + ":" + server.getLocalPort());
    }

    private static void accept() throws IOException {
        while (true) {
            try {
                Socket socket = server.accept();
                System.out.println("Client connected: " + socket.toString());
            } catch (SocketException ignored) { break; }
        }
    }

    public static void startup() throws Exception {
        bind();
        SwingUtilities.invokeLater(() -> {
            ControlPanel.init();
            ControlPanel.display();
        });
        accept();
    }

    public static int getPort() {
        return server.getLocalPort();
    }
    public static InetAddress getAddress() {
        return server.getInetAddress();
    }
    public static int getBacklog() {
        return Protocol.DEFAULT_BACKLOG;
    }

    public static void close() throws Exception {
        if (!server.isClosed()) server.close();
        System.out.println("Server was closed");
    }
}
