import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Duke {

    public static void saveTasks(Task[] task, int taskCount, File dataFile) {
        try {
            FileWriter writer = new FileWriter(dataFile);

            for (int i = 0; i < taskCount; i++) {
                Task currentTask = task[i];

                String done = currentTask.isDone() ? "1" : "0";

                if (currentTask instanceof Todo) {
                    writer.write(
                            "T | " + done + " | "
                                    + currentTask.getDescription()
                                    + System.lineSeparator()
                    );

                } else if (currentTask instanceof Deadline) {
                    Deadline deadline = (Deadline) currentTask;

                    writer.write(
                            "D | " + done + " | "
                                    + deadline.getDescription()
                                    + " | "
                                    + deadline.getDeadline()
                                    + System.lineSeparator()
                    );

                } else if (currentTask instanceof Event) {
                    Event event = (Event) currentTask;

                    writer.write(
                            "E | " + done + " | "
                                    + event.getDescription()
                                    + " | "
                                    + event.getFromDate()
                                    + " | "
                                    + event.getToDate()
                                    + System.lineSeparator()
                    );
                }
            }

            writer.close();

        } catch (IOException e) {
            System.out.println(
                    "Oi. Couldn't save your tasks."
            );
        }
    }

    public static void main(String[] args) {
        System.out.println("Zsiggy here. Make it quick.");
        System.out.println("What mess do you need me to sort out today?");

        Scanner scanner = new Scanner(System.in);

        File dataFolder = new File("data");
        File dataFile = new File("data/tasks.txt");

        try {
            if (!dataFolder.exists()) {
                dataFolder.mkdir();
            }

            if (!dataFile.exists()) {
                dataFile.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("Oi. Couldn't create the save file.");
        }

        Task[] task = new Task[100];
        int taskCount = 0;

        try {
            Scanner fileScanner = new Scanner(dataFile);

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();

                if (line.isBlank()) {
                    continue;
                }

                String[] parts = line.split(" \\| ");

                String type = parts[0];
                boolean isDone = parts[1].equals("1");

                Task loadedTask;

                if (type.equals("T")) {
                    String description = parts[2];
                    loadedTask = new Todo(description);

                } else if (type.equals("D")) {
                    String description = parts[2];
                    String deadline = parts[3];

                    loadedTask = new Deadline(
                            description,
                            deadline
                    );

                } else if (type.equals("E")) {
                    String description = parts[2];
                    String fromDate = parts[3];
                    String toDate = parts[4];

                    loadedTask = new Event(
                            description,
                            fromDate,
                            toDate
                    );

                } else {
                    continue;
                }

                if (isDone) {
                    loadedTask.mark();
                }

                task[taskCount] = loadedTask;
                taskCount++;
            }

            fileScanner.close();

        } catch (FileNotFoundException e) {
            System.out.println(
                    "Oi. Couldn't load your saved tasks."
            );
        }

        while (true) {
            String input = scanner.nextLine();

            try {
                if (input.isBlank()) {
                    throw new ZsiggyException(
                            "...Saying nothing won't make your work disappear. Give me a command."
                    );

                } else if (input.equals("bye")) {
                    break;

                } else if (input.equals("list")) {
                    System.out.println(
                            "Fine. Here's what you've dumped on me:"
                    );

                    for (int i = 0; i < taskCount; i++) {
                        System.out.println(
                                (i + 1) + ". " + task[i]
                        );
                    }

                } else if (input.startsWith("mark ")) {
                    try {
                        int taskNumber =
                                Integer.parseInt(
                                        input.substring(5)
                                );

                        int index = taskNumber - 1;

                        if (index < 0 || index >= taskCount) {
                            throw new ZsiggyException(
                                    "That task doesn't exist."
                            );
                        }

                        task[index].mark();

                        saveTasks(task, taskCount, dataFile);

                        System.out.println(
                                "Wait, you actually finished something? Wonders never cease."
                        );
                        System.out.println("Marked this one done:");
                        System.out.println(task[index]);

                    } catch (NumberFormatException e) {
                        throw new ZsiggyException(
                                "Give me a proper task number."
                        );
                    }

                } else if (input.startsWith("unmark ")) {
                    try {
                        int taskNumber =
                                Integer.parseInt(
                                        input.substring(7)
                                );

                        int index = taskNumber - 1;

                        if (index < 0 || index >= taskCount) {
                            throw new ZsiggyException(
                                    "That task doesn't exist."
                            );
                        }

                        task[index].unmark();

                        saveTasks(task, taskCount, dataFile);

                        System.out.println(
                                "Caught you faking it, huh?"
                        );
                        System.out.println(
                                "Whatever, it's unmarked now:"
                        );
                        System.out.println(task[index]);

                    } catch (NumberFormatException e) {
                        throw new ZsiggyException(
                                "Give me a proper task number."
                        );
                    }

                } else if (input.startsWith("delete ")) {
                    try {
                        int taskNumber =
                                Integer.parseInt(
                                        input.substring(7)
                                );

                        int index = taskNumber - 1;

                        if (index < 0 || index >= taskCount) {
                            throw new ZsiggyException(
                                    "That task doesn't exist."
                            );
                        }

                        Task deletedTask = task[index];

                        for (int i = index; i < taskCount - 1; i++) {
                            task[i] = task[i + 1];
                        }

                        taskCount--;
                        task[taskCount] = null;

                        saveTasks(task, taskCount, dataFile);

                        System.out.println(
                                "Finally, one less thing cluttering your life:"
                        );
                        System.out.println(deletedTask);
                        System.out.println(
                                "Now you've got " + taskCount + " task(s) left."
                        );

                    } catch (NumberFormatException e) {
                        throw new ZsiggyException(
                                "Give me a proper task number."
                        );
                    }

                } else if (input.equals("todo")) {
                    throw new ZsiggyException(
                            "A todo needs a description."
                    );

                } else if (input.startsWith("todo ")) {
                    String description =
                            input.substring(5);

                    if (description.isBlank()) {
                        throw new ZsiggyException(
                                "A todo needs a description."
                        );
                    }

                    Task t = new Todo(description);
                    task[taskCount] = t;
                    taskCount++;

                    saveTasks(task, taskCount, dataFile);

                    System.out.println(
                            "Got it. Added to your never-ending pile:"
                    );
                    System.out.println(t);

                } else if (input.equals("deadline")) {
                    throw new ZsiggyException(
                            "Use: deadline DESCRIPTION /by TIME"
                    );

                } else if (input.startsWith("deadline ")) {
                    String content =
                            input.substring(9);

                    if (!content.contains(" /by ")) {
                        throw new ZsiggyException(
                                "Use: deadline DESCRIPTION /by TIME"
                        );
                    }

                    String[] parts =
                            content.split(" /by ", 2);

                    String description = parts[0];
                    String deadline = parts[1];

                    if (description.isBlank()
                            || deadline.isBlank()) {
                        throw new ZsiggyException(
                                "A deadline needs both a task and a deadline."
                        );
                    }

                    Task t =
                            new Deadline(
                                    description,
                                    deadline
                            );

                    task[taskCount] = t;
                    taskCount++;

                    saveTasks(task, taskCount, dataFile);

                    System.out.println(
                            "Tick-tock. Added this ticking time bomb:"
                    );
                    System.out.println(t);

                } else if (input.equals("event")) {
                    throw new ZsiggyException(
                            "Use: event DESCRIPTION /from START /to END"
                    );

                } else if (input.startsWith("event ")) {
                    String content =
                            input.substring(6);

                    if (!content.contains(" /from ")
                            || !content.contains(" /to ")) {
                        throw new ZsiggyException(
                                "Use: event DESCRIPTION /from START /to END"
                        );
                    }

                    String[] fromParts =
                            content.split(" /from ", 2);

                    String description =
                            fromParts[0];

                    String[] timeParts =
                            fromParts[1].split(" /to ", 2);

                    String fromDate =
                            timeParts[0];

                    String toDate =
                            timeParts[1];

                    if (description.isBlank()
                            || fromDate.isBlank()
                            || toDate.isBlank()) {
                        throw new ZsiggyException(
                                "An event needs a description, start, and end."
                        );
                    }

                    Task t =
                            new Event(
                                    description,
                                    fromDate,
                                    toDate
                            );

                    task[taskCount] = t;
                    taskCount++;

                    saveTasks(task, taskCount, dataFile);

                    System.out.println(
                            "Locked it into your schedule:"
                    );
                    System.out.println(t);

                } else {
                    throw new ZsiggyException(
                            "That's not a command I understand."
                    );
                }

            } catch (ZsiggyException e) {
                System.out.println(
                        "Oi. " + e.getMessage()
                );
            }
        }

        scanner.close();

        System.out.println(
                "Hmph. Bye. Go drink your green milk tea."
        );
    }
}