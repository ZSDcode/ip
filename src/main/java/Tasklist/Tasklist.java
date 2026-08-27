package Tasklist;

import java.util.ArrayList;
import java.util.Scanner;

public class Tasklist {
    private ArrayList<Task> tasklist;
    private int size = 0;
    public String postfixStr = String.format("You have %d tasks left! Better get Cracking!\n", this.size);

    public Tasklist() {
        this.tasklist = new ArrayList<>();
    }

    public Tasklist(Scanner s) {
        this.tasklist = new ArrayList<>();
        while(s.hasNextLine()) {
            String newl = s.nextLine();
            String[] parts = newl.split("\\|");
            for (int i = 0; i < parts.length; i++) {
                parts[i] = parts[i].trim();
            }
            switch(parts[0]) {
                case "T":
                    this.tasklist.add(new Task(parts[1]));
                    break;
                case "D":
                    this.tasklist.add(new Deadline(parts[1], parts[2]));
                    break;
                case "E":
                    this.tasklist.add(new Event(parts[1], parts[2], parts[3]));
                    break;
                default:
                    break;
            }
            this.size++;
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
        System.out.println("You've added a new task! Congrats, more work now :(\n"
                + t + '\n' 
                + String.format("You have %d tasks left! Better get Cracking!\n", this.size));
    }

    public void deleteItem(int idx) {
        Task t = tasklist.get(idx);
        this.tasklist.remove(idx);
        this.size--;
        System.out.println("You've deleted a task! Time to sleep! :)\n"
                + t + '\n'
                + String.format("You have %d tasks left! Better get Cracking!\n", this.size));
    }

    public void markItem(int idx) {
        Task marked = this.tasklist.get(idx);
        marked.setDone();
        System.out.println("Productive today I see! WHO'S NEXT!!\n"
                + marked + '\n' 
                + String.format("You have %d tasks left! Better get Cracking!\n", this.size));
    }

    public void unmarkItem(int idx) {
        Task unmarked = this.tasklist.get(idx);
        unmarked.setUndone();
        System.out.println("THERE'S MORE??? Toughies, there there...\n"
                + unmarked + '\n'
                + String.format("You have %d tasks left! Better get Cracking!\n", this.size));
    }

    public String fileFormat() {
        String s = "";
        for (int i = 0; i < this.size; i++) {
            s += tasklist.get(i).fileFormatText() + "\n";
        }
        return s;
    }
}
