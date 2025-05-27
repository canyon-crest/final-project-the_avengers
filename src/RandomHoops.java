import java.util.Random;

public class RandomHoops implements GameMode {
    private Random random = new Random();

 // changes hoop height every time someone scores
    public void onScore(GamePanel panel, Player scorer) {
        int newY = 150 + random.nextInt(200);
        if (scorer == panel.getPlayer1()) {
            panel.getRightHoop().y = newY;
        } else {
            panel.getLeftHoop().y = newY;
        }
    }
    public void onShoot(Ball ball, Player shooter) {}
}
