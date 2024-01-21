package Main;

import Entity.Entity;
import Entity.Player;
import Objects.SuperObject;
import Tile.Tile;
import Tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable{

    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // 48x48 Tile
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = maxScreenCol * tileSize; // 768 pixels
    public final int screenHeight = maxScreenRow * tileSize; // 576 pixels



    // WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    // FPS
    int FPS = 60;

    //SYSTEM
    TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    Thread gameThread;
    public collisionChecker cChecker = new collisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public SuperObject obj[] = new SuperObject[10];

    // ENTITY AND OBJECTS
    public Player player = new Player(this, keyH);
    public ArrayList<Entity> projectileList = new ArrayList<>();
    ArrayList<Entity> entityList = new ArrayList<>();

    // GAME STATE
    public int gameState;
    public final int playState = 1;
    public final int pauseState = 2;

    public GamePanel()
    {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

    }
    public void setUpGame()
    {
        aSetter.setObject();
        gameState = playState;
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while(gameThread != null){

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;

            lastTime = currentTime;
            if(delta >= 1)
            {
                update();

                repaint();
                delta--;
            }

        }

    }
    public void update()
    {
        if (gameState == playState)
        {
            player.update();
        }
        if (gameState == pauseState)
        {
            // nothing
        }
        // PLAYER

        for (int i = 0; i < projectileList.size(); i++)
        {
            if (projectileList.get(i) != null)
            {
                if (projectileList.get(i).alive)
                {
                    projectileList.get(i).update();
                }
                if (!(projectileList.get(i).alive))
                {
                    projectileList.remove(i);
                }
            }
        }


    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        // TILE
        tileM.draw(g2);

        // OBJECT
        for (int i = 0; i < obj.length; i++)
        {
            if (obj[i] != null)
            {
                obj[i].draw(g2, this);
            }
        }
        // PROJECTILES
        for (int i = 0; i < projectileList.size(); i++)
        {
            if (projectileList.get(i) != null)
            {
                entityList.add(projectileList.get(i));
            }
        }

        // PLAYER
        player.draw(g2);
        //UI
        ui.draw(g2);

    }
}
