package game;

public class Bullet {
    int x;
    int y;
    int dy;

    public Bullet(int x, int y, int dy) {
        this.x = x;
        this.y = y;
        this.dy = dy;
    }

    public void move() {
        y = y + dy;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
