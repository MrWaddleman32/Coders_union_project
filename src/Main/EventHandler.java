package Main;

import Entity.Entity;
import Entity.Player;

import java.awt.*;

public class EventHandler{

    GamePanel gp;
    EventRect eventRect[][];

    public EventHandler(GamePanel gp)
    {
        this.gp = gp;

        eventRect = new EventRect[gp.maxWorldCol][gp.maxWorldRow];
        int col = 0;
        int row = 0;
        while(col< gp.maxWorldCol && row < gp.maxWorldRow)
        {
            eventRect[col][row] = new EventRect();
            eventRect[col][row].x = 23;
            eventRect[col][row].y = 23;
            eventRect[col][row].width = 2;
            eventRect[col][row].height = 2;
            eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
            eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;

            col++;
            if(col == gp.maxWorldCol)
            {
                col = 0;
                row++;
            }
        }

    }

    public void checkEvent()
    {
        // PLACE THE EVENTS THAT YOU WANT TO CHECK HERE
    }

    public boolean hit(int col, int row, String reqDirection)
    {
        boolean hit = false;
        gp.player.hitbox.x = gp.player.worldX + gp.player.hitbox.x;
        gp.player.hitbox.y = gp.player.worldY + gp.player.hitbox.y;
        eventRect[col][row].x = col*gp.tileSize + eventRect[col][row].x;
        eventRect[col][row].y = row*gp.tileSize + eventRect[col][row].y;

        if (gp.player.hitbox.intersects(eventRect[col][row]))
        {
            if (gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any"))
            {
                hit = true;
            }
        }

        gp.player.hitbox.x = gp.player.hitboxDefaultX;
        gp.player.hitbox.y = gp.player.hitboxDefaultY;
        eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
        eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;


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
            gp.ui.currentDialogue = "You dring the water \n your life is recovered";
            gp.player.life = gp.player.maxLife;
        }
    }


}
