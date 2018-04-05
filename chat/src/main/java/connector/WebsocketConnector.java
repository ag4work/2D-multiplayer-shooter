package connector;

import game.GameStateDrawerImpl;
import game.GameStateDrawer;
import game.PCAndHumanGameRunner;
import org.apache.log4j.Logger;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/websocket/endpoint")
public class WebsocketConnector {
    public static final Logger logger = Logger.getLogger(WebsocketConnector.class);
    private static PCAndHumanGameRunner gameRunner; // todo static

    @OnOpen
    public void start(Session session) {
        logger.info("Session started1.");
        final GameStateDrawer drawer = new GameStateDrawerImpl(session);
        gameRunner = new PCAndHumanGameRunner(drawer);
        gameRunner.runInSeparateThread();
        logger.info("Session started2.");
    }

    @OnMessage
    public void incoming(String message) {
        logger.info("Message received: '" + message + "'");
        System.out.println("Message received: '" + message + "'");
        if (gameRunner != null) {
            gameRunner.humanCommand(message);
        }
    }

    @OnClose
    public void end() {
        logger.info("Connection closed.");
    }

    @OnError
    public void onError(Throwable t) throws Throwable {
        logger.error("Game Error: " + t.toString(), t);
    }

}
