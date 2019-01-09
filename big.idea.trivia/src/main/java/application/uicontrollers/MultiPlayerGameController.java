package application.uicontrollers;

import application.Application;
import application.services.GameService;
import application.services.QuestionService;
import com.google.gson.Gson;
import game.model.GameState;
import game.model.MultiPlayerGame;
import game.model.Player;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import question.model.Enums.Difficulty;
import websocket.client.ClientWebSocket;
import websocket.client.Communicator;
import websocket.shared.Message;
import websocket.shared.Operation;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;
import java.util.Observer;

public class MultiPlayerGameController implements Observer {
    public Label lb_score_playerA;
    public Label lb_strikes_playerA;
    public Label lb_score_playerB;
    public Label lb_strikes_playerB;
    public Button btn_quit;
    public TextArea txt_question;
    public Label lb_playerA_name;
    public Label lb_playerB_name;
    public Button btn_answerA;
    public Button btn_answerB;
    public Button btn_answerC;
    public Button btn_answerD;
    public Label lb_status;

    private Communicator communicator;

    private MultiPlayerGame game;
    private Application application;
    private GameService gameService;
    private QuestionService questionService;

    private ArrayList<Button> buttons;

    public MultiPlayerGameController() {
        this.application = Application.getInstance();
        this.gameService = new GameService();
        this.questionService = new QuestionService();
        buttons = new ArrayList<>();

    }

    public void initialize() throws IOException {
        // Create the client socket for communication
        communicator = ClientWebSocket.getInstance();
        communicator.addObserver(this);

        // Establish connection
        communicator.start();

        buttons.add(btn_answerA);
        buttons.add(btn_answerB);
        buttons.add(btn_answerC);
        buttons.add(btn_answerD);

        this.game = MultiPlayerGame.getInstance();
        // If there is no playerB there has to be waited on that player.
        // If player B is present the game can be started.
        if (game.getPlayerB() == null) {
            waitForPlayerB();
        } else {
            startGame();
        }
        // After each initialization the UI should be updated.
        requestUpdate();
    }

    private void startGame() throws IOException {
        if (game.getGameState() == GameState.NOT_STARTED) {
            game.setGameState(GameState.PLAYER_B_TURN);
            setTurn();
            requestUpdate();
        }
    }

    private void getQuestion() throws IOException {
        game.setCurrentQuestion(questionService.getQuestion(game.getCategory().getId(), game.getDifficulty().toString()));
        requestUpdate();
    }

    private void waitForPlayerB() throws IOException {
        game.setGameState(GameState.NOT_STARTED);
        for (Button button : buttons) {
            button.setDisable(false);
        }
        requestUpdate();
    }

    private void updateUI() {
        Platform.setImplicitExit(false);
        Platform.runLater(() -> {
            // Update player A information
            Player playerA = game.getPlayerA();

            lb_playerA_name.setText(playerA.getName());
            lb_score_playerA.setText("" + playerA.getScore());
            lb_strikes_playerA.setText("" + playerA.getStrikes());

            if (game.getPlayerB() != null) {
                // Update player B information if there is a player B present.
                Player playerB = game.getPlayerB();

                lb_playerB_name.setText(playerB.getName());
                lb_score_playerB.setText("" + playerB.getScore());
                lb_strikes_playerB.setText("" + playerB.getStrikes());
            }

            lb_status.setText(game.getGameState().toString());

            if (game.getCurrentQuestion() != null) {
                // Update question text and buttons if a question has been retrieved.
                txt_question.setText(game.getCurrentQuestion().getQuestion());
                setButtons();
            }
        });
    }

    private void setButtons() {
        resetButtons();
        Collections.shuffle(buttons);

        for (int i = 0; i < game.getCurrentQuestion().getAnswers().size(); i++) {
            buttons.get(i).setText(game.getCurrentQuestion().getAnswers().get(i).getAnswer());
        }
    }

    private void resetButtons() {
        for (Button button : buttons) {
            button.setText("");
        }
    }


    private void setTurn() throws IOException {
        switch (game.getGameState()) {
            case PLAYER_A_TURN:
                getQuestion();
                if (game.getPlayerA().getName().equals(Application.currentUser.getName())) {
                    for (Button button : buttons) {
                        button.setDisable(false);
                    }
                } else {
                    for (Button button : buttons) {
                        button.setDisable(true);
                        resetButtons();
                    }
                }
                break;
            case PLAYER_B_TURN:
                getQuestion();
                if (game.getPlayerB().getName().equals(Application.currentUser.getName())) {
                    for (Button button : buttons) {
                        button.setDisable(false);
                        resetButtons();
                    }
                } else {
                    for (Button button : buttons) {
                        button.setDisable(true);
                    }
                }
        }
    }

    private void switchGameState() {
        switch (game.getGameState()) {
            case PLAYER_A_TURN:
                game.setGameState(GameState.PLAYER_B_TURN);
                //todo uitzoeken of playerA de applicatie gebruikt of playerB
                break;
            case PLAYER_B_TURN:
                game.setGameState(GameState.PLAYER_A_TURN);
                break;
        }
    }

    private void requestUpdate() throws IOException {
        Gson gson = new Gson();

        Message message = new Message();
        message.setOperation(Operation.UPDATE);
        message.setChannel(this.game.getGameName());
        message.setContent(gson.toJson(this.game));

        communicator.update(message);
    }

    public void btnQuitClicked(ActionEvent event) {

    }

    public void btnAnswerAClicked(ActionEvent event) throws IOException {
        checkAnswer(btn_answerA.getText());
        switchGameState();
        requestUpdate();
    }


    public void btnAnswerBClicked(ActionEvent event) throws IOException {
        checkAnswer(btn_answerB.getText());
        switchGameState();
        requestUpdate();
    }

    public void btnAnswerCClicked(ActionEvent event) throws IOException {
        checkAnswer(btn_answerC.getText());
        switchGameState();
        requestUpdate();
    }

    public void btnAnswerDClicked(ActionEvent event) throws IOException {
        checkAnswer(btn_answerD.getText());
        switchGameState();
        requestUpdate();
    }

    private void checkAnswer(String answer) throws IOException {
        boolean result = questionService.checkAnswer(game.getCurrentQuestion().getId(), answer);

        if (result) {
            awardPoints();
        } else {
            awardStrike();
        }

        setTurn();
    }

    private void awardStrike() throws IOException {
        if (game.getGameState() == GameState.PLAYER_B_TURN) {
            game.getPlayerB().setStrikes(1);
        } else {
            game.getPlayerA().setStrikes(1);
        }

        JOptionPane.showMessageDialog(null, "False answer!");

        if (game.getPlayerA().getStrikes() >= 3 || game.getPlayerB().getStrikes() >= 3) {
            JOptionPane.showMessageDialog(null, "Game over!");

            //gameService.saveMultiPlayerGame();
            game.setGameState(GameState.FINISHED);
            application.openStage("homepage_ui.fxml");

            Stage stageToClose = (Stage) btn_answerA.getScene().getWindow();
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

        if (game.getGameState() == GameState.PLAYER_B_TURN) {
            game.getPlayerB().setScore(points);
        } else {
            game.getPlayerA().setScore(points);
        }

        JOptionPane.showMessageDialog(null, "Correct answer!");

    }

    @Override
    public void update(Observable o, Object arg) {
        Gson gson = new Gson();

        Message message = (Message) arg;
        String content = message.getContent();

        game = gson.fromJson(content, MultiPlayerGame.class);

        updateUI();
    }
}
