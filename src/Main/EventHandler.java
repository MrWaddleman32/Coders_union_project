package Main;


import Entity.Entity;
import Entity.Player;


import java.awt.*;


public class EventHandler{


    GamePanel gp;
    EventRect eventRect[][][];
    int previousEventX;
    int previousEventY;
    public int counter = 0;


    public EventHandler(GamePanel gp)
    {
        this.gp = gp;


        eventRect = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
        int map = 0;
        int col = 0;
        int row = 0;
        while(map < gp.maxMap && col < gp.maxWorldCol && row < gp.maxWorldRow)
        {
            eventRect[map][col][row] = new EventRect();
            eventRect[map][col][row].x = 23;
            eventRect[map][col][row].y = 23;
            eventRect[map][col][row].width = 2;
            eventRect[map][col][row].height = 2;
            eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
            eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;


            col++;
            if(col == gp.maxWorldCol)
            {
                col = 0;
                row++;


                if (row == gp.maxWorldRow)
                {
                    row = 0;
                    map++;
                }
            }
        }


    }


    public void checkEvent()
    {
        // PLACE THE EVENTS THAT YOU WANT TO CHECK HERE
        if(hit(1,11,35,"any")){tutorialFight(1,11,35);}
        else if (hit(1,41,13,"any") && gp.ui.lvl1Done){teleport(2,10,41, gp.cave);}
        else if (hit(2,9,41,"any")){teleport(1,41,14, gp.outside); gp.player.direction = "down";}
        else if (hit(2,15,37,"right")){evilBatCutScene(); gp.player.speed = 0;}
        else if (hit(2,8,7,"any")){teleport(3,26,40,gp.cave);}
        else if (hit(3,26,41,"anyd")){teleport(2,9,7, gp.cave);}
        else if (hit(3,25,27,"up")){lvl3Scene();}
    }


    public boolean hit(int map, int col, int row, String reqDirection)
    {
        boolean hit = false;
        if (map == gp.currentMap){
            gp.player.hitbox.x = gp.player.worldX + gp.player.hitbox.x;
            gp.player.hitbox.y = gp.player.worldY + gp.player.hitbox.y;
            eventRect[map][col][row].x = col*gp.tileSize + eventRect[map][col][row].x;
            eventRect[map][col][row].y = row*gp.tileSize + eventRect[map][col][row].y;


            if (gp.player.hitbox.intersects(eventRect[map][col][row]) && eventRect[map][col][row].eventDone == false)
            {
                if (gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any"))
                {
                    hit = true;


                    previousEventX = gp.player.worldX;
                    previousEventY = gp.player.worldY;
                }
            }


            gp.player.hitbox.x = gp.player.hitboxDefaultX;
            gp.player.hitbox.y = gp.player.hitboxDefaultY;
            eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
            eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;
        }
        gp.player.hitbox.x = gp.player.worldX + gp.player.hitbox.x;
        gp.player.hitbox.y = gp.player.worldY + gp.player.hitbox.y;
        eventRect[map][col][row].x = col*gp.tileSize + eventRect[map][col][row].x;
        eventRect[map][col][row].y = row*gp.tileSize + eventRect[map][col][row].y;


        if (gp.player.hitbox.intersects(eventRect[map][col][row]) && eventRect[map][col][row].eventDone == false)
        {
            if (gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any"))
            {
                hit = true;
            }
        }


        gp.player.hitbox.x = gp.player.hitboxDefaultX;
        gp.player.hitbox.y = gp.player.hitboxDefaultY;
        eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
        eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;




        return hit;
    }


    public void damagePit(int gameState)
    {
        gp.gameState = gameState;
        gp.ui.currentDialogue = "You fell in a pit";
        gp.player.life -=  1;
    }


    public void healingPit(int gameState)
    {
        if(gp.keyH.enterPressed)
        {
            gp.gameState = gameState;
            gp.player.attackCancel = true;
            gp.ui.currentDialogue = "You drink the water \n your life is recovered";
            gp.player.life = gp.player.maxLife;
        }
    }

    public void tutorialFight(int map,int col, int row)
    {
        gp.player.worldY = 35 * gp.tileSize;
        gp.gameState = gp.cutsceneState;
        eventRect[map][col][row].eventDone = true;
        gp.csManager.sceneNum = gp.csManager.tutorialScene;
        gp.csManager.scenePhase = 0;
    }
    public void teleport(int map, int col, int row, int area)
    {
            gp.currentMap = map;
            gp.currentArea = area;

        gp.player.worldX = gp.tileSize * col;
        gp.player.worldY = gp.tileSize * row;

    }
    public void evilBatCutScene()
    {
        if (counter < 1) {
            gp.gameState = gp.cutsceneState;
            gp.csManager.sceneNum = gp.csManager.evilBatScene;
            gp.csManager.scenePhase = 0;
            counter++;
        }
    }

    public void lvl3Scene()
    {
        gp.gameState = gp.cutsceneState;
        gp.csManager.sceneNum = gp.csManager.lvl3Scene;
        gp.csManager.scenePhase = 0;
    }




}
