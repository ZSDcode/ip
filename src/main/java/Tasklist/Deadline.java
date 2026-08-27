package Tasklist;

public class Deadline extends Task {
    public String by;

    public Deadline(String task, String by) {
        super(task);
        super.category = taskType.DEADLINE;
        this.by = by;
    }

    @Override
    public String toString() {
        return String.format("[D] [%s] %s (By: %s)", super.done ? "X" : " ", super.objective, this.by);
    }

    @Override
    public String fileFormatText() {
        return String.format("D | %s | %s", super.objective, this.by);
    }
}
