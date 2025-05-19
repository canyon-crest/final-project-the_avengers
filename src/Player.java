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

    private static final String[][] imageKeys = {{"left","forward","right"},{"neutral","up"}};
    private HashMap<String,BufferedImage> imageMap;

    private BufferedImage playerImage;

    public Player(int x, int y, Color color, boolean isPlayer1) {
        super(x, y, 18*2, 54*2);
        this.color = color;

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

    public void updateImage() {
        String direction;
        String level;

        if (velocityX < 0) {
            direction = imageKeys[0][0];
        }
        else if (velocityX > 0) {
            direction = imageKeys[0][2];
        }
        else {
            direction = imageKeys[0][1];
        }

        if (velocityY < 0) {
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
            velocityY = -10;
            onGround = false;
        }
        jump = value;
    }

    public void update() {
        if (left) x -= 5;
        if (right) x += 5;
        applyGravity();
    }

    public void draw(Graphics g) {
        g.drawImage(playerImage, x,y,width,height, null);
    }

    @Override
    public void applyGravity() {
        if (!onGround) {
            velocityY += GRAVITY;
            y += velocityY;
            if (y >= 300) {
                y = 300;
                velocityY = 0;
                onGround = true;
            }
        }
    }

    public void addScore() { score++; }
    public int getScore() { return score; }
    public boolean hasBall() { return hasBall; }
    public void setHasBall(boolean value) { hasBall = value; }
    public int getX() { return x; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}