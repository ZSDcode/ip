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
        assert tL != null : "loadFile returned null Tasklist";

        p = new Parser(tL); // adjust Parser if it needs Scanner — remove that dependency for GUI
        assert p != null : "Parser init failed";
    }

    public String getResponse(String input) {
        assert input != null : "input null";
        assert !input.isBlank() : "input blank";

        if (input.equals("bye")) {
            FileManipulator.saveFile(tL);
            return "Bye, enjoy your day!!";
        }
        if (input.equals("list")) {
            String result = tL.toString();
            assert result != null : "Tasklist.toString() null";
            return result;
        }

        String result = p.firstParse(input); // Parser.firstParse should return String, not print directly
        assert result != null : "firstParse returned null";
        return result;
    }
}
