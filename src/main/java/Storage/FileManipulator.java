package Storage;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import java.io.File;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

import Tasklist.Tasklist;

public class FileManipulator {
    private final static String OS = System.getProperty("os.name").toLowerCase();
    private final static Path BASE_DIR = FileManipulator.resolveBaseDir();
    private final static Path MASTER_PATH = BASE_DIR.resolve("masterTasklist.txt");
    private final static Path TEMP_PATH = BASE_DIR.resolve("tempTasklist.txt");

    public FileManipulator() { }

    private static Path resolveBaseDir() {
        String home = System.getProperty("user.home");
        if (OS.contains("win")) {
            String localAppData = System.getenv("LOCALAPPDATA");
            Path base = (localAppData != null) ? Paths.get(localAppData) : Paths.get(home, "AppData", "Local");
            return base.resolve("remy");
        } else {
            String xdgCache = System.getenv("XDG_CACHE_HOME");
            Path base = (xdgCache != null) ? Paths.get(xdgCache) : Paths.get(home, ".cache");
            return base.resolve("remy");
        }
    }

    public static Tasklist loadFile() {
        try {
            if (!Files.exists(MASTER_PATH)) {
                Files.createDirectories(MASTER_PATH.getParent());
                Files.writeString(MASTER_PATH, "");
            }
            File f = MASTER_PATH.toFile();
            Scanner s = new Scanner(f);
            Tasklist curr = new Tasklist(s);
            s.close();
            return curr;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return new Tasklist();
        }
    }

    public static void saveFile(Tasklist tL) {
        try {
            if (!Files.exists(TEMP_PATH)) {
                Files.writeString(TEMP_PATH, "");
            }
            File tmp = TEMP_PATH.toFile();
            FileWriter fw = new FileWriter(tmp);
            fw.write(tL.fileFormat());
            fw.close();
            Files.move(TEMP_PATH, MASTER_PATH, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println("Something went wrong! Can't write to temp file!");
            System.out.println(e.getMessage());
        }
    }
}
