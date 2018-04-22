package dispatcher;

import game.GameImpl;
import game.Game;
import game.GameStateDrawer;
import game.GameStateDrawerImpl;
import org.apache.log4j.Logger;
import player.*;

import javax.websocket.MessageHandler;
import javax.websocket.Session;
import java.io.IOException;
import java.util.*;


public class TwoHumanAndPCGameStarter implements Runnable {
    Player pcPlayer;
    Player playerHuman1;
    Player playerHuman2;
    Game game;

    private static final Logger logger = Logger.getLogger(TwoHumanAndPCGameStarter.class);
    Session session1;
    Session session2;

    public TwoHumanAndPCGameStarter(Session session1, Session session2) {
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
        playerHuman1 = new PlayerHumanImpl("Human1");
        playerHuman2 = new PlayerHumanImpl("Human2");
        List<Player> players = new LinkedList<>(Arrays.asList(playerHuman1, playerHuman2, pcPlayer));
        session1.addMessageHandler(new MyMEssageHandler(session1, playerHuman1));
        session2.addMessageHandler(new MyMEssageHandler(session2, playerHuman2));

        game = new GameImpl(players, drawer);
        drawer.setGame(game);
        game.playGame();
        logger.info("Game thread finished.");
    }

    public void sendStop() {
        game.stop();
    }


    class MyMEssageHandler implements MessageHandler.Whole<String> {
        Session session;
        Player player;

        MyMEssageHandler(Session session, Player player) {
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
