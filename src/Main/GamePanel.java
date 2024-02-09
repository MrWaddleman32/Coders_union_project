package Main;

import Entity.Entity;
import Entity.Player;
import Objects.OBJ_Key;
import Tile.TileManager;
import ai.PathFinder;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

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

    // FPS
    int FPS = 60;

    //SYSTEM
    public TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    Sound sound = new Sound();
    public collisionChecker cChecker = new collisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);

    Thread gameThread;
    public EventHandler eHandler = new EventHandler(this);
    public PathFinder pFinder = new PathFinder(this);
    public UI ui = new UI(this);
    public Entity obj[] = new Entity[10];
    public Entity npc[] = new Entity[10];
    public Entity monster[] = new Entity[20];

    // ENTITY AND OBJECTS
    public Player player = new Player(this, keyH);
    public ArrayList<Entity> projectileList = new ArrayList<>();
    ArrayList<Entity> entityList = new ArrayList<>();

    // GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int gameOverState = 4;
    public final int characterState = 5;

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

        aSetter.setMonster();
        aSetter.setObject();
        aSetter.setNPC();
        gameState = titleState;
    }

    public void retry() {
        player.setDefaultPositions();
        player.restoreLife();
        aSetter.setMonster();
        aSetter.setNPC();
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

            // PLAYER
            player.update();
            // NPC
            for (int i = 0; i <npc.length; i++)
            {
                if (npc[i]!= null){
                    npc[i].update();
                }
            }
            for (int i = 0; i < monster.length; ++i)
            {
                if (monster[i] != null) {
                   if (monster[i].alive && !monster[i].dying)
                  {
                        monster[i].update();
                    }
                    if (!monster[i].alive)
                    {
                        monster[i] = null;
                    }
                }
            }
            for (int i = 0; i < projectileList.size(); i++)
            {
                if (projectileList.get(i) != null)
                {
                    if (projectileList.get(i).alive)
                    {
                        projectileList.get(i).update();
                    }
                    if (!projectileList.get(i).alive)
                    {
                        projectileList.remove(i);
                    }
                }
            }
        }
        if (gameState == pauseState)
        {
            // nothing
        }
        // PLAYER




    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;


        // TITLE SCREEN
        if (gameState == titleState)
        {
            ui.draw(g2);
        }
        // OTHERS
        else
        {
            // TILE
            tileM.draw(g2);

            entityList.add(player);
            for(int i = 0; i <npc.length; i++)
            {
                if (npc[i] != null)
                {
                    entityList.add(npc[i]);
                }
            }
            for (int i = 0; i < obj.length; i++)
            {
                if (obj[i] != null)
                {
                    entityList.add(obj[i]);
                }
            }
            for (int i = 0; i < monster.length; i++)
            {
                if (monster[i] != null)
                {
                    entityList.add(monster[i]);
                }
            }
            for (int i = 0; i < projectileList.size(); i++)
            {
                if (projectileList.get(i) != null)
                {
                    entityList.add(projectileList.get(i));
                }
            }



            // SORT
            Collections.sort(entityList, new Comparator<Entity>() {

                @Override
                public int compare(Entity e1, Entity e2) {
                    int result = Integer.compare(e1.worldY, e2.worldY);

                    return result;
                }
            });

            // DRAW ENTITIES
            for (int i = 0; i < entityList.size(); i++)
            {
                entityList.get(i).draw(g2);
            }
            // EMPTY ENTITY LIST
            entityList.clear();
            //UI
            ui.draw(g2);

        }




    }

    public void playMusic(int i)
    {
        sound.setFile(i);
        sound.play();
        sound.loop();
    }
    public void stopMusic()
    {
        sound.stop();
    }
    public void playSE(int i)
    {
        sound.setFile(i);
        sound.play();
    }
}
