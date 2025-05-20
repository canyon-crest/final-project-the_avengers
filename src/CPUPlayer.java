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
            if (x < 1280/2) shootTimer++;

            if (decisionTimer > 30) {

                boolean moveLeft = Math.random() < (double) x/800;
                setLeft(moveLeft);
                setRight(!moveLeft);

                decisionTimer = 0;

            }
            if (x < 500 && x > 400 && shootTimer > 150) {
                if (Math.random() < .05) GamePanel.requestCPUShoot(this);
            }

            if (x < 200) {
                setLeft(false);
                setRight(true);
            } else if (x > 1000) {
                setLeft(true);
                setRight(false);
            }
            
        } else {
            reactionTimer++;
            if (reactionTimer > reactionThreshold) {
                if (opponent.getY() < getY()) {
                    jumpTimer++;
                }
    
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
