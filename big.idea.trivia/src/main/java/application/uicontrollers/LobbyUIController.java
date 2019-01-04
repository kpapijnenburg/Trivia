package application.uicontrollers;

import application.Application;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import game.model.MultiPlayerGame;
import game.model.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import question.model.Enums.Difficulty;
import user.model.User;
import websocket.client.ClientWebSocket;
import websocket.client.Communicator;
import websocket.shared.Message;

import java.io.IOException;
import java.util.*;

public class LobbyUIController implements Observer {

    public Button btn_back;
    public Button btn_start;
    public ListView lst_games;
    private Application application;

    private Communicator communicator = null;

    private  List<MultiPlayerGame> games = new ArrayList<>();

    public LobbyUIController() {
        this.application = Application.getInstance();
    }


    public void initialize() throws IOException {
        // Create the client socket for communication
        communicator = ClientWebSocket.getInstance();
        communicator.addObserver(this);

        // Establish connection
        communicator.start();

        // Register properties
        communicator.register("Lobby");

        // Subsribe to channel
        communicator.subscribe("Lobby");

        communicator.connect("Lobby");

    }

    public void btnBackClicked(ActionEvent actionEvent) throws IOException {

        communicator.unsubscribe("lobby");

        application.openStage("homepage_ui.fxml");

        Stage stageToClose = (Stage) btn_back.getScene().getWindow();
        stageToClose.close();
    }

    public void btnStartClicked(ActionEvent actionEvent) {

    }



    public void btnCreateClicked(ActionEvent event) {
        MultiPlayerGame game = new MultiPlayerGame();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Choose a difficulty for the game.");

        ButtonType easy = new ButtonType("Easy");
        ButtonType medium = new ButtonType("Medium");
        ButtonType hard = new ButtonType("Hard");

        alert.getButtonTypes().setAll(easy, medium, hard);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == easy) {
            game.setDifficulty(Difficulty.EASY);
        }
        else if (result.get() == medium) {
            game.setDifficulty(Difficulty.MEDIUM);
        }
        else if (result.get() == hard) {
            game.setDifficulty(Difficulty.HARD);
        }

        User user = Application.currentUser;
        Player player = new Player(user.getName(), 0,0);
        game.setPlayerA(player);

        broadcast(game);

        this.games.add(game);

    }

    private void broadcast(MultiPlayerGame game) {
        Gson gson = new Gson();

        try {
            Message message = new Message();
            message.setContent(gson.toJson(game));
            message.setChannel("Lobby");
            communicator.update(message);

            communicator.register(game.getPlayerA().getName() + "'s game");
            communicator.subscribe(game.getPlayerA().getName() + "'s game");
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void update(Observable o, Object arg) {

        Gson gson = new Gson();

        Message message = (Message) arg;
        String channel = message.getChannel();
        String content = message.getContent();

        if (content.equals("[]")){
            System.out.println("Empty array skipped.");
        } else {
            games.clear();

            JsonArray array = gson.fromJson(content, JsonArray.class);

            for (JsonElement jsonGame: array){
                MultiPlayerGame game = gson.fromJson(jsonGame, MultiPlayerGame.class);
                games.add(game);
            }

            ObservableList<MultiPlayerGame> observableList = FXCollections.observableList(games);
            lst_games.setItems(observableList);
        }
    }
}
