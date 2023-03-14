package entities;

import logic.GamePanel;

public class Box extends Entity{
    public Box(GamePanel gp) {
        super(gp);
        mass = 1;
        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = gp.tileSize;
        solidArea.height = gp.tileSize;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        getImage();

    }

    public void update() {
        if(gp.keyH.rightPressed){
            rightForce = 2;
        }else if(gp.keyH.leftPressed){
            rightForce = -2;
        } else if(rightVelocity > 0){
            rightForce = -1;
        }else if(rightVelocity < 0){
            rightForce = 1;
        }else {
            rightForce = 0;
        }
        if(gp.keyH.upPressed){
            if(gp.cChecker.collisionDown(this)){
                doubleJump = true;
                doubleJumpCounter = 0;
                downForce = -20;
            }
            else{
                downForce = 0;
            }
        }

        if(gp.cChecker.collisionDown(this)){
            if(downForce>0)
                downForce=0;
            downVelocity = 0;
        }else{
            downForce = 1;
            doubleJumpCounter ++;
        }

        if(gp.keyH.upPressed){
            if (doubleJump && (doubleJumpCounter > 10)){
                System.out.println("not jumping");
                downForce = -30;
                doubleJump = false;
            }
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

    public void getImage() {

        image = setup("square", gp.tileSize, gp.tileSize);

    }
}
