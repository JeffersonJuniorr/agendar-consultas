package com.example.clinic;

import com.example.clinic.ui.console.ConsoleMain;
import com.example.clinic.ui.swing.SwingMain;

import javax.swing.*;

public class App {
//
//    private static final DateTimeFormatter PADRAO =
//            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm", new Locale("pt", "BR"));

    public static void main(String[] args) {
        String ui = parseArg(args, "--ui");

        if ("console".equalsIgnoreCase(ui)) {
            new ConsoleMain().run();
        } else if ("swing".equalsIgnoreCase(ui)) {
            SwingUtilities.invokeLater(() -> new SwingMain().run());
//            new SwingMain().run();
        } else {
            System.out.println("""
                Uso:
                  java ... com.example.clinic.App --ui=console   # entrada via Scanner
                  java ... com.example.clinic.App --ui=swing     # entrada via Swing

                Formato de data/hora: dd/MM/yyyy HH:mm  (ex.: 21/08/2025 10:00)
                """);
        }
    }

    private static String parseArg(String[] args, String key) {
        if (args == null) return null;
        String prefix = key + "=";
        for (String a : args) {
            if (a != null && a.startsWith(prefix)) {
                return a.substring(prefix.length());
            }
        }
        return null;
    }
}