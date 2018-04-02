package game;

import player.Player;

import java.util.List;

public interface Game {
    void playGame();
    Player getWinner();
    List<Player> getPlayers();
}
