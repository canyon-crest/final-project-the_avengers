import java.awt.*;

public abstract class Character {
    protected int x, y, width, height;
    protected double velocityX = 0, velocityY = 0;
    protected final double GRAVITY = 0.5;
    protected boolean onGround = true;

    public Character(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void applyGravity() {
        if (!onGround) {
            velocityY += GRAVITY;
            y += velocityY;
            if (y >= 460) {
                y = 460;
                velocityY = 0;
                onGround = true;
            }
        }
    }

    public abstract void draw(Graphics g);
    public abstract void update();
}