package game;

import player.PlayerAsync;
import player.PlayerThreadImpl;

import java.io.IOException;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class GameRunner {
    private static final Logger logger = Logger.getLogger(GameRunner.class.getName());
    public static void main(String[] args) throws IOException {
//        LogManager.getLogManager().readConfiguration(GameRunner.class.getClassLoader().getResourceAsStream("logging.properties"));
//        PlayerAsync player1 = new PlayerThreadImpl("Player1");
//        PlayerAsync player2 = new PlayerThreadImpl("Player2");
//        Game game = new TwoAsyncPlayerGameImpl(player1, player2);
//        game.playGame();
//        logger.info("Winners is " + game.getWinner()) ;
    }
}
