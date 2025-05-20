import java.awt.*;

public class Ball extends Character {
    private boolean inFlight = false;
    private Player owner;

    public Ball(int x, int y,GamePanel panel) {
        super(x, y, 25, 25,panel);
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
            if (y >= 550 - height) {
                y = 550 - height;
                velocityY = (1.3/3 * (-velocityY));
                velocityX = (2.0/3 * (velocityX));

                if (Math.abs(velocityY) < 1.2) {
                    velocityY=0;
                }
                //inFlight = false;
                //onGround = true;
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
    public Player getOwner() {return owner;}
    public boolean isInFlight() { return inFlight; }
    public void reset(Player newOwner) {
        owner = newOwner;
        inFlight = false;
        onGround = true;
    }
}
