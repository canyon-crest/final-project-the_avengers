import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class Player extends Character {
    private boolean left, right, jump;
    private int score;
    private boolean hasBall;
    private String name = "Player";
    private Color color;
    private int startX, startY;
    private boolean block;

    private static final String[][] imageKeys = {{"left","forward","right"},{"neutral","up"}};
    private HashMap<String,BufferedImage> imageMap;

    private BufferedImage playerImage;

    public Player(int x, int y, Color color, boolean isPlayer1, GamePanel panel) {
        super(x, y, 18*3, 54*3,panel);
        startX = x;
        startY = y;
        this.color = color;
        block = false;

        imageMap = new HashMap<>();
        initImages(isPlayer1 ? "lebron" : "steph");
    }

    private void initImages(String folder) {
        for (String direction : imageKeys[0]) {
            for (String level : imageKeys[1]) {
                imageMap.put(direction + "-" + level, loadImage(folder + "/" + direction + "-" + level));
            }
        }
        playerImage = imageMap.get("forward-neutral");
    }

    public static BufferedImage loadImage(String name) {
        BufferedImage image;

        try {
            image = ImageIO.read(Character.class.getResource("/assets/characters/" + name + ".png"));
            }   
        catch (Exception e) {
            image = null;
        }

        return image;
    }
// updating the players image based on their direction, jumping or not, has the ball, etc.
    public void updateImage() {
        String direction;
        String level;

        if (left) {
            direction = imageKeys[0][0];
        }
        else if (right) {
            direction = imageKeys[0][2];
        }
        else {
            direction = imageKeys[0][1];
        }

        if (!onGround) {
            level = imageKeys[1][1];
        }
        else {
            level = imageKeys[1][0];
        }

        playerImage = imageMap.get(direction + "-" + level);
    }

    public void setLeft(boolean value) { left = value; }
    public void setRight(boolean value) { right = value; }
    public void setJump(boolean value) {
        if (value && onGround) {
            velocityY = -15;
            onGround = false;
        }
        jump = value;
    }

    public boolean getJump() {
        return jump;
    }

    public void update() {
        if (left && x > 0) x -= 8;
        if (right && x < 1280-54) x += 8;
        applyGravity();
        updateImage();
    }

    public void reset() {
        x = startX;
        y = startY;
        onGround = true;
    }

    public void draw(Graphics g) {
        g.drawImage(playerImage, x,y,width,height, null);
    }

    @Override
    public void applyGravity() {
        if (!onGround) {
            velocityY += GRAVITY;
            y += velocityY;
            if (y >= 400) {
                y = 400;
                velocityY = 0;
                onGround = true;
                if (!block) panel.shootBall(this);
            }
        } else {
            block = false;
        }
    }

    public void addScore() { score++; }
    public int getScore() { return score; }
    public void setScore(int score) {this.score = score;}
    public boolean hasBall() { return hasBall; }
    public void setHasBall(boolean value) { hasBall = value; }
    public void setBlock(boolean block) {this.block = block;}
    public int getX() { return x; }
    
    public int getY() { return y; }
    public int getStartY() { return startY; }
    public String getDirection() {
        if (left) { return "left"; }
        if (right) { return "right"; }
        return "jump";
    }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
