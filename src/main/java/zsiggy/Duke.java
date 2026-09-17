package zsiggy;

import java.io.FileNotFoundException;
import java.io.IOException;

import zsiggy.task.Deadline;
import zsiggy.task.Event;
import zsiggy.task.Task;
import zsiggy.task.Todo;


/**
 * Main class of the Zsiggy chatbot.
 *
 * Coordinates user interaction, task management, command handling,
 * and persistent storage.
 */
public class Duke {
    private final Ui ui;
    private final Storage storage;
    private TaskList tasks;

    /**
     * Creates a new Duke instance with its user interface,
     * storage system, and task list.
     */
    public Duke() {
        this.ui = new Ui();
        this.storage = new Storage("data/tasks.txt");
        this.tasks = new TaskList();
    }

    /**
     * Starts Zsiggy and continuously processes user commands
     * until the user exits the application.
     */
    public void run() {
        ui.showWelcome();

        try {
            storage.createDataFile();
            tasks = storage.load();
        } catch (FileNotFoundException e) {
            ui.showError("Couldn't load your saved tasks.");
        } catch (IOException e) {
            ui.showError("Couldn't create the save file.");
        }

        while (true) {
            String input = ui.readCommand();

            try {
                if (input.isBlank()) {
                    throw new ZsiggyException(
                            "...Saying nothing won't make your work disappear. Give me a command."
                    );

                } else if (input.equals("bye")) {
                    break;

                } else if (input.equals("list")) {
                    ui.showTaskList(tasks.getTasks(), tasks.getTaskCount());

                } else if (input.equals("find")) {
                    throw new ZsiggyException(
                            "You want me to find... what exactly? Give me a keyword."
                    );
                } else if (input.startsWith("find ")) {
                    String keyword = input.substring(5);

                    if (keyword.isBlank()) {
                        throw new ZsiggyException(
                                "You want me to find... what exactly? Give me a keyword."
                        );
                    }

                    Task[] matches = tasks.find(keyword);
                    ui.showFoundTasks(matches);
                } else if (input.startsWith("mark ")) {
                    int index = parseExistingTaskIndex(input, 5);

                    tasks.mark(index);
                    saveTasks();
                    ui.showMarkedTask(tasks.get(index));

                } else if (input.startsWith("unmark ")) {
                    int index = parseExistingTaskIndex(input, 7);

                    tasks.unmark(index);
                    saveTasks();
                    ui.showUnmarkedTask(tasks.get(index));

                } else if (input.startsWith("delete ")) {
                    int index = parseExistingTaskIndex(input, 7);

                    Task deletedTask = tasks.delete(index);

                    saveTasks();
                    ui.showDeletedTask(deletedTask, tasks.getTaskCount());

                } else if (input.equals("todo") || input.equals("t")) {
                    throw new ZsiggyException("A todo needs an actual description. I can't organise invisible tasks.");

                } else if (input.startsWith("todo ") || input.startsWith("t ")) {
                    String description;

                    if (input.startsWith("t ")) {
                        description = input.substring(2);
                    } else {
                        description = input.substring(5);
                    }

                    if (description.isBlank()) {
                        throw new ZsiggyException(
                                "A todo needs an actual description. I can't organise invisible tasks.");
                    }

                    Task task = new Todo(description);

                    tasks.add(task);
                    saveTasks();
                    ui.showTodoAdded(task);

                } else if (input.equals("deadline")) {
                    throw new ZsiggyException(
                            "That's not much of a deadline. "
                                    + "Use: deadline DESCRIPTION /by YYYY-MM-DD"
                    );

                } else if (input.startsWith("deadline ")) {
                    String content = input.substring(9);

                    if (!content.contains(" /by ")) {
                        throw new ZsiggyException(
                                "That's not much of a deadline. "
                                        + "Use: deadline DESCRIPTION /by YYYY-MM-DD"
                        );
                    }

                    String[] parts = content.split(" /by ", 2);

                    String description = parts[0];
                    String deadline = parts[1];

                    if (description.isBlank() || deadline.isBlank()) {
                        throw new ZsiggyException(
                                "A deadline needs both a task and a deadline."
                        );
                    }

                    Task task = new Deadline(description, deadline);

                    tasks.add(task);
                    saveTasks();
                    ui.showDeadlineAdded(task);

                } else if (input.equals("event")) {
                    throw new ZsiggyException(
                            "Give me the whole event. Use: event DESCRIPTION /from YYYY-MM-DD /to YYYY-MM-DD"
                    );

                } else if (input.startsWith("event ")) {
                    String content = input.substring(6);

                    if (!content.contains(" /from ") || !content.contains(" /to ")) {
                        throw new ZsiggyException(
                                "Give me the whole event. Use: event DESCRIPTION /from YYYY-MM-DD /to YYYY-MM-DD"
                        );
                    }

                    String[] fromParts = content.split(" /from ", 2);
                    String description = fromParts[0];

                    String[] timeParts = fromParts[1].split(" /to ", 2);
                    String fromDate = timeParts[0];
                    String toDate = timeParts[1];

                    if (description.isBlank() || fromDate.isBlank() || toDate.isBlank()) {
                        throw new ZsiggyException(
                                "You can't just show up to nowhere, never. "
                                        + "An event needs a description, start, and end."
                        );
                    }

                    Task task = new Event(description, fromDate, toDate);

                    tasks.add(task);
                    saveTasks();
                    ui.showEventAdded(task);

                } else {
                    throw new ZsiggyException(
                            "That's not a command I understand."
                    );
                }

            } catch (ZsiggyException e) {
                ui.showError("Oi. " + e.getMessage());
            }
        }

        ui.close();
        ui.showExit();
    }

    /**
     * Processes a user command and returns Zsiggy's response.
     *
     * @param input The command entered by the user.
     * @return Zsiggy's response to the command.
     */
    public String getResponse(String input) {
        try {
            if (input.isBlank()) {
                throw new ZsiggyException(
                        "...Saying nothing won't make your work disappear. Give me a command."
                );

            } else if (input.equals("bye")) {
                return "Hmph. Bye. Go drink your green milk tea.";

            } else if (input.equals("list")) {
                StringBuilder response = new StringBuilder(
                        "Fine. Here's what you've dumped on me:"
                );

                for (int i = 0; i < tasks.getTaskCount(); i++) {
                    response.append(System.lineSeparator())
                            .append(i + 1)
                            .append(". ")
                            .append(tasks.get(i));
                }

                return response.toString();

            } else if (input.equals("find")) {
                throw new ZsiggyException(
                        "You want me to find... what exactly? Give me a keyword."
                );

            } else if (input.startsWith("find ")) {
                String keyword = input.substring(5);

                if (keyword.isBlank()) {
                    throw new ZsiggyException(
                            "You want me to find... what exactly? Give me a keyword."
                    );
                }

                Task[] matches = tasks.find(keyword);

                StringBuilder response = new StringBuilder(
                        "Fine. Here are the matching tasks:"
                );

                for (int i = 0; i < matches.length; i++) {
                    response.append(System.lineSeparator())
                            .append(i + 1)
                            .append(". ")
                            .append(matches[i]);
                }

                return response.toString();

            } else if (input.startsWith("mark ")) {
                int index = parseExistingTaskIndex(input, 5);

                tasks.mark(index);
                saveTasks();

                return "Wait, you actually finished something? Wonders never cease."
                        + System.lineSeparator()
                        + "Marked this one done:"
                        + System.lineSeparator()
                        + tasks.get(index);

            } else if (input.startsWith("unmark ")) {
                int index = parseExistingTaskIndex(input, 7);

                tasks.unmark(index);
                saveTasks();

                return "Caught you faking it, huh?"
                        + System.lineSeparator()
                        + "Whatever, it's unmarked now:"
                        + System.lineSeparator()
                        + tasks.get(index);

            } else if (input.startsWith("delete ")) {
                int index = parseExistingTaskIndex(input, 7);

                Task deletedTask = tasks.delete(index);
                saveTasks();

                return "Finally, one less thing cluttering your life:"
                        + System.lineSeparator()
                        + deletedTask
                        + System.lineSeparator()
                        + "Now you've got "
                        + tasks.getTaskCount()
                        + " task(s) left.";

            } else if (input.equals("todo") || input.equals("t")) {
                throw new ZsiggyException(
                        "A todo needs an actual description. I can't organise invisible tasks."
                );

            } else if (input.startsWith("todo ") || input.startsWith("t ")) {
                String description;

                if (input.startsWith("t ")) {
                    description = input.substring(2);
                } else {
                    description = input.substring(5);
                }

                if (description.isBlank()) {
                    throw new ZsiggyException(
                            "A todo needs an actual description. I can't organise invisible tasks."
                    );
                }

                Task task = new Todo(description);

                tasks.add(task);
                saveTasks();

                return "Got it. Added to your never-ending pile:"
                        + System.lineSeparator()
                        + task;

            } else if (input.equals("deadline")) {
                throw new ZsiggyException(
                        "That's not much of a deadline. "
                                + "Use: deadline DESCRIPTION /by YYYY-MM-DD"
                );

            } else if (input.startsWith("deadline ")) {
                String content = input.substring(9);

                if (!content.contains(" /by ")) {
                    throw new ZsiggyException(
                            "That's not much of a deadline. "
                                    + "Use: deadline DESCRIPTION /by YYYY-MM-DD"
                    );
                }

                String[] parts = content.split(" /by ", 2);

                String description = parts[0];
                String deadline = parts[1];

                if (description.isBlank() || deadline.isBlank()) {
                    throw new ZsiggyException(
                            "A deadline needs both a task and a deadline."
                    );
                }

                Task task = new Deadline(description, deadline);

                tasks.add(task);
                saveTasks();

                return "Tick-tock. Added this ticking time bomb:"
                        + System.lineSeparator()
                        + task;

            } else if (input.equals("event")) {
                throw new ZsiggyException(
                        "Give me the whole event. Use: event DESCRIPTION /from YYYY-MM-DD /to YYYY-MM-DD"
                );

            } else if (input.startsWith("event ")) {
                String content = input.substring(6);

                if (!content.contains(" /from ")
                        || !content.contains(" /to ")) {
                    throw new ZsiggyException(
                            "Give me the whole event. Use: event DESCRIPTION /from YYYY-MM-DD /to YYYY-MM-DD"
                    );
                }

                String[] fromParts = content.split(" /from ", 2);
                String description = fromParts[0];

                String[] timeParts = fromParts[1].split(" /to ", 2);

                String fromDate = timeParts[0];
                String toDate = timeParts[1];

                if (description.isBlank()
                        || fromDate.isBlank()
                        || toDate.isBlank()) {
                    throw new ZsiggyException(
                            "You can't just show up to nowhere, never. "
                                    + "An event needs a description, start, and end."
                    );
                }

                Task task = new Event(
                        description,
                        fromDate,
                        toDate
                );

                tasks.add(task);
                saveTasks();

                return "Locked it into your schedule:"
                        + System.lineSeparator()
                        + task;

            } else {
                throw new ZsiggyException(
                        "That's not a command I understand."
                );
            }

        } catch (ZsiggyException e) {
            return "Oi. " + e.getMessage();
        }
    }

    /**
     * Parses and validates the task number shared by task update commands.
     *
     * @param input The complete user command.
     * @param commandLength The length of the command prefix.
     * @return The zero-based index of an existing task.
     * @throws ZsiggyException If the task number is invalid or does not exist.
     */
    private int parseExistingTaskIndex(String input, int commandLength) throws ZsiggyException {
        int index = Parser.parseTaskNumber(input, commandLength);
        if (!tasks.isValidIndex(index)) {
            throw new ZsiggyException("That task doesn't exist.");
        }
        return index;
    }

    /**
     * Saves the current task list to persistent storage.
     */
    private void saveTasks() {
        try {
            storage.save(tasks);
        } catch (IOException e) {
            ui.showError("Couldn't save your tasks.");
        }
    }

    /**
     * Loads the saved task data.
     */
    public void loadTasks() {
        try {
            storage.createDataFile();
            tasks = storage.load();
        } catch (FileNotFoundException e) {
            tasks = new TaskList();
        } catch (IOException e) {
            tasks = new TaskList();
        }
    }

    /**
     * Entry point of the application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        new Duke().run();
    }
}
