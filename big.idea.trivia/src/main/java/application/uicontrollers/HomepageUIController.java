package application.uicontrollers;

import application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HomepageUIController {

    public HomepageUIController() {

    }

    @FXML
    public void btnSinglePlayerClicked(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("game_ui.fxml")));
        Stage stage = new Stage();
        stage.setResizable(false);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void btnMultiplayerGameClicked(ActionEvent actionEvent) {
    }

    public void btnLeaderBoardClicked(ActionEvent actionEvent) {
    }

    public void btnLogoutClicked(ActionEvent actionEvent) throws IOException {
        Application.currentUser = null;

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("login_ui.fxml")));
        Stage stage = new Stage();
        stage.setResizable(false);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
