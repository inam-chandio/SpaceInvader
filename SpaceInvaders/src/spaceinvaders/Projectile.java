package spaceinvaders;

import java.awt.Color;
import java.awt.Graphics;

public class Projectile {
    private int x;
    private int y;
    private static final int SPEED = 5;
    private static final int WIDTH = 10;
    private static final int HEIGHT = 5;

    public Projectile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move() {
        x += SPEED;
    }


    public boolean collidesWith(SpaceObject enemy) {
        return x < enemy.getX() + enemy.getWidth() &&
                x + WIDTH > enemy.getX() &&
                y < enemy.getY() + enemy.getHeight() &&
                y + HEIGHT > enemy.getY();
    }

    public void drawMe(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillRect(x, y, WIDTH, HEIGHT);
    }

    public int getX() {
        return x;
    }
}
