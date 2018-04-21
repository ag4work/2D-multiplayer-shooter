package game;

import player.Player;

import java.util.List;

public interface Game {
    void playGame();
    Player getWinner();
    boolean isGameOver();
    List<Player> getPlayers();

    void stop();
}
