package game;

import org.apache.log4j.Logger;

import javax.websocket.MessageHandler;
import javax.websocket.Session;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class TwoHumanGameStarter implements Runnable {
    private static final Logger logger = Logger.getLogger(TwoHumanGameStarter.class);
    Session session1;
    Session session2;

    public TwoHumanGameStarter(Session session1, Session session2) {
        this.session1 = session1;
        this.session2 = session2;
    }

    @Override
    public void run() {
        logger.info("Game thread is starting.");
        Set<Session> sessionSet = new HashSet<>();
        sessionSet.add(session1);
        sessionSet.add(session2);
        session1.addMessageHandler(new MyMEssageHandler(session1));
        session2.addMessageHandler(new MyMEssageHandler(session2));
        final GameStateDrawer drawer = new GameStateDrawerImpl(sessionSet);
        PCAndHumanGameRunner gameRunner = new PCAndHumanGameRunner(drawer);
        gameRunner.startGame();
        logger.info("Game thread finished.");
    }

    class MyMEssageHandler implements MessageHandler.Whole<String> {
        Session session;

        MyMEssageHandler(Session session) {
            this.session = session;
        }

        @Override
        public void onMessage(String message) {
            logger.info("Message handler of session " + session);
            try {
                session.getBasicRemote().sendText("Message handler of session " + session);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}