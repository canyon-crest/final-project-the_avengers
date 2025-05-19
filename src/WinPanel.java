import javax.swing.*;
import java.awt.*;

public class WinPanel extends JPanel {
    public WinPanel(MainContainer container, String winner, int time, double accuracy) {
        setLayout(null);
        setBackground(Color.DARK_GRAY);

        JLabel winText = new JLabel(winner + " Wins!", SwingConstants.CENTER);
        winText.setFont(new Font("Arial", Font.BOLD, 36));
        winText.setForeground(Color.WHITE);
        winText.setBounds(200, 50, 400, 50);
        add(winText);

        JLabel timeText = new JLabel("Time Elapsed: " + time + "s", SwingConstants.CENTER);
        timeText.setFont(new Font("Arial", Font.PLAIN, 24));
        timeText.setForeground(Color.WHITE);
        timeText.setBounds(200, 120, 400, 40);
        add(timeText);

        JLabel accText = new JLabel("Shot Accuracy: " + (int)accuracy + "%", SwingConstants.CENTER);
        accText.setFont(new Font("Arial", Font.PLAIN, 24));
        accText.setForeground(Color.WHITE);
        accText.setBounds(200, 170, 400, 40);
        add(accText);

        JButton cpuButton = new JButton("CPU");
        JButton pvpButton = new JButton("2 Players");
        JComboBox<String> modeBox = new JComboBox<>(new String[]{"None", "SniperShots", "SuddenDeath", "RandomHoops"});

        cpuButton.setBounds(300, 250, 200, 40);
        pvpButton.setBounds(300, 300, 200, 40);
        modeBox.setBounds(300, 350, 200, 40);

        add(cpuButton);
        add(pvpButton);
        add(modeBox);

        cpuButton.addActionListener(e -> container.startGame(true, getSelectedMode(modeBox)));
        pvpButton.addActionListener(e -> container.startGame(false, getSelectedMode(modeBox)));
    }

    private GameMode getSelectedMode(JComboBox<String> box) {
    	String selected = (String) box.getSelectedItem();
    	if ("SniperShots".equals(selected)) return new SniperShots();
    	else if ("SuddenDeath".equals(selected)) return new SuddenDeath();
    	else if ("RandomHoops".equals(selected)) return new RandomHoops();
    	else return new GameMode() {
    	    public void onScore(GamePanel panel, Player scorer) {}
    	    public void onShoot(Ball ball, Player shooter) {}
    	};
    }
}
