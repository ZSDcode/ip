package Ui;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class DialogBox extends HBox {
    @FXML private Label dialog;
    @FXML private ImageView displayPicture;

    private DialogBox(String text, Image img) {
        assert text != null : "text null";
        assert img != null : "img null";

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert dialog != null : "dialog Label not injected -> FXML load failed";
        assert displayPicture != null : "displayPicture ImageView not injected -> FXML load failed";

        dialog.setText(text);
        displayPicture.setImage(img);
    }

    private void flip() {
        assert !getChildren().isEmpty() : "no children -> flip invalid";

        setAlignment(Pos.TOP_LEFT);
        java.util.List<javafx.scene.Node> tmp = new java.util.ArrayList<>(getChildren());
        java.util.Collections.reverse(tmp);
        getChildren().setAll(tmp);

        assert getChildren().size() == tmp.size() : "child count changed after flip";
    }

    public static DialogBox getUserDialog(String text, Image img) {
        assert text != null : "text null";
        assert img != null : "img null";
        return new DialogBox(text, img);
    }

    public static DialogBox getRemyDialog(String text, Image img) {
        assert text != null : "text null";
        assert img != null : "img null";
        DialogBox db = new DialogBox(text, img);
        db.flip();
        return db;
    }
}
