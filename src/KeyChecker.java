import java.awt.event.*;

public class KeyChecker implements KeyListener {
    private GamePanel panel;

    public KeyChecker(GamePanel panel) {
        this.panel = panel;
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A -> panel.getPlayer1().setLeft(true);
            case KeyEvent.VK_D -> panel.getPlayer1().setRight(true);
            case KeyEvent.VK_W -> panel.getPlayer1().setJump(true);
            case KeyEvent.VK_LEFT -> panel.getPlayer2().setLeft(true);
            case KeyEvent.VK_RIGHT -> panel.getPlayer2().setRight(true);
            case KeyEvent.VK_UP -> panel.getPlayer2().setJump(true);
            case KeyEvent.VK_S -> panel.shootBall(panel.getPlayer1());
            case KeyEvent.VK_DOWN -> panel.shootBall(panel.getPlayer2());
        }
    }

    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A -> panel.getPlayer1().setLeft(false);
            case KeyEvent.VK_D -> panel.getPlayer1().setRight(false);
            case KeyEvent.VK_W -> panel.getPlayer1().setJump(false);
            case KeyEvent.VK_LEFT -> panel.getPlayer2().setLeft(false);
            case KeyEvent.VK_RIGHT -> panel.getPlayer2().setRight(false);
            case KeyEvent.VK_UP -> panel.getPlayer2().setJump(false);
        }
    }

    public void keyTyped(KeyEvent e) {}
}