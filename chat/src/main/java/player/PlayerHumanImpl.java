package player;

import game.Move;
import org.apache.log4j.Logger;


public class PlayerHumanImpl implements PlayerHuman {
    private static final Logger LOGGER = Logger.getLogger(PlayerHumanImpl.class);

    private Move nextMove;
    private Integer x = 50;
    private static final int DX = 10;


    @Override
    public Move getNextMove() {
        return nextMove;
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
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void applyMove() {
        if (nextMove == null) {
            LOGGER.warn("next move is null..");
            return;
        }
        if (x >= 10 && nextMove.equals(Move.LEFT)) {
            x = x - DX;
        }
        if (x <= 90 && nextMove.equals(Move.RIGHT)) {
            x = x + DX;
        }
    }

    @Override
    public Integer getX() {
        return x;
    }
}
