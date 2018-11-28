package application.uicontrollers;

import api.opentrivia.OpenTriviaDBService;
import application.Application;
import game.model.Game;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import question.model.Enums.Difficulty;
import question.model.Question;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
    private OpenTriviaDBService openTriviaDBService;
    private ArrayList<Button> buttons;
    private ArrayList<Question> questions;
    private Question currentQuestion;

    public GameUIController() {
        this.application = Application.getInstance();
        this.game = Game.getInstance();
        openTriviaDBService = new OpenTriviaDBService();
        buttons = new ArrayList<>();
        questions = new ArrayList<>();
    }

    public void initialize() throws IOException {
        if (game.getQuestions().size() == 0) {
            try {
                questions = openTriviaDBService.getQuestions(game);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        buttons.add(btn_answerA);
        buttons.add(btn_answerB);
        buttons.add(btn_answerC);
        buttons.add(btn_answerD);

        getQuestion();
        setButtons();
        updateLabels();

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
        Collections.shuffle(questions);
        if (questions.size() == 0){
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
        Collections.shuffle(buttons);

        for (int i = 0; i < currentQuestion.getAnswers().getFalseAnswers().size(); i++) {
            buttons.get(i).setText(currentQuestion.getAnswers().getFalseAnswers().get(i));
        }

        for (Button button: buttons){
            if (button.getText().equals("")){
                button.setText(currentQuestion.getAnswers().getCorrectAnswer());
            }
        }

    }

    private void setQuestionText(String text) {
        txt_question.setText(text);
    }

    private void checkAnswer(String answer) throws IOException {
        if (currentQuestion.getAnswers().getCorrectAnswer().equals(answer)){
            awardPoints();
        }
        else {
            awardStrike();
        }

        updateLabels();
        getQuestion();
        setButtons();

    }

    private void updateLabels() {
        lb_strikes.setText("" + game.getPlayers().get(0).getStrikes());
        lb_score.setText("" + game.getPlayers().get(0).getScore());
    }

    private void awardStrike() {
        game.getPlayers().get(0).setStrikes(1);
        if (game.getPlayers().get(0).getStrikes() > 3){
            //todo game over
        }

    }

    private void awardPoints() {
        int points = 0;

        if (game.getDifficulty() == Difficulty.EASY){
            points = 1;
        }
        if (game.getDifficulty() == Difficulty.MEDIUM){
            points = 2;
        }
        if (game.getDifficulty() == Difficulty.HARD){
            points = 3;
        }

        game.getPlayers().get(0).setScore(points);
    }
}



