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
    ArrayList<DbItem> db = new ArrayList<>();

    public static class DbItem {
        public enum ItemType {
            TODO, DEADLINE, EVENT
        }
        String objective;
        Boolean done;
        ItemType eventType;
        String by;
        String from;
        String to;

        public DbItem(String o) {
            if (o.length() > 9 && o.substring(0, 9).equals("deadline ")) {
                this.eventType = ItemType.DEADLINE;
                if (!o.contains("/")) {
                    this.objective = o.substring(9);
                } else {
                    this.objective = o.substring(9, o.indexOf("/"));
                }
            } else if (o.length() > 6 && o.substring(0, 6).equals("event ")) {
                this.eventType = ItemType.EVENT;
                if (!o.contains("/")) {
                    this.objective = o.substring(6);
                } else {
                    this.objective = o.substring(6, o.indexOf("/"));
                }
            } else {
                this.eventType = ItemType.TODO;
                if (o.substring(0, 5).equals("todo ")) {
                    this.objective = o.substring(4);
                } else {
                    this.objective = o;
                }
            }
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

        public void setBy(String s) {
            this.by = s;
        }

        public void setFrom(String s) {
            this.from = s;
        }

        public void setTo(String s) {
            this.to = s;
        }

        @Override
        public String toString() {
            switch(this.eventType) {
                case TODO:
                    return String.format("[T] [%c] %s", this.done ? 'X' : ' ', this.objective);
                case DEADLINE:
                    return String.format("[D] [%c] %s %s", this.done ? 'X' : ' ', this.objective, this.done ? " " : "(by: " + this.by + ")");
                case EVENT:
                    return String.format("[E] [%c] %s %s", this.done ? 'X' : ' ', this.objective, this.done ? " " : "(from: " + this.from + ", to: " + this.to + ")");
                default:
                    return "";
            }
        }
    }

    public static void main(String[] args) {
        Remy r = new Remy();
        System.out.println(Remy.banner);
        System.out.println(Remy.Greeting);
        Scanner scanner = new Scanner(System.in);
        String prefix = "Here ya go! Synced to Database: \n";
        Boolean bool = true;
        while(bool) {
            String newline = scanner.nextLine();
            switch (newline) {
                case "list": {
                                 System.out.print(line);
                                 for (int i = 0; i < r.db.size(); i++) {
                                     System.out.println(String.format("%d.%s", i+1, r.db.get(i).toString()));
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
                             if (newline.length() > 5 && newline.substring(0, 5).equals("mark ") && 
                                     newline.substring(5).chars().allMatch(Character::isDigit)) {
                                 int idx = Integer.parseInt(newline.substring(5));
                                 if (idx > r.db.size() || idx <= 0) {
                                     System.out.println(line + 
                                             "Sir, that's out of the index! Please try again! \n" +
                                             line);
                                 } else {
                                     r.db.get(idx - 1).markDone();
                                     System.out.println(line + 
                                             "Great! Marking as Done...\n" +
                                             r.db.get(idx-1).toString() +
                                             '\n' + line);
                                 }
                             }
                             else if (newline.length() > 7 && newline.substring(0, 7).equals("unmark ") && 
                                     newline.substring(7).chars().allMatch(Character::isDigit)) {
                                 int idx = Integer.parseInt(newline.substring(7));
                                 if (idx > r.db.size() || idx <= 0) {
                                     System.out.println(line + 
                                             "Sir, that's out of the index! Please try again!" +
                                             line);
                                 } else {
                                     r.db.get(idx - 1).markUndone();
                                     System.out.println(line + 
                                             "Oops! Getting back to it I see!\n" +
                                             r.db.get(idx-1).toString() +
                                             '\n' + line);
                                 }
                             }
                             else {
                                 DbItem curr = new DbItem(newline);
                                 if (newline.length() > 9 && newline.substring(0, 9).equals("deadline ")) {
                                     if (!newline.contains("/by ")) {
                                         System.out.print("/by ");
                                         curr.setBy(scanner.nextLine());
                                     } else {
                                         curr.setBy(newline.substring(newline.indexOf("/by ") + 4));
                                     }
                                 } else if (newline.length() > 6 && newline.substring(0, 6).equals("event ")) {
                                     boolean f = newline.contains("/from ");
                                     boolean t = newline.contains("/to ");
                                     if (!f && !t) {
                                         System.out.print("/from ");
                                         curr.setFrom(scanner.nextLine());
                                         System.out.print("/to ");
                                         curr.setTo(scanner.nextLine());
                                     } else if (f && t) {
                                         if (newline.indexOf("/from ") > newline.indexOf("/to ")) {
                                             curr.setTo(newline.substring(newline.indexOf("/to ") + 4, newline.indexOf("/from ")));
                                             curr.setFrom(newline.substring(newline.indexOf("/from ") + 6));
                                         } else {
                                             curr.setFrom(newline.substring(newline.indexOf("/from ") + 6, newline.indexOf("/to ")));
                                             curr.setTo(newline.substring(newline.indexOf("/to ") + 4));
                                         }
                                     } else {
                                         if (f) {
                                             curr.setFrom(newline.substring(newline.indexOf("/from ") + 6));
                                             System.out.print("/to ");
                                             curr.setTo(scanner.nextLine());
                                         } else {
                                             curr.setTo(newline.substring(newline.indexOf("/to ") + 4));
                                             System.out.print("/from ");
                                             curr.setFrom(scanner.nextLine());
                                         }
                                     }
                                 }
                                 r.db.add(curr);
                                 System.out.println(line + prefix + curr);
                                 System.out.println("Now you have " + r.db.size() + " tasks in the list. \n" + line);
                             }
                             break;
                }
            }
        }
    }
}
