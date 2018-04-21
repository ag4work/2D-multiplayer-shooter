package game;

import org.apache.log4j.Logger;
import player.Player;
import player.PlayerAsync;

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
        try {
            for (Session session : sessions) {
                if (session.isOpen()) {
                    session.getBasicRemote().sendText(getPlayersInfo());
                } else {
                    LOGGER.warn("Attempt to write to closed session:" + session);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error during sending websocket message to frontend.");
        }
    }

    private String getPlayersInfo() {
        StringBuilder sb = new StringBuilder();
        for (PlayerAsync player : game.getPlayers()) {
            sb.append(player.getName()).append(":").append(player.getX()).append(" ");
        }
        return sb.toString();
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
