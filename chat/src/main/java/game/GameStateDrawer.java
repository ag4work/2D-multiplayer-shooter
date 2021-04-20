package game;

/**
 * Created by agordeev on 02.04.2018.
 */
public interface GameStateDrawer {
    void draw();
    void setGame(Game game);
    Game getGame();

    void drawGameOver();
}
