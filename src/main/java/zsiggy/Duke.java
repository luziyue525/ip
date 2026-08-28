package zsiggy;

import zsiggy.task.Deadline;
import zsiggy.task.Event;
import zsiggy.task.Task;
import zsiggy.task.Todo;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Duke {
    private final Ui ui;
    private final Storage storage;
    private TaskList tasks;

    public Duke() {
        this.ui = new Ui();
        this.storage = new Storage("data/tasks.txt");
        this.tasks = new TaskList();
    }

    public void run() {
        ui.showWelcome();

        try {
            storage.createDataFile();
            tasks = storage.load();

        } catch (FileNotFoundException e) {
            ui.showError(
                    "Couldn't load your saved tasks."
            );

        } catch (IOException e) {
            ui.showError(
                    "Couldn't create the save file."
            );
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
                    ui.showTaskList(
                            tasks.getTasks(),
                            tasks.getTaskCount()
                    );

                } else if (input.equals("find")) {
                    throw new ZsiggyException(
                            "A find command needs a keyword."
                    );
                } else if (input.startsWith("find ")) {
                    String keyword = input.substring(5);

                    if (keyword.isBlank()) {
                        throw new ZsiggyException(
                                "A find command needs a keyword."
                        );
                    }

                    Task[] matches = tasks.find(keyword);
                    ui.showFoundTasks(matches);
                } else if (input.startsWith("mark ")) {
                    int index =
                            Parser.parseTaskNumber(
                                    input,
                                    5
                            );

                    if (!tasks.isValidIndex(index)) {
                        throw new ZsiggyException(
                                "That task doesn't exist."
                        );
                    }

                    tasks.mark(index);

                    saveTasks();

                    ui.showMarkedTask(
                            tasks.get(index)
                    );

                } else if (input.startsWith("unmark ")) {
                    int index =
                            Parser.parseTaskNumber(
                                    input,
                                    7
                            );

                    if (!tasks.isValidIndex(index)) {
                        throw new ZsiggyException(
                                "That task doesn't exist."
                        );
                    }

                    tasks.unmark(index);

                    saveTasks();

                    ui.showUnmarkedTask(
                            tasks.get(index)
                    );

                } else if (input.startsWith("delete ")) {
                    int index =
                            Parser.parseTaskNumber(
                                    input,
                                    7
                            );

                    if (!tasks.isValidIndex(index)) {
                        throw new ZsiggyException(
                                "That task doesn't exist."
                        );
                    }

                    Task deletedTask =
                            tasks.delete(index);

                    saveTasks();

                    ui.showDeletedTask(
                            deletedTask,
                            tasks.getTaskCount()
                    );

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

                    Task task =
                            new Todo(description);

                    tasks.add(task);

                    saveTasks();

                    ui.showTodoAdded(task);

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

                    String description =
                            parts[0];

                    String deadline =
                            parts[1];

                    if (description.isBlank()
                            || deadline.isBlank()) {
                        throw new ZsiggyException(
                                "A deadline needs both a task and a deadline."
                        );
                    }

                    Task task =
                            new Deadline(
                                    description,
                                    deadline
                            );

                    tasks.add(task);

                    saveTasks();

                    ui.showDeadlineAdded(task);

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
                            content.split(
                                    " /from ",
                                    2
                            );

                    String description =
                            fromParts[0];

                    String[] timeParts =
                            fromParts[1].split(
                                    " /to ",
                                    2
                            );

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

                    Task task =
                            new Event(
                                    description,
                                    fromDate,
                                    toDate
                            );

                    tasks.add(task);

                    saveTasks();

                    ui.showEventAdded(task);

                } else {
                    throw new ZsiggyException(
                            "That's not a command I understand."
                    );
                }

            } catch (ZsiggyException e) {
                ui.showError(
                        e.getMessage()
                );
            }
        }

        ui.close();
        ui.showExit();
    }

    private void saveTasks() {
        try {
            storage.save(tasks);

        } catch (IOException e) {
            ui.showError(
                    "Couldn't save your tasks."
            );
        }
    }

    public static void main(String[] args) {
        new Duke().run();
    }
}