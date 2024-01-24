package Entity;

import Main.GamePanel;
import Main.KeyHandler;
import Main.UtilityTool;
import Objects.OBJ_Snowball;
import jdk.jshell.execution.Util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.io.IOException;

public class Player extends Entity{
    Graphics2D snowball;
    KeyHandler keyH;
    public final int screenX;
    public final int screenY;
    public int numOfKeys = 0;
    public int numOfScrolls = 0;
    public Player(GamePanel gp, KeyHandler keyH)
    {
        super(gp);
        this.keyH = keyH;

        screenX = gp.screenWidth/2;
        screenY = gp.screenHeight/2;
        hitbox = new Rectangle();
        hitbox.x = 8;
        hitbox.y = 16;
        hitboxDefaultX = hitbox.x;
        hitboxDefaultY = hitbox.y;
        hitbox.width = 24;
        hitbox.height = 24;
        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues()
    {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 7;
        direction = "down";
        maxLife = 6;
        life = maxLife;
        strength = 1;
        dexterity = 1;
        exp = 0;
        nextLevelExp = 0;
        coin = 0;
        projectile = new OBJ_Snowball(gp);
    }

    public void setDefaultPositions()
    {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        direction = "down";
    }
    public void restoreLife(){
        life = maxLife;
        invincible = false;
    }
    public void getPlayerImage()
    {
        up1 = setup("/player/up_1");
        up2 = setup("/player/up_2");
        down1 = setup("/player/down_1");
        down2 = setup("/player/down_2");
        right1 = setup("/player/Right");
        left1 = setup("/player/Left");
    }
    public void update()
    {
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed || keyH.enterPressed)
        {
            if(keyH.upPressed)
            {
                direction = "up";
            }
            if(keyH.downPressed)
            {
                direction = "down";
            }
            if(keyH.leftPressed)
            {
                direction = "left";
            }
            if(keyH.rightPressed)
            {
                direction = "right";
            }

            // CHECK TILE COLLISION
            collisionOn = false;
            gp.cChecker.checkTile(this);

            // CHECK OBJ COLLISION
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            // CHECK NPC COLLISION
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            // CHECK MONSTER COLLISION
            int monIndex = gp.cChecker.checkEntity(this,gp.monster);
            contactMonster(monIndex);
            // CHECK EVENT
            gp.eHandler.checkEvent();

            // IF COLLISION IS FALSE, PLAYER CAN MOVE
            if (!collisionOn && keyH.enterPressed == false)
            {
                switch(direction)
                {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }

            gp.keyH.enterPressed = false;


            spriteCounter++;
            if(spriteCounter > 12){
                if(spriteNum == 1)
                {
                    spriteNum = 2;
                }
                else if (spriteNum ==2)
                {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
        if (gp.keyH.shotKeyPressed && numOfScrolls > 0 && projectile.alive == false && shotAvailableCounter == 30)
        {
            // SET DEFAULT COORDINATES, AND DIRECTION
            projectile.set(worldX, worldY, direction,true, this);

            // ADD IT TO THE ARRAYLIST
            gp.projectileList.add(projectile);
            shotAvailableCounter = 0;
        }
        if(invincible)
        {
            invincibleCounter++;
            if (invincibleCounter > 90)
            {
                invincible = false;
                invincibleCounter = 0;
            }
        }

        if(shotAvailableCounter <  30)
        {
            shotAvailableCounter++;
        }

        if (life<=0)
        {
            gp.gameState = gp.gameOverState;
        }
    }


    public void pickUpObject(int i)
    {
        if (i != 999)
        {

            String objectName = gp.obj[i].name;

            switch(objectName)
            {
                case("Key"):
                    numOfKeys++;
                    gp.obj[i] = null;
                    gp.ui.showMessage("You got a key!");
                    break;
                case("Door"):
                    if (numOfKeys > 0)
                    {
                        gp.obj[i] = null;
                        numOfKeys--;
                        gp.ui.showMessage("You opened a door!");
                    }
                    else {
                        gp.ui.showMessage("You need a key to open the door!");
                    }
                    break;
                case ("Scroll"):
                    numOfScrolls++;
                    speed += 3;
                    gp.obj[i] = null;
                    gp.ui.showMessage("Speed up + shoot snowballs (space bar)");
                    break;
                case ("Chest"):
                    gp.ui.gameFinished = true;
                    break;
            }
        }
    }
    public void interactNPC(int i)
    {
        if (i != 999)
        {
            if (gp.keyH.enterPressed)
            {
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();
            }
        }

    }
    public void contactMonster(int index)
    {
        if (index != 999)
        {
            if (!invincible) {
                life -= 1;
                invincible = true;
            }
        }
    }

    public void damageMonster(int i, int attack)
    {
        if (i != 999)
        {
            if (gp.monster[i].invincible == false)
            {
                gp.monster[i].life -= attack;
                gp.monster[i].invincible = true;

                if (gp.monster[i].life <= 0)
                {
                    gp.monster[i] = null;
                }
            }
        }
    }
    public void draw(Graphics2D g2)
    {
        BufferedImage image = null;

        switch(direction){
            case "up":
                if (spriteNum == 1) {
                    image = up1;
                }
                if (spriteNum == 2)
                    image = up2;
                break;
            case "down":
                if (spriteNum == 1)
                    image = down1;
                if (spriteNum == 2)
                    image = down2;
                break;
            case "right":
                image = right1;
                break;
            case "left":
                image = left1;
                break;
        }

        if (invincible)
        {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }
        g2.drawImage(image, screenX, screenY, null);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }


}
