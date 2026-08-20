import java.util.Scanner;
import java.util.ArrayList;
import java.util.EmptyStackException;

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

        public DbItem(String o, Scanner scanner) throws EmptyStringException {
            if (o.length() >= 9 && o.substring(0, 9).equals("deadline ")) {
                this.eventType = ItemType.DEADLINE;
                parseDeadline(o.substring(9), scanner);
            } else if (o.length() >= 6 && o.substring(0, 6).equals("event ")) {
                this.eventType = ItemType.EVENT;
                parseEvent(o.substring(6), scanner);
            } else {
                this.eventType = ItemType.TODO;
                if (o.length() >= 5 && o.substring(0, 5).equals("todo ")) {
                    this.objective = o.substring(5);
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

        private void parseDeadline(String inp, Scanner scanner) throws EmptyStringException {
            try {
                if (inp.contains("/by ")) {
                    String[] parts = inp.split("/by ", 2);
                    this.objective = parts[0].trim();
                    if (parts[1].trim().isEmpty()) {
                        this.by = prompt(scanner, "By: ");
                    } else {
                        this.by = parts[1].trim();
                    }
                } else {
                    this.objective = inp.trim();
                    this.by = prompt(scanner, "By: ");
                }
            }
            catch (EmptyStringException e) {
                System.out.print(line + e.getMessage() + '\n' + line);
            }
        }

        private void parseEvent(String inp, Scanner scanner) throws EmptyStringException {
            try {
                boolean f = inp.contains("/from ");
                boolean t = inp.contains("/to ");
                if (!f && !t) {
                    this.objective = inp.trim();
                    this.from = prompt(scanner, "From: ");
                    this.to = prompt(scanner, "To: ");
                } else if (f && t) {
                    int idxOfF = inp.indexOf("/from ");
                    int idxOfT = inp.indexOf("/to ");
                    String objective = inp.substring(0, Math.min(idxOfF, idxOfT)).trim();
                    this.objective = objective;
                    if (idxOfF < idxOfT) {
                        String from = inp.substring(idxOfF + 6, idxOfT).trim();
                        if (from.isEmpty()) this.from = prompt(scanner, "From: ");
                        else this.from = from;
                        String to = inp.substring(idxOfT + 4).trim();
                        if (to.isEmpty()) this.to = prompt(scanner, "To: ");
                        else this.to = to;
                    } else {
                        String from = inp.substring(idxOfF + 6).trim();
                        if (from.isEmpty()) this.from = prompt(scanner, "From: ");
                        else this.from = from;
                        String to = inp.substring(idxOfT + 4, idxOfF).trim();
                        if (to.isEmpty()) this.to = prompt(scanner, "To: ");
                        else this.to = to;
                    }
                } else {
                    if (f) {
                        String[] parts = inp.split("/from ", 2);
                        this.objective = parts[0];
                        if (parts[1].trim().isEmpty()) this.from = prompt(scanner, "From: ");
                        else this.from = parts[1];
                        this.to = prompt(scanner, "To: ");
                    } else {
                        String[] parts = inp.split("/to ", 2);
                        this.objective = parts[0];
                        if (parts[1].trim().isEmpty()) this.to = prompt(scanner, "To: ");
                        else this.to = parts[1];
                        this.from = prompt(scanner, "From: ");
                    }
                }
            } catch (EmptyStringException e) {
                System.out.print(line + e.getMessage() + "\n" + line);
            }
        }

        private String prompt(Scanner scanner, String tag) throws EmptyStringException {
            while (true) {
                System.out.print(tag);
                try {
                    String response = scanner.nextLine().trim();
                    if (response.isEmpty()) throw new EmptyStringException("(" + tag.substring(0, tag.length()-2) + ") cannot be empty!");
                    return response;
                } catch(EmptyStringException e) {
                    System.out.print(e.getMessage() + '\n');
                }
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
            try {
                if (newline.isEmpty()) throw new EmptyStringException("Can't be blank mate!");
                switch (newline) {
                    case "list": {
                                     System.out.print(line);
                                     for (int i = 0; i < r.db.size(); i++) {
                                         System.out.println(String.format("%d.%s", i+1, r.db.get(i).toString()));
                                     }
                                     System.out.println("Now you have " + r.db.size() + " tasks in the list. \n" + line);
                                     break;
                    }
                    case "bye": {
                                    scanner.close();
                                    bool = false;
                                    System.out.println(line + Goodbye);
                                    break;
                    }
                    default: {
                                 if (newline.length() >= 5 && newline.substring(0, 5).equals("mark ") && 
                                         newline.substring(5).chars().allMatch(Character::isDigit)) {
                                     if (newline.substring(5).trim().isEmpty()) throw new EmptyStringException("Please key in a number to mark!");
                                     int idx = Integer.parseInt(newline.substring(5));
                                     if (idx > r.db.size() || idx <= 0) throw new MarkOutException();
                                     else {
                                         r.db.get(idx - 1).markDone();
                                         System.out.println(line + 
                                                 "Great! Marking as Done...\n" +
                                                 r.db.get(idx-1).toString() +
                                                 '\n' + line);
                                     }
                                         }
                                 else if (newline.length() >= 7 && newline.substring(0, 7).equals("unmark ") && 
                                         newline.substring(7).chars().allMatch(Character::isDigit)) {
                                     if (newline.substring(5).trim().isEmpty()) throw new EmptyStringException("Please key in a number to unmark!");
                                     int idx = Integer.parseInt(newline.substring(7));
                                     if (idx > r.db.size() || idx <= 0) throw new MarkOutException();
                                     else {
                                         r.db.get(idx - 1).markUndone();
                                         System.out.println(line + 
                                                 "Oops! Getting back to it I see!\n" +
                                                 r.db.get(idx-1).toString() +
                                                 '\n' + line);
                                     }
                                         }
                                 else {
                                     DbItem curr = new DbItem(newline, scanner);
                                     if (curr.getObjective().isEmpty()) throw new EmptyStringException("Please key in an event!");
                                     r.db.add(curr);
                                     System.out.println(line + prefix + curr);
                                     System.out.println("Now you have " + r.db.size() + " tasks in the list. \n" + line);
                                 }
                                 break;
                    }
                }
            } catch (MarkOutException | EmptyStringException e) {
                System.out.print(line + e.getMessage() + "\n" + line);
            }
        }
    }
}
