package player;

import enums.Move;
import org.apache.log4j.Logger;


public class PlayerCompImpl implements PlayerAsync {
    public static final Logger logger = Logger.getLogger(PlayerCompImpl.class);
    private static final long DEFAULT_DELAY_MS = 500;

    private boolean stopped = false;
    private Move nextMove = Move.NONE;
    private String name;
    private Integer x = 50;
    private static final int DX = 10;
    private boolean alive = true;
    private int y;

    public PlayerCompImpl(final String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    @Override
    public void setDied() {
        alive = false;
    }

    @Override
    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void start() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                logger.info(name + "started.");
                do {
                    sleep();
                    makeMove();
                } while (!stopped && alive);
            }
        });
        thread.start();
    }

    private void makeMove() {
        int rnd = (int)(Math.random()*3);
        Move result;
        switch (rnd) {
            case 0 : nextMove = Move.SHOOT;
                break;
            case 1 : nextMove = Move.LEFT;
                break;
            case 2 : nextMove = Move.RIGHT;
        }
//        logger.info(name + " next move to do : " + nextMove);
    }

    @Override
    public void stop() {
        stopped = true;
    }

    @Override
    public Integer getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    static void sleep() {
        try {
            Thread.sleep(DEFAULT_DELAY_MS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void applyMove() {
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
    public void moveLeft() {

    }

    @Override
    public void moveRight() {

    }
}
