package spaceinvaders;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Screen extends JPanel implements ActionListener {
    private Ship playerShip;
    private List<Projectile> bullets;
    private List<Star> stars;
    private Timer enemySpawnTimer;
    private List<SpaceObject> enemies = new ArrayList<>();

    private int playerLives;
    private int currentLevel;

    private static final int MAX_BULLETS = 1000000;
    private static final int HITS_TO_ADVANCE = 3;
    private static final int ENEMY_SPAWN_DELAY = 800;

    private static final int ENEMY_SPEED_INCREASE = 1;
    private static final int FPS = 60;  // Adjust the frame rate as needed
    private Timer gameLoopTimer;
    private boolean shooting;
    private boolean cheatMode;
    private int cheatCodeIndex;
    private final int[] cheatCode = { KeyEvent.VK_O };

    public Screen(Game game) {
    	
        playerShip = new Ship(50, 300, this);
        bullets = new ArrayList<>();
        stars = new ArrayList<>();
        playerLives = 3;
        currentLevel = 1;

        for (int i = 0; i < 100; i++) {
            stars.add(new Star());
        }

        setFocusable(true);
        addKeyListener(new MyKeyListener());

        enemySpawnTimer = new Timer(ENEMY_SPAWN_DELAY, this);
        enemySpawnTimer.start();
        

        gameLoopTimer = new Timer(1000 / FPS, this);
        gameLoopTimer.start();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == enemySpawnTimer) {
            spawnEnemy();
        } else {
            updateGame();
        }
        repaint();
    }

    public void updateEnemies() {
        for (SpaceObject enemy : enemies) {
            enemy.move();
            enemy.draw(this.getGraphics());
        }
    }

    private void updateGame() {
        moveBackground();
        moveProjectiles();
        moveEnemies();
        checkCollisions();
        checkGameOver();
//        checkLevelCompletion();
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBackground(g);
        drawStars(g);
        drawShip(g);
        drawProjectiles(g);
        drawEnemies(g);
        drawText(g);
    }

    private void spawnEnemy() {
        int x = getWidth();
        int y = (int) (Math.random() * getHeight());
        spawnEnemy(x, y);
    }

    private void spawnEnemy(int x, int y) {
        if (currentLevel == 1) {
            enemies.add(new Enemy(x, y));
        } else if (currentLevel == 2) {
            enemies.add(new FasterEnemy(x, y, ENEMY_SPEED_INCREASE));
        }
    }

    private void moveBackground() {
        for (Star star : stars) {
            star.move();
        }
    }

    private void moveProjectiles() {
        Iterator<Projectile> iterator = bullets.iterator();
        while (iterator.hasNext()) {
            Projectile bullet = iterator.next();
            bullet.move();
            if (bullet.getX() > getWidth()) {
                iterator.remove();
            }
        }
    }

    private void moveEnemies() {
        Iterator<SpaceObject> iterator = enemies.iterator();
        while (iterator.hasNext()) {
            Enemy enemy = (Enemy) iterator.next();
            enemy.move();
            if (enemy.getX() < 0) {
                iterator.remove();
                playerLives--;
            }
        }
    }

    private void drawBackground(Graphics g) {
        if (currentLevel == 1) {
            g.setColor(Color.BLACK);
        } else if (currentLevel == 2) {
            g.setColor(Color.DARK_GRAY);
        }
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    private void drawStars(Graphics g) {
        for (Star star : stars) {
            star.drawMe(g);
        }
    }

    private void drawShip(Graphics g) {
        playerShip.drawMe(g);
    }

    private void drawProjectiles(Graphics g) {
        for (Projectile bullet : bullets) {
            bullet.drawMe(g);
        }
    }

    private void drawEnemies(Graphics g) {
        for (SpaceObject spaceObject : enemies) {
            spaceObject.draw(g);
        }
    }

    private void drawText(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawString("Lives: " + playerLives, 10, 20);
        g.drawString("Level: " + currentLevel, 10, 40);
    }

    private void checkCollisions() {
        Iterator<Projectile> projectileIterator = bullets.iterator();
        while (projectileIterator.hasNext()) {
            Projectile projectile = projectileIterator.next();

            Iterator<SpaceObject> enemyIterator = enemies.iterator();
            while (enemyIterator.hasNext()) {
                SpaceObject enemy = enemyIterator.next();

                if (projectile.collidesWith(enemy)) {
                    handleProjectileEnemyCollision(projectile, enemy, projectileIterator, enemyIterator);
                }
            }
        }

        for (SpaceObject enemy : enemies) {
            if (playerShip.collidesWith(enemy)) {
                playerLives--;
                enemy.setVisible(false);
            }
        }
    }

    private void handleProjectileEnemyCollision(Projectile projectile, SpaceObject enemy,
                                               Iterator<Projectile> projectileIterator, Iterator<SpaceObject> enemyIterator) {
        projectileIterator.remove();
        enemy.setVisible(false);
    }

    private void checkGameOver() {
        if (playerLives <= 0) {
            resetGame();
        }
    }

    private void resetGame() {
        playerLives = 3;
        currentLevel = 1;
        resetLevel();
    }

    private void resetLevel() {
        bullets.clear();
        enemies.clear();
        initializeLevel(currentLevel);
    }

    private void initializeLevel(int level) {
        if (level == 1) {
            initializeLevelOne();
        } else if (level == 2) {
            initializeLevelTwo();
        }
    }

    private void initializeLevelOne() {
        for (int i = 0; i < 5; i++) {
            spawnEnemy();
        }
    }

    private void initializeLevelTwo() {
        for (int i = 0; i < 8; i++) {
            spawnEnemy();
        }
    }

    private class MyKeyListener extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();

            if (keyCode == KeyEvent.VK_UP) {
                playerShip.moveUp();
            } else if (keyCode == KeyEvent.VK_DOWN) {
                playerShip.moveDown();
            } else if (keyCode == KeyEvent.VK_SPACE) {
                shoot();
            }

            if (checkCheatCode(keyCode)) {
                handleCheatCode();
            }
        }

        private boolean checkCheatCode(int keyCode) {
            return keyCode == cheatCode[cheatCodeIndex++];
        }

        private void handleCheatCode() {
            if (cheatCodeIndex == cheatCode.length) {
                cheatMode = true;
                JOptionPane.showMessageDialog(Screen.this, "Cheat mode activated!");
            }
        }

        public void keyReleased(KeyEvent e) {
            int keyCode = e.getKeyCode();

            if (keyCode == KeyEvent.VK_SPACE) {
                shooting = false;
            }
        }
    }

    public void shoot() {
        int bulletCount=0;
		if (bulletCount < MAX_BULLETS) {
            bullets.add(new Projectile(playerShip.getX() + playerShip.getWidth(),
                    playerShip.getY() + playerShip.getHeight() / 2));
            bulletCount++;
        }
    }

    public void moveShipUp() {
        playerShip.moveUp();
    }

    public void moveShipDown() {
        playerShip.moveDown();
    }

    public void cheat() {
        if (cheatMode) {
            JOptionPane.showMessageDialog(this, "Cheaters never prosper!");
            resetGame();
        }
    }
}
