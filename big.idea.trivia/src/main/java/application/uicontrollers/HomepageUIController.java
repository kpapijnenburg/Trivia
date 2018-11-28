package application.uicontrollers;

import application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class HomepageUIController {

    public Button btn_singleplayer;

    public HomepageUIController() {

    }

    @FXML
    public void btnSinglePlayerClicked(ActionEvent actionEvent) throws IOException {
        Application.openStage("game_ui.fxml");

        Stage stageToClose = (Stage) btn_singleplayer.getScene().getWindow();
        stageToClose.close();
    }

    public void btnMultiplayerGameClicked(ActionEvent actionEvent) {
    }

    public void btnLeaderBoardClicked(ActionEvent actionEvent) {
    }

    public void btnLogoutClicked(ActionEvent actionEvent) throws IOException {
        Application.currentUser = null;

        Application.openStage("login_ui.fxml");

        Stage stageToClose = (Stage) btn_singleplayer.getScene().getWindow();
        stageToClose.close();
    }
}
