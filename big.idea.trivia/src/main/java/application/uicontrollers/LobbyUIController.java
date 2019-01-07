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
import question.model.Category;
import question.model.Enums.Difficulty;
import user.model.User;
import websocket.client.ClientWebSocket;
import websocket.client.Communicator;
import websocket.shared.Message;
import websocket.shared.Operation;

import java.io.IOException;
import java.util.*;

@SuppressWarnings("Duplicates")
public class LobbyUIController implements Observer {

    public Button btn_back;
    public Button btn_start;
    public ListView<MultiPlayerGame> lst_games;
    public Button btn_create;
    private Application application;

    private Communicator communicator = null;

    private List<MultiPlayerGame> games = new ArrayList<>();

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

        // Connect to lobby to receive all current accessible games.
        communicator.connect("Lobby");

    }

    public void btnBackClicked(ActionEvent actionEvent) throws IOException {

        // Unsubscribe from the lobby
        communicator.unsubscribe("lobby");

        application.openStage("homepage_ui.fxml");

        Stage stageToClose = (Stage) btn_back.getScene().getWindow();
        stageToClose.close();
    }

    public void btnStartClicked(ActionEvent actionEvent) {
        User user = Application.currentUser;
        MultiPlayerGame game = lst_games.getSelectionModel().getSelectedItem();
        MultiPlayerGame gameInstance = MultiPlayerGame.getInstance();
        MultiPlayerGame.setInstance(game);

        if (game != null && user != null){
            game.setPlayerB(new Player(user.getName(), 0,0));
            MultiPlayerGame.setInstance(game);
            // Subscribe to playerA's game and unsubscribe to the lobby.
            try {
                communicator.subscribe(game.getGameName());
                communicator.unsubscribe("Lobby");

                application.openStage("multiplayer_game_ui.fxml");

                Stage stageToClose = (Stage) btn_back.getScene().getWindow();
                stageToClose.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void btnCreateClicked(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Choose a difficulty for the game.");

        ButtonType easy = new ButtonType("Easy");
        ButtonType medium = new ButtonType("Medium");
        ButtonType hard = new ButtonType("Hard");

        alert.getButtonTypes().setAll(easy, medium, hard);

        Optional<ButtonType> result = alert.showAndWait();

        MultiPlayerGame multiPlayerGame = MultiPlayerGame.getInstance();

        if (result.get() == easy) {
            multiPlayerGame.setDifficulty(Difficulty.EASY);
        } else if (result.get() == medium) {
            multiPlayerGame.setDifficulty(Difficulty.MEDIUM);
        } else if (result.get() == hard) {
            multiPlayerGame.setDifficulty(Difficulty.HARD);
        }

        // todo mogelijkheid bieden om per drie vragen een nieuwe category te kiezen.
        // Eerst de andere functionaliteiten van de multiplayer game maken.
        multiPlayerGame.setCategory(new Category(1,"General Knowlegde"));

        User user = Application.currentUser;
        Player player = new Player(user.getName(), 0, 0);
        multiPlayerGame.setPlayerA(player);

        broadcast(multiPlayerGame);
        communicator.subscribe(multiPlayerGame.getGameName());

        // Unsubscribe the lobby.
        communicator.unsubscribe("Lobby");

        application.openStage("multiplayer_game_ui.fxml");

        Stage stageToClose = (Stage) btn_back.getScene().getWindow();
        stageToClose.close();


    }

    private void broadcast(MultiPlayerGame game) {
        Gson gson = new Gson();

        try {
            Message message = new Message();
            message.setContent(gson.toJson(game));
            message.setChannel("Lobby");
            communicator.update(message);
            communicator.register(game.getGameName());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update(Observable o, Object arg) {
        Gson gson = new Gson();

        Message message = (Message) arg;
        String content = message.getContent();
        Operation operation = message.getOperation();

        // Check if either the operation is connected or update.
        // When connected a list of games should be processed.
        // When updating a single game object should be added.

        switch (operation) {
            case UPDATE:
                MultiPlayerGame updateGame = gson.fromJson(content, MultiPlayerGame.class);
                games.add(updateGame);
                System.out.println("added update game");
                break;
            case CONNECTED:
                if (content.equals("[]")) {
                    System.out.println("Empty array skipped.");
                } else {
                    games.clear();

                    System.out.println("Cleared list");

                    JsonArray array = gson.fromJson(content, JsonArray.class);

                    for (JsonElement jsonGame : array) {
                        MultiPlayerGame connectedGame = gson.fromJson(jsonGame, MultiPlayerGame.class);
                        games.add(connectedGame);
                        System.out.println("Added connected game");
                    }
                }
        }

        // Finsih with updating the list with the new items.
        ObservableList<MultiPlayerGame> observableList = FXCollections.observableList(games);
        lst_games.setItems(observableList);
    }
}
