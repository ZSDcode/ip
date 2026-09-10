package Ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {
    private Remy remy = new Remy();

    @Override
    public void start(Stage stage) {
        assert stage != null : "stage null";

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            assert ap != null : "AnchorPane load failed";

            Scene scene = new Scene(ap);
            stage.setScene(scene);

            MainWindow controller = fxmlLoader.getController();
            assert controller != null : "controller null -> FXML load failed";

            controller.setRemy(remy);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
