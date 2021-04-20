package player;

import game.Game;

public interface Player {
    String getName();
    int getX();
    int getY();
    void setGame(Game game);
    void applyMove();
    void moveLeft();
    void moveRight();
    void shoot();
    boolean isAlive();
    void setDied();
}
