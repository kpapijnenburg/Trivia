package application.uicontrollers;

import application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class homepageUIController {

    public void btnSinglePlayerClicked(ActionEvent actionEvent) {
    }

    public void btnMultiplayerGameClicked(ActionEvent actionEvent) {
    }

    public void btnLeaderBoardClicked(ActionEvent actionEvent) {
    }

    public void btnLogoutClicked(ActionEvent actionEvent) throws IOException {
        Application.currentUser = null;

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("login_ui.fxml.fxml")));
        Stage stage = new Stage();
        stage.setResizable(false);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
