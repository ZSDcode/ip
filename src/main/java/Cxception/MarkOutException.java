package Cxception;

/**
<<<<<<< HEAD
 * Signals that the user is accessing an out of index item
 */
public class MarkOutException extends Exception {
    /**
     * Constructs an {@code MarkOutException} with a default message
     * describing the out of index error to the user.
=======
 * Exception thrown when a user-provided task index is out of valid range.
 */
public class MarkOutException extends Exception {

    /**
     * Constructs a MarkOutException with a default error message.
>>>>>>> branch-A-CodeQuality
     */
    public MarkOutException() {
        super("Can't access this index!");
    }
}
