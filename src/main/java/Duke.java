import java.util.Scanner;

public class Duke {
    public static void main(String[] args) {
        System.out.println("Zsiggy here. Make it quick.");
        System.out.println("What mess do you need me to sort out today?");
        Scanner scanner = new Scanner(System.in);

        Task[] task = new Task[100];
        int taskCount= 0;

        while (true) {
            String input = scanner.nextLine();

            if (input.equals("bye")) {
                break;

            } else if (input.equals("list")) {
                System.out.println("Fine. Here's what you've dumped on me:");

                for (int i = 0; i < taskCount; i++) {
                    String status;

                    if (task[i].isDone) {
                        status = "[X]";
                    } else {
                        status = "[ ]";
                    }
                    System.out.println((i + 1) + ". " + status + " " + task[i].description);
                }

            }
            else if (input.startsWith("mark ")) {
                int taskNumber = Integer.parseInt(input.substring(5));
                int index = taskNumber - 1;
                task[index].isDone = true;

                System.out.println("Wait, you actually finished something? Wonders never cease.");
                System.out.println("Marked this one done:");
                System.out.println("[X] " + task[index].description);

            }
            else if (input.startsWith("unmark ")) {
                int taskNumber = Integer.parseInt(input.substring(7));
                int index = taskNumber - 1;
                task[index].isDone = false;

                System.out.println("Caught you faking it, huh?");
                System.out.println("Whatever, it's unmarked now:");
                System.out.println("[ ] " + task[index].description);

            }


            else {
                task[taskCount] = new Task(input);
                taskCount++;

                System.out.println("Yeah, yeah. I've got it:");
                System.out.println(input);
            }
        }

        scanner.close();
        System.out.println("Hmph. Bye. Go drink your green milk tea.");
    }
}
