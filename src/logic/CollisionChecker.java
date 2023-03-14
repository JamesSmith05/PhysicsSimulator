package logic;

import entities.Entity;

public class CollisionChecker {
    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public boolean collisionDown (Entity entity){

        int bottomLeftX = entity.x + entity.solidArea.x;
        int bottomRightX = entity.x + entity.solidArea.x + entity.solidArea.width;
        int bottomY = entity.y + entity.solidArea.y + entity.solidArea.height;

        if (bottomY>=gp.screenHeight){
            entity.y = gp.screenHeight - entity.solidArea.height;
            return true;
        }
        else return false;
    }
}
