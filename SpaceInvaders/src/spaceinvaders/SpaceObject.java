package spaceinvaders;

import java.awt.Graphics;

public interface SpaceObject {
    void move();
    void draw(Graphics g);
    boolean isVisible();
    int getX();
    int getY();
    int getWidth();
    int getHeight();
	void setVisible(boolean b);
}
