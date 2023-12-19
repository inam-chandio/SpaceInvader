package spaceinvaders;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Ship {
    private int x;
    private int y;
    private int width = 50;
    private int height = 30;
    private static final int SHIP_SPEED = 5;
    private Screen screen;

    public Ship(int x, int y, Screen screen) {
        this.x = x;
        this.y = y;
        this.screen = screen;

        screen.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    moveUp();
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    moveDown();
                } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    screen.shoot();
                }
            }
        });
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void moveUp() {
        if (y - SHIP_SPEED > 0) {
            y -= SHIP_SPEED;
        }
    }

    public void moveDown() {
        if (y + height + SHIP_SPEED < 600) {
            y += SHIP_SPEED;
        }
    }
    public void drawMe(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(x, y, width, height);
    }

    public void handleProjectiles() {
        // Handle projectiles logic here
    }

    private void shoot() {
        screen.shoot();
    }

    private void moveShipUp() {
        screen.moveShipUp();
    }

    private void moveShipDown() {
        screen.moveShipDown();
    }

    private void cheat() {
        screen.cheat();
    }

	public boolean collidesWith(SpaceObject enemy) {
		// TODO Auto-generated method stub
		return false;
	}
}
