package Cxception;

public class MarkOutException extends Exception {
    public MarkOutException() {
        super("That index doesn't exist for you. I decide what you're allowed to reach.");
    }
}
