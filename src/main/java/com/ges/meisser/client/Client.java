package com.ges.meisser.client;

import com.ges.meisser.network.PacketInput;
import com.ges.meisser.network.PacketOutput;
import com.ges.meisser.util.InvalidDataException;
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

    static void handshake() throws InvalidDataException, IOException {
        PacketOutput request = new PacketOutput(HANDSHAKE_REQUEST_LENGTH);
        request.writeByte(HANDSHAKE_INIT_CODE);
        request.writeUTF(username);
        request.send(output);

        PacketInput response = new PacketInput(HANDSHAKE_RESPONSE_LENGTH);
        response.receive(input);
        int code = response.readUnsignedByte();
        int version = response.getVersion();

        if (code == HANDSHAKE_SERVER_RESPONSE_STATUS_INCOMPATIBLE_PROTOCOL_VERSION)
            throw new InvalidDataException("Server uses different Protocol version: " +
                    ((version >> 8) & 0xff) + "." + (version & 0xff) + ". Try update/downgrade your client");
        else if (code == HANDSHAKE_SERVER_RESPONSE_STATUS_DUPLICATE_USERNAME)
            throw new InvalidDataException("User with the same name '" + username + "' is already on the server");
    }
}
