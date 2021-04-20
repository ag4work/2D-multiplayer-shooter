package dispatcher;

import com.google.gson.Gson;
import dto.GameStartedDTO;
import game.GameImpl;
import game.Game;
import game.GameStateDrawer;
import game.GameStateDrawerImpl;
import org.apache.log4j.Logger;
import player.Player;
import player.PlayerCompImpl;
import player.PlayerHuman;
import player.PlayerHumanImpl;

import javax.websocket.MessageHandler;
import javax.websocket.Session;
import java.io.IOException;
import java.util.*;


public class TwoHumanAndPCGameStarter implements Runnable {
    public static final boolean TOP = false;
    public static final boolean BOTTOM = true;
    Player pcPlayer;
    PlayerHuman playerHuman1;
    PlayerHuman playerHuman2;
    Game game;
    private final static String PLAYER1_NAME = "Human1";
    private final static String PLAYER2_NAME = "Human2";

    private static final Logger LOGGER = Logger.getLogger(TwoHumanAndPCGameStarter.class);
    Session session1;
    Session session2;

    public TwoHumanAndPCGameStarter(Session session1, Session session2) {
        this.session1 = session1;
        this.session2 = session2;
    }

    @Override
    public void run() {
        LOGGER.info("Game thread is starting.");
        Set<Session> sessionSet = new HashSet<>();
        sessionSet.add(session1);
        sessionSet.add(session2);
        final GameStateDrawer drawer = new GameStateDrawerImpl(sessionSet);

        pcPlayer = new PlayerCompImpl("PC Player", 50, 50);
        playerHuman1 = new PlayerHumanImpl(PLAYER1_NAME, 5, TOP);
        playerHuman2 = new PlayerHumanImpl(PLAYER2_NAME, 95, BOTTOM);
        List<Player> players = new LinkedList<>(Arrays.asList(playerHuman1, playerHuman2, pcPlayer));
        session1.addMessageHandler(new MyMEssageHandler(session1, playerHuman1));
        session2.addMessageHandler(new MyMEssageHandler(session2, playerHuman2));

        game = new GameImpl(players, drawer);
        playerHuman1.setGame(game);
        playerHuman2.setGame(game);
        drawer.setGame(game);
        notifyPlayersAboutStart();
        game.playGame();
        LOGGER.info("Game thread finished.");
    }

    private void notifyPlayersAboutStart() {
        try {
            session1.getBasicRemote().sendText(new Gson().toJson(new GameStartedDTO(PLAYER1_NAME)));
            session2.getBasicRemote().sendText(new Gson().toJson(new GameStartedDTO(PLAYER2_NAME)));
        } catch (IOException e) {
            LOGGER.error(e);
        }
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
                LOGGER.info("Message " + message +" of " + session);
                humanCommand(message);
        }

        public void humanCommand(String msg) {
            if (!player.isAlive()) {
                return;
            }
            if (msg.equals("right")) {
                player.moveRight();
            } else if (msg.equals("left")) {
                player.moveLeft();
            } else if (msg.equals("shoot")) {
                player.shoot();
            }
        }

    }

}
