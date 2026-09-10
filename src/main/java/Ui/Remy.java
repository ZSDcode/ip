// Remy.java (rename from Ui, or create new class wrapping logic)
package Ui;

import Tasklist.Tasklist;
import Parser.Parser;
import Storage.FileManipulator;

/**
 * Backend facade connecting the GUI to task storage and command parsing logic.
 */
public class Remy {
    private Tasklist tL;
    private Parser p;

    /**
     * Constructs a Remy instance, loading the saved task list from file and
     * initializing the parser.
     */
    public Remy() {
        tL = FileManipulator.loadFile();
        p = new Parser(tL); // adjust Parser if it needs Scanner — remove that dependency for GUI
    }

    /**
     * Processes user input and returns Remy's response. Handles "bye" (save and exit
     * message) and "list" (display all tasks) directly; all other input is delegated
     * to the parser.
     *
     * @param input the raw user input.
     * @return the response message to display.
     */
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
