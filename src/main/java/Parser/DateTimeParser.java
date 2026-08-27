package Parser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import Cxception.InvalidDateException;
import Tasklist.MyDateTime;

public class DateTimeParser {
    private static final DateTimeFormatter INPUT_DT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter INPUT_D = DateTimeFormatter.ofPattern("yyyy-MM-dd");

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
