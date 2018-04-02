package connector;

import game.FrontendGameStateDrawerImpl;
import game.GameStateDrawer;
import game.PCAndHumanGameRunner;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.logging.Logger;

@ServerEndpoint(value = "/websocket/endpoint")
public class WebsocketConnector {
    public static final Logger logger = Logger.getLogger(WebsocketConnector.class.getName());

    private PCAndHumanGameRunner gameRunner;

    @OnOpen
    public synchronized void start(Session session) {
        GameStateDrawer drawer = new FrontendGameStateDrawerImpl(session);
        gameRunner = new PCAndHumanGameRunner(drawer);
    }

    @OnMessage
    public void incoming(String message) {
        logger.info("Message received: '" + message + "'");
        gameRunner.humanCommand(message);
    }

    @OnClose
    public void end() {
        logger.info("Connection closed.");
    }

    @OnError
    public void onError(Throwable t) throws Throwable {
        logger.severe("Game Error: " + t.toString() + t);
    }

}
