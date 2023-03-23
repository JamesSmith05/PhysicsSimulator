package logic;

import entities.Box;
import entities.Entity;
import entities.Platform;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable, ActionListener {
    public int tileSize = 32;

    public final int maxScreenCol = 34;
    public final int maxScreenRow = 24;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;
    public KeyHandler keyH = new KeyHandler(this);
    public CheckMouse keyM = new CheckMouse(this);
    public EventHandler eHandler = new EventHandler(this);
    public CollisionChecker cChecker = new CollisionChecker(this);
    public Thread gameThread;
    public boolean rightClick,leftClick;
    public ArrayList<Entity> platformList = new ArrayList<>();

    public TileManager tileM = new TileManager(this);

    public Box player = new Box(this);

    public int currentLevel = 1;

    public GamePanel() {

        //setup Jframe
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.darkGray);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.addMouseListener(keyM);
        this.setFocusable(true);

    }

    public void setupGame(){
        platformList.clear();

        Platform platform = new Platform(this);
        platform.y = tileSize*20 + 1;
        platform.x = tileSize*25 + 1;
        platform.downVelocity = -2;
        platformList.add(platform);

        player.x = 35;
        player.y = 16;
    }

    public void level2(){
        platformList.clear();

        tileM.loadMap("/resources/maps/Map02.txt");

        Platform platform = new Platform(this);
        platform.y = tileSize*9 + 1;
        platform.x = tileSize + 1;
        platform.rightVelocity = 5;
        platformList.add(platform);

        player.x = 35;
        player.y = 16;
    }

    public void level3(){
        platformList.clear();

        tileM.loadMap("/resources/maps/Map03.txt");

        player.x = 35;
        player.y = 16;
    }

    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        int FPS = 40;
        double drawInterval = 1000000000 / FPS; //0.01666 seconds
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime)/drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta>=1) {
                // update information e.g. positions
                update();
                // draw the screen with updated information
                repaint();
                delta--;
                drawCount++;
            }

            if(timer>=1000000000){
                drawCount = 0;
                timer = 0;
            }


        }

    }

    private void update() {

        player.update();

        for (int i = 0; i < platformList.size(); i++) {
            platformList.get(i).update();
        }

        //System.out.println("x: " + entityList.get(0).x + " y: " + entityList.get(0).y + " width: " +entityList.get(0).solidArea.width + " height: " +entityList.get(0).solidArea.height  + " solidX: " + entityList.get(0).solidArea.x + " solidY: " + entityList.get(0).solidArea.y);
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        tileM.draw(g2);

        for (int i = 0; i < platformList.size(); i++) {
            platformList.get(i).draw(g2);
        }

        player.draw(g2);

        g2.dispose();
    }

    public void resetLevel(){
        switch (currentLevel){
            case 1:
                setupGame();
                break;
            case 2:
                level2();
                break;
            case 3:
                level3();
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
            case 9:
                break;
            case 10:
                break;
            default:
                break;
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
