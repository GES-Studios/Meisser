package com.ges.meisser.client;

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
        byte[] handshakeReq = new byte[HANDSHAKE_REQUEST_LENGTH];
        insertSignature(handshakeReq);
        insertVersion(handshakeReq);
        handshakeReq[SIGNATURE_LENGTH + VERSION_LENGTH] = HANDSHAKE_INIT_CODE;
        copyStringInto(username, handshakeReq, SIGNATURE_LENGTH + VERSION_LENGTH + 1, USERNAME_LENGTH);

        output.write(handshakeReq);

        byte[] handshakeResp = new byte[HANDSHAKE_RESPONSE_LENGTH];
        input.read(handshakeResp);
        if (!isValidSignature(handshakeResp))
            throw new InvalidDataException("Packet has no valid signature");

        int code = handshakeResp[SIGNATURE_LENGTH + VERSION_LENGTH];
        if (code == HANDSHAKE_SERVER_RESPONSE_STATUS_INCOMPATIBLE_PROTOCOL_VERSION)
            throw new InvalidDataException("Server uses different Protocol version: " +
                    handshakeResp[SIGNATURE_LENGTH] + "." + handshakeResp[SIGNATURE_LENGTH + 1] +
                    ". Try update/downgrade your client");
        else if (code == HANDSHAKE_SERVER_RESPONSE_STATUS_DUPLICATE_USERNAME)
            throw new InvalidDataException("User with the same name '" + username + "' is already on the server");
    }
}
