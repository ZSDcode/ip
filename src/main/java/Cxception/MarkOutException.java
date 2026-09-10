package Cxception;

/**
 * Signals that the user is accessing an out of index item
 */
public class MarkOutException extends Exception {
    /**
     * Constructs an {@code MarkOutException} with a default message
     * describing the out of index error to the user.
     */
    public MarkOutException() {
        super("Can't access this index!");
    }
}
