package application.uicontrollers;

import application.Application;
import application.services.GameService;
import application.services.QuestionService;
import com.google.gson.Gson;
import game.model.MultiPlayerGame;
import game.model.Player;
import game.model.SinglePlayerGame;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import question.model.Question;
import websocket.client.ClientWebSocket;
import websocket.client.Communicator;
import websocket.shared.Message;
import websocket.shared.Operation;

import java.io.IOException;
import java.util.ArrayList;
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

    private Communicator communicator;

    private boolean playerATurn = true;

    private MultiPlayerGame game;
    private Application application;
    private GameService gameService;
    private QuestionService questionService;

    private ArrayList<Button> buttons;
    private ArrayList<Question> questions;
    private Question currentQuestion;

    MultiPlayerGameController() {
        this.application = Application.getInstance();
        this.gameService = new GameService();
        this.questionService = new QuestionService();

        buttons = new ArrayList<>();
        questions = new ArrayList<>();

        communicator = ClientWebSocket.getInstance();
    }

    public void initialize() throws IOException {
        // Create the client socket for communication
        communicator = ClientWebSocket.getInstance();
        communicator.addObserver(this);

        // Establish connection
        communicator.start();

        // Subscribe to current game.
        communicator.subscribe(game.getPlayerA().getName() + "'s game");

        buttons.add(btn_answerA);
        buttons.add(btn_answerB);
        buttons.add(btn_answerC);
        buttons.add(btn_answerD);

        updateUI();

        if (game.getPlayerB() == null){
            waitForPlayerB();
        }
        else {
            startGame();
        }
    }

    private void startGame() {
        setTurn();


    }

    private void waitForPlayerB() {
        for (Button button: buttons){
            button.setDisable(true);
        }
    }

    private void updateUI() {
        Player playerA = game.getPlayerA();

        lb_playerA_name.setText(playerA.getName());
        lb_score_playerA.setText("" + playerA.getScore());
        lb_strikes_playerA.setText("" + playerA.getStrikes());

        if (game.getPlayerB() != null){
            lb_playerB_name.setText(playerA.getName());
            lb_score_playerB.setText("" + playerA.getScore());
            lb_strikes_playerB.setText("" + playerA.getStrikes());
        }
    }

    private void setTurn() {
        if (playerATurn) {
            if (game.getPlayerA().getPlayerId() != (Application.currentUser.getId())) {
                for (Button button : buttons) {
                    button.setDisable(true);
                }
            } else {
                for (Button button : buttons) {
                    button.setDisable(false);
                }
            }
        }
    }

    private void requestUpdate() throws IOException {
        Gson gson = new Gson();

        Message message = new Message();
        message.setOperation(Operation.UPDATE);
        message.setChannel(game.getPlayerA().getName() + "'s game");
        message.setContent(gson.toJson(game));

        communicator.update(message);
    }

    public void btnQuitClicked(ActionEvent event) {

    }

    public void btnAnswerAClicked(ActionEvent event) throws IOException {
        playerATurn = !playerATurn;
        setTurn();
        requestUpdate();
    }



    public void btnAnswerBClicked(ActionEvent event) throws IOException {
        playerATurn = !playerATurn;
        setTurn();
        requestUpdate();
    }

    public void btnAnswerCClicked(ActionEvent event) throws IOException {
        playerATurn = !playerATurn;
        setTurn();
        requestUpdate();
    }

    public void btnAnswerDClicked(ActionEvent event) throws IOException {
        playerATurn = !playerATurn;
        setTurn();
        requestUpdate();
    }

    @Override
    public void update(Observable o, Object arg) {
        Gson gson = new Gson();

        Message message = (Message) arg;
        String content = message.getContent();
        Operation operation = message.getOperation();

        game = gson.fromJson(content, MultiPlayerGame.class);

        updateUI();
    }
}
