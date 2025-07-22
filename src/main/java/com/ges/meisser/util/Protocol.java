package com.ges.meisser.util;

import java.nio.charset.StandardCharsets;

public final class Protocol {
    public static final int DEFAULT_PORT = 32216;
    public static final int DEFAULT_BACKLOG = 128;
    public static final String INADDR_ANY = "0.0.0.0";

    public static final byte PROTOCOL_VERSION_MAJOR = 1;
    public static final byte PROTOCOL_VERSION_MINOR = 0;

    public static final int SIGNATURE_LENGTH = 4;
    public static final int VERSION_LENGTH = 2;
    public static final int USERNAME_LENGTH = 32;

    public static final int HANDSHAKE_INIT_CODE = 0x01;

    public static final int HANDSHAKE_REQUEST_LENGTH = SIGNATURE_LENGTH + VERSION_LENGTH + 1 + USERNAME_LENGTH;
    public static final int HANDSHAKE_RESPONSE_LENGTH = SIGNATURE_LENGTH + VERSION_LENGTH + 1;

    public static final int HANDSHAKE_SERVER_RESPONSE_STATUS_OK = 0x10;
    public static final int HANDSHAKE_SERVER_RESPONSE_STATUS_INCOMPATIBLE_PROTOCOL_VERSION = 0x11;
    public static final int HANDSHAKE_SERVER_RESPONSE_STATUS_DUPLICATE_USERNAME = 0x12;

    public static void copyStringInto(String source, byte[] dest, int doff, int length) {
        byte[] array = source.getBytes(StandardCharsets.UTF_8);
        System.arraycopy(array, 0, dest, doff, Math.min(array.length, length));
    }

    public static int insertSignature(byte[] packet) {
        if (packet.length < SIGNATURE_LENGTH)
            throw new IllegalArgumentException("Packet must be at least " + SIGNATURE_LENGTH +
                    " bytes long to insert a signature");
        packet[0] = 'M';
        packet[1] = 'P';
        packet[2] = '3';
        packet[3] = '2';
        return packet.length - 4;
    }
    public static boolean isValidSignature(byte[] packet) {
        if (packet.length < SIGNATURE_LENGTH) return false;
        return (packet[0] == 'M') && (packet[1] == 'P') && (packet[2] == '3') && (packet[3] == '2');
    }

    public static int insertVersion(byte[] packet) {
        if (packet.length < SIGNATURE_LENGTH + VERSION_LENGTH)
            throw new IllegalArgumentException("Packet must be at least " + (SIGNATURE_LENGTH + VERSION_LENGTH) +
                    " bytes long to insert a version");
        packet[4] = PROTOCOL_VERSION_MAJOR;
        packet[5] = PROTOCOL_VERSION_MINOR;
        return packet.length - (SIGNATURE_LENGTH + VERSION_LENGTH);
    }
    public static boolean isValidVersion(byte[] packet) {
        if (packet.length < SIGNATURE_LENGTH + VERSION_LENGTH) return false;
        return (packet[4] == PROTOCOL_VERSION_MAJOR) && (packet[5] == PROTOCOL_VERSION_MINOR);
    }
}
