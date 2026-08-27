package Tasklist;

import Cxception.InvalidDateException;
import Parser.DateTimeParser;

public class Deadline extends Task {
    private MyDateTime by;

    public Deadline(String task, String byStr) throws InvalidDateException {
        super(task);
        super.category = taskType.DEADLINE;
        this.by = DateTimeParser.parse(byStr);
    }

    @Override
    public String toString() {
        return String.format("[D] [%s] %s (By: %s)", super.done ? "X" : " ", super.objective, by.toDisplayString());
    }

    @Override
    public String fileFormatText() {
        return String.format("D | %s | %s", super.objective, by.toFileString());
    }
}
