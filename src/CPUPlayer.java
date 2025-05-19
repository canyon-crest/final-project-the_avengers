import java.awt.*;
import java.util.Random;

public class CPUPlayer extends Player {
    private int decisionTimer = 0;
    private int shootTimer = 0;
    private Random random = new Random();

    public CPUPlayer(int x, int y) {
        super(x, y, Color.BLUE);
        setName("CPU");
    }

    public void updateAI(Player opponent, Ball ball) {
        if (hasBall()) {
            decisionTimer++;
            shootTimer++;
            if (decisionTimer > 30) {
                boolean moveLeft = random.nextBoolean();
                setLeft(moveLeft);
                setRight(!moveLeft);
                decisionTimer = 0;
            }
            if (shootTimer > 120) {
                shootTimer = 0;
                GamePanel.requestCPUShoot(this);
            }
        } else {
            if (getX() < opponent.getX()) {
                setLeft(false);
                setRight(true);
            } else {
                setLeft(true);
                setRight(false);
            }
            if (random.nextInt(60) == 0) setJump(true);
            else setJump(false);
        }
        super.update();
    }
}
