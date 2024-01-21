package Entity;

import Main.GamePanel;
import Main.KeyHandler;
import Main.UtilityTool;
import Objects.OBJ_Snowball;
import jdk.jshell.execution.Util;

import javax.imageio.ImageIO;
import java.awt.*;
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
        direction = "down";
    }

    public void setDefaultValues()
    {
        worldX = gp.tileSize * 23 - (gp.tileSize/2);
        worldY = gp.tileSize * 21 - (gp.tileSize/2);
        speed = 9;
        strength = 1;
        dexterity = 1;
        exp = 0;
        nextLevelExp = 0;
        coin = 0;
        projectile = new OBJ_Snowball(gp);
    }
    public void getPlayerImage()
    {
        up1 = setup("up_1");
        up2 = setup("up_2");
        down1 = setup("down_1");
        down2 = setup("down_2");
        right = setup("Right");
        left = setup("Left");
    }

    public BufferedImage setup(String imageName)
    {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try
        {
            image = ImageIO.read(getClass().getResourceAsStream("/player/" + imageName + ".png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return image;
    }
    public void update()
    {
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed)
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
            // IF COLLISION IS FLASE, PLAYER CAN MOVE
            if (!collisionOn)
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
        if (gp.keyH.shotKeyPressed && numOfScrolls > 0)
        {
            // SET DEFAULT COORDINATES, AND DIRECTION
            projectile.set(worldX, worldY, direction,true);

            // ADD IT TO THE ARRAYLIST
            gp.projectileList.add(projectile);
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
                image = right;
                break;
            case "left":
                image = left;
                break;
        }
        g2.drawImage(image, screenX, screenY, null);
    }


}
