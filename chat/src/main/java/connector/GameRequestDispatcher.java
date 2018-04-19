package connector;

import game.GameStateDrawer;
import game.GameStateDrawerImpl;
import game.PCAndHumanGameRunner;
import org.apache.log4j.Logger;

import javax.websocket.MessageHandler;
import javax.websocket.Session;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;


public class GameRequestDispatcher {
    private static final int HUMAN_PLAYERS_REQUEIRED = 2;
    private AtomicInteger cntToWait = new AtomicInteger(HUMAN_PLAYERS_REQUEIRED);
    private static final GameRequestDispatcher instance = new GameRequestDispatcher();
    private static final Logger logger = Logger.getLogger(GameRequestDispatcher.class);
    CyclicBarrier barrier = new CyclicBarrier(HUMAN_PLAYERS_REQUEIRED, new TwoHumanGameStarter());
    ConcurrentLinkedDeque<Session> sessions = new ConcurrentLinkedDeque<>();

    public void register(Session session) throws BrokenBarrierException, InterruptedException {
        sessions.addFirst(session);
        if (!barrier.isBroken()) {
            logger.info(session + " registered and is waiting for another player.");
            try {
                session.getBasicRemote().sendText("Registered. Waiting for another player.");
            } catch (IOException e) {
                logger.error(e);
            }
        }
        barrier.await();
    }

    public static GameRequestDispatcher getInstance() {
        return instance;
    }

    class TwoHumanGameStarter implements Runnable {
        @Override
        public void run() {
            logger.info("All needed players have come. The game is starting.");
            Set<Session> sessionSet = new HashSet<>();
            for (int i = 0; i < HUMAN_PLAYERS_REQUEIRED; i++) {
                Session e = sessions.removeLast();
                sessionSet.add(e);
                if (i==0) {
                    e.addMessageHandler(new MyMEssageHandler());
                }
            }
            final GameStateDrawer drawer = new GameStateDrawerImpl(sessionSet);
            PCAndHumanGameRunner gameRunner = new PCAndHumanGameRunner(drawer);
            gameRunner.runInSeparateThread();
            logger.info("Session started2.");
            cntToWait.set(2);
        }
    }

    class MyMEssageHandler implements MessageHandler.Whole<String> {
        @Override
        public void onMessage(String message) {
            logger.info("my messsage handler invoked.");
        }
    }

}
