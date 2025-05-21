import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel implements ActionListener {
    private static GamePanel activeInstance;
    private MainContainer container;
    private Timer gameTimer;
    private final int FPS = 60;

    private Player player1;
    private Player player2;
    private Player currentPlayer; 
    private Player offPlayer;
    private Ball ball;
    private boolean cpuMode;
    private GameMode gameMode;

    private Rectangle leftHoop;
    private Rectangle rightHoop;

    private double powerX;
    private double powerY;

    private int shotsTaken = 0;
    private int shotsMade = 0;
    private long startTime;

    private Font scoreFont = new Font("Arial", Font.BOLD, 28);
    private BufferedImage ctImage;

    public GamePanel(MainContainer container, boolean cpuMode, GameMode mode) {
        this.container = container;
        this.cpuMode = cpuMode;
        this.gameMode = mode;
        
        powerX = .4;
        powerY = 0;

        activeInstance = this;

        try {
            ctImage = ImageIO.read(Character.class.getResource("/assets/backgrounds/court.png"));
            }   
        catch (Exception e) {
            ctImage = null;
        }

        setPreferredSize(new Dimension(1280, 720));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(new KeyChecker(this));

        player1 = new Player(100, 400, Color.red,true,this);
        player1.setName("Player 1");

        if (cpuMode) {
            player2 = new CPUPlayer(1280-100, 400,false,this);
        } else {
            player2 = new Player(1280-100-54, 400, Color.blue,false,this);
            player2.setName("Player 2");
        }
        currentPlayer = player1;
        offPlayer = player2;

        ball = new Ball(0, 0,this);
        ball.setOwner(player1);
        player1.setHasBall(true);

        leftHoop = new Rectangle(100, 300, 30, 10);
        rightHoop = new Rectangle(1280-100-30, 300, 30, 10);

        gameTimer = new Timer(1000 / FPS, this);
        gameTimer.start();
        startTime = System.currentTimeMillis();
    }

    public void actionPerformed(ActionEvent e) {
        update();
        repaint();
    }

    public void update() {
        player1.update();
        if (cpuMode && player2 instanceof CPUPlayer cpu) {
            cpu.updateAI(player1, ball);
        } else {
            player2.update();
        }
        ball.update();
        if (ball.x < 0 || ball.x + ball.width > 1280) {
            ball.velocityX = -ball.velocityX;
        }
        if (ball.y + ball.height >= 550) {
            //Player next = currentPlayer.equals(player1) ? player2 : player1;
            //resetPlay(next,true);
        }
        checkScore();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(ctImage, 0, 100,1280,720, null);
        g.setColor(Color.WHITE);

        g.fillRect(leftHoop.x, leftHoop.y, leftHoop.width, leftHoop.height);
        g.fillRect(rightHoop.x, rightHoop.y, rightHoop.width, rightHoop.height);

        player1.draw(g);
        player2.draw(g);
        ball.draw(g);

        g.setFont(scoreFont);
        g.setColor(Color.YELLOW);
        g.drawString("P1: " + player1.getScore(), 50, 50);
        g.drawString(cpuMode ? "CPU: " + player2.getScore() : "P2: " + player2.getScore(), 1280-150, 50);
    }

    public void startShootBall(Player shooter) {
        if (ball.isInFlight() || !shooter.hasBall()) return;

        powerX *= 10;                
    }

    public void shootBall(Player shooter) {
        if (ball.isInFlight()) return;

        if (shooter.hasBall()) {

        shooter.setHasBall(false);
        currentPlayer = shooter;
        ball.setOwner(null);
        shotsTaken++;
        //double distance = (shooter == player1) ? (rightHoop.getCenterX() - player1.getX()) : (leftHoop.getCenterX() - player2.getX());
        //double powerX = (Math.abs(distance/58) > 10) ? (int) (Math.abs(distance)/distance) * 10 : distance/58;

        if (shooter instanceof CPUPlayer) {
            double distance = (shooter == player1) ? (rightHoop.getCenterX() - player1.getX()) : (leftHoop.getCenterX() - player2.getX());
            powerX = (Math.abs(distance/58) > 10) ? (int) (Math.abs(distance)/distance) * 10 : distance/58;
            powerY = -13.5 + ((shooter == player1) ? (rightHoop.getCenterY() - player1.getY())/50 : (leftHoop.getCenterY() - player2.getY())/50);

        }
        else {
            powerX = (Math.abs(powerX) > 20) ? 10 : powerX;
            powerX = (shooter == player1) ? powerX : -powerX;
            powerY = -13.5 + ((shooter == player1) ? (rightHoop.getCenterY() - player1.getY())/50 : (leftHoop.getCenterY() - player2.getY())/50);
            
        }
        
        //double powerY = -13.5 + ((shooter == player1) ? (rightHoop.getCenterY() - player1.getY())/50 : (leftHoop.getCenterY() - player2.getY())/50);

        ball.shoot(powerX, powerY);
        powerX = .4;
        powerY = 0;
        gameMode.onShoot(ball, shooter);
        }
        // else if (player1.intersects(player2)){
        //     offPlayer.setHasBall(false);
        //     currentPlayer = shooter;
        //     ball.setOwner(null);
        //     ball.shoot((player1.x < player2.x) ? -10 : 10, -4);
        //     gameMode.onShoot(ball, shooter);

        // }
    }

    public void checkScore() {
        if (!ball.isInFlight()) return;

        if (offPlayer.intersects(ball) && ball.velocityY <= 0) {
            resetPlay(offPlayer,false);
            currentPlayer.setBlock(true);
        }
        else if (player1.intersects(ball) && ball.getY() >= player1.getStartY()) {
            resetPlay(player1, false);
            player1.setBlock(true);
        }
        else if (player2.intersects(ball) && ball.getY() >= player2.getStartY()) {
            resetPlay(player2, false);
            player2.setBlock(true);

        }
        else if (ball.intersects(leftHoop)) {
            player2.addScore();
            shotsMade++;
            gameMode.onScore(this, player2);
            resetPlay(player1,true);
        } 
        else if (ball.intersects(rightHoop)) {
            player1.addScore();
            shotsMade++;
            gameMode.onScore(this, player1);
            resetPlay(player2,true);
        }
        else if (ball.getY() >= 550) {
        	resetPlay(offPlayer,true);
        	
        }
    }

    public void resetPlay(Player nextPossession,boolean resetPlayers) {
        if (resetPlayers) {
            player1.reset();
            player2.reset();
        }
        ball.reset(nextPossession);
        nextPossession.setHasBall(true);
        ball.x = nextPossession.x + nextPossession.width / 2 - ball.width / 2;
        ball.y = nextPossession.y - ball.height;

        if (currentPlayer.getScore() >= 5) {
            int time = (int)((System.currentTimeMillis() - startTime) / 1000);
            double accuracy = shotsTaken == 0 ? 0 : (shotsMade * 100.0 / shotsTaken);
            container.showWinScreen(currentPlayer.getName(), time, accuracy);
        }
        if(currentPlayer == player1) {
        	currentPlayer = player2;
            offPlayer = player1;
        }
        else {
        	currentPlayer = player1;
            offPlayer = player2;
        }

    }

    public Player getPlayer1() { return player1; }
    public Player getPlayer2() { return player2; }
    public Rectangle getLeftHoop() { return leftHoop; }
    public Rectangle getRightHoop() { return rightHoop; }

    public static void requestCPUShoot(Player cpu) {
        EventQueue.invokeLater(() -> {
            GamePanel panel = getActiveInstance();
            if (panel != null) {
                panel.shootBall(cpu);
            }
        });
    }

    public static GamePanel getActiveInstance() {
        return activeInstance;
    }
}