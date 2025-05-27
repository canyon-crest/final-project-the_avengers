import javax.swing.*;

public class SuddenDeath implements GameMode {
// when a player scores it automatically finishes the game
    public void onScore(GamePanel panel, Player scorer) {
        JOptionPane.showMessageDialog(panel, "" + (scorer.getName()) + " wins (Sudden Death)!");
        System.exit(0);
    }
    public void onShoot(Ball ball, Player shooter) {}
}