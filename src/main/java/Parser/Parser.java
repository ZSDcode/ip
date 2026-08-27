package Parser;

import java.util.Scanner;

import Tasklist.Tasklist;
import Tasklist.Task;
import Tasklist.Deadline;
import Tasklist.Event;

import Cxception.EmptyEventException;
import Cxception.MarkOutException;
import Cxception.InvalidDateException;

/**
 * Parses raw user input into commands and dispatches them to a {@link Tasklist}.
 */
public class Parser {
    private Tasklist tL;
    private Scanner s;

    /**
     * Constructs a {@code Parser} bound to the given task list and input scanner.
     *
     * @param tL the task list to operate on.
     * @param s the scanner used to read follow-up prompts from the user.
     */
    public Parser(Tasklist tL, Scanner s) {
        this.tL = tL;
        this.s = s;
    }

    /**
     * Parses a single line of user input and executes the corresponding command.
     * Supported commands: mark, unmark, delete, deadline, event, todo; any other
     * input is treated as a plain task description.
     *
     * @param inp the raw input line entered by the user.
     */
    public void firstParse(String inp) {
        try {
            inp = inp.trim();
            int idxCmdSubstr = inp.contains(" ") ? inp.indexOf(" ") : inp.length();
            if (idxCmdSubstr == inp.length()) {
                addToStruct(inp);
                return;
            }
            String potentialCmd = inp.substring(0, idxCmdSubstr);
            String restOfCmd = inp.substring(idxCmdSubstr).trim();
            boolean isNum = restOfCmd.chars().allMatch(Character::isDigit);
            int idxInConsideration = isNum ? Integer.parseInt(restOfCmd) : inp.length();
            switch (potentialCmd) {
                case "mark" -> {
                    if (isNum) {
                        if (idxInConsideration > tL.getSize() || idxInConsideration <= 0) {
                            throw new MarkOutException();
                        }
                        tL.markItem(idxInConsideration - 1);
                    } else {
                        addToStruct(inp);
                    }
                }
                case "unmark" -> {
                    if (isNum) {
                        if (idxInConsideration > tL.getSize() || idxInConsideration <= 0) {
                            throw new MarkOutException();
                        }
                        tL.unmarkItem(idxInConsideration - 1);
                    } else {
                        addToStruct(inp);
                    }
                }
                case "delete" -> {
                    if (isNum) {
                        if (idxInConsideration > tL.getSize() || idxInConsideration <= 0) {
                            throw new MarkOutException();
                        }
                        tL.deleteItem(idxInConsideration - 1);
                    } else {
                        addToStruct(inp);
                    }
                }
                case "deadline" -> parseDeadline(restOfCmd);
                case "event" -> parseEvent(restOfCmd);
                case "todo" -> addToStruct(restOfCmd);
                default -> addToStruct(inp);
            }
        } catch (MarkOutException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Adds a plain (todo-type) task to the task list.
     *
     * @param inp the task description; must be non-empty after trimming.
     */
    private void addToStruct(String inp) {
        try {
            inp = inp.trim();
            if (inp.isEmpty()) throw new EmptyEventException();
            tL.addItem(new Task(inp));
        } catch (EmptyEventException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Parses a deadline command and adds the resulting task to the task list.
     * Accepts an inline {@code /by} date, or prompts for one if absent.
     *
     * @param inp the command text following the "deadline" keyword.
     */
    private void parseDeadline(String inp) {
        try {
            String objective;
            String by;
            if (inp.contains("/by ")) {
                String[] parts = inp.split("/by ", 2);
                objective = parts[0].trim();
                if (objective.isEmpty()) throw new EmptyEventException();
                by = prompt("By: ", parts[1].trim());
            } else {
                objective = inp.trim();
                if (objective.isEmpty()) throw new EmptyEventException();
                by = prompt("By: ");
            }
            tL.addItem(new Deadline(objective, by));
        } catch (EmptyEventException | InvalidDateException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Parses an event command and adds the resulting task to the task list.
     * Accepts inline {@code /from} and {@code /to} dates in either order, or
     * prompts for any that are absent.
     *
     * @param inp the command text following the "event" keyword.
     */
    private void parseEvent(String inp) {
        try {
            String objective;
            String from;
            String to;
            boolean f = inp.contains("/from ");
            boolean t = inp.contains("/to ");
            if (!f && !t) {
                objective = inp.trim();
                if (objective.isEmpty()) throw new EmptyEventException();
                from = prompt("From: ");
                to = prompt("To: ");
            } else if (f && t) {
                int fIdx = inp.indexOf("/from ");
                int tIdx = inp.indexOf("/to ");
                objective = inp.substring(0, Math.min(fIdx, tIdx)).trim();
                if (fIdx < tIdx) {
                    from = prompt("From: ", inp.substring(fIdx + 6, tIdx).trim());
                    to = prompt("To: ", inp.substring(tIdx + 4).trim());
                } else {
                    from = prompt("From: ", inp.substring(fIdx + 6).trim());
                    to = prompt("To: ", inp.substring(tIdx + 4, fIdx).trim());
                }
            } else {
                if (f) {
                    String[] parts = inp.split("/from ", 2);
                    objective = parts[0].trim();
                    from = prompt("From: ", parts[1].trim());
                    to = prompt("To: ");
                } else {
                    String[] parts = inp.split("/to ", 2);
                    objective = parts[0].trim();
                    to = prompt("To: ", parts[1].trim());
                    from = prompt("From: ");
                }
            }
            if (objective.isEmpty()) throw new EmptyEventException();
            tL.addItem(new Event(objective, from, to));
        } catch (EmptyEventException | InvalidDateException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Prompts the user for a date/time field, retrying until valid input is given.
     *
     * @param tag the prompt label displayed to the user.
     * @return a validated, non-empty date/time string.
     */
    private String prompt(String tag) {
        return prompt(tag, "");
    }

    /**
     * Prompts the user for a date/time field, first validating an existing
     * candidate (e.g. an inline-supplied value) before falling back to
     * repeated user input on empty or invalid input.
     *
     * @param tag the prompt label displayed to the user.
     * @param initialCandidate a pre-supplied value to validate before prompting;
     *                         pass an empty string to prompt immediately.
     * @return a validated, non-empty date/time string.
     */
    private String prompt(String tag, String initialCandidate) {
        String candidate = initialCandidate;
        while (true) {
            try {
                if (candidate.isEmpty()) {
                    System.out.print(tag);
                    if (!s.hasNextLine()) {
                        continue;
                    }
                    candidate = this.s.nextLine().trim();
                }
                if (candidate.isEmpty()) throw new EmptyEventException();
                DateTimeParser.parse(candidate);
                return candidate;
            } catch (EmptyEventException | InvalidDateException e) {
                System.out.println(e.getMessage());
                candidate = "";
            }
        }
    }
}
