package player;

import enums.Move;
import org.apache.log4j.Logger;


public class PlayerHumanImpl implements PlayerHuman {
    private static final Logger LOGGER = Logger.getLogger(PlayerHumanImpl.class);

    private Move nextMove = Move.NONE;
    private Integer x = 50;
    private static final int DX = 10;
    private String name;

    public PlayerHumanImpl(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
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
    public Integer getX() {
        return x;
    }
}
