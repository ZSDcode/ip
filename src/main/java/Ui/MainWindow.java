package Ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

public class MainWindow {
    @FXML private ScrollPane scrollPane;
    @FXML private VBox dialogContainer;
    @FXML private TextField userInput;
    @FXML private Button sendButton;

    private Remy remy;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.jpg"));
    private Image remyImage = new Image(this.getClass().getResourceAsStream("/images/remy.jpg"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        dialogContainer.getChildren().add(
            DialogBox.getRemyDialog("Hello! I'm Remy, your friendly reminder app!\nHow can I help you today?", remyImage)
        );
    }

    public void setRemy(Remy r) {
        remy = r;
    }

    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        if (input.isBlank()) return;
        String response = remy.getResponse(input);
        dialogContainer.getChildren().addAll(
            DialogBox.getUserDialog(input, userImage),
            DialogBox.getRemyDialog(response, remyImage)
        );
        userInput.clear();
        if (input.equals("bye")) {
            javafx.application.Platform.exit();
        }
    }
}
