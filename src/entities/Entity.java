package entities;

import logic.GamePanel;
import logic.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {

    GamePanel gp;
    public BufferedImage image;
    public Rectangle solidArea = new Rectangle(0, 0, 0, 0);
    public int solidAreaDefaultX, solidAreaDefaultY;

    public int downForce, rightForce, downAcceleration, rightAcceleration, downVelocity, rightVelocity, mass;
    public int vMax = 15;
    public int x,y;
    public boolean doubleJump = true;
    public int doubleJumpCounter;

    public Entity(GamePanel gp){
        this.gp = gp;
        solidArea.width = gp.tileSize;
        solidArea.height = gp.tileSize;
    }

    //update position direction and health of entities that don't overide
    public void update() {
        if(gp.cChecker.collisionDown(this)){
            downVelocity = 0;
        }else{
            downForce = 1;
        }
        downAcceleration = downForce/mass;
        downVelocity += downAcceleration;

        rightAcceleration = rightForce/mass;
        rightVelocity += rightAcceleration;

        if (downVelocity > vMax ){
            downVelocity = vMax;
        }
        if (-downVelocity > vMax){
            downVelocity = -vMax;
        }

        if (rightVelocity > vMax ){
            rightVelocity = vMax;
        }
        if (-rightVelocity > vMax){
            rightVelocity = -vMax;
        }

        x += rightVelocity;

        y += downVelocity;

    }

    public void draw(Graphics2D g2) {

        g2.drawImage(image, x, y,null);
    }

    public void changeOpacity(Graphics2D g2, Float alphaValue){
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alphaValue));
    }

    public BufferedImage setup(String imagePath,int width,int height) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/resources/" + imagePath + ".png"));
            image = uTool.scaleImage(image, width, height);

        } catch (Exception e) {
        }
        return image;
    }

}
