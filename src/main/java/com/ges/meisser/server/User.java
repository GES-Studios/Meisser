package com.ges.meisser.server;

import com.ges.meisser.util.InvalidDataException;
import static com.ges.meisser.util.Protocol.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;

class User {
    private static int userId = 0;

    private final Socket socket;
    private final InputStream input;
    private final OutputStream output;

    private final Thread thread;

    private String username;

    public User(Socket socket) throws IOException {
        this.socket = socket;
        this.input = socket.getInputStream();
        this.output = socket.getOutputStream();
        this.thread = new Thread(this::handle, "User-" + userId);
        userId++;
    }

    public void validate() throws InvalidDataException, IOException {
        System.out.println("Validating user");

        byte[] handshakeReq = new byte[HANDSHAKE_REQUEST_LENGTH];
        input.read(handshakeReq);

        if (!isValidSignature(handshakeReq))
            throw new InvalidDataException("Packet has no valid signature");
        else if (handshakeReq[SIGNATURE_LENGTH + VERSION_LENGTH] != HANDSHAKE_INIT_CODE)
            throw new InvalidDataException("Packet is not signed as HADNSHAKE");

        byte[] handshakeResp = new byte[HANDSHAKE_RESPONSE_LENGTH];
        insertSignature(handshakeResp);
        insertVersion(handshakeResp);

        if (!isValidVersion(handshakeReq)) {
            handshakeResp[SIGNATURE_LENGTH + VERSION_LENGTH] =
                    HANDSHAKE_SERVER_RESPONSE_STATUS_INCOMPATIBLE_PROTOCOL_VERSION;
            output.write(handshakeResp);

            throw new InvalidDataException("Client has incompatible version");
        }

        handshakeResp[SIGNATURE_LENGTH + VERSION_LENGTH] = HANDSHAKE_SERVER_RESPONSE_STATUS_OK;
        output.write(handshakeResp);

        this.username = new String(handshakeReq, SIGNATURE_LENGTH + VERSION_LENGTH + 1, USERNAME_LENGTH).trim();
        System.out.println("User joined: " + username);
    }

    private void handle() {
        while (true) {
            try {
                //"Ping" client
                byte[] b = new byte[1];
                output.write(b);
            } catch (IOException e) { break; }
        }
        //I dont know why, but it remains unclosed
        try { socket.close(); } catch (IOException ignored) {};
        System.out.println("User left: " + username);
        thread.interrupt();
    }

    public void startThread() {
        thread.start();
    }

    public boolean isActive() {
        return !socket.isClosed();
    }
}
