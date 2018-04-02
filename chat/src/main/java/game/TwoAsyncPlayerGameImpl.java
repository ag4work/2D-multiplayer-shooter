package game;

import org.apache.log4j.Logger;
import player.Player;
import player.PlayerAsync;

import java.util.LinkedList;
import java.util.List;

public class TwoAsyncPlayerGameImpl implements Game {

    private PlayerAsync player1;
    private PlayerAsync player2;
    private Player winner;
    private static final Logger logger = Logger.getLogger(TwoAsyncPlayerGameImpl.class);
    private static final int INTERNAL_DELAY_IN_MS = 2000;
    private GameStateDrawer drawer;
    private List<Player> players = new LinkedList<Player>();

    public TwoAsyncPlayerGameImpl(PlayerAsync player1, PlayerAsync player2, GameStateDrawer drawer) {
        this.player1 = player1;
        this.player2 = player2;
        players.add(player1);
        players.add(player2);
        this.drawer = drawer;
    }

    @Override
    public void playGame() {
        logger.info("Starting game.");
        player1.start();
        player2.start();
        do {
            logger.info("Start internal game iteration.");
            sleep();
            player1.applyMove();
            player2.applyMove();
            drawer.draw();
        } while (!someoneWin());
        player1.stop();
        player2.stop();
    }

    private boolean someoneWin() {
        if (player1.getNextMove() == null) {
            logger.info("player1 did not do his move");
            return false;
        }
        if (player2.getNextMove() == null) {
            logger.info("player2 did not do his move");
            return false;
        }

        if (player1.getNextMove().equals(Move.SHOOT) && !player2.getNextMove().equals(Move.SHOOT)) {
            logger.info("player1 shot player2. Game over.");
            winner = player1;
            return true;
        }
        if (!player1.getNextMove().equals(Move.SHOOT) && player2.getNextMove().equals(Move.SHOOT)) {
            logger.info("player2 shot player1. Game over.");
            winner = player2;
            return true;
        }
        if (player1.getNextMove().equals(Move.SHOOT) && player2.getNextMove().equals(Move.SHOOT)) {
            logger.info("Players shoot simultaniously. Continue game.");
        }
        logger.info("Players missed. Continue game.");
        return false;
    }

    @Override
    public Player getWinner() {
        if (winner != null) {
            return winner;
        } else {
            throw new RuntimeException("Game is not completed yet.");
        }
    }

    @Override
    public List<Player> getPlayers() {
        return players;
    }

    void sleep() {
        try {
            Thread.sleep(INTERNAL_DELAY_IN_MS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
