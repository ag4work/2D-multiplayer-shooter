package game;

import com.google.gson.Gson;
import dto.PlayerDTO;
import dto.mapper.PlayerDTOMapper;
import org.apache.log4j.Logger;
import javax.websocket.Session;
import java.io.IOException;
import java.util.List;
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
            List<PlayerDTO> playerDTOs = PlayerDTOMapper.map(game.getPlayers());
            String json = obj2json(playerDTOs);
            for (Session session : sessions) {
                if (session.isOpen()) {
                    session.getBasicRemote().sendText(json);
                } else {
                    LOGGER.warn("Attempt to write to closed session:" + session);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error during sending websocket message to frontend.");
        }
    }

    private String obj2json(List<PlayerDTO> playerDTOs) {
        Gson gson = new Gson();
        return gson.toJson(playerDTOs);
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
