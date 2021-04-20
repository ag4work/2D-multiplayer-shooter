package player;

import game.Bullet;
import org.apache.log4j.Logger;


public class PlayerCompImpl extends BasePlayerImpl implements PlayerAsync {

    public static final Logger logger = Logger.getLogger(PlayerCompImpl.class);
    private static final long DEFAULT_DELAY_MS = 500;

    private boolean stopped = false;
    private static final int BULLET_SPEED = 4;


    public PlayerCompImpl(final String name, int x, int y) {
        super(x, y, name);
    }

    @Override
    public void start() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                logger.info(getName() + "started.");
                do {
                    sleep();
                    decideMove();
                } while (!stopped && isAlive());
            }
        });
        thread.start();
    }

    private void decideMove() {
        int rnd = (int)(Math.random()*3);
        switch (rnd) {
            case 0 : shoot();
                break;
            case 1 : moveLeft();
                break;
            case 2 : moveRight();
        }
    }

    @Override
    public void applyShoot() {
        Bullet bullet = (int)(Math.random()*2) == 0 ? new Bullet(getX(), 60, BULLET_SPEED) : new Bullet(getX(), 40, -BULLET_SPEED);
        getGame().getBullets().add(bullet);
    }

    @Override
    public void stop() {
        stopped = true;
    }

    static void sleep() {
        try {
            Thread.sleep(DEFAULT_DELAY_MS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
