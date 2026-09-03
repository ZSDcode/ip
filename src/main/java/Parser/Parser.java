package Parser;

import Tasklist.Tasklist;
import Tasklist.Task;
import Tasklist.Deadline;
import Tasklist.Event;

import Cxception.EmptyEventException;
import Cxception.MarkOutException;
import Cxception.InvalidDateException;

public class Parser {
    private Tasklist tL;

    public Parser(Tasklist tL) {
        this.tL = tL;
    }

    public String firstParse(String inp) {
        try {
            inp = inp.trim();
            int idxCmdSubstr = inp.contains(" ") ? inp.indexOf(" ") : inp.length();
            if (idxCmdSubstr == inp.length()) {
                return addToStruct(inp);
            }
            String potentialCmd = inp.substring(0, idxCmdSubstr);
            String restOfCmd = inp.substring(idxCmdSubstr).trim();
            boolean isNum = restOfCmd.chars().allMatch(Character::isDigit);
            int idxInConsideration = isNum ? Integer.parseInt(restOfCmd) : inp.length();

            switch (potentialCmd) {
                case "mark":
                    if (!isNum) return addToStruct(inp);
                    if (idxInConsideration > tL.getSize() || idxInConsideration <= 0) throw new MarkOutException();
                    return tL.markItem(idxInConsideration - 1);
                case "unmark":
                    if (!isNum) return addToStruct(inp);
                    if (idxInConsideration > tL.getSize() || idxInConsideration <= 0) throw new MarkOutException();
                    return tL.unmarkItem(idxInConsideration - 1);
                case "delete":
                    if (!isNum) return addToStruct(inp);
                    if (idxInConsideration > tL.getSize() || idxInConsideration <= 0) throw new MarkOutException();
                    return tL.deleteItem(idxInConsideration - 1);
                case "find":
                    return tL.findItems(restOfCmd);
                case "deadline":
                    return parseDeadline(restOfCmd);
                case "event":
                    return parseEvent(restOfCmd);
                case "todo":
                    return addToStruct(restOfCmd);
                default:
                    return addToStruct(inp);
            }
        } catch (MarkOutException e) {
            return e.getMessage();
        }
    }

    private String addToStruct(String inp) {
        try {
            inp = inp.trim();
            if (inp.isEmpty()) throw new EmptyEventException();
            return tL.addItem(new Task(inp));
        } catch (EmptyEventException e) {
            return e.getMessage();
        }
    }

    private String parseDeadline(String inp) {
        try {
            if (!inp.contains("/by ")) {
                return "Missing /by <date>. Usage: deadline <task> /by <date>";
            }
            String[] parts = inp.split("/by ", 2);
            String objective = parts[0].trim();
            if (objective.isEmpty()) throw new EmptyEventException();
            String by = validate(parts[1].trim());
            tL.addItem(new Deadline(objective, by));
            return "Added deadline: " + objective + " (by " + by + ")";
        } catch (EmptyEventException | InvalidDateException e) {
            return e.getMessage();
        }
    }

    private String parseEvent(String inp) {
        try {
            boolean f = inp.contains("/from ");
            boolean t = inp.contains("/to ");
            if (!f || !t) {
                return "Missing /from or /to. Usage: event <task> /from <date> /to <date>";
            }
            int fIdx = inp.indexOf("/from ");
            int tIdx = inp.indexOf("/to ");
            String objective = inp.substring(0, Math.min(fIdx, tIdx)).trim();
            String from, to;
            if (fIdx < tIdx) {
                from = validate(inp.substring(fIdx + 6, tIdx).trim());
                to = validate(inp.substring(tIdx + 4).trim());
            } else {
                from = validate(inp.substring(fIdx + 6).trim());
                to = validate(inp.substring(tIdx + 4, fIdx).trim());
            }
            if (objective.isEmpty()) throw new EmptyEventException();
            tL.addItem(new Event(objective, from, to));
            return "Added event: " + objective + " (from " + from + " to " + to + ")";
        } catch (EmptyEventException | InvalidDateException e) {
            return e.getMessage();
        }
    }

    private String validate(String candidate) throws EmptyEventException, InvalidDateException {
        if (candidate.isEmpty()) throw new EmptyEventException();
        DateTimeParser.parse(candidate);
        return candidate;
    }
}
