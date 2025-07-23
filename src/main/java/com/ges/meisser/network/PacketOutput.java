package com.ges.meisser.network;

import java.io.*;

public class PacketOutput implements DataOutput {
    private final ByteArrayOutputStream baos;
    private final DataOutputStream dos;

    public PacketOutput(int size) {
        this.baos = new ByteArrayOutputStream(size);
        this.dos = new DataOutputStream(baos);
    }

    @Override
    public void write(int b) throws IOException { dos.write(b); }
    @Override
    public void write(byte[] b) throws IOException { dos.write(b); }
    @Override
    public void write(byte[] b, int off, int len) throws IOException { dos.write(b, off, len); }
    @Override
    public void writeBoolean(boolean v) throws IOException { dos.writeBoolean(v); }
    @Override
    public void writeByte(int v) throws IOException { dos.writeByte(v); }
    @Override
    public void writeShort(int v) throws IOException { dos.writeShort(v); }
    @Override
    public void writeChar(int v) throws IOException { dos.writeChar(v); }
    @Override
    public void writeInt(int v) throws IOException { dos.writeInt(v); }
    @Override
    public void writeLong(long v) throws IOException { dos.writeLong(v); }
    @Override
    public void writeFloat(float v) throws IOException { dos.writeFloat(v); }
    @Override
    public void writeDouble(double v) throws IOException { dos.writeDouble(v); }
    @Override
    public void writeBytes(String s) throws IOException { dos.writeBytes(s); }
    @Override
    public void writeChars(String s) throws IOException { dos.writeChars(s); }
    @Override
    public void writeUTF(String s) throws IOException { dos.writeUTF(s); }


    public void send(OutputStream os) throws IOException {
        baos.writeTo(os);
    }
}
