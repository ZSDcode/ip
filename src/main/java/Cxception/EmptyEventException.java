package Cxception;

/**
 * Signals that a task creation command was missing required task description.
 */
public class EmptyEventException extends Exception {
    public String message = "Please key in an event!";

    /**
     * Constructs an {@code EmptyEventException} with no detail message.
     */
    public EmptyEventException() { }
}
