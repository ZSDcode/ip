package Ui;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UiTest {

    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    @Test
    public void displayGreet_called_printsGreetingAndBanner() {
        Ui.displayGreet();
        String output = outContent.toString();
        assertTrue(output.contains("Remy, your friendly reminder app!"));
    }

    @Test
    public void displayGoodbye_called_printsGoodbyeMessage() {
        Ui.displayGoodbye();
        String output = outContent.toString();
        assertTrue(output.contains("Bye, enjoy your day!!"));
    }

    @Test
    public void display_validString_printsToStandardOutput() {
        Ui.display("Test Output\n");
        String output = outContent.toString();
        assertTrue(output.contains("Test Output"));
    }

    @Test
    public void main_byeCommand_exitsGracefully() {
        String input = "bye\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Ui.main(new String[]{});

        String output = outContent.toString();
        assertTrue(output.contains("Remy, your friendly reminder app!"));
        assertTrue(output.contains("Bye, enjoy your day!!"));
    }

    @Test
    public void main_listCommand_printsTaskList() {
        String input = "list\nbye\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Ui.main(new String[]{});

        String output = outContent.toString();
        assertTrue(output.contains("Remy, your friendly reminder app!"));
        assertTrue(output.contains("Bye, enjoy your day!!"));
    }

    @Test
    public void main_addTodoCommand_executesAndExits() {
        String input = "todo read book\nbye\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Ui.main(new String[]{});

        String output = outContent.toString();
        assertTrue(output.contains("You've added a new task!"));
        assertTrue(output.contains("Bye, enjoy your day!!"));
    }
}
