import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    private final LocalDate deadline;

    public Deadline(String description, String deadline) {
        super(description);

        this.deadline = LocalDate.parse(
                deadline,
                DateTimeFormatter.ofPattern("yyyy-MM-dd")
        );
    }

    public String getDeadline() {
        return this.deadline.format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd")
        );
    }

    @Override
    public String toString() {
        return "[D]" + super.toString()
                + " (by: "
                + this.deadline.format(
                DateTimeFormatter.ofPattern("MMM d yyyy")
        )
                + ")";
    }
}