package com.ges.meisser.client;

import com.ges.meisser.network.PacketInput;
import com.ges.meisser.network.PacketOutput;
import com.ges.meisser.util.ProtocolException;
import static com.ges.meisser.util.Protocol.*;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

final class Client {
    private static Socket client;
    private static OutputStream output;
    private static InputStream input;
    private static String username;


    static void connect(String host, int port) throws IOException {
        if (client == null) client = new Socket(host, port);
        output = client.getOutputStream();
        input = client.getInputStream();
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

        handshake();

        SwingUtilities.invokeLater(() -> {
            UserInterface.init();
            UserInterface.display();
        });

        listenServer();
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

    static void handshake() throws ProtocolException, IOException {
        PacketOutput request = new PacketOutput(HANDSHAKE_REQUEST_LENGTH);
        request.write(HANDSHAKE_INIT_CODE);
        request.write(PROTOCOL_VERSION_MAJOR);
        request.write(PROTOCOL_VERSION_MINOR);
        request.writeUTF(username);

        request.send(output);

        PacketInput response = new PacketInput(HANDSHAKE_RESPONSE_LENGTH);
        response.receive(input);

        int error = response.readUnsignedByte();
        if (error == HANDSHAKE_SERVER_RESPONSE_STATUS_INCOMPATIBLE_PROTOCOL_VERSION)
            throw new ProtocolException("Server, you are trying to connect to, uses different protocol version: " +
                    response.readUnsignedByte() + "." + response.readUnsignedByte() +
                    ". Your one is " + PROTOCOL_VERSION_MAJOR + "." + PROTOCOL_VERSION_MINOR +
                    ". Try update/downgrade your client app version");
        else if (error == HANDSHAKE_SERVER_RESPONSE_STATUS_DUPLICATE_USERNAME)
            throw new ProtocolException("User with name '" + username + "' is already on the server");
        else if (error != HANDSHAKE_SERVER_RESPONSE_STATUS_OK)
            throw new ProtocolException("Server sent unrecognized error code: " + error);
    }

    static void sendMessage(String message) throws IOException, ProtocolException {
        PacketOutput request = new PacketOutput(MESSAGE_REQUEST_LENGTH);
        request.write(SENDING_MESSAGE_INIT_CODE);
        request.writeUTF(message);
        request.send(output);
    }

    static void listenServer() {
        while (true) {
            try {
                PacketInput response = new PacketInput(MESSAGE_BROADCAST_LENGTH);
                int status = response.receive(input);
                if (status == -1) throw new RuntimeException("Server closed connection");

                analyzeResponsePacket(response);
            } catch (IOException e) { break; }
        }
    }

    private static void analyzeResponsePacket(PacketInput packet) throws IOException {
        int code = packet.readUnsignedByte();

        if (code == BROADCASTING_MESSAGE_INIT_CODE) {
            String user = packet.readUTF();
            String message = packet.readUTF();
            receiveMessage(user, message);
        }
        else System.err.println("Server sent unrecognized initial code");
    }

    static void receiveMessage(String user, String message) throws IOException {
        UserInterface.appendToMessageDisplay("<" + user + "> " + message + "\n");
    }
}
