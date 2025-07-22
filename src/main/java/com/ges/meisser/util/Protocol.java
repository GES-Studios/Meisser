package com.ges.meisser.util;

public final class Protocol {
    public static final int DEFAULT_PORT = 32216;
    public static final int DEFAULT_BACKLOG = 128;
    public static final String INADDR_ANY = "0.0.0.0";

    public static final byte PROTOCOL_VERSION_MAJOR = 1;
    public static final byte PROTOCOL_VERSION_MINOR = 0;
    public static final int PROTOCOL_VERSION_ID = (PROTOCOL_VERSION_MAJOR << 8) | PROTOCOL_VERSION_MINOR;

    public static final int SIGNATURE_LENGTH = 4;
    public static final int VERSION_LENGTH = 2;
    public static final int USERNAME_LENGTH = 32;

    public static final int HANDSHAKE_INIT_CODE = 0x01;

    public static final int HANDSHAKE_REQUEST_LENGTH = SIGNATURE_LENGTH + VERSION_LENGTH + 1 + USERNAME_LENGTH;
    public static final int HANDSHAKE_RESPONSE_LENGTH = SIGNATURE_LENGTH + VERSION_LENGTH + 1;

    public static final int HANDSHAKE_SERVER_RESPONSE_STATUS_OK = 0x10;
    public static final int HANDSHAKE_SERVER_RESPONSE_STATUS_INCOMPATIBLE_PROTOCOL_VERSION = 0x11;
    public static final int HANDSHAKE_SERVER_RESPONSE_STATUS_DUPLICATE_USERNAME = 0x12;
}
