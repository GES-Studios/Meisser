package com.ges.meisser.util;

public final class Protocol {
    public static final int DEFAULT_PORT = 32216;
    public static final int DEFAULT_BACKLOG = 128;
    public static final String INADDR_ANY = "0.0.0.0";

    public static final byte PROTOCOL_VERSION_MAJOR = 1;
    public static final byte PROTOCOL_VERSION_MINOR = 0;

    //public static final int SIGNATURE_LENGTH = 4;
    public static final int VERSION_LENGTH = 2;
    public static final int USERNAME_LENGTH = 32;
    public static final int MESSAGE_LENGTH = 256;

    public static final int HANDSHAKE_INIT_CODE = 0x01;

    public static final int HANDSHAKE_REQUEST_LENGTH = 1 + VERSION_LENGTH + USERNAME_LENGTH;
    public static final int HANDSHAKE_RESPONSE_LENGTH = 1 + VERSION_LENGTH;

    public static final int HANDSHAKE_SERVER_RESPONSE_STATUS_OK = 0x10;
    public static final int HANDSHAKE_SERVER_RESPONSE_STATUS_INCOMPATIBLE_PROTOCOL_VERSION = 0x11;
    public static final int HANDSHAKE_SERVER_RESPONSE_STATUS_DUPLICATE_USERNAME = 0x12;

    public static final int SENDING_MESSAGE_INIT_CODE = 0x02;
    public static final int BROADCASTING_MESSAGE_INIT_CODE = 0x02;

    public static final int MESSAGE_REQUEST_LENGTH = 1 + MESSAGE_LENGTH;
    public static final int MESSAGE_BROADCAST_LENGTH = 1 + USERNAME_LENGTH + MESSAGE_LENGTH;
}
