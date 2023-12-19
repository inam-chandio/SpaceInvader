package spaceinvaders;

public class FasterEnemy extends Enemy implements SpaceObject {
    private int fasterSpeed;

    public FasterEnemy(int x, int y, int speedIncrease) {
        super(x, y);
        this.fasterSpeed = speedIncrease;
    }

    @Override
    public void move() {
        // Assuming the move logic is different for FasterEnemy
        setX(getX() - fasterSpeed);
    }
}
