package game;

import player.Player;

import javax.websocket.Session;
import java.io.IOException;

public class FrontendGameStateDrawerImpl implements GameStateDrawer {
    private Session session;
    private Game game;

    public FrontendGameStateDrawerImpl(Session session) {
        this.session = session;
    }

    @Override
    public void draw() {
        Player player1 = game.getPlayers().get(0);
        Player player2 = game.getPlayers().get(1);
        try {
            session.getBasicRemote().sendText("Player1:" + player1.getX() + "    Player2:" + player2.getX());
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
