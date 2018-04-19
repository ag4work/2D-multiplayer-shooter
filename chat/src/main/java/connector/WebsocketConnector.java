package connector;

import game.GameStateDrawerImpl;
import game.GameStateDrawer;
import game.PCAndHumanGameRunner;
import org.apache.log4j.Logger;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.BrokenBarrierException;

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
            MessageHandler.Whole<String> handler = new MyMEssageHandler.Whole<String>() {
                @Override
                public void onMessage(String message) {
                    logger.info("my message handler invoked in session:" + session);
                }
            };
            logger.info("adding handler:" + handler);
            session.addMessageHandler(handler);
        } catch (Exception e) {
            int a  =1;
            throw e;
        }
    }

//    @OnMessage
    public void incoming(String message) {
        logger.info("Message received: '" + message + "'");
//        if (gameRunner != null) {
//            gameRunner.humanCommand(message);
//        }
    }

    @OnClose
    public void end() {
        logger.info("Connection closed.");
    }

    @OnError
    public void onError(Throwable t) throws Throwable {
        logger.error("Game Error: " + t.toString(), t);
    }

    class MyMEssageHandler implements MessageHandler.Whole<String> {
        @Override
        public void onMessage(String message) {
            logger.info("my messsage handler invoked.");
        }
    }

}
