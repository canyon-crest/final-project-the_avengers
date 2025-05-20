import java.awt.*;

import javax.swing.text.Highlighter.Highlight;

public abstract class Character {
    protected int x, y, width, height;
    protected double velocityX = 0, velocityY = 0;
    protected final double GRAVITY = 0.5;
    protected boolean onGround = true;
    protected GamePanel panel;

    public Character(int x, int y, int width, int height, GamePanel panel) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.panel = panel;
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

    public boolean contains(int X, int Y) {return new Rectangle(x, y, width, height).contains(X, Y);}
    public boolean intersects(Rectangle r) {return new Rectangle(x, y, width, height).intersects(r);}
    public int getY() {return y;}

    public abstract void draw(Graphics g);
    public abstract void update();
}