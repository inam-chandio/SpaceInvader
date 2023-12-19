package spaceinvaders;

import javax.swing.*;

public class Game {
    private JFrame frame;
    private Screen screen;

    public Game() {
        frame = new JFrame("Space Invaders");
        screen = new Screen(this);
        frame.add(screen);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void shoot() {
        screen.shoot();
    }

    public void moveShipUp() {
        screen.moveShipUp();
    }

    public void moveShipDown() {
        screen.moveShipDown();
    }

    public void cheat() {
        screen.cheat();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Game::new);
    }
}
