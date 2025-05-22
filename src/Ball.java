import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Ball extends Character {
    private boolean inFlight = false;
    private Player owner;
    private int index;
    private int pause;
    private boolean dribble;
    private int dInd;
    private boolean reDrib;
    private Image[] framesOriginal;
    private Image[] framesScaled;
    private int rInd;
    private boolean isRotating;
    private int vR;
    private int lastDeg;
    private int yNew;
    private boolean slowDown;
    private int slowDownI;
    private boolean slowDownable;

    public Ball(int x, int y, GamePanel panel) {
        super(x, y, 40, 40, panel);
        framesOriginal = new Image[14];
        framesScaled = new Image[14];
        dribble = false;
        dInd = 0;
        reDrib = false;
        rInd = 0;
        isRotating = false;
        vR = (int) (Math.random() * 31 - 15);
        lastDeg = 0;
        index = 0;
        pause = 0;
        yNew = y;
        slowDown = false;
        slowDownI = 0;
        slowDownable = true;
        for (int i = 0; i <= 13; i++) {
            try {
				framesOriginal[i] = ImageIO.read(getClass().getResourceAsStream("/res/ball/b" + i + ".png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
            framesScaled[i] = framesOriginal[i].getScaledInstance(250, 250, Image.SCALE_SMOOTH);
        }
    }

    public void shoot(double powerX, double powerY) {
        this.velocityX = powerX;
        this.velocityY = powerY;
        this.inFlight = true;
        this.onGround = false;
        lastDeg = rInd*vR+lastDeg;
        vR = (int) (Math.random() * 51 - 25);
        isRotating = true;
        rInd = 0;
    }

    public void update() {
        if (inFlight) {
            x += velocityX;
            y += velocityY;
            velocityY += GRAVITY;
            if (y >= 550 - height) {
                y = 550 - height;
                velocityY = (1.3/3 * (-velocityY));
                velocityX = (2.0/3 * (velocityX));

                if (Math.abs(velocityY) < 1.2) {
                    if (slowDownable) {
                        slowDown = true;
                        slowDownable = false;
                    }
                    velocityY=0;
                    // inFlight = false;
                    // onGround = true;
                }
            }
        } else if (owner != null) {
            if (owner.getDirection().equals("right")) {
                x = owner.getX() + 45;
            }
            else if (owner.getDirection().equals("left")) {
                x = owner.getX() - 30;
            }
            else {
                x = owner.getX() + 8;
            }
            if (onGround && !owner.onGround) {
                x = owner.getX() + 8;
            }
            y = owner.y;
            isRotating = false;
            slowDown = false;
            slowDownI = 0;
            slowDownable = true;
        }

        if (slowDown) {
            double negV = -vR/30.0;
            lastDeg += negV*slowDownI;
            slowDownI++;
            if (Math.abs(negV*slowDownI) >= Math.abs(vR)) {
                slowDown = false;
                slowDownable = false;
                slowDownI = 0;
                isRotating = false;
                velocityX = 0;
            }
        }
    }

    public void dribble() {
        if (!dribble) {
            dInd = 0;
        }
        // else if (dInd > 15) {
        //     reDrib = true;
        // }
        dribble = true;
    }

    public void draw(Graphics g) {
    	BufferedImage newImg;
        if (onGround && owner.onGround) {
            yNew = y + 70;
        }
        else {
            yNew = y - height;
        }
        
        if (pause == 0) {
            BufferedImage image = new BufferedImage(framesScaled[index].getWidth(null), framesScaled[index].getHeight(null), BufferedImage.TYPE_INT_ARGB);
            image.getGraphics().drawImage(framesScaled[index], 0, 0, null);
            
            int wid = image.getWidth();
            int hei = image.getHeight();
            newImg = new BufferedImage(wid, hei, image.getType());
            Graphics2D gNew = newImg.createGraphics();
            gNew.rotate(Math.toRadians(rInd*vR+lastDeg), wid/2, hei/2);
            gNew.drawImage(image, null, 0, 0);

            index++;
            if (index > 13) {
                index = 0;
                pause = 80;
            }
        }
        else {
            pause--;

            BufferedImage image = new BufferedImage(framesScaled[0].getWidth(null), framesScaled[0].getHeight(null), BufferedImage.TYPE_INT_ARGB);
            image.getGraphics().drawImage(framesScaled[0], 0, 0, null);

            int wid = image.getWidth();
            int hei = image.getHeight();
            newImg = new BufferedImage(wid, hei, image.getType());
            Graphics2D gNew = newImg.createGraphics();
            gNew.rotate(Math.toRadians(rInd*vR+lastDeg), wid/2, hei/2);
            gNew.drawImage(image, null, 0, 0);
        }
        if (isRotating) {
            rInd++;
        }

        if (dribble && onGround) {
            int dInv = 20 - dInd;
            if (!owner.onGround) {
                isRotating = false;
                dInd += 5;
            }
            if (dInd == 3) {
                lastDeg = rInd*vR+lastDeg;
                vR = (int) (Math.random() * 31 - 15);
                isRotating = true;
                rInd = 0;
            }
            else if (dInd == 18) {
                isRotating = false;
            }
            if (dInd > 20) {
                if (reDrib) {
                    reDrib = false;
                    dInd = 0;
                    g.drawImage(newImg, x, yNew, 40, 40, null);
                }
                else {
                    dribble = false;
                    g.drawImage(newImg, x, yNew, 40, 40, null);
                }
            }
            else if (dInd < 8) {
                g.drawImage(newImg, x, yNew+(dInd*dInd*dInd*15/300*dInd*dInd/20), 40, 40, null);
            }
            else if (dInd == 8 || dInd == 12) {
                g.drawImage(newImg, x, yNew+(49*49*11/600+15), 40, 35, null);
            }
            else if (dInd == 9 || dInd == 11) {
                g.drawImage(newImg, x, yNew+(49*49*11/600+30), 40, 30, null);
            }
            else if (dInd == 10) {
                g.drawImage(newImg, x, yNew+(49*49*11/600+50), 40, 30, null);
            }
            else {
                g.drawImage(newImg, x, yNew+((dInv*dInv*dInv*15)/300*(dInv*dInv)/20), 40, 40, null);
            }
            dInd++;
            return;
        }

        g.drawImage(newImg, x, yNew, 40, 40, null);
    }

    public void setOwner(Player p) { owner = p; }
    public Player getOwner() {return owner;}
    public boolean isInFlight() { return inFlight; }
    public void reset(Player newOwner) {
        owner = newOwner;
        inFlight = false;
        onGround = true;
        isRotating = false;
        dInd = 0;
        dribble = false;
    }
    public boolean intersects(Character c) {return new Rectangle(x, y-50, width, height).intersects(new Rectangle(c.x, c.y, c.width, c.height));}
    public boolean intersects(Rectangle r) {return new Rectangle(x, y-50, width, height).intersects(r);}
}
