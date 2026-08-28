package zsiggy;

import zsiggy.task.Task;

import java.util.Scanner;

/**
 * Handles all interaction between Zsiggy and the user.
 */
public class Ui {
    private final Scanner scanner;

    /**
     * Creates a user interface that reads input from standard input.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays Zsiggy's welcome message.
     */
    public void showWelcome() {
        System.out.println("Zsiggy here. Make it quick.");
        System.out.println("What mess do you need me to sort out today?");
    }

    /**
     * Reads the next command entered by the user.
     *
     * @return the user's input
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Displays all currently stored tasks.
     *
     * @param tasks the array containing tasks
     * @param taskCount the number of tasks to display
     */
    public void showTaskList(Task[] tasks, int taskCount) {
        System.out.println(
                "Fine. Here's what you've dumped on me:"
        );

        for (int i = 0; i < taskCount; i++) {
            System.out.println(
                    (i + 1) + ". " + tasks[i]
            );
        }
    }

    /**
     * Displays a message confirming that a task was marked as done.
     *
     * @param task the task that was marked
     */
    public void showMarkedTask(Task task) {
        System.out.println(
                "Wait, you actually finished something? Wonders never cease."
        );
        System.out.println("Marked this one done:");
        System.out.println(task);
    }

    /**
     * Displays a message confirming that a task was unmarked.
     *
     * @param task the task that was unmarked
     */
    public void showUnmarkedTask(Task task) {
        System.out.println(
                "Caught you faking it, huh?"
        );
        System.out.println(
                "Whatever, it's unmarked now:"
        );
        System.out.println(task);
    }

    /**
     * Displays information about a deleted task.
     *
     * @param task the deleted task
     * @param taskCount the number of remaining tasks
     */
    public void showDeletedTask(Task task, int taskCount) {
        System.out.println(
                "Finally, one less thing cluttering your life:"
        );
        System.out.println(task);
        System.out.println(
                "Now you've got " + taskCount + " task(s) left."
        );
    }

    /**
     * Displays confirmation that a todo was added.
     *
     * @param task the added todo
     */
    public void showTodoAdded(Task task) {
        System.out.println(
                "Got it. Added to your never-ending pile:"
        );
        System.out.println(task);
    }

    /**
     * Displays confirmation that a deadline was added.
     *
     * @param task the added deadline
     */
    public void showDeadlineAdded(Task task) {
        System.out.println(
                "Tick-tock. Added this ticking time bomb:"
        );
        System.out.println(task);
    }

    /**
     * Displays confirmation that an event was added.
     *
     * @param task the added event
     */
    public void showEventAdded(Task task) {
        System.out.println(
                "Locked it into your schedule:"
        );
        System.out.println(task);
    }

    /**
     * Displays an error message.
     *
     * @param message the error message to display
     */
    public void showError(String message) {
        System.out.println(
                "Oi. " + message
        );
    }

    /**
     * Displays Zsiggy's goodbye message.
     */
    public void showExit() {
        System.out.println(
                "Hmph. Bye. Go drink your green milk tea."
        );
    }

    /**
     * Closes the input scanner.
     */
    public void close() {
        scanner.close();
    }
}