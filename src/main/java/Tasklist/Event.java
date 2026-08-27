package Tasklist;

import Cxception.InvalidDateException;
import Parser.DateTimeParser;

public class Event extends Task {
    private MyDateTime from;
    private MyDateTime to;

    public Event(String task, String fromStr, String toStr) throws InvalidDateException {
        super(task);
        super.category = taskType.EVENT;
        this.from = DateTimeParser.parse(fromStr);
        this.to = DateTimeParser.parse(toStr);
    }

    @Override
    public String toString() {
        return String.format("[E] [%s] %s (FROM: %s, TO: %s)",
                super.done ? "X" : " ", super.objective, from.toDisplayString(), to.toDisplayString());
    }

    @Override
    public String fileFormatText() {
        return String.format("E | %s | %s | %s", super.objective, from.toFileString(), to.toFileString());
    }
}
