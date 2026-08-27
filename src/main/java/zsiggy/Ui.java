package zsiggy;

import zsiggy.task.Task;

import java.util.Scanner;

public class Ui {
    private final Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public void showWelcome() {
        System.out.println("Zsiggy here. Make it quick.");
        System.out.println("What mess do you need me to sort out today?");
    }

    public String readCommand() {
        return scanner.nextLine();
    }

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

    public void showMarkedTask(Task task) {
        System.out.println(
                "Wait, you actually finished something? Wonders never cease."
        );
        System.out.println("Marked this one done:");
        System.out.println(task);
    }

    public void showUnmarkedTask(Task task) {
        System.out.println(
                "Caught you faking it, huh?"
        );
        System.out.println(
                "Whatever, it's unmarked now:"
        );
        System.out.println(task);
    }

    public void showDeletedTask(Task task, int taskCount) {
        System.out.println(
                "Finally, one less thing cluttering your life:"
        );
        System.out.println(task);
        System.out.println(
                "Now you've got " + taskCount + " task(s) left."
        );
    }

    public void showTodoAdded(Task task) {
        System.out.println(
                "Got it. Added to your never-ending pile:"
        );
        System.out.println(task);
    }

    public void showDeadlineAdded(Task task) {
        System.out.println(
                "Tick-tock. Added this ticking time bomb:"
        );
        System.out.println(task);
    }

    public void showEventAdded(Task task) {
        System.out.println(
                "Locked it into your schedule:"
        );
        System.out.println(task);
    }

    public void showError(String message) {
        System.out.println(
                "Oi. " + message
        );
    }

    public void showExit() {
        System.out.println(
                "Hmph. Bye. Go drink your green milk tea."
        );
    }

    public void close() {
        scanner.close();
    }
}