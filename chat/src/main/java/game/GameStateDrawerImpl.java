package game;

import org.apache.log4j.Logger;
import player.Player;
import javax.websocket.Session;
import java.io.IOException;
import java.util.Set;

public class GameStateDrawerImpl implements GameStateDrawer {
    private Game game;
    private Set<Session> sessions;
    private static final Logger LOGGER = Logger.getLogger(GameStateDrawerImpl.class);

    public GameStateDrawerImpl(Set<Session> sessions) {
        this.sessions = sessions;
    }

    @Override
    public void draw() {
        Player player1 = game.getPlayers().get(0);
        Player player2 = game.getPlayers().get(1);
        try {
            for (Session session : sessions) {
                session.getBasicRemote().sendText("Player1:" + player1.getX() + "    Player2:" + player2.getX());
                LOGGER.info("Game state sent to:" + session);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error during sending websocket message to frontend.");
        }
    }

    @Override
    public Game getGame() {
        return game;
    }

    @Override
    public void setGame(Game game) {
        this.game = game;
    }
}
