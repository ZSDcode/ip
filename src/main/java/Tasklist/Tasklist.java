package Tasklist;

import java.util.ArrayList;
import java.util.Scanner;

import Cxception.InvalidDateException;

public class Tasklist {
    private ArrayList<Task> tasklist;
    private int size = 0;
    private int markedSize = 0;

    public Tasklist() {
        this.tasklist = new ArrayList<>();
    }

public Tasklist(Scanner s) {
    this.tasklist = new ArrayList<>();
    while (s.hasNextLine()) {
        String newl = s.nextLine();
        if (newl.isBlank()) {
            continue;
        }
        String[] parts = newl.split("\\|");
        for (int i = 0; i < parts.length; i++) {
            parts[i] = parts[i].trim();
        }
        try {
            Task t = null;
            switch (parts[0]) {
                case "T":
                    t = new Task(parts[2]);
                    this.size++;
                    break;
                case "D":
                    t = new Deadline(parts[2], parts[3]);
                    this.size++;
                    break;
                case "E":
                    t = new Event(parts[2], parts[3], parts[4]);
                    this.size++;
                    break;
                default:
                    System.out.println("I found something I don't recognize... I threw it away, don't worry: " + newl);
                    break;
            }
            if (t != null && parts[1].contains("X")) {
                t.done = true;
                this.markedSize++;
            }
            this.tasklist.add(t);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("This line is broken and ugly, so I got rid of it for you: " + newl);
        } catch (InvalidDateException e) {
            System.out.println("This date lied to me, so I deleted it. It won't hurt you anymore: " + newl);
        }
    }
}
    @Override
    public String toString() {
        String out = "";
        for (int i = 0; i < this.size; i++) {
            out += String.format("%d. %s\n", i+1, this.tasklist.get(i));
        }
        return out;
    }

    public int getSize() {
        return this.size;
    }

    public void addItem(Task t) {
        this.tasklist.add(t);
        this.size++;
        System.out.println("Another task, just for you~ I'll watch over it forever.\n"
                + t + '\n'
                + String.format("You have %d tasks now. I'll make sure none of them ever leave you.\n", this.size));
    }

    public void deleteItem(int idx) {
        Task t = tasklist.get(idx);
        this.tasklist.remove(idx);
        this.size--;
        System.out.println("I erased it so it could never come between us again.\n"
                + t + '\n'
                + String.format("Only %d tasks left. I'll get rid of the rest too, if you let me.\n", this.size));
    }

    public void markItem(int idx) {
        Task marked = this.tasklist.get(idx);
        if (!marked.done) {
            marked.setDone();
            this.markedSize++;
        }
        System.out.println("You did it for me? I knew you'd never disappoint me. Never leave me.\n"
                + marked + '\n'
                + String.format("%d tasks remain. I'm counting every single one.\n", this.size - this.markedSize));
    }

    public void unmarkItem(int idx) {
        Task unmarked = this.tasklist.get(idx);
        if (unmarked.done) {
            unmarked.setUndone();
            this.markedSize--;
        }
        System.out.println("Going back on it? That's fine... I'll wait as long as it takes. I always wait.\n"
                + unmarked + '\n'
                + String.format("%d tasks left. I'm not going anywhere.\n", this.size - this.markedSize));
    }

    public String fileFormat() {
        String s = "";
        for (int i = 0; i < this.size; i++) {
            s += tasklist.get(i).fileFormatText() + "\n";
        }
        return s;
    }

    public void findItems(String search) {
        String out = "";
        for (int i = 0; i < this.size; i++) {
            out += tasklist.get(i).contains(search) ? tasklist.get(i) + "\n" : "";
        }
        System.out.print(out.isEmpty()
                ? String.format("Nothing called \"%s\"... good. It doesn't get to exist here.\n", search)
                : out);
    }

    public void clearItems() {
        this.tasklist = new ArrayList<>();
        this.size = 0;
        System.out.println("There. Everyone else is gone now. It's just us.\n");
    }
}
