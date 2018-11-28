package application.uicontrollers;

import application.Application;
import game.model.Game;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class GameUIController {
    public Label lb_score;
    public Label lb_strikes;
    public Button btn_quit;
    public TextField txt_question;
    public Button btn_answerA;
    public Button btn_answerB;
    public Button btn_answerC;
    public Button btn_answerD;

    private Game game;
    private Application application;

    public GameUIController(){

    }

    public void initialize(){
        application = Application.getInstance();

        try {
            categoryPrompt();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public void btnQuitClicked(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quit");
        alert.setContentText("Are you sure you want to quit?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            application.openStage("homepage_ui.fxml");

            Stage stageToClose = (Stage) lb_strikes.getScene().getWindow();
            stageToClose.close();
        }
    }

    public void btnAnswerAClicked(ActionEvent actionEvent) {
    }

    public void btnAnswerBClicked(ActionEvent actionEvent) {
    }

    public void btnAnswerCClicked(ActionEvent actionEvent) {
    }

    public void btnAnswerDClicked(ActionEvent actionEvent) {
    }

    private void categoryPrompt() throws IOException {
        application.openStage("category_ui.fxml");

        Stage stageToClose = (Stage) lb_strikes.getScene().getWindow();
        stageToClose.close();

    }
}
