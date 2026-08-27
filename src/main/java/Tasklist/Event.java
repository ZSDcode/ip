package Tasklist;

public class Event extends Task {
    public String from;
    public String to;

    public Event(String task, String from, String to) {
        super(task);
        super.category = taskType.EVENT;
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return String.format("[E] [%s] %s (FROM: %s, TO: %s)", super.done ? "X" : " ", super.objective, this.from, this.to);
    }

    @Override
    public String fileFormatText() {
        return String.format("E | %s | %s | %s", super.objective, this.from, this.to);
    }
}
