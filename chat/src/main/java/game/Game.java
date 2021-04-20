package game;

import player.Player;
import player.PlayerAsync;

import java.util.List;

public interface Game {
    void playGame();

    Player getWinner();
    boolean isGameOver();
    List<Player> getPlayers();
    void stop();

    List<Bullet> getBullets();
}
