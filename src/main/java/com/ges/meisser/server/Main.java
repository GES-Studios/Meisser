package com.ges.meisser.server;

public class Main {
    public static void main(String[] args) {
        try {
            Server.startup();
        } catch (Throwable throwable) {
            System.err.print("\u001b[31;1m");
            throwable.printStackTrace(System.err);
            System.err.print("\u001b[0m");
        }
    }

    public static void quit(int status) {
        try { Server.close(); } catch (Exception ignored) {}
        System.exit(status);   //Anyway JVM will close all FDs
    }
}
