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
                    System.out.println((i + 1) + ". "  + task[i]);
                }

            }
            else if (input.startsWith("mark ")) {
                int taskNumber = Integer.parseInt(input.substring(5));
                int index = taskNumber - 1;
                task[index].mark();

                System.out.println("Wait, you actually finished something? Wonders never cease.");
                System.out.println("Marked this one done:");
                System.out.println(task[index]);

            }
            else if (input.startsWith("unmark ")) {
                int taskNumber = Integer.parseInt(input.substring(7));
                int index = taskNumber - 1;
                task[index].unmark();

                System.out.println("Caught you faking it, huh?");
                System.out.println("Whatever, it's unmarked now:");
                System.out.println(task[index]);

            }
            else if (input.startsWith("todo ")){
                String description = input.substring(5);
                Task t  = new Todo(description);
                task[taskCount] = t;
                taskCount ++;


                System.out.println("Got it. Added to your never-ending pile:");
                System.out.println(t);



            }
            else if (input.startsWith("deadline ")){
                String content = input.substring(9);

                String[] parts = content.split(" /by ");

                String description = parts[0];
                String deadline = parts[1];

                Task t = new Deadline(description, deadline);
                task[taskCount] = t;
                taskCount++;

                System.out.println("Tick-tock. Added this ticking time bomb:");
                System.out.println(t);


            }
            else if (input.startsWith("event ")) {
                String content = input.substring(6);

                String[] fromParts = content.split(" /from ");

                String description = fromParts[0];

                String[] timeParts = fromParts[1].split(" /to ");

                String fromDate = timeParts[0];
                String toDate = timeParts[1];

                Task t = new Event(description, fromDate, toDate);
                task[taskCount] = t;
                taskCount++;

                System.out.println("Locked it into your schedule:");
                System.out.println(t);
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
