package Tasklist;

public class Task {
    public enum taskType {
        TODO, DEADLINE, EVENT
    };
    protected taskType category;
    protected String objective;
    protected boolean done;

    public Task(String s) {
        this.category = taskType.TODO;
        this.objective = s;
        this.done = false;
    }

    @Override
    public String toString() {
        return String.format("[T] [%s] %s", this.done ? "X" : " ", this.objective);
    }

    public String fileFormatText() {
        return String.format("T | %s", this.objective);
    }

    public void setDone() {
        this.done = true;
    }

    public void setUndone() {
        this.done = false;
    }

    public taskType getCategory() {
        return this.category;
    }
}
