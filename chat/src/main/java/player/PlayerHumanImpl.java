package player;

import enums.Move;
import game.Bullet;
import game.Game;
import org.apache.log4j.Logger;


public class PlayerHumanImpl implements PlayerHuman {
    private static final Logger LOGGER = Logger.getLogger(PlayerHumanImpl.class);

    private Move nextMove = Move.NONE;
    private Integer x = 50;
    private int y;
    private static final int DX = 10;
    private String name;
    private Game game;

    public PlayerHumanImpl(String name, int y) {
        this.y = y;
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public void moveLeft() {
        nextMove = Move.LEFT;
    }

    @Override
    public void moveRight() {
        nextMove = Move.RIGHT;
    }

    @Override
    public void applyMove() {
        if (nextMove == null) {
            LOGGER.warn("next move is NONE..");
            return;
        }
        if (x >= 10 && nextMove.equals(Move.LEFT)) {
            x = x - DX;
            nextMove = Move.NONE;
        }
        if (x <= 80 && nextMove.equals(Move.RIGHT)) {
            x = x + DX;
            nextMove = Move.NONE;
        }
    }

    @Override
    public void shoot() {
        Bullet bullet = new Bullet(x, y, -2);
        game.getBullets().add(bullet);
    }

    @Override
    public Integer getX() {
        return x;
    }
}
