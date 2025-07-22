package com.ges.meisser.server;

import com.ges.meisser.util.ExceptionOccurrenceWindow;

public class Main {
    public static void main(String[] args) throws Exception {
        Thread.setDefaultUncaughtExceptionHandler((thread, ex) -> {
            (new ExceptionOccurrenceWindow(ex, () -> quit(1))).setVisible(true);
            System.err.println("\u001b[31;1m" + ExceptionOccurrenceWindow.getError(ex) + "\u001b[0m");
        });
        Server.startup();
    }

    public static void quit(int status) {
        try { Server.close(); } catch (Exception ignored) {}
        System.exit(status);   //Anyway JVM will close all FDs
    }
}
