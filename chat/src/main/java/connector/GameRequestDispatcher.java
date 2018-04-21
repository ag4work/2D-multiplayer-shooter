package connector;

import game.HumanAndHumanObserverAndPCGameStarter;
import javafx.util.Pair;
import org.apache.log4j.Logger;
import javax.websocket.Session;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedDeque;

public class GameRequestDispatcher {
    private static final int TWO_HUMAN_PLAYERS_REQUEIRED = 2;
    private static final GameRequestDispatcher instance = new GameRequestDispatcher();
    private static final Logger logger = Logger.getLogger(GameRequestDispatcher.class);
    ConcurrentLinkedDeque<Session> sessions = new ConcurrentLinkedDeque<>(); // todo change to regular Deque
    Map<Session, HumanAndHumanObserverAndPCGameStarter> ses2game = new HashMap<>();
    Map<HumanAndHumanObserverAndPCGameStarter, Set<Session>> game2ses = new HashMap<>();

    public synchronized void register(Session session) throws Exception {
        if (sessions.size() < TWO_HUMAN_PLAYERS_REQUEIRED - 1) {
            sessions.add(session);
            session.getBasicRemote().sendText("Needed another player to start.");
        } else {
            logger.info("Required " + TWO_HUMAN_PLAYERS_REQUEIRED + " sessions are ready. Trying to arrange game.");
            session.getBasicRemote().sendText("Requered " + TWO_HUMAN_PLAYERS_REQUEIRED + " sessions are ready." +
                    " Trying to arrange the game.");
            Session session1 = sessions.removeLast();
            Session session2 = session;
            HumanAndHumanObserverAndPCGameStarter gamesStarter = new HumanAndHumanObserverAndPCGameStarter(session1,
                    session2);
            game2ses.put(gamesStarter, new HashSet<Session>(){{add(session1); add(session2);}});
            ses2game.put(session1, gamesStarter);
            ses2game.put(session2, gamesStarter);
            new Thread(gamesStarter).start();
        }
    }
    public synchronized void closeGameBySession(Session session) throws IOException {
        HumanAndHumanObserverAndPCGameStarter gameSt = ses2game.get(session);
        if (gameSt == null) {
            sessions.remove(session);
            return;
        }
        logger.info("Trying to stop game loop.");
        gameSt.sendStop();
        Set<Session> sessions = game2ses.get(gameSt);
        game2ses.remove(gameSt);
        logger.info("Trying to close all sessions for the game");
        for (Session s : sessions) {
            ses2game.remove(s);
            if (!s.equals(session) && s.isOpen()) {
                s.getBasicRemote().sendText("Other player closed the game.");
                s.close();
            }
        }
    }

    public static GameRequestDispatcher getInstance() {
        return instance;
    }
}
