package Ui;

import java.util.Scanner;

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
    private Scanner s;
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

    public static void main(String[] args) {
        Ui ui = new Ui();
        ui.tL = FileManipulator.loadFile();
        ui.s = new Scanner(System.in);
        ui.p = new Parser(ui.tL, ui.s);
        displayGreet();
        String nextL = ui.s.nextLine();
        try {
            while (!nextL.equals("bye")) {
                if (nextL.equals("list")) {
                    System.out.print(ui.tL);
                } else {
                    ui.p.firstParse(nextL);
                }
                System.out.print(line);
                nextL = ui.s.nextLine();
            }
        } finally {
            FileManipulator.saveFile(ui.tL);
            ui.s.close();
        }
        displayGoodbye();
    }
}
