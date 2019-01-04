package websocket.client;

import websocket.shared.Message;

import java.io.IOException;

public interface ICommunicator {

    /**
     * Start the connection.
     */
    public void start();

    /**
     * Stop the connection.
     */
    public void stop();

    /**
     * Register a property.
     *
     * @param property
     */
    public void register(String property);

    /**
     * Unregister a property.
     *
     * @param property
     */
    public void unregister(String property);

    /**
     * Subscribe to a property.
     *
     * @param property
     */
    public void subscribe(String property);

    /**
     * Unsubscribe from a property.
     *
     * @param property
     */
    public void unsubscribe(String property);

    /**
     * Update a property by sending a message to all clients
     * that are subscribed to the property of the message.
     *
     * @param message the message to be sent
     */
    public void update(Message message);

    public void connect(String channel) throws IOException;

}
