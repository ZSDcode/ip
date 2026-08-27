package Tasklist;

/**
 * Represents a basic task with a description and completion status.
 */
public class Task {
    /**
     * The category of task: a plain to-do, a deadline, or an event.
     */
    public enum taskType {
        TODO, DEADLINE, EVENT
    };

    protected taskType category;
    protected String objective;
    protected boolean done;

    /**
     * Constructs a {@code Task} of type TODO.
     *
     * @param s the task description.
     */
    public Task(String s) {
        this.category = taskType.TODO;
        this.objective = s;
        this.done = false;
    }

    /**
     * Returns the string representation of this task for display.
     *
     * @return the formatted display string.
     */
    @Override
    public String toString() {
        return String.format("[T] [%s] %s", this.done ? "X" : " ", this.objective);
    }

    /**
     * Returns the string representation of this task for file storage.
     *
     * @return the formatted save-file string.
     */
    public String fileFormatText() {
        return String.format("T | %s", this.objective);
    }

    /**
     * Marks this task as done.
     */
    public void setDone() {
        this.done = true;
    }

    /**
     * Marks this task as not done.
     */
    public void setUndone() {
        this.done = false;
    }

    /**
     * Returns the category of this task.
     *
     * @return the task's {@code taskType}.
     */
    public taskType getCategory() {
        return this.category;
    }

    public boolean contains(String s) {
        return objective.contains(s);
    }
}
