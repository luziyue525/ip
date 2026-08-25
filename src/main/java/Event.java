public class Event extends Task {
    private final String fromDate;
    private final String toDate;

    public Event(String description, String fromDate, String toDate) {
        super(description);
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: "
        + this.fromDate + " to: "
        + this.toDate + ")";
    }


}
