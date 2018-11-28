package application.uicontrollers;

import api.opentrivia.OpenTriviaDBService;
import application.Application;
import game.model.Game;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
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
    private int questionsAnswerdCounter = 0;
    private OpenTriviaDBService openTriviaDBService;
    private ArrayList<Button> buttons;

    public GameUIController(){
        this.application = Application.getInstance();
        this.game = Game.getInstance();
        openTriviaDBService = new OpenTriviaDBService();
        buttons = new ArrayList<>();
    }

    public void initialize(){
        if (game.getQuestions().size() == 0){
            try {
                openTriviaDBService.getQuestions(game);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        buttons.add(btn_answerA);
        buttons.add(btn_answerB);
        buttons.add(btn_answerC);
        buttons.add(btn_answerD);

        for (Button button: buttons){

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
        questionsAnswerdCounter++;
    }

    public void btnAnswerBClicked(ActionEvent actionEvent) {
        questionsAnswerdCounter++;
    }

    public void btnAnswerCClicked(ActionEvent actionEvent) {
        questionsAnswerdCounter++;
    }

    public void btnAnswerDClicked(ActionEvent actionEvent) {
        questionsAnswerdCounter++;
    }

}
