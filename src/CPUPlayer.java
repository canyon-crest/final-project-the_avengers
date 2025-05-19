import java.awt.*;
import java.util.Random;

public class CPUPlayer extends Player {
    private int decisionTimer = 0;
    private int shootTimer = 0;
    private int reactionTimer = 0;
    private final int reactionThreshold = 4;
    private Random random = new Random();

    public CPUPlayer(int x, int y,boolean isPlayer1, GamePanel panel) {
        super(x, y, Color.BLUE,isPlayer1,panel);
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
            reactionTimer++;
            if (reactionTimer > reactionThreshold) {
                if (getX() < opponent.getX()) {
                    setLeft(false);
                    setRight(true);
                } else if (getX() > opponent.getX() + 5){
                    setLeft(true);
                    setRight(false);
                } else {
                    setLeft(false);
                    setRight(false);
                }
                if (random.nextInt(60) == 0) setJump(true);
                else setJump(false);
                reactionTimer = 0;
            }
        }
        super.update();
    }
}
