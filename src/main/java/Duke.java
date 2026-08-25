import java.util.Scanner;
public class Duke {
    public static void main(String[] args) {
        System.out.println("Zsiggy here. Make it quick.");
        System.out.println("What mess do you need me to sort out today?");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();


            if (input.equals("bye")) {
                break;
            }
            System.out.println(input);

        }

        System.out.println("Hmph. Bye. Go drink your green milk tea.");
    }
}
