package Cxception;
public class InvalidDateException extends Exception {
    public InvalidDateException() {
        super("That date is wrong, and wrong things don't get to stay near me. Give me yyyy-MM-dd HHmm (e.g. 2026-08-27 1830) or yyyy-MM-dd, and only that.");
    }
}
