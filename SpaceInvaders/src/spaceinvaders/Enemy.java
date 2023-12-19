package spaceinvaders;

import java.awt.*;

public class Enemy implements SpaceObject {
    private int x;
    private int y;
    private static final int WIDTH = 30;
    private static final int HEIGHT = 20;
    private static final int ENEMY_SPEED_INCREASE = 1;
    private boolean visible;

    public Enemy(int x, int y) {
        this.x = x;
        this.y = y;
        this.visible = true;
    }
    protected void setX(int newX) {
        x = newX;
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }

    @Override
    public void move() {
        x -= ENEMY_SPEED_INCREASE;
        if (Math.random() < 0.5) {
            y += (int) (Math.random() * 10) - 5;
        }
    }

    @Override
    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public void draw(Graphics g) {
        if (visible) {
            g.setColor(Color.RED);
//            g.fillRect(x, y, WIDTH, HEIGHT);
            g.fillOval(x, y, WIDTH, HEIGHT);
            g.fillArc(x + 15, y, 70, 60, 0, 180);

        }
    }
}
