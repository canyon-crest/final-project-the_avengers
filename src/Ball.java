import java.awt.*;

public class Ball extends Character {
    private boolean inFlight = false;
    private Player owner;

    public Ball(int x, int y) {
        super(x, y, 20, 20);
    }

    public void shoot(double powerX, double powerY) {
        this.velocityX = powerX;
        this.velocityY = powerY;
        this.inFlight = true;
        this.onGround = false;
    }

    public void update() {
        if (inFlight) {
            x += velocityX;
            y += velocityY;
            velocityY += GRAVITY;
            if (y >= 500 - height) {
                y = 500 - height;
                velocityY = 0;
                inFlight = false;
                onGround = true;
            }
        } else if (owner != null) {
            x = owner.getX() + 10;
            y = owner.y - height;
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.ORANGE);
        g.fillOval(x, y, width, height);
    }

    public void setOwner(Player p) { owner = p; }
    public boolean isInFlight() { return inFlight; }
    public void reset(Player newOwner) {
        owner = newOwner;
        inFlight = false;
        onGround = true;
    }
}
