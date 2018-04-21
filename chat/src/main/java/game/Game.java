package game;

import player.Player;
import player.PlayerAsync;

import java.util.List;

public interface Game {
    void playGame();
    Player getWinner();
    boolean isGameOver();
    List<PlayerAsync> getPlayers();

    void stop();
}
