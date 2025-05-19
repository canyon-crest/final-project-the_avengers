import java.awt.*;

public class Player extends Character {
    private boolean left, right, jump;
    private int score;
    private boolean hasBall;
    private String name = "Player";
    private Color color;

    public Player(int x, int y, Color color) {
        super(x, y, 40, 40);
        this.color = color;
    }

    public void setLeft(boolean value) { left = value; }
    public void setRight(boolean value) { right = value; }
    public void setJump(boolean value) {
        if (value && onGround) {
            velocityY = -10;
            onGround = false;
        }
        jump = value;
    }

    public void update() {
        if (left) x -= 5;
        if (right) x += 5;
        applyGravity();
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, width, height);
    }

    public void addScore() { score++; }
    public int getScore() { return score; }
    public boolean hasBall() { return hasBall; }
    public void setHasBall(boolean value) { hasBall = value; }
    public int getX() { return x; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}