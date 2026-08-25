import java.util.Scanner;
public class Duke {
    public static void main(String[] args) {
        System.out.println("Zsiggy here. Make it quick.");
        System.out.println("What mess do you need me to sort out today?");
        Scanner scanner = new Scanner(System.in);

        String[] tasks = new String[100];
        int taskCount = 0;

        while (true) {
            String input = scanner.nextLine();

            if (input.equals("bye")) {
                break;

            } else if (input.equals("list")) {
                System.out.println("Fine. Here's what you've dumped on me:");

                for (int i = 0; i < taskCount; i++) {
                    System.out.println((i + 1) + ". " + tasks[i]);
                }

            } else {
                tasks[taskCount] = input;
                taskCount++;

                System.out.println("Yeah, yeah. I've got it:");
                System.out.println(input);
            }
        }

        scanner.close();
        System.out.println("Hmph. Bye. Go drink your green milk tea.");
    }
}
