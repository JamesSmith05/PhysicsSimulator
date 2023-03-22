package entities;

import logic.GamePanel;

public class Platform extends Entity{


    public Platform(GamePanel gp) {
        super(gp);
        isPlatform = true;
        mass = 1;
        solidArea.x = 0;
        solidArea.y = gp.tileSize/4;
        solidArea.width = gp.tileSize*2;
        solidArea.height = gp.tileSize/2;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        eventSolidArea.x = gp.tileSize/2 - 1;
        eventSolidArea.y = gp.tileSize/4;
        eventSolidArea.width = (gp.tileSize - (gp.tileSize/2 - 1)) * 2;
        eventSolidArea.height = gp.tileSize/2;
        eventSolidAreaDefaultX = eventSolidArea.x;
        eventSolidAreaDefaultY = eventSolidArea.y;

        getImage();
    }

    public void update() {
        gp.eHandler.checkEvent(this);

        x += rightVelocity;

    }

    public void getImage() {

        image = setup("entities/platform", gp.tileSize*2, gp.tileSize);

    }
}
