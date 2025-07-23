package com.ges.meisser.network;

import java.io.*;

public class PacketInput implements DataInput {
    private final int size;
    private DataInputStream dis;

    public PacketInput(int size) {
        this.size = size;
    }

    @Override
    public void readFully(byte[] b) throws IOException { dis.readFully(b); }
    @Override
    public void readFully(byte[] b, int off, int len) throws IOException { dis.readFully(b, off, len); }
    @Override
    public int skipBytes(int n) throws IOException { return dis.skipBytes(n); }
    @Override
    public boolean readBoolean() throws IOException { return dis.readBoolean(); }
    @Override
    public byte readByte() throws IOException { return dis.readByte(); }
    @Override
    public int readUnsignedByte() throws IOException { return dis.readUnsignedByte(); }
    @Override
    public short readShort() throws IOException { return dis.readShort(); }
    @Override
    public int readUnsignedShort() throws IOException { return dis.readUnsignedShort(); }
    @Override
    public char readChar() throws IOException { return dis.readChar(); }
    @Override
    public int readInt() throws IOException { return dis.readInt(); }
    @Override
    public long readLong() throws IOException { return dis.readLong(); }
    @Override
    public float readFloat() throws IOException { return dis.readFloat(); }
    @Override
    public double readDouble() throws IOException { return dis.readDouble(); }
    @Override
    @Deprecated
    public String readLine() throws IOException { return dis.readLine(); }
    @Override
    public String readUTF() throws IOException { return dis.readUTF(); }


    public int receive(InputStream is) throws IOException {
        byte[] buffer = new byte[size];
        int count = is.read(buffer);
        this.dis = new DataInputStream(new ByteArrayInputStream(buffer));
        return count;
    }
}
