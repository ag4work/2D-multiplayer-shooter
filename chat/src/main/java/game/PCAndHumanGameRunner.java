package game;

import player.PlayerAsync;
import player.PlayerHuman;
import player.PlayerHumanImpl;
import player.PlayerThreadImpl;


public class PCAndHumanGameRunner {
    PlayerAsync pcPlayer;
    PlayerHuman playerHuman;

    public PCAndHumanGameRunner(GameStateDrawer drawer) {
        pcPlayer = new PlayerThreadImpl("PC Player");
        playerHuman = new PlayerHumanImpl();
        Game game = new TwoAsyncPlayerGameImpl(pcPlayer, playerHuman, drawer);
        drawer.setGame(game); // todo
        game.playGame();      // todo

    }

    public void humanCommand(String msg) {
        if (msg.equals("right")) {
            playerHuman.moveRight();
        } else if (msg.equals("left")) {
            playerHuman.moveLeft();
        }
    }

}
