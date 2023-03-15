package logic;

import entities.Entity;

public class CollisionChecker {
    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public boolean collisionDown (Entity entity){

        int leftX = entity.x + entity.solidArea.x +1; //single offset so that only the square is checked, not the corners, that leak into the next tile
        int rightX = entity.x + entity.solidArea.x + entity.solidArea.width -1; //single offset so that only the square is checked, not the corners, that leak into the next tile
        int bottomY = entity.y + entity.solidArea.y + entity.solidArea.height;

        if (bottomY>=gp.screenHeight) {
            entity.y =  gp.screenHeight - entity.solidArea.y - entity.solidArea.height;
            entity.downVelocity = 0;
            return true;
        }

        int bottomLeftCol = leftX/gp.tileSize;
        int bottomRightCol = rightX/gp.tileSize;
        int bottomRow = bottomY/gp.tileSize;

        int tileNum1 = gp.tileM.mapTileNum[bottomLeftCol][bottomRow];
        int tileNum2 = gp.tileM.mapTileNum[bottomRightCol][bottomRow];

        if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
            entity.downVelocity = 0;
            entity.y = bottomRow*gp.tileSize - entity.solidArea.y -entity.solidArea.height;
            return true;
        }

        return false;
    }

    public boolean collisionLeft (Entity entity){

        int leftX = entity.x + entity.solidArea.x;
        int topY = entity.y + entity.solidArea.y + 1; //single offset so that only the square is checked, not the corners, that leak into the next tile
        int bottomY = entity.y + entity.solidArea.y + entity.solidArea.height - 1; //single offset so that only the square is checked, not the corners, that leak into the next tile

        if (leftX <= 0 ){
            entity.x = entity.solidArea.x;
            entity.rightVelocity = 0;
            return true;
        }

        int topRow = topY/gp.tileSize;
        int leftCol = leftX/gp.tileSize;
        int bottomRow = bottomY/gp.tileSize;

        int tileNum1 = gp.tileM.mapTileNum[leftCol][topRow];
        int tileNum2 = gp.tileM.mapTileNum[leftCol][bottomRow];

        if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
            entity.rightVelocity = 0;
            entity.x = (leftCol)*gp.tileSize + 31 + entity.solidArea.x;
            return true;
        }

        return false;

    }

    public boolean collisionRight (Entity entity){

        int rightX = entity.x + entity.solidArea.x + entity.solidArea.width;
        int topY = entity.y + entity.solidArea.y +1; //single offset so that only the square is checked, not the corners, that leak into the next tile
        int bottomY = entity.y + entity.solidArea.y + entity.solidArea.height -1; //single offset so that only the square is checked, not the corners, that leak into the next tile

        if (rightX >= gp.screenWidth ){
            entity.x = gp.screenWidth - entity.solidArea.x - entity.solidArea.width;
            entity.rightVelocity = 0;
            return true;
        }

        int topRow = topY/gp.tileSize;
        int rightCol = rightX/gp.tileSize;
        int bottomRow = bottomY/gp.tileSize;

        int tileNum1 = gp.tileM.mapTileNum[rightCol][topRow];
        int tileNum2 = gp.tileM.mapTileNum[rightCol][bottomRow];

        if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
            entity.rightVelocity = 0;
            entity.x = rightCol*gp.tileSize - entity.solidArea.x - entity.solidArea.width;
            return true;
        }

        return false;

    }

    public int futureCollisionRight (Entity entity){

        int rightX = entity.x + entity.solidArea.x + entity.solidArea.width;
        int nextRightX = rightX + entity.rightVelocity;
        int topY = entity.y + entity.solidArea.y +1; //single offset so that only the square is checked, not the corners, that leak into the next tile
        int bottomY = entity.y + entity.solidArea.y + entity.solidArea.height -1; //single offset so that only the square is checked, not the corners, that leak into the next tile

        if (nextRightX >= gp.screenWidth ){
            return gp.screenWidth - rightX;
        }

        int topRow = topY/gp.tileSize;
        int rightCol = nextRightX/gp.tileSize;
        int bottomRow = bottomY/gp.tileSize;

        int tileNum1 = gp.tileM.mapTileNum[rightCol][topRow];
        int tileNum2 = gp.tileM.mapTileNum[rightCol][bottomRow];

        if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
            return rightCol*gp.tileSize - rightX;
        }
        return -1;
    }

    public int futureCollisionLeft (Entity entity){

        int leftX = entity.x + entity.solidArea.x;
        int nextLeftX = leftX + entity.rightVelocity;
        int topY = entity.y + entity.solidArea.y +1; //single offset so that only the square is checked, not the corners, that leak into the next tile
        int bottomY = entity.y + entity.solidArea.y + entity.solidArea.height -1; //single offset so that only the square is checked, not the corners, that leak into the next tile

        if (nextLeftX <= 0 ){
            return - leftX;
        }

        int topRow = topY/gp.tileSize;
        int leftCol = nextLeftX/gp.tileSize;
        int bottomRow = bottomY/gp.tileSize;

        int tileNum1 = gp.tileM.mapTileNum[leftCol][topRow];
        int tileNum2 = gp.tileM.mapTileNum[leftCol][bottomRow];

        if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {

            return ((leftCol )*gp.tileSize + 31 ) - leftX;
        }
        return 1;
    }

    public int futureCollisionDown (Entity entity){

        int leftX = entity.x + entity.solidArea.x +1; //single offset so that only the square is checked, not the corners, that leak into the next tile
        int rightX = entity.x + entity.solidArea.x + entity.solidArea.width -1; //single offset so that only the square is checked, not the corners, that leak into the next tile
        int bottomY = entity.y + entity.solidArea.y + entity.solidArea.height;
        int nextBottomY = bottomY + entity.downVelocity;

        if (nextBottomY>=gp.screenHeight) {
            return gp.screenHeight - bottomY;
        }

        int bottomLeftCol = leftX/gp.tileSize;
        int bottomRightCol = rightX/gp.tileSize;
        int bottomRow = nextBottomY/gp.tileSize;

        int tileNum1 = gp.tileM.mapTileNum[bottomLeftCol][bottomRow];
        int tileNum2 = gp.tileM.mapTileNum[bottomRightCol][bottomRow];

        if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
            return bottomRow*gp.tileSize - bottomY;
        }

        return -1;
    }

    public void checkTile(Entity entity) {

        int entityLeftWorldX = entity.x + entity.solidArea.x;
        int entityRightWorldX = entity.x + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.y + entity.solidArea.y;
        int entityBottomWorldY = entity.y + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        int tileNum1, tileNum2;

        if(entity.downVelocity > 0){
            entityBottomRow = (entityBottomWorldY + entity.downVelocity) / gp.tileSize;
            tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
            tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
            if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                entity.collisionRight = true;
            }
        }else if(entity.downVelocity < 0){
            entityTopRow = (entityTopWorldY + entity.downVelocity) / gp.tileSize;
            tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
            tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
            if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                entity.collisionUp = true;
            }
        }
        if(entity.rightVelocity > 0){
            entityRightCol = (entityRightWorldX + entity.rightVelocity) / gp.tileSize;
            tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
            tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
            if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                entity.collisionRight = true;
            }
        }else if(entity.rightVelocity < 0){
            entityLeftCol = (entityLeftWorldX + entity.rightVelocity) / gp.tileSize;
            tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
            tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
            if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                entity.collisionLeft = true;
            }
        }
    }
}
