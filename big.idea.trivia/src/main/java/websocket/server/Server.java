package websocket.server;

import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;

import javax.websocket.server.ServerContainer;

public class Server {
    private static final int PORT = 9000;

    public static void main(String[] args) {
        startServer();
    }

    private static void startServer() {
        //start the server
        org.eclipse.jetty.server.Server server = new org.eclipse.jetty.server.Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(PORT);
        server.addConnector(connector);

        // Setup context and handlers
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        try{
            // initialize websockets
            ServerContainer container = WebSocketServerContainerInitializer.configureContext(context);

            // Add ws eindpoint
            container.addEndpoint(ServerWebSocket.class);

            server.start();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
