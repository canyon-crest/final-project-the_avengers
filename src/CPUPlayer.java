import java.awt.*;
import java.util.Random;

public class CPUPlayer extends Player {
    private int decisionTimer = 0;
    private int shootTimer = 0;
    private int reactionTimer = 0;
    private int jumpTimer = 0;
    private int randJump;
    private int shootThreshold = (int) Math.random() * 100;
    private final int reactionThreshold = 10;
    private Random random = new Random();

    public CPUPlayer(int x, int y,boolean isPlayer1, GamePanel panel) {
        super(x, y, Color.BLUE,isPlayer1,panel);
        setName("CPU");
    }

    @Override
    public void reset() {
        super.reset();
        shootTimer = 0;
        shootThreshold = (int) Math.random() * 100;
    }
// how the ai decides when to move, jump, shoot
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
            if (x < 500 && x > 100 && shootTimer > shootThreshold) {
                if (Math.random() < .02) GamePanel.requestCPUShoot(this);
            }

            if (x < 200) {
                setLeft(false);
                setRight(true);
            } else if (x > 1000) {
                setLeft(true);
                setRight(false);
            }
            
        } else if (opponent.hasBall()){
            reactionTimer++;
            if (reactionTimer > reactionThreshold) {
                if (opponent.getY() < getY()) {
                    jumpTimer++;
                }
    
                if (getX() < opponent.getX()+ 50) {
                    setLeft(false);
                    setRight(true);
                } else if (getX() > opponent.getX() + 55){
                    setLeft(true);
                    setRight(false);
                } else {
                    setLeft(false);
                    setRight(false);
                }
                reactionTimer = 0;

                if (getX() < 1280/2) {
                    randJump = 40;
                }
                 else {
                    randJump = 5;
                }
                
                if (random.nextInt(randJump) == 0) setJump(true);
                else if (jumpTimer > 0) {setJump(true); jumpTimer = 0;}
                else setJump(false);

            }

            if(ball.getY() < opponent.getY()-100) {setJump(true); jumpTimer = 0;}
        }
        else {
            reactionTimer++;
            if (reactionTimer > reactionThreshold) {
                if (getX() < ball.x) {
                    setLeft(false);
                    setRight(true);
                } else {
                    setLeft(true);
                    setRight(false);
                }
                reactionTimer = 0;

            }

        }
        super.update();
    }
}
