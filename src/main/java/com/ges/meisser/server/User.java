package com.ges.meisser.server;

import com.ges.meisser.network.PacketInput;
import com.ges.meisser.network.PacketOutput;
import com.ges.meisser.util.ProtocolException;
import static com.ges.meisser.util.Protocol.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

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

    public void validate() throws ProtocolException, IOException {
        System.out.println("Validating user");

        PacketInput request = new PacketInput(HANDSHAKE_REQUEST_LENGTH);
        request.receive(input);

        if (request.readUnsignedByte() != HANDSHAKE_INIT_CODE)
            throw new ProtocolException("Not handshaking");

        PacketOutput response = new PacketOutput(HANDSHAKE_RESPONSE_LENGTH);

        int versionMajor = request.readUnsignedByte(), versionMinor = request.readUnsignedByte();
        if (versionMajor != PROTOCOL_VERSION_MAJOR || versionMinor != PROTOCOL_VERSION_MINOR) {
            response.write(HANDSHAKE_SERVER_RESPONSE_STATUS_INCOMPATIBLE_PROTOCOL_VERSION);
            response.write(PROTOCOL_VERSION_MAJOR);
            response.write(PROTOCOL_VERSION_MINOR);
            response.send(output);
            throw new ProtocolException("Incompatible protocol version");
        }

        this.username = request.readUTF();
        if (Server.hasUser(username)) {
            response.write(HANDSHAKE_SERVER_RESPONSE_STATUS_DUPLICATE_USERNAME);
            response.send(output);
            throw new ProtocolException("User with name '" + username + "' is already on the server");
        }

        response.write(HANDSHAKE_SERVER_RESPONSE_STATUS_OK);
        response.send(output);

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
        Server.removeUser(this);
        System.out.println("User left: " + username);
        thread.interrupt();
    }

    public void startThread() {
        thread.start();
    }

    public boolean isActive() {
        return !socket.isClosed();
    }

    public String getName() {
        return username;
    }
}
