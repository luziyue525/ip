package zsiggy.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    private final LocalDate fromDate;
    private final LocalDate toDate;

    public Event(String description, String fromDate, String toDate) {
        super(description);

        this.fromDate = LocalDate.parse(
                fromDate,
                DateTimeFormatter.ofPattern("yyyy-MM-dd")
        );

        this.toDate = LocalDate.parse(
                toDate,
                DateTimeFormatter.ofPattern("yyyy-MM-dd")
        );
    }

    public String getFromDate() {
        return this.fromDate.format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd")
        );
    }

    public String getToDate() {
        return this.toDate.format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd")
        );
    }

    @Override
    public String toString() {
        return "[E]" + super.toString()
                + " (from: "
                + this.fromDate.format(
                DateTimeFormatter.ofPattern("MMM d yyyy")
        )
                + " to: "
                + this.toDate.format(
                DateTimeFormatter.ofPattern("MMM d yyyy")
        )
                + ")";
    }
}