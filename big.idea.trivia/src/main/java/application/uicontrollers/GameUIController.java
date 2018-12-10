package application.uicontrollers;

import application.Application;
import application.GameService;
import application.QuestionService;
import game.model.Game;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import question.model.Enums.Difficulty;
import question.model.Question;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

public class GameUIController {
    //todo OpentriviaService interface maken en implementeren.

    public Label lb_score;
    public Label lb_strikes;
    public Button btn_quit;
    public TextArea txt_question;
    public Button btn_answerA;
    public Button btn_answerB;
    public Button btn_answerC;
    public Button btn_answerD;

    private Game game;
    private Application application;
    private GameService gameService;
    private QuestionService questionService;

    private ArrayList<Button> buttons;
    private ArrayList<Question> questions;
    private Question currentQuestion;

    public GameUIController() throws MalformedURLException {
        this.application = Application.getInstance();
        this.game = Game.getInstance();
        this.gameService = new GameService();
        this.questionService = new QuestionService();

        buttons = new ArrayList<>();
        questions = new ArrayList<>();
    }

    public void initialize() throws IOException {
        if (game.getQuestions().size() == 0) {
            questions = (ArrayList<Question>) questionService.getQuestions(game);
        }

        buttons.add(btn_answerA);
        buttons.add(btn_answerB);
        buttons.add(btn_answerC);
        buttons.add(btn_answerD);

        getQuestion();
        updateLabels();

    }

    public void btnQuitClicked(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quit");
        alert.setContentText("Are you sure you want to quit?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            gameService.saveSinglePlayer(game);
            application.openStage("homepage_ui.fxml");

            Stage stageToClose = (Stage) lb_strikes.getScene().getWindow();
            stageToClose.close();
        }
    }

    public void btnAnswerAClicked(ActionEvent actionEvent) throws IOException {
        checkAnswer(btn_answerA.getText());

    }

    public void btnAnswerBClicked(ActionEvent actionEvent) throws IOException {
        checkAnswer(btn_answerB.getText());

    }

    public void btnAnswerCClicked(ActionEvent actionEvent) throws IOException {
        checkAnswer(btn_answerC.getText());

    }

    public void btnAnswerDClicked(ActionEvent actionEvent) throws IOException {
        checkAnswer(btn_answerD.getText());
    }

    private void getQuestion() throws IOException {
        if (questions.size() == 0) {
            application.openStage("category_ui.fxml");

            Stage stageToClose = (Stage) btn_answerA.getScene().getWindow();
            stageToClose.close();

        } else {
            currentQuestion = questions.get(0);
            setButtons();
            setQuestionText(currentQuestion.getQuestion());
            questions.remove(0);
        }

    }

    private void setButtons() {
        resetButtons();
        Collections.shuffle(buttons);

        for (int i = 0; i < currentQuestion.getAnswers().size(); i++){
            buttons.get(i).setText(currentQuestion.getAnswers().get(i).getAnswer());
        }

    }

    private void resetButtons() {
        for (Button button : buttons) {
            button.setText("");
        }
    }

    private void setQuestionText(String text) {
        txt_question.setText(text);
    }


    private void updateLabels() {
        lb_strikes.setText("" + game.getPlayers().get(0).getStrikes());
        lb_score.setText("" + game.getPlayers().get(0).getScore());
    }


    private void checkAnswer(String answer) throws IOException {
        //todo questionrepository gebruiken ipv opentriviadb

//        if (currentQuestion.getAnswers().getCorrectAnswer().equals(answer)) {
//            awardPoints();
//            JOptionPane.showMessageDialog(null, "Correct answer!");
//
//        } else {
//            awardStrike();
//            JOptionPane.showMessageDialog(null, "False answer! Correct answer was: " + currentQuestion.getAnswers().getCorrectAnswer());
//        }
//
//        updateLabels();
//        getQuestion();

    }

    private void awardStrike() throws IOException {
        game.getPlayers().get(0).setStrikes(1);
        if (game.getPlayers().get(0).getStrikes() >= 3) {
            gameService.saveSinglePlayer(game);

            application.openStage("homepage_ui.fxml");

            Stage stageToClose = (Stage) lb_strikes.getScene().getWindow();
            stageToClose.close();
        }

    }


    private void awardPoints() {
        int points = 0;

        if (game.getDifficulty() == Difficulty.EASY) {
            points = 1;
        }
        if (game.getDifficulty() == Difficulty.MEDIUM) {
            points = 2;
        }
        if (game.getDifficulty() == Difficulty.HARD) {
            points = 3;
        }

        game.getPlayers().get(0).setScore(points);
    }
}



