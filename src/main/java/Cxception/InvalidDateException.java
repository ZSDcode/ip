package Cxception;
public class InvalidDateException extends Exception {
    public InvalidDateException() {
        super("Invalid date format! Use yyyy-MM-dd HHmm (e.g. 2026-08-27 1830) or yyyy-MM-dd for date-only.");
    }
}
