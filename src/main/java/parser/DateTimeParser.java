package parser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

import cxception.InvalidDateException;
import tasklist.MyDateTime;

/**
 * Parses raw date/time strings into {@link MyDateTime} instances.
 */
public class DateTimeParser {
    private static final DateTimeFormatter INPUT_DT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm")
        .withResolverStyle(ResolverStyle.STRICT);
    private static final DateTimeFormatter INPUT_D = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        .withResolverStyle(ResolverStyle.STRICT);

    /**
     * Parses a string into a {@code MyDateTime}, trying date-time format first,
     * then falling back to date-only format.
     *
     * @param s the raw date/time string to parse.
     * @return a {@code MyDateTime} representing the parsed value.
     * @throws InvalidDateException if {@code s} matches neither accepted format.
     */
    public static MyDateTime parse(String s) throws InvalidDateException {
        try {
            return new MyDateTime(LocalDateTime.parse(s, INPUT_DT));
        } catch (DateTimeParseException e1) {
            try {
                return new MyDateTime(LocalDate.parse(s, INPUT_D));
            } catch (DateTimeParseException e2) {
                throw new InvalidDateException();
            }
        }
    }
}
