package entities;

import logic.GamePanel;
import logic.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {

    GamePanel gp;
    public BufferedImage image;
    public Rectangle solidArea = new Rectangle(0, 0, gp.tileSize,gp.tileSize);
    public int solidAreaDefaultX, solidAreaDefaultY;

    public int nForce, sForce, wForce, eForce, nAcceleration, sAcceleration, wAcceleration, eAcceleration, nVelocity, sVelocity, wVelocity, eVelocity, mass;
    public int x,y;

    public Entity(GamePanel gp){
        this.gp = gp;
        solidArea.width = gp.tileSize;
        solidArea.height = gp.tileSize;
    }

    //update position direction and health of entities that don't overide
    public void update() {
        sForce = 9;
        nAcceleration = nForce/mass;
        sAcceleration = sForce/mass;
    }

    public void draw(Graphics2D g2) {
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
