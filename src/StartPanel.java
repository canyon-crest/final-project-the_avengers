import javax.swing.*;
import java.awt.*;

public class StartPanel extends JPanel {
    public StartPanel(MainContainer container) {
        setLayout(null);
        setBackground(Color.LIGHT_GRAY);

        JLabel title = new JLabel("Choose Mode", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 32));
        title.setBounds(250, 50, 300, 50);
        add(title);

        JButton cpuButton = new JButton("CPU");
        JButton pvpButton = new JButton("2 Players");

        cpuButton.setBounds(300, 150, 200, 40);
        pvpButton.setBounds(300, 200, 200, 40);

        String[] modifiers = {"None", "SniperShots", "SuddenDeath", "RandomHoops"};
        JComboBox<String> modeBox = new JComboBox<>(modifiers);
        modeBox.setBounds(300, 250, 200, 40);

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
