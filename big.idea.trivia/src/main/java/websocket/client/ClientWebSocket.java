package websocket.client;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import websocket.shared.Message;
import websocket.shared.Operation;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class ClientWebSocket extends Communicator {
    // using a singleton to ensure there is only one instance of this client.
    private static ClientWebSocket instance = null;

    private final String uri = "localhost:9000/game/";
    private Session session;
    private String message;

    private Gson gson;

    // status of WS client;
    boolean isRunning = false;

    private ClientWebSocket(){
        gson = new Gson();
    }

    private static ClientWebSocket getInstance(){
        if (instance == null){
            instance = new ClientWebSocket();
        }
        return instance;
    }

    // Start connection
    @Override
    public void start() {
        if (!isRunning){
            isRunning = true;
            startClient();
        }
    }


    @Override
    public void stop() {
        if (isRunning){
            isRunning = false;
            stopClient();
        }
    }

    @OnOpen
    public void onConnect(Session session){
        this.session = session;
    }

    @OnMessage
    public void onText(String message){
        this.message = message;
        processMessage(message);
    }

    @OnError
    public void onError(Throwable cause){
        System.out.println("Connection error: " + cause.getMessage());
    }

    @OnClose
    public void onClose(CloseReason reason){
        System.out.println("Client closed with reason: " + reason);
        session = null;
    }

    @Override
    public void register(String property) {
        Message message = new Message();
        message.setOperation(Operation.REGISTER);
        message.setProperty(property);

        sendToServer(message);
    }


    @Override
    public void unregister(String property) {
        Message message = new Message();
        message.setOperation(Operation.UNREGISTER);
        message.setProperty(property);

        sendToServer(message);
    }

    @Override
    public void subscribe(String property) {
        Message message = new Message();
        message.setOperation(Operation.SUBSCRIBE);
        message.setProperty(property);

        sendToServer(message);
    }

    @Override
    public void unsubscribe(String property) {
        Message message = new Message();
        message.setOperation(Operation.UNSUBSCRIBE);
        message.setProperty(property);

        sendToServer(message);
    }

    @Override
    public void update(Message message) {
        Message wsMessage = new Message();
        wsMessage.setOperation(Operation.UPDATE);
        wsMessage.setProperty(message.getProperty());
        wsMessage.setContent(message.getContent());

        sendToServer(wsMessage);
    }

    private void sendToServer(Message message) {
        String s = gson.toJson(message);

        session.getAsyncRemote().sendText(s);
    }

    // Start a new websocket client
    private void startClient() {
        try{
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, new URI(uri));
        } catch (DeploymentException | IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    // Stop client when running
    private void stopClient() {
        try{
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Process incoming json message
    private void processMessage(String message) {

        // Parse message
        Message wsMessage = null;
        try{
            wsMessage = gson.fromJson(message, Message.class);
        } catch (JsonSyntaxException e){
            System.out.println("Cannot parse JSON" + message);
        }

        // The only operation to be processed will be the Update
        Operation operation;
        operation = wsMessage.getOperation();

        if (operation == null || operation != Operation.UPDATE){
            return;
        }

        // Obtain property from message
        String property = wsMessage.getProperty();
        if (property == null || "".equals(property)){
            return;
        }

        // Obtain content from message
        String content = wsMessage.getContent();
        if (content == null || "".equals(content)){
            return;
        }

        // Create object to be send to observers.
        Message observerMessage = new Message();
        observerMessage.setProperty(property);
        observerMessage.setContent(content);

        // Notify observers
        this.setChanged();
        this.notifyObservers(observerMessage);

    }

}
