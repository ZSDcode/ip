package Cxception;

/**
 * Exception thrown when a user-provided task index is out of valid range.
 */
public class MarkOutException extends Exception {

    /**
     * Constructs a MarkOutException with a default error message.
     */
    public MarkOutException() {
        super("Can't access this index!");
    }
}
