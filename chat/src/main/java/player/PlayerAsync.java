package player;

import enums.Move;

public interface PlayerAsync extends Player{
    // get direction of next move
    Move getNextMove();
    void start();
    void stop();
    void applyMove();
}
