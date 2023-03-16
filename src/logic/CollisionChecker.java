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

    public boolean collisionUp (Entity entity){

        int leftX = entity.x + entity.solidArea.x +1; //single offset so that only the square is checked, not the corners, that leak into the next tile
        int rightX = entity.x + entity.solidArea.x + entity.solidArea.width -1; //single offset so that only the square is checked, not the corners, that leak into the next tile
        int topY = entity.y + entity.solidArea.y;

        if (topY <= 0) {
            entity.y = entity.solidArea.y;
            entity.downVelocity = 0;
            return true;
        }

        int topLeftCol = leftX/gp.tileSize;
        int topRightCol = rightX/gp.tileSize;
        int topRow = topY/gp.tileSize;

        int tileNum1 = gp.tileM.mapTileNum[topLeftCol][topRow];
        int tileNum2 = gp.tileM.mapTileNum[topRightCol][topRow];

        if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
            entity.downVelocity = 0;
            entity.y = (topRow +1)*gp.tileSize + entity.solidArea.y;
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

    public int futureCollisionUp (Entity entity){

        int leftX = entity.x + entity.solidArea.x +1; //single offset so that only the square is checked, not the corners, that leak into the next tile
        int rightX = entity.x + entity.solidArea.x + entity.solidArea.width -1; //single offset so that only the square is checked, not the corners, that leak into the next tile
        int topY = entity.y + entity.solidArea.y;
        int nextTopY = topY + entity.downVelocity;

        if (nextTopY <= 0) {
            System.out.println("topY: " + topY );
            System.out.println("nextTopY: " + nextTopY);
            return - topY;
        }

        int topLeftCol = leftX/gp.tileSize;
        int topRightCol = rightX/gp.tileSize;
        int topRow = nextTopY/gp.tileSize;

        int tileNum1 = gp.tileM.mapTileNum[topLeftCol][topRow];
        int tileNum2 = gp.tileM.mapTileNum[topRightCol][topRow];

        if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
            System.out.println("topY: " + topY );
            System.out.println("nextTopY: " + nextTopY);
            System.out.println("returned speed: " + (((topRow + 1 )*gp.tileSize) - topY));
            return ((topRow + 1 )*gp.tileSize) - topY;
        }

        return 1;
    }

}
