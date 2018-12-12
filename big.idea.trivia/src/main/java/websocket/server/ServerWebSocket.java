package websocket.server;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import websocket.shared.Message;
import websocket.shared.Operation;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ServerEndpoint(value = "/game/")
public class ServerWebSocket {

    // List of all sessions
    private static final List<Session> sessions = new ArrayList<>();

    // Map of each list of sessions that are subscribed to that property
    private static final Map<String, List<Session>> propertySessions = new HashMap<>();

    @OnOpen
    public void onConnect(Session session) {
        sessions.add(session);
    }

    @OnMessage
    public void onText(String message, Session session) {
        handleMessageFromClient(message, session);
    }

    @OnClose
    public void onClose(CloseReason reason, Session session){
        sessions.remove(session);
    }

    @OnError
    public void onError(Throwable cause, Session session){
        cause.printStackTrace(System.err);
    }

    // Incoming Message handling from client
    private void handleMessageFromClient(String s, Session session) {
        Gson gson = new Gson();
        Message message;

        try {
            message = gson.fromJson(s, Message.class);
        } catch (JsonSyntaxException e) {
            System.out.println("Cannot parse JSON " + s);
            return;
        }

        // get the operation the message needs to perform
        Operation operation;
        operation = message.getOperation();

        // process based on which operations needs to be handled
        String property = message.getProperty();
        if (null != operation && null != property && !"".equals(property)) {
            switch (operation) {
                case REGISTER:
                    // Register property if not registered yet
                    if (propertySessions.get(property) == null) {
                        propertySessions.put(property, new ArrayList<>());
                    }
                    break;
                case UNREGISTER:
                    // breaking immediately because other clients may be subscribed
                    break;
                case SUBSCRIBE:
                    //subscribe property if this has not been done.
                    if (propertySessions.get(property) == null) {
                        propertySessions.get(property).add(session);
                    }
                    break;
                case UNSUBSCRIBE:
                    // Unsubscribe if the property has been subscribed
                    if (propertySessions.get(property) != null) {
                        propertySessions.get(property).remove(session);
                    }
                case UPDATE:
                    // Send message to all clients subscribed to this property
                    if (propertySessions.get(property) != null) {
                        for (Session sess : propertySessions.get(property)) {
                            // Use async communication
                            sess.getAsyncRemote().sendText(s);
                        }
                    }
                    break;
                default:
                    System.out.println("Cannot parse JSON " + s);
            }
        }
    }

}
