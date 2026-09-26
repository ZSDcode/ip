package Ui;

import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.InfoCmp.Capability;

import Tasklist.Tasklist;
import Parser.Parser;
import Storage.FileManipulator;

public class Ui {
    private static String line = "_______________________________________________\n";
    private static String banner = " ____                    \n"
        + "|  _ \\  ___  _ __ ___  _   _ \n"
        + "| |_) |/ _ \\| '_ ` _ \\| | | |\n"
        + "|  _ <|  __/| | | | | | |_| |\n"
        + "|_| \\_\\\\___||_| |_| |_|\\__, |\n"
        + "                       |___/ \n" + line;
    private static String greeting = "Hello! I'm Remy, your friendly reminder app! \n"
        + "How can I help you today?\n" + line;
    private static String goodbye = "Bye, enjoy your day!! \n" + line;
    private Tasklist tL;
    private Terminal terminal;
    private LineReader reader;
    private Parser p;

    public static void display(String s) {
        System.out.print(s);
    }

    public static void displayGreet() {
        System.out.println(Ui.banner + Ui.greeting);
    }

    public static void displayGoodbye() {
        System.out.println(Ui.goodbye);
    }

    /**
     * Reads one line of input, with up/down arrow history navigation.
     * Returns "bye" on Ctrl+C / Ctrl+D so the main loop exits cleanly.
     */
    private String readCommand() {
        try {
            return this.reader.readLine("> ");
        } catch (UserInterruptException | EndOfFileException e) {
            return "bye";
        }
    }

    private void clearScreen() {
        this.terminal.puts(Capability.clear_screen);
        this.terminal.flush();
        displayGreet();
    }

    public static void main(String[] args) {
        Ui ui = new Ui();
        ui.tL = FileManipulator.loadFile();

        try {
            ui.terminal = TerminalBuilder.builder().system(true).build();
        } catch (java.io.IOException e) {
            System.out.println("Couldn't start terminal: " + e.getMessage());
            return;
        }
        ui.reader = LineReaderBuilder.builder().terminal(ui.terminal).build();
        ui.p = new Parser(ui.tL, ui.reader);

        // Saves tasks on graceful termination too (OS shutdown, logout, Ctrl+C
        // at the OS level, `kill`) — not just when the user types "bye"/"exit".
        // Can't catch a hard power cut or `kill -9`; nothing running in the JVM can.
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            FileManipulator.saveFile(ui.tL);
        }));


        displayGreet();
        String nextL = ui.readCommand();
        try {
            while (!nextL.equals("bye") && !nextL.equals("q") && !nextL.equals("exit")) {
                if (nextL.equals("ls")) {
                    System.out.print(ui.tL);
                    System.out.print(line);
                } else if (nextL.equals("clear") || nextL.equals("cls")) {
                    ui.clearScreen();
                } else {
                    ui.p.firstParse(nextL);
                    System.out.print(line);
                }
                nextL = ui.readCommand();
            }
        } finally {
            FileManipulator.saveFile(ui.tL);
            try {
                ui.terminal.close();
            } catch (java.io.IOException e) {
                // ignore close failure on exit
            }
        }
        displayGoodbye();
    }
}
