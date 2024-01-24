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
        if (hit(24,23,"right")) {damagePit(gp.dialogueState);}
        if (hit(23,7,"up")){healingPit(gp.dialogueState);}
        if (hit(35,21,"right")){tutorial(gp.dialogueState);}
        if (hit(37,42,"down")){scrollInstructions(gp.dialogueState);}
        if (hit(38,13,"up")){firstFight(gp.dialogueState);}
        if (hit(10,11,"up")){congrats(gp.dialogueState);}
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
            gp.ui.currentDialogue = "You dring the water \n your life is recovered";
            gp.player.life = gp.player.maxLife;
        }
    }
    public void tutorial(int gamestate)
    {
        gp.gameState = gamestate;
        gp.ui.currentDialogue = "You must find the sacred \n scroll of snowball fighting \n before fighting your enemies";
    }
    public void scrollInstructions(int gamestate)
    {
        gp.gameState =gamestate;
        gp.ui.currentDialogue = "You are now faster and can \n shoot snowballs by pressing space bar. \n This is how you defeat enemies";
    }

    public void firstFight(int gamestate)
    {
        gp.gameState = gamestate;
        gp.ui.currentDialogue = "You cannot grab the key until all \n three frostnappers are defeated";
    }

    public void congrats(int gamestate)
    {
        gp.gameState = gamestate;
        gp.ui.currentDialogue = "Congratulations, you have saved \n Frosty's family";
    }


}
