package game;

import org.apache.log4j.Logger;
import player.Player;
import player.PlayerAsync;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class AsyncPlayersGameImpl implements Game {

    private Player winner;
    private static final Logger logger = Logger.getLogger(AsyncPlayersGameImpl.class);
    private static final int INTERNAL_DELAY_IN_MS = 2000;
    private static final int MAX_GAME_TIME_SEC = 5;
    private GameStateDrawer drawer;
    private List<PlayerAsync> players = new LinkedList<>();
    private long startTimer;
    private boolean gameStopped = false;

    public AsyncPlayersGameImpl(List<PlayerAsync> players, GameStateDrawer drawer) {
        this.players.addAll(players);
        this.drawer = drawer;
    }

    @Override
    public void playGame() {
        logger.info("Starting game.");
        startPlayers();
        initTimer();
        do {
//            logger.info("Start internal game iteration.");
            sleep();
            applyPlayersMoves();
            drawer.draw();
        } while (!isGameOver());
        stopPlayers();
        logger.info("Game finished.");
    }

    private void startPlayers() {
        for (PlayerAsync player : players) {
            player.start();
        }
    }

    private void stopPlayers() {
        for (PlayerAsync player : players) {
            player.stop();
        }
    }

    private void applyPlayersMoves() {
        for (PlayerAsync player : players) {
            player.applyMove();
        }
    }

    private void initTimer() {
        startTimer = new Date().getTime();
    }

    @Override
    public boolean isGameOver() {
        return someoneWin() || isTimeOver() || gameStopped;
    }

    private boolean isTimeOver() {
        if (startTimer - new Date().getTime() > MAX_GAME_TIME_SEC) {
            logger.info("Game time is over.");
            return true;
        } else {
            return false;
        }
    }


    private boolean someoneWin() {
        if (true) {
            return false; // todo remove it
        }
/*        if (player1.getNextMove() == null) {
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
        }*/
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
    public List<PlayerAsync> getPlayers() {
        return players;
    }

    @Override
    public void stop() {
        gameStopped = true;
    }

    void sleep() {
        try {
            Thread.sleep(INTERNAL_DELAY_IN_MS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
