package player;

import enums.Move;
import org.apache.log4j.Logger;


public class PlayerCompImpl implements PlayerAsync{
    public static final Logger logger = Logger.getLogger(PlayerCompImpl.class);
    private static final long DEFAULT_DELAY_MS = 1900;

    private boolean stopped = false;
    private Move nextMove;
    private String name;
    private Integer x = 50;
    private static final int DX = 10;


    public PlayerCompImpl(final String name) {
        this.name = name;
    }


    @Override
    public Move getNextMove() {
        return nextMove;
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
                } while (!stopped);
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
        logger.info(name + " next move to do : " + nextMove);
    }

    @Override
    public void stop() {
        stopped = true;
    }

    @Override
    public Integer getX() {
        return x;
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
        }
        if (x <= 90 && nextMove.equals(Move.RIGHT)) {
            x = x + DX;
        }
    }


}
