package Main;

import Entity.Entity;
import Entity.Player;
import Objects.OBJ_Key;
import Tile.TileManager;
import Tile_Interactive.InteractiveTile;
import ai.PathFinder;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
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
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 12;
    public final int screenWidth = maxScreenCol * tileSize; // 960 pixels
    public final int screenHeight = maxScreenRow * tileSize; // 576 pixels



    // WORLD SETTINGS
    public int maxWorldCol;
    public int maxWorldRow;
    public final int maxMap = 10;
    public int currentMap = 1;
    // FOR FULL SCREEN
    public int screenWidth2 = screenWidth;
    public int screenHeight2 = screenHeight;
    BufferedImage tempScreen;
    Graphics2D g2;

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
    public Entity obj[] = new Entity[20];
    public Entity npc[] = new Entity[10];
    public Entity monster[] = new Entity[20];
    public InteractiveTile iTile[] = new InteractiveTile[50];

    // ENTITY AND OBJECTS
    public Player player = new Player(this, keyH);
    public ArrayList<Entity> projectileList = new ArrayList<>();
    public ArrayList<Entity> entityList = new ArrayList<>();
    public ArrayList<Entity> particleList = new ArrayList<Entity>();

    // GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int gameOverState = 4;
    public final int characterState = 5;
    public final int selectLevelState = 6;
    public final int lvl1PreSceneState = 7;

    //Music settings/debugging
    public int musicPlayed = 0;

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
        aSetter.setInteractiveTiles();
        gameState = titleState;

        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) tempScreen.getGraphics();

        setFullScreen();
    }
    public void setFullScreen()
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        Main.window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        screenWidth2 = (int) width;
        screenHeight2 = (int) height;
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

                drawToTempScreen();
                drawToScreen();
                delta--;
            }

        }

    }
    public void update()
    {
        if (gameState == lvl1PreSceneState)
        {
            player.update();
        }
        else if (gameState == playState)
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
                        monster[i].checkDrop();
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
            for (int i = 0; i < particleList.size(); i++)
            {
                if (particleList.get(i) != null)
                {
                    if (particleList.get(i).alive)
                    {
                        particleList.get(i).update();
                    }
                    if (!particleList.get(i).alive)
                    {
                        particleList.remove(i);
                    }
                }
            }
            for(int i = 0; i < iTile.length; ++i)
            {
                if (iTile[i] != null)
                {
                    iTile[i].update();
                }
            }
        }
        if (gameState == pauseState)
        {
            // nothing
        }
        // PLAYER




    }

    public void drawToTempScreen()
    {
        // TITLE SCREEN
        if (gameState == titleState || gameState == selectLevelState)
        {
            ui.draw(g2);
        }
        // OTHERS
        else
        {
            // TILE
            tileM.draw(g2);
//            if (musicPlayed < 1) {
//                playMusic(0);
//                musicPlayed++;
//            }
            // INTERACTIVE TILES
            for (int i = 0; i < iTile.length; i++)
            {
                if (iTile[i] != null){
                    iTile[i].draw(g2);
                }
            }

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
            for (int i = 0; i < particleList.size(); i++)
            {
                if (particleList.get(i) != null)
                {
                    entityList.add(particleList.get(i));
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

    public void drawToScreen()
    {
        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
        g.dispose();

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
