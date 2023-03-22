package logic;

import entities.Entity;

import java.awt.*;

public class EventHandler {

    GamePanel gp;
    EventRect eventRect[][];

    int previousEventX, previousEventY;
    boolean canTouchEvent = true;

    public EventHandler(GamePanel gp){
        this.gp = gp;
        eventRect = new EventRect[gp.maxScreenCol][gp.maxScreenRow];

        int col = 0;
        int row = 0;
        while (col< gp.maxScreenCol && row < gp.maxScreenRow){

            eventRect[col][row]  = new EventRect();
            eventRect[col][row].x = gp.tileSize/2 - 1;
            eventRect[col][row].y = gp.tileSize/2 - 1;
            eventRect[col][row].width = 2;
            eventRect[col][row].height = 2;
            eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
            eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;

            col++;
            if(col == gp.maxScreenCol){
                col = 0;
                row++;
            }
        }

    }

    public void checkEvent(Entity entity){

        //check if player is more than 1 tile away
        int xDistance = Math.abs(entity.x - previousEventX);
        int yDistance = Math.abs(entity.y - previousEventY);
        int distance = Math.max(xDistance,yDistance);
//        if (distance > gp.tileSize){
//            canTouchEvent = true;
//        }
//
//        if(canTouchEvent){
            if(hit(32, 5,entity, "any")){
                nextRoom();
            }
            if(entity.isPlatform){
                if(hit(15, 9,entity, "any")){
                    platformBounceLeft(entity);
                }
                if(hit(1,9,entity,"up")){
                    platformBounceRight(entity);
                }
            }

//        }
    }
    public boolean hit(int col, int row, Entity entity, String reqDirection){
        boolean hit = false;

        entity.eventSolidArea.x = entity.x + entity.eventSolidArea.x;
        entity.eventSolidArea.y = entity.y + entity.eventSolidArea.y;
        eventRect[col][row].x = col*gp.tileSize + eventRect[col][row].x;
        eventRect[col][row].y = row*gp.tileSize + eventRect[col][row].y;

        if (entity.eventSolidArea.intersects(eventRect[col][row]) && !eventRect[col][row].eventDone){
                hit = true;

                previousEventX = entity.x;
                previousEventY = entity.y;

        }

        entity.eventSolidArea.x = entity.eventSolidAreaDefaultX;
        entity.eventSolidArea.y = entity.eventSolidAreaDefaultY;
        eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
        eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;

        return hit;
    }

    public void platformBounceLeft(Entity entity){
        entity.rightVelocity = -5;
    }

    public void platformBounceRight(Entity entity){
        entity.rightVelocity = 5;
    }

    public void nextRoom(){

        if(gp.keyH.downPressed){
            switch (gp.currentLevel){
                case 1:
                    gp.level2();
                    gp.currentLevel=2;
                    break;
                case 2:
                    gp.level3();
                    gp.currentLevel=3;
                    break;
                default:
                    gp.setupGame();
                    gp.currentLevel=1;
                    break;
            }
        }
    }
}
