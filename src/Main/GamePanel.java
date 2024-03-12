package Main;


import Entity.Entity;
import Entity.Player;
import Objects.OBJ_Key;
import Tile.TileManager;
import Tile_Interactive.InteractiveTile;
import environment.EnvironmentManager;


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
    public int currentMap = 0;
    // FOR FULL SCREEN
    public int screenWidth2 = screenWidth;
    public int screenHeight2 = screenHeight;
    BufferedImage tempScreen;
    public Graphics2D g2;


    // AREA
    public int currentArea;
    public final int outside = 50;
    public final int indoor = 51;
    public final int cave = 52;
    public boolean fullScreenOn = false;

    // FPS
    int FPS = 60;


    //SYSTEM
    public TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    public Sound sound = new Sound();
    public collisionChecker cChecker = new collisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);


    Thread gameThread;
    public EventHandler eHandler = new EventHandler(this);
    public CutSceneManager csManager = new CutSceneManager(this);
    EnvironmentManager eManager = new EnvironmentManager(this);
    public UI ui = new UI(this);
    public Entity obj[][] = new Entity[maxMap][20];
    public Entity npc[][] = new Entity[maxMap][10];
    public Entity monster[][] = new Entity[maxMap][20];
    public InteractiveTile iTile[][] = new InteractiveTile[maxMap][50];


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
    public final int cutsceneState = 7;
    public final int lvl1PlayState = 8;
    public final int transitionState = 9;
    public final int optionsState = 10;


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
        eManager.setup();
        gameState = titleState;
        csManager.sceneNum = csManager.beginningScene;
        currentArea = outside;


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
        if (gameState == playState)
        {


            // PLAYER
            player.update();
            // NPC
            for (int i = 0; i <npc[1].length; i++)
            {
                if (npc[currentMap][i]!= null){
                    npc[currentMap][i].update();
                }
            }
            for (int i = 0; i < monster[1].length; ++i)
            {
                if (monster[currentMap][i] != null) {
                    if (monster[currentMap][i].alive && !monster[currentMap][i].dying)
                    {
                        monster[currentMap][i].update();
                    }
                    if (!monster[currentMap][i].alive)
                    {
                        monster[currentMap][i].checkDrop();
                        monster[currentMap][i] = null;
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
            for(int i = 0; i < iTile[1].length; ++i)
            {
                if (iTile[currentMap][i] != null)
                {
                    iTile[currentMap][i].update();
                }
            }
            eManager.update();
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
            for (int i = 0; i < iTile[1].length; i++)
            {
                if (iTile[currentMap][i] != null){
                    iTile[currentMap][i].draw(g2);
                }
            }


            entityList.add(player);
            for(int i = 0; i <npc[1].length; i++)
            {
                if (npc[currentMap][i] != null)
                {
                    entityList.add(npc[currentMap][i]);
                }
            }
            for (int i = 0; i < obj[1].length; i++)
            {
                if (obj[currentMap][i] != null)
                {
                    entityList.add(obj[currentMap][i]);
                }
            }
            for (int i = 0; i < monster[1].length; i++)
            {
                if (monster[currentMap][i] != null)
                {
                    entityList.add(monster[currentMap][i]);
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
            // LIGHTING
            eManager.draw(g2);
            //CUTSCENE
            csManager.draw(g2);
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
