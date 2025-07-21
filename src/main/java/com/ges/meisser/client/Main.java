package com.ges.meisser.client;

public class Main {
    public static void main(String[] args) {
        try {
            Client.startup();
        } catch (Throwable throwable) {
            System.err.print("\u001b[31;1m");
            throwable.printStackTrace(System.err);
            System.err.print("\u001b[0m");
        }
    }

    public static void quit(int status) {
        try { Client.close(); } catch (Exception ignored) {}
        System.exit(status);   //Anyway JVM will close all FDs
    }
}
