import javax.swing.*;
import java.awt.*;

public class MainContainer extends JFrame {
    private CardLayout layout;
    private JPanel mainPanel;

    private StartPanel startPanel;
    private GamePanel gamePanel;
    private WinPanel winPanel;

    public MainContainer() {
        setTitle("Basketball Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setSize(800, 600);

        layout = new CardLayout();
        mainPanel = new JPanel(layout);

        startPanel = new StartPanel(this);
        mainPanel.add(startPanel, "Start");

        add(mainPanel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void startGame(boolean cpuMode, GameMode modifier) {
        gamePanel = new GamePanel(this, cpuMode, modifier);
        mainPanel.add(gamePanel, "Game");
        layout.show(mainPanel, "Game");
    }

    public void showWinScreen(String winner, int time, double accuracy) {
        winPanel = new WinPanel(this, winner, time, accuracy);
        mainPanel.add(winPanel, "Win");
        layout.show(mainPanel, "Win");
    }

    public void goToStart() {
        layout.show(mainPanel, "Start");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainContainer::new);
    }
}