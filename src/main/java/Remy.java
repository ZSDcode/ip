import java.util.Scanner;
import java.util.ArrayList;

public class Remy {
    public static String banner = " ____                    \n"
        + "|  _ \\  ___  _ __ ___  _   _ \n"
        + "| |_) |/ _ \\| '_ ` _ \\| | | |\n"
        + "|  _ <|  __/| | | | | | |_| |\n"
        + "|_| \\_\\\\___||_| |_| |_|\\__, |\n"
        + "                       |___/ \n";
    public static String line = "_______________________________________________\n";
    public static String Greeting = line
        + "Hello! I'm Remy, your friendly reminder app! \n"
        + "How can I help you today?\n"
        + line;
    public static String Goodbye = "Bye, enjoy your day!! \n" + line;

    public static class DbItem {
        String objective;
        Boolean done;

        public DbItem(String o) {
            this.objective = o;
            this.done = false;
        }

        public boolean isDone() {
            return this.done;
        }

        public String getObjective() {
            return this.objective;
        }

        public void markDone() {
            this.done = true;
        }

        public void markUndone() {
            this.done = false;
        }

        @Override
        public String toString() {
            return String.format("[%c] %s", this.done ? 'X' : ' ', this.objective);
        }
    }

    public static void main(String[] args) {
        System.out.println(Remy.banner);
        System.out.println(Remy.Greeting);
        Scanner scanner = new Scanner(System.in);
        String prefix = "added: ";
        ArrayList<DbItem> db = new ArrayList<>();
        Boolean bool = true;
        while(bool) {
            String newline = scanner.nextLine();
            switch (newline) {
                case "list": {
                                 System.out.print(line);
                                 for (int i = 0; i < db.size(); i++) {
                                     System.out.println(String.format("%d.%s", i+1, db.get(i).toString()));
                                 }
                                 System.out.print(line);
                                 break;
                }
                case "bye": {
                                scanner.close();
                                bool = false;
                                System.out.println(line + Goodbye);
                                break;
                }
                default: {
                             if (newline.substring(0, 5).equals("mark ") && 
                                     newline.substring(5).chars().allMatch(Character::isDigit)) {
                                 int idx = Integer.parseInt(newline.substring(5));
                                 if (idx > db.size() || idx <= 0) {
                                     System.out.println(line + 
                                             "Sir, that's out of the index! Please try again!" +
                                             line);
                                 } else {
                                     db.get(idx - 1).markDone();
                                     System.out.println(line + 
                                             "Great! Marking as Done...\n" +
                                             db.get(idx-1).toString() +
                                             '\n' + line);
                                 }
                             }
                             else if (newline.substring(0, 7).equals("unmark ") && 
                                     newline.substring(7).chars().allMatch(Character::isDigit)) {
                                 int idx = Integer.parseInt(newline.substring(7));
                                 if (idx > db.size() || idx <= 0) {
                                     System.out.println(line + 
                                             "Sir, that's out of the index! Please try again!" +
                                             line);
                                 } else {
                                     db.get(idx - 1).markUndone();
                                     System.out.println(line + 
                                             "Oops! Getting back to it I see!\n" +
                                             db.get(idx-1).toString() +
                                             '\n' + line);
                                 }
                             }
                             else {
                                 db.add(new DbItem(newline));
                                 System.out.println(line + prefix + newline + '\n' + line);
                             }
                             break;
                }
            }
        }
    }
}
