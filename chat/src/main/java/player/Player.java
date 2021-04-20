package player;

public interface Player {
    Integer getX();
    int getY();
    String getName();
    void moveLeft();
    void moveRight();
    void applyMove();
    boolean isAlive();
    void setDied();

    default void shoot() {

    }
}
