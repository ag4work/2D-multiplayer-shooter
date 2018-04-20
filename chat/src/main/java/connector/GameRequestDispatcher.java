package connector;

import game.TwoHumanGameStarter;
import org.apache.log4j.Logger;
import javax.websocket.Session;
import java.util.concurrent.ConcurrentLinkedDeque;

public class GameRequestDispatcher {
    private static final int TWO_HUMAN_PLAYERS_REQUEIRED = 2;
    private static final GameRequestDispatcher instance = new GameRequestDispatcher();
    private static final Logger logger = Logger.getLogger(GameRequestDispatcher.class);
    ConcurrentLinkedDeque<Session> sessions = new ConcurrentLinkedDeque<>();

    public synchronized void register(Session session) throws Exception {
        if (sessions.size() < TWO_HUMAN_PLAYERS_REQUEIRED - 1) {
            sessions.add(session);
            session.getBasicRemote().sendText("Needed another player to start.");
        } else {
            logger.info("Required " + TWO_HUMAN_PLAYERS_REQUEIRED + " sessions are ready. Trying to arrange game.");
            session.getBasicRemote().sendText("Requered " + TWO_HUMAN_PLAYERS_REQUEIRED + " sessions are ready. Trying " +
                    "to arrange the game.");
            Session session1 = sessions.removeLast();
            Session session2 = session;
            new Thread(new TwoHumanGameStarter(session1, session2)).start();
        }
    }

    public static GameRequestDispatcher getInstance() {
        return instance;
    }



}
