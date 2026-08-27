package zsiggy.task;

public class Parser {

    public static int parseTaskNumber(
            String input,
            int commandLength
    ) throws ZsiggyException {

        try {
            int taskNumber =
                    Integer.parseInt(
                            input.substring(commandLength)
                    );

            return taskNumber - 1;

        } catch (NumberFormatException e) {
            throw new ZsiggyException(
                    "Give me a proper task number."
            );
        }
    }
}