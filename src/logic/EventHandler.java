package logic;

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
            eventRect[col][row].x = 23;
            eventRect[col][row].y = 23;
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

    public  void checkEvent(){

        //check if player is more than 1 tile away
        int xDistance = Math.abs(gp.player.x - previousEventX);
        int yDistance = Math.abs(gp.player.y - previousEventY);
        int distance = Math.max(xDistance,yDistance);
        if (distance > gp.tileSize){
            canTouchEvent = true;
        }

        if(canTouchEvent){
            if(hit(32, 5, "any")){
                nextRoom(32,5);
            }
//            if(hit(23, 19, "any"))
//            {damagePit(23,19,gp.dialogueState);
//            }
//            if(hit(23,12,"up")){
//                healingPool(23,12,gp.dialogueState);
//            }
        }
    }
    public boolean hit(int col, int row, String reqDirection){
        boolean hit = false;

        gp.player.solidArea.x = gp.player.x + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.y + gp.player.solidArea.y;
        eventRect[col][row].x = col*gp.tileSize + eventRect[col][row].x;
        eventRect[col][row].y = row*gp.tileSize + eventRect[col][row].y;

        if (gp.player.solidArea.intersects(eventRect[col][row]) && !eventRect[col][row].eventDone){
                hit = true;

                previousEventX = gp.player.x;
                previousEventY = gp.player.y;

        }

        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
        eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;

        return hit;
    }

    public void nextRoom(int col, int row){

        if(gp.keyH.downPressed){
            switch (gp.currentLevel){
                case 1:
                    gp.level2();
                    break;
                case 2:
                    gp.level3();
                    break;
                default:
                    break;
            }
        }
    }
}
