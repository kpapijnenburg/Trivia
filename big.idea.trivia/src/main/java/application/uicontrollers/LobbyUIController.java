package application.uicontrollers;

import application.Application;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import websocket.client.ClientWebSocket;
import websocket.client.Communicator;
import websocket.shared.Message;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class LobbyUIController implements Observer {

    public Button btn_back;
    public Button btn_start;
    private Application application;

    private Communicator communicator = null;

    public LobbyUIController() {
        this.application = Application.getInstance();
    }


    public void initialize(){
        // Create the client socket for communication
        communicator = ClientWebSocket.getInstance();
        communicator.addObserver(this);

        // Establish connection
        communicator.start();

        // Register properties
        communicator.register(Application.currentUser.toString());

        // Subsribe to channel
        communicator.subscribe(Application.currentUser.toString());
    }

    @Override
    public void update(Observable o, Object arg) {
        Message message = (Message) arg;
        String channel = message.getChannel();
        String content = message.getContent();

        //todo message verwerken naar lijst van aangemelde gebruikers.
    }

    public void btnBackClicked(ActionEvent actionEvent) throws IOException {
        application.openStage("homepage_ui.fxml");

        Stage stageToClose = (Stage) btn_back.getScene().getWindow();
        stageToClose.close();
    }

    public void btnStartClicked(ActionEvent actionEvent) {

    }
}
