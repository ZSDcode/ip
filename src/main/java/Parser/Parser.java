package Parser;

import java.util.Scanner;

import Tasklist.Tasklist;
import Tasklist.Task;
import Tasklist.Deadline;
import Tasklist.Event;

import Cxception.EmptyEventException;
import Cxception.MarkOutException;

public class Parser {
    private Tasklist tL;
    private Scanner s;
    public Parser(Tasklist tL, Scanner s) {
        this.tL = tL; 
        this.s = s; 
    }

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
                    }
                    else {
                        addToStruct(inp); 
                    }
                }
                case "unmark" -> {
                    if (isNum) {
                        if (idxInConsideration > tL.getSize() || idxInConsideration <= 0) {
                            throw new MarkOutException();
                        }
                        tL.unmarkItem(idxInConsideration - 1); 
                    }
                    else {
                        addToStruct(inp); 
                    }
                }
                case "delete" -> {
                    if (isNum) {
                        if (idxInConsideration > tL.getSize() || idxInConsideration <= 0) {
                            throw new MarkOutException();
                        }
                        tL.deleteItem(idxInConsideration - 1); 
                    }
                    else {
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

    private void addToStruct(String inp) {
        try {
            inp = inp.trim();
            if (inp.isEmpty()) throw new EmptyEventException();
            tL.addItem(new Task(inp));
        } catch (EmptyEventException e) {
            System.out.println(e.getMessage());
            return;
        }
    }


    private void parseDeadline(String inp) {
        try {
            String objective = "";
            String by = "";
            if (inp.contains("/by ")) {
                String[] parts = inp.split("/by ", 2);
                objective = parts[0].trim();
                if (objective.isEmpty()) throw new EmptyEventException();
                by = parts[1].trim();
            } else {
                objective = inp.trim();
                by = prompt("By: ").trim();
            }
            tL.addItem(new Deadline(objective, by));
        } catch (EmptyEventException e) {
            System.out.println(e.getMessage());
            return;
        }
    }

    private void parseEvent(String inp) {
        try {
            String objective = "";
            String from = "";
            String to = "";
            boolean f = inp.contains("/from ");
            boolean t = inp.contains("/to ");
            if (!f && !t) {
                objective = inp.trim();
                from = prompt("From: ");
                to = prompt("To: ");
            } else if (f && t) {
                int fIdx = inp.indexOf("/from ");
                int tIdx = inp.indexOf("/to ");
                objective = inp.substring(0, Math.min(fIdx, tIdx)).trim();
                if (fIdx < tIdx) {
                    from = inp.substring(fIdx + 6, tIdx).trim();
                    if (from.isEmpty()) from = prompt("From: ");
                    to = inp.substring(tIdx + 4).trim();
                    if (to.isEmpty()) to = prompt("To: ");
                } else {
                    from = inp.substring(fIdx + 6).trim();
                    if (from.isEmpty()) from = prompt("From: ");
                    to = inp.substring(tIdx + 4, fIdx).trim();
                    if (to.isEmpty()) to = prompt("To: ");
                }
            } else {
                if (f) {
                    String[] parts = inp.split("/from ", 2);
                    objective = parts[0].trim();
                    from = parts[1].trim();
                    if (from.isEmpty()) from = prompt("From: ");
                    to = prompt("To: ");
                }
                else {
                    String[] parts = inp.split("/to ", 2);
                    objective = parts[0].trim();
                    to = parts[1].trim();
                    if (to.isEmpty()) to = prompt("To: ");
                    from = prompt("From: ");
                }
            }
            if (objective.isEmpty()) throw new EmptyEventException();
            tL.addItem(new Event(objective, from, to));
        } catch (EmptyEventException e) {
            System.out.println(e.getMessage());
            return;
        };
    }

    private String prompt(String tag) {
        while (true) {
            System.out.print(tag);
            try {
                if (s.hasNextLine()) {
                    String response = this.s.nextLine().trim();
                    if (response.isEmpty()) throw new EmptyEventException(); //To be changed with actual date error!
                    return response;
                }
            } catch (EmptyEventException e) {
                System.out.print(e.getMessage() + '\n');
            }
        }
    }
}
