package connector;

import org.apache.log4j.Logger;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint(value = "/websocket/endpoint")
public class WebsocketConnector {
    public static final Logger logger = Logger.getLogger(WebsocketConnector.class);
    private static GameRequestDispatcher dispatcher =  GameRequestDispatcher.getInstance();  // todo static ?

    public WebsocketConnector() {
        logger.info("Server endpoint started: " + this);
    }

    @OnOpen
    public void start(Session session) {
        logger.info("Websocket request to game received.");
        try {
            dispatcher.register(session);
        } catch (Exception e) {
            logger.error("Error during session registration for game", e);
            try {
                session.getBasicRemote().sendText("Error during session registration for game");
            } catch (IOException e1) {
                logger.error("Error during sending to frontend");
            }
        }
        try {
            logger.info("Session:" + session + " hashcode: " + session.hashCode());
            logger.info("handlers: " + session.getMessageHandlers());
        } catch (Exception e) {
            throw e;
        }
    }

    @OnClose
    public void end(Session session, CloseReason closeReason) throws IOException {
        logger.info("Connection closed. Session : " + session + " reason: " + closeReason.getReasonPhrase() + " "
                + closeReason.getCloseCode() );
        dispatcher.closeGameBySession(session);
    }

    @OnError
    public void onError(Throwable t) throws Throwable {
        logger.error("Game Error: " + t.toString(), t);
    }

}
