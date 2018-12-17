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

    // Map of each list of sessions that are subscribed to that channel
    private static final Map<String, List<Session>> channels = new HashMap<>();

    @OnOpen
    public void onConnect(Session session) {
        System.out.println("new connection: sessionID: " + session.getId());
        sessions.add(session);
        System.out.println("Total number of sessions: " + sessions.size());
    }

    @OnMessage
    public void onText(String message, Session session) {
        System.out.println("[WebSocket Session ID] : " + session.getId() + " [Received] : " + message);
        handleMessageFromClient(message, session);
    }

    @OnClose
    public void onClose(CloseReason reason, Session session){
        System.out.println("[WebSocket Session ID] : " + session.getId() + " [Socket Closed]: " + reason);
        sessions.remove(session);
    }

    @OnError
    public void onError(Throwable cause, Session session){
        System.out.println("[WebSocket Session ID] : " + session.getId() + "[ERROR]: ");
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
        String channel = message.getChannel();
        if (null != operation && null != channel && !"".equals(channel)) {
            switch (operation) {
                case REGISTER:
                    // Register channel if not registered yet
                    if (channels.get(channel) == null) {
                        channels.put(channel, new ArrayList<>());
                    }
                    break;
                case UNREGISTER:
                    // breaking immediately because other clients may be subscribed
                    break;
                case SUBSCRIBE:
                    //subscribe channel if this has not been done.
                    if (channels.get(channel) == null) {
                        channels.get(channel).add(session);
                    }
                    break;
                case UNSUBSCRIBE:
                    // Unsubscribe if the channel has been subscribed
                    if (channels.get(channel) != null) {
                        channels.get(channel).remove(session);
                    }
                case UPDATE:
                    // Send message to all clients subscribed to this channel
                    if (channels.get(channel) != null) {
                        for (Session sess : channels.get(channel)) {
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
