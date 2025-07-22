package com.ges.meisser.network;

import com.ges.meisser.util.Protocol;

import java.io.*;
import java.util.Arrays;

public class PacketOutput implements DataOutput {
    private final ByteArrayOutputStream baos;
    private final DataOutputStream das;

    public PacketOutput(int size) throws IOException {
        this.baos = new ByteArrayOutputStream(Protocol.SIGNATURE_LENGTH + Protocol.VERSION_LENGTH + size);
        this.das = new DataOutputStream(baos);
        insertSignature();
        insertVersion();
    }

    private void insertSignature() throws IOException {
        write('M');
        write('P');
        write('3');
        write('2');
    }
    private void insertVersion() throws IOException {
        write(Protocol.PROTOCOL_VERSION_MAJOR);
        write(Protocol.PROTOCOL_VERSION_MINOR);
    }

    @Override
    public void write(int b) throws IOException { das.write(b); }

    @Override
    public void write(byte[] b) throws IOException { das.write(b); }

    @Override
    public void write(byte[] b, int off, int len) throws IOException { das.write(b, off, len); }

    @Override
    public void writeBoolean(boolean v) throws IOException { das.writeBoolean(v); }
    @Override
    public void writeByte(int v) throws IOException { das.writeByte(v); }
    @Override
    public void writeShort(int v) throws IOException { das.writeShort(v); }
    @Override
    public void writeChar(int v) throws IOException { das.writeChar(v); }
    @Override
    public void writeInt(int v) throws IOException { das.writeInt(v); }
    @Override
    public void writeLong(long v) throws IOException { das.writeLong(v); }
    @Override
    public void writeFloat(float v) throws IOException { das.writeFloat(v);}
    @Override
    public void writeDouble(double v) throws IOException { das.writeDouble(v); }
    @Override
    public void writeBytes(String s) throws IOException { das.writeBytes(s); }
    @Override
    public void writeChars(String s) throws IOException { das.writeChars(s); }
    @Override
    public void writeUTF(String s) throws IOException { das.writeUTF(s); }


    public byte[] getByteArray() {
        return baos.toByteArray();
    }


    public void send(OutputStream os) throws IOException {
        os.write(baos.toByteArray());
    }

    @Override
    public String toString() {
        return Arrays.toString(baos.toByteArray());
    }
}
