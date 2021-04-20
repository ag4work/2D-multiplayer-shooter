package game;

import org.apache.log4j.Logger;
import player.Player;
import player.PlayerAsync;

import java.util.*;

public class GameImpl implements Game {

    private Player winner;
    private static final Logger logger = Logger.getLogger(GameImpl.class);
    private static final int INTERNAL_DELAY_IN_MS = 200;
    private static final int MAX_GAME_TIME_SEC = 5;
    private GameStateDrawer drawer;
    private List<Player> players = new LinkedList<>();
    private List<Bullet> bullets = new ArrayList<>();
    private long startTimer;
    private boolean gameStopped = false;

    public GameImpl(List<Player> players, GameStateDrawer drawer) {
        this.players.addAll(players);
        this.drawer = drawer;
    }

    @Override
    public List<Bullet> getBullets() {
        return bullets;
    }

    @Override
    public void playGame() {
        logger.info("Starting game.");
        initTimer();
        startAsyncPlayers();
        do {
            sleep();
            applyPlayersMoves();
            moveBullets();
            removeShotPlayers();
            drawer.draw();
        } while (!isGameOver());
        drawer.drawGameOver();
        logger.info("Game finished.");
    }

    private void removeShotPlayers() {
        for (Player player : players) {
            for (Bullet bullet : bullets) {
                if (playerWasShot(player, bullet)) {
                    player.setDied();
                }
            }
        }
    }

    private boolean playerWasShot(Player p, Bullet b) {
        return b.getX()  >= p.getX() - 5 && b.getX()  <= p.getX() + 5 &&
                b.getY()  >= p.getY() - 5 && b.getY()  <= p.getY() + 5;
    }


    private void moveBullets() {
        Iterator<Bullet> iterator = bullets.iterator();
        while (iterator.hasNext()) {
            Bullet bullet = iterator.next();
            if (bullet.y < 0 || bullet.y > 100) {
                iterator.remove();
            } else {
                bullet.move();
            }
        }
    }

    private void startAsyncPlayers() {
        for (Player p : players) {
            if (p instanceof PlayerAsync) {
                ((PlayerAsync) p).start();
            }
        }
    }

    private void applyPlayersMoves() {
        for (Player player : players) {
            player.applyMove();
        }
    }

    private void initTimer() {
        startTimer = new Date().getTime();
    }

    @Override
    public boolean isGameOver() {
        return oneOrNobodyAlive() || isTimeOver() || gameStopped;
    }

    private boolean isTimeOver() {
        if (startTimer - new Date().getTime() > MAX_GAME_TIME_SEC) {
            logger.info("Game time is over.");
            return true;
        } else {
            return false;
        }
    }

    private boolean oneOrNobodyAlive() {
        long aliveCount = players.stream().filter(Player::isAlive).count();
        if (aliveCount == 1) {
            winner = players.stream().filter(Player::isAlive).findFirst().get();
        }
        return aliveCount <= 1;
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

    @Override
    public void stop() {
        gameStopped = true;
        for (Player p : players) {
            if (p instanceof PlayerAsync) {
                ((PlayerAsync) p).stop();
            }
        }
    }

    void sleep() {
        try {
            Thread.sleep(INTERNAL_DELAY_IN_MS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
