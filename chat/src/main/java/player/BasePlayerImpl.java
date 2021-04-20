package player;

import enums.Move;
import game.Game;

public abstract class BasePlayerImpl implements Player {

    protected Move nextMove = Move.NONE;
    private boolean alive = true;
    private static final int DX = 10;
    final private String name;
    private int x;
    private int y;
    private Game game;

    public BasePlayerImpl(int x, int y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }

    @Override
    public void applyMove() {
        if (getX() >= 10 && nextMove.equals(Move.LEFT)) {
            setX(getX() - DX);
        } else if (getX() <= 80 && nextMove.equals(Move.RIGHT)) {
            setX(getX() + DX);
        } else if (nextMove.equals(Move.SHOOT)) {
            applyShoot();
        }
        nextMove = Move.NONE;
    }

    abstract void applyShoot();

    @Override
    public void moveLeft() {
        nextMove = Move.LEFT;
    }

    @Override
    public void moveRight() {
        nextMove = Move.RIGHT;
    }

    @Override
    public void shoot() {
        nextMove = Move.SHOOT;
    }

    @Override
    public boolean isAlive() {
        return alive;
    }

    @Override
    public void setDied() {
        alive = false;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getX() {
        return x;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    @Override
    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
