package player;

import enums.Move;
import game.Bullet;
import game.Game;
import org.apache.log4j.Logger;


public class PlayerHumanImpl extends BasePlayerImpl implements PlayerHuman {
    private static final Logger LOGGER = Logger.getLogger(PlayerHumanImpl.class);

    private boolean bottom;

    public PlayerHumanImpl(String name, int y, boolean isBottom) {
        super(50, y, name);
        this.bottom = isBottom;
    }

    @Override
    public void applyShoot() {
        int bulletSpeed = 4;
        int distFromBottom = 15;
        int startY = bottom ?  100 - distFromBottom : distFromBottom;
        Bullet bullet = new Bullet(getX() + 5, startY, bottom ? -bulletSpeed : bulletSpeed);
        getGame().getBullets().add(bullet);
    }

}
