package Ui;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Represents a chat bubble containing a dialog label and a display picture,
 * used to render user and bot messages in the chat window.
 */
public class DialogBox extends HBox {
    @FXML private Label dialog;
    @FXML private ImageView displayPicture;

    /**
     * Constructs a DialogBox loaded from FXML, displaying the given text and image.
     *
     * @param text the message text to display.
     * @param img the display picture (avatar) to show alongside the text.
     */
    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        dialog.setText(text);
        displayPicture.setImage(img);
    }

    /**
     * Flips the dialog box so the picture appears on the left and text on the right,
     * used to visually distinguish bot messages from user messages.
     */
    private void flip() {
        setAlignment(Pos.TOP_LEFT);
        java.util.List<javafx.scene.Node> tmp = new java.util.ArrayList<>(getChildren());
        java.util.Collections.reverse(tmp);
        getChildren().setAll(tmp);
    }

    /**
     * Creates a dialog box representing a message from the user.
     *
     * @param text the message text.
     * @param img the user's display picture.
     * @return the constructed DialogBox.
     */
    public static DialogBox getUserDialog(String text, Image img) {
        return new DialogBox(text, img);
    }

    /**
     * Creates a dialog box representing a message from Remy, flipped to appear
     * on the opposite side from user messages.
     *
     * @param text the message text.
     * @param img Remy's display picture.
     * @return the constructed, flipped DialogBox.
     */
    public static DialogBox getRemyDialog(String text, Image img) {
        DialogBox db = new DialogBox(text, img);
        db.flip();
        return db;
    }
}
