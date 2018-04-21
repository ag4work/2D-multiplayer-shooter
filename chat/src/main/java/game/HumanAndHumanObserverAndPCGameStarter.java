package game;

import org.apache.log4j.Logger;
import player.PlayerAsync;
import player.PlayerCompImpl;
import player.PlayerHuman;
import player.PlayerHumanImpl;

import javax.websocket.MessageHandler;
import javax.websocket.Session;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class HumanAndHumanObserverAndPCGameStarter implements Runnable {
    PlayerAsync pcPlayer;
    PlayerHuman playerHuman;
    Game game;

    private static final Logger logger = Logger.getLogger(HumanAndHumanObserverAndPCGameStarter.class);
    Session session1;
    Session session2;

    public HumanAndHumanObserverAndPCGameStarter(Session session1, Session session2) {
        this.session1 = session1;
        this.session2 = session2;
    }

    @Override
    public void run() {
        logger.info("Game thread is starting.");
        Set<Session> sessionSet = new HashSet<>();
        sessionSet.add(session1);
        sessionSet.add(session2);
        final GameStateDrawer drawer = new GameStateDrawerImpl(sessionSet);

        pcPlayer = new PlayerCompImpl("PC Player");
        playerHuman = new PlayerHumanImpl();
        session1.addMessageHandler(new MyMEssageHandler(session1, playerHuman));

        game = new TwoAsyncPlayerGameImpl(pcPlayer, playerHuman, drawer);
        drawer.setGame(game);
        game.playGame();
        logger.info("Game thread finished.");
    }

    public void sendStop() {
        game.stop();
    }


    class MyMEssageHandler implements MessageHandler.Whole<String> {
        Session session;
        PlayerHuman player;

        MyMEssageHandler(Session session, PlayerHuman player) {
            this.session = session;
            this.player = player;
        }

        @Override
        public void onMessage(String message) {
            logger.info("Message handler of session " + session);
            try {
                session.getBasicRemote().sendText("Message " + message +" of " + session);
                humanCommand(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void humanCommand(String msg) {
            if (msg.equals("right")) {
                player.moveRight();
            } else if (msg.equals("left")) {
                player.moveLeft();
            }
        }

    }

}