package game;

import player.PlayerAsync;
import player.PlayerHuman;
import player.PlayerHumanImpl;
import player.PlayerThreadImpl;


public class PCAndHumanGameRunner {
    PlayerAsync pcPlayer;
    PlayerHuman playerHuman;
    Game game;

    public PCAndHumanGameRunner(GameStateDrawer drawer) {
        pcPlayer = new PlayerThreadImpl("PC Player");
        playerHuman = new PlayerHumanImpl();
        game = new TwoAsyncPlayerGameImpl(pcPlayer, playerHuman, drawer);
        drawer.setGame(game);
    }

    public void runInSeparateThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                game.playGame();
            }
        }).start();
    }

    public void humanCommand(String msg) {
        if (msg.equals("right")) {
            playerHuman.moveRight();
        } else if (msg.equals("left")) {
            playerHuman.moveLeft();
        }
    }

}
