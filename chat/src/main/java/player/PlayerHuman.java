package player;

import game.Game;

/**
 * Created by agordeev on 02.04.2018.
 */
public interface PlayerHuman extends Player {
    void moveRight();

    void setGame(Game game);

    void moveLeft();
}
