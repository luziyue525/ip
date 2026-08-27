import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Storage {
    private final File dataFolder;
    private final File dataFile;

    public Storage(String filePath) {
        this.dataFolder = new File("data");
        this.dataFile = new File(filePath);
    }

    public void createDataFile() throws IOException {
        if (!dataFolder.exists()) {
            dataFolder.mkdir();
        }

        if (!dataFile.exists()) {
            dataFile.createNewFile();
        }
    }

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

            tasks.add(loadedTask);
        }

        fileScanner.close();

        return tasks;
    }

    public void save(TaskList tasks) throws IOException {
        FileWriter writer = new FileWriter(dataFile);

        for (int i = 0; i < tasks.getTaskCount(); i++) {
            Task currentTask = tasks.get(i);

            String done =
                    currentTask.isDone() ? "1" : "0";

            if (currentTask instanceof Todo) {
                writer.write(
                        "T | " + done + " | "
                                + currentTask.getDescription()
                                + System.lineSeparator()
                );

            } else if (currentTask instanceof Deadline) {
                Deadline deadline =
                        (Deadline) currentTask;

                writer.write(
                        "D | " + done + " | "
                                + deadline.getDescription()
                                + " | "
                                + deadline.getDeadline()
                                + System.lineSeparator()
                );

            } else if (currentTask instanceof Event) {
                Event event =
                        (Event) currentTask;

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