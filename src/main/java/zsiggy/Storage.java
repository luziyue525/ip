package zsiggy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Handles loading and saving tasks to persistent storage.
 */

import zsiggy.task.Deadline;
import zsiggy.task.Event;
import zsiggy.task.Task;
import zsiggy.task.Todo;


public class Storage {
    private final File dataFolder;
    private final File dataFile;

    /**
     * Creates a Storage object using the specified file path.
     *
     * @param filePath the path of the file used to store tasks
     */
    public Storage(String filePath) {
        this.dataFolder = new File("data");
        this.dataFile = new File(filePath);
    }

    /**
     * Creates the data folder and task data file if they do not exist.
     *
     * @throws IOException if the data folder or file cannot be created
     */
    public void createDataFile() throws IOException {
        if (!dataFolder.exists()) {
            dataFolder.mkdir();
        }

        if (!dataFile.exists()) {
            dataFile.createNewFile();
        }
    }

    /**
     * Loads tasks from the data file.
     *
     * @return a TaskList containing the tasks stored in the file
     * @throws FileNotFoundException if the data file cannot be found
     */
    public TaskList load() throws FileNotFoundException {
        TaskList tasks = new TaskList();
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

                loadedTask = new Deadline(description, deadline);

            } else if (type.equals("E")) {
                String description = parts[2];
                String fromDate = parts[3];
                String toDate = parts[4];

                loadedTask = new Event(description, fromDate, toDate);

            } else {
                continue;
            }

            if (isDone) {
                loadedTask.mark();
            }

            tasks.add(loadedTask);
        }

        fileScanner.close();
        return tasks;
    }

    /**
     * Saves all tasks in the given task list to the data file.
     *
     * @param tasks the task list to save
     * @throws IOException if the task data cannot be written
     */
    public void save(TaskList tasks) throws IOException {
        FileWriter writer = new FileWriter(dataFile);

        for (int i = 0; i < tasks.getTaskCount(); i++) {
            Task currentTask = tasks.get(i);
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
    }
}