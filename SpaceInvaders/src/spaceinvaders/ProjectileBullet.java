package spaceinvaders;

import java.awt.*;

public class ProjectileBullet {
    private int x;
    private int y;
    private int width;
    private int height;
    private Color color;

    public ProjectileBullet(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = 10;
        this.height = 10;
        this.color = Color.red;
    }

    public void drawMe(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, width, height);
    }

    public void moveRight() {
        x += 5;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean collidesWith(Enemy enemy) {
        return x < enemy.getX() + enemy.getWidth() &&
                x + width > enemy.getX();
    }
}
