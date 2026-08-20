import java.util.Scanner;
import java.util.ArrayList;

public class Remy {
    public static void main(String[] args) {
        String banner = " ____                    \n"
            + "|  _ \\  ___  _ __ ___  _   _ \n"
            + "| |_) |/ _ \\| '_ ` _ \\| | | |\n"
            + "|  _ <|  __/| | | | | | |_| |\n"
            + "|_| \\_\\\\___||_| |_| |_|\\__, |\n"
            + "                       |___/ \n";
        System.out.println(banner);
        String line = "_______________________________________________\n";
        String Greeting = line
            + "Hello! I'm Remy, your friendly reminder app! \n"
            + "How can I help you today?\n"
            + line;
        System.out.println(Greeting);
        String Goodbye = "Bye, enjoy your day!! \n" + line;
        Scanner scanner = new Scanner(System.in);
        String prefix = "added: ";
        ArrayList<String> db = new ArrayList<>();
        while(true) {
            String newline = scanner.nextLine();
            if (newline.equals("list")) {
                System.out.print(line);
                for (int i = 0; i < db.size(); i++) {
                    System.out.println(String.format("%d. %s", i, db.get(i)));
                }
                System.out.print(line);
            }
            else if (newline.equals("bye")) {
                scanner.close();
                break;
            }
            else {
                db.add(newline);
                System.out.println(line + prefix + newline + '\n' + line);
            }
        }
        System.out.println(line + Goodbye);
    }
}
