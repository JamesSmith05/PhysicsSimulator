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
        eventSolidArea.x = gp.tileSize/8;
        eventSolidArea.y = gp.tileSize/8;
        eventSolidArea.width = gp.tileSize*6/8;
        eventSolidArea.height = gp.tileSize*6/8;

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        eventSolidAreaDefaultX = eventSolidArea.x;
        eventSolidAreaDefaultY = eventSolidArea.y;
        getImage();

    }

    public void update() {
        gp.eHandler.checkEvent(this);
        //allows left and right movement, along with slowing down the box if no button is pressed
        if(gp.keyH.rightPressed){
            rightForce = 1;
        }else if(gp.keyH.leftPressed){
            rightForce = -1;
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
        if(gp.cChecker.collisionUp(this)) {
            if (downForce < 0)
                downForce = 0;
            downVelocity = 0;
        }

        if(gp.keyH.upPressed){
            if (doubleJump && (doubleJumpCounter > 10)){
                downForce = -30;
                doubleJump = false;
            }
        }

        downAcceleration = downForce/mass;

        downVelocity += (int) downAcceleration;

        rightAcceleration = rightForce/mass;
        rightVelocity += (int) rightAcceleration;

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

        int temp;

        if(rightVelocity > 0){
            temp = gp.cChecker.futureCollisionRight(this);
            if(temp != -1){
                rightVelocity = temp;
            }
            if(!gp.cChecker.collisionRight(this)){
                x += rightVelocity;
            }
        }else if(rightVelocity < 0){
            temp = gp.cChecker.futureCollisionLeft(this);
            if(temp != 1){
                rightVelocity = temp;
            }
            if(!gp.cChecker.collisionLeft(this)){
                x += rightVelocity;
            }
        }
        if(downVelocity > 0){
            temp = gp.cChecker.futureCollisionDown(this);
            if(temp != -1){
                downVelocity = temp;
            }
        } else if (downVelocity < 0){
            temp = gp.cChecker.futureCollisionUp(this);
            if(temp != 1){
                downVelocity = temp;
            }
        }

        y += downVelocity;

    }

    public void getImage() {

        image = setup("entities/square", gp.tileSize, gp.tileSize);

    }
}
