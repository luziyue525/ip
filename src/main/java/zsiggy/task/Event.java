package zsiggy.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents an event with a start date and end date.
 */

public class Event extends Task {
    private final LocalDate fromDate;
    private final LocalDate toDate;

    /**
     * Creates an event.
     *
     * @param description the event description
     * @param fromDate the start date in yyyy-MM-dd format
     * @param toDate the end date in yyyy-MM-dd format
     */

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

    /**
     * Returns the event in storage format.
     *
     * @return the event dates-from  in yyyy-MM-dd format
     */

    public String getFromDate() {
        return this.fromDate.format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd")
        );
    }

    /**
     * Returns the event in storage format.
     *
     * @return the event dates-to  in yyyy-MM-dd format
     */

    public String getToDate() {
        return this.toDate.format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd")
        );
    }

    /**
     * Returns the formatted textual representation of the event.
     *
     * @return the formatted event task
     */

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