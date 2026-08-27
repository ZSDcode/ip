package Tasklist;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MyDateTime {
    private final LocalDateTime dateTime;
    private final LocalDate date;
    private final boolean hasTime;

    private static final DateTimeFormatter INPUT_DT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter INPUT_D = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DISPLAY_DT = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma");
    private static final DateTimeFormatter DISPLAY_D = DateTimeFormatter.ofPattern("MMM dd yyyy");

    public MyDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
        this.date = null;
        this.hasTime = true;
    }

    public MyDateTime(LocalDate date) {
        this.date = date;
        this.dateTime = null;
        this.hasTime = false;
    }

    public String toDisplayString() {
        return hasTime ? dateTime.format(DISPLAY_DT) : date.format(DISPLAY_D);
    }

    public String toFileString() {
        return hasTime ? dateTime.format(INPUT_DT) : date.format(INPUT_D);
    }

    public boolean hasTime() {
        return hasTime;
    }
}
