package spaceinvaders;

import java.awt.*;
import java.util.Random;

public class Star {
    private int x;
    private int y;
    private int size;

    public Star() {
        Random random = new Random();
        x = random.nextInt(800);
        y = random.nextInt(600);
        size = random.nextInt(3) + 1;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSize() {
        return size;
    }

    public void move() {
        x -= size;
        if (x < 0) {
            x = 800;
        }
    }

    public void drawMe(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillOval(x, y, size, size);
    }
}
