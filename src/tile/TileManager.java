package tile;

import logic.GamePanel;
import logic.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][];

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[50];
        mapTileNum = new int[gp.maxScreenCol][gp.maxScreenRow];
        getTileImage();
        loadMap("/resources/maps/Map01.txt");
    }


    public void getTileImage() {
        //PLACEHOLDERS
        setup(0, "background", false);
        setup(1, "door", false);
        setup(2, "wall", true);
//        setup(3, "grass00", false);
//        setup(4, "grass00", false);
//        setup(5, "grass00", false);
//        setup(6, "grass00", false);
//        setup(7, "grass00", false);
//        setup(8, "grass00", false);
//        setup(9, "grass00", false);

//        //ACTUALLY USED
//        setup(10, "grass00", false);
//        setup(11, "grass01", false);
//        setup(12, "water00", true);
//        setup(13, "water01", true);
//        setup(14, "water02", true);
//        setup(15, "water03", true);
//        setup(16, "water04", true);
//        setup(17, "water05", true);
//        setup(18, "water06", true);
//        setup(19, "water07", true);
//        setup(20, "water08", true);
//        setup(21, "water09", true);
//        setup(22, "water10", true);
//        setup(23, "water11", true);
//        setup(24, "water12", true);
//        setup(25, "water13", true);
//        setup(26, "road00", false);
//        setup(27, "road01", false);
//        setup(28, "road02", false);
//        setup(29, "road03", false);
//        setup(30, "road04", false);
//        setup(31, "road05", false);
//        setup(32, "road06", false);
//        setup(33, "road07", false);
//        setup(34, "road08", false);
//        setup(35, "road09", false);
//        setup(36, "road10", false);
//        setup(37, "road11", false);
//        setup(38, "road12", false);
//        setup(39, "earth", false);
//        setup(40, "wall", true);
//        setup(41, "tree", true);



    }

    public void setup(int index, String imageName, boolean collision) {

        UtilityTool uTool = new UtilityTool();

        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/resources/tiles/" + imageName + ".png"));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0, row = 0;

            while (col < gp.maxScreenCol && row < gp.maxScreenRow) { //loops untill whole map has been added

                String line = br.readLine(); //reads next line

                while (col < gp.maxScreenCol) { //loops until edge of screen is reached
                    String numbers[] = line.split(" "); //splits up line String into array

                    int num = Integer.parseInt(numbers[col]); //gets the number corresponding to current column number

                    mapTileNum[col][row] = num; //assigns the number into the 2d Array
                    col++; //increments the column
                }
                if (col == gp.maxScreenCol) { //once the edge is reached the column is reset and the row is incremented
                    col = 0;
                    row++;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        int worldCol = 0, worldRow = 0;

        while (worldCol < gp.maxScreenCol && worldRow < gp.maxScreenRow) {

            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            g2.drawImage(tile[tileNum].image, worldX, worldY,null);
            worldCol++;

            if (worldCol == gp.maxScreenCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
