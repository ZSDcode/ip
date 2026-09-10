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
        assert scrollPane != null : "scrollPane not injected";
        assert dialogContainer != null : "dialogContainer not injected";
        assert userInput != null : "userInput not injected";
        assert sendButton != null : "sendButton not injected";
        assert userImage != null : "userImage failed to load";
        assert remyImage != null : "remyImage failed to load";

        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        dialogContainer.getChildren().add(
            DialogBox.getRemyDialog("Hello! I'm Remy, your friendly reminder app!\nHow can I help you today?", remyImage)
        );
    }

    public void setRemy(Remy r) {
        assert r != null : "Remy null";
        remy = r;
    }

    @FXML
    private void handleUserInput() {
        assert remy != null : "remy not set -> call setRemy first";

        String input = userInput.getText();
        if (input.isBlank()) return;

        String response = remy.getResponse(input);
        assert response != null : "response null";

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
