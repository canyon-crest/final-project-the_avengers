import java.awt.*;
import java.util.Random;

public class CPUPlayer extends Player {
    private int decisionTimer = 0;
    private int shootTimer = 0;
    private int reactionTimer = 0;
    private int jumpTimer = 0;
    private final int reactionThreshold = 10;
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
                if (opponent.getY() < getY()) {
                    jumpTimer++;
                }
                System.out.println(jumpTimer);
    
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
                reactionTimer = 0;

                if (random.nextInt(40) == 0) setJump(true);
                else if (jumpTimer > 0) {setJump(true); jumpTimer = 0;}
                else setJump(false);

            }
        }
        super.update();
    }
}
