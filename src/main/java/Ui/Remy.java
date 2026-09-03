// Remy.java (rename from Ui, or create new class wrapping logic)
package Ui;

import Tasklist.Tasklist;
import Parser.Parser;
import Storage.FileManipulator;

public class Remy {
    private Tasklist tL;
    private Parser p;

    public Remy() {
        tL = FileManipulator.loadFile();
        p = new Parser(tL); // adjust Parser if it needs Scanner — remove that dependency for GUI
    }

    public String getResponse(String input) {
        if (input.equals("bye")) {
            FileManipulator.saveFile(tL);
            return "Bye, enjoy your day!!";
        }
        if (input.equals("list")) {
            return tL.toString();
        }
        return p.firstParse(input); // Parser.firstParse should return String, not print directly
    }
}
