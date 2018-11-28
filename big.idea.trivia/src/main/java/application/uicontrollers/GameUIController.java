package application.uicontrollers;

import api.opentrivia.OpenTriviaDBService;
import application.Application;
import game.model.Game;
import game.model.Player;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import question.model.Enums.Difficulty;

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
    private OpenTriviaDBService openTriviaDBService = new OpenTriviaDBService();

    public GameUIController(){

    }

    public void initialize(){
        //todo wordt iedere keer weergevem als scherm wordt geopent.
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Difficulty");
        alert.setContentText("Choose a difficulty for the game.");

        ButtonType easy = new ButtonType("Easy");
        ButtonType medium = new ButtonType("Medium");
        ButtonType hard = new ButtonType("Hard");

        alert.getButtonTypes().setAll(easy, medium, hard);

        game = Game.getInstance();

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == easy) {
            game.setDifficulty(Difficulty.EASY);
        }
        else if (result.get() == easy) {
            game.setDifficulty(Difficulty.MEDIUM);
        }
        else if (result.get() == easy) {
            game.setDifficulty(Difficulty.HARD);
        }

        this.setGame(game);

        Player player = new Player(Application.currentUser.getName(), Application.currentUser.getPassword(),0,0);
        game.addPlayer(player);

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
            Application.openStage("homepage_ui.fxml");

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

    private void setGame(Game game) {
        this.game = game;
    }

    private void categoryPrompt() throws IOException {
        Application.openStage("category_ui.fxml");

        Stage stageToClose = (Stage) lb_strikes.getScene().getWindow();
        stageToClose.close();

    }
}
