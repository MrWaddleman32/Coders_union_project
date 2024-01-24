package Entity;

import Main.GamePanel;
import Main.KeyHandler;
import Main.UtilityTool;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Entity {

    GamePanel gp;
    public int worldX,worldY;
    public BufferedImage up1, up2, down1, down2, right1, right2, left1, left2, snowball;
    public String direction = "down";
    //COUNTER
    public int spriteCounter = 0;
    public int spriteNum = 1;

    boolean attacking = false;
    public boolean alive = false;
    public boolean dying = false;
    boolean hpBarOn = false;

    // CHARACTER ATTRIBUTES
    public String name;
    public int speed;
    public int maxLife;
    public int life;
    public int maxMana;
    public int mana;
    public int level;
    public int strength;
    public int dexterity;
    public int attack;
    public int defense;
    public int exp;
    public int nextLevelExp;
    public int coin;
    public Entity currentWeapon;
    public Entity currentShield;
    public Projectile projectile;

    // ITEM ATTRIBUTES
    public int attackValue;
    public int defenseValue;
    public String description = "";
    public int useCost;
    public Rectangle hitbox = new Rectangle(0, 0 , 48 , 48);
    public int hitboxDefaultX, hitboxDefaultY;
    public boolean collisionOn = false;
    public int actionLockCounter;
    public boolean invincible = false;
    public int invincibleCounter = 0;
    public int shotAvailableCounter = 0;
    public String dialogues[] = new String[20];
    public int dialogueIndex = 0;
    public int type_PickUpOnly = 0;
    public BufferedImage image, image2, image3;
    public boolean collision;
    public boolean onPath = false;
    public int type; // 0 is player, 1 is npc, 2 is monster
    public Entity(GamePanel gp)
    {
        this.gp = gp;
    }


    public void draw(Graphics2D g2)
    {
        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
                && worldY + gp.tileSize > gp.player.worldY - gp.player.screenY && worldY - gp.tileSize < gp.player.worldY + gp.player.screenY)
        {
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
            g2.drawImage(image,screenX,screenY,gp.tileSize,gp.tileSize,null);

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        }


    }
    public BufferedImage setup(String imagePath)
    {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try
        {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return image;
    }

    public void setAction() { }
    public void speak() {
        if (dialogues[dialogueIndex] == null)
        {
            dialogueIndex = 0;
        }
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;
        switch(gp.player.direction)
        {
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "left":
                direction = "right";
                break;
            case "right":
                direction = "left";
                break;

        }
    }

    public void checkCollision()
    {
        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkEntity(this, gp.npc);
        gp.cChecker.checkEntity(this, gp.monster);
        boolean contactPlayer = gp.cChecker.checkPlayer(this);

        if (this.type == 2 && contactPlayer)
        {
            damagePlayer(attack);
        }
    }
    public void update() {
        setAction();
        checkCollision();

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
        if(invincible)
        {
            invincibleCounter++;
            if (invincibleCounter > 40)
            {
                invincible = false;
                invincibleCounter = 0;
            }
        }
        if(shotAvailableCounter <  30)
        {
            shotAvailableCounter++;
        }

    }
    public void damagePlayer(int attack)
    {
        if (!(gp.player.invincible))
        {
            gp.player.life--;
            gp.player.invincible = true;
        }
    }

//    public void searchPath(int goalCol, int goalRow)
//    {
//        int startCol = (worldX + hitbox.x)/gp.tileSize;
//        int startRow =  (worldY + hitbox.y)/gp.tileSize;
//
//        gp.pFinder.setNodes(startCol,startRow,goalCol,goalRow,this);
//
//        if (gp.pFinder.search())
//        {
//            int nextX = gp.pFinder.pathList.get(0).col* gp.tileSize;
//            int nextY = gp.pFinder.pathList.get(0).row * gp.tileSize;
//
//            int enLeftX = worldX + hitbox.x;
//            int enRightX = worldX + hitbox.x + hitbox.width;
//            int enTopY = worldY + hitbox.y;
//            int enBottomY = worldY + hitbox.y + hitbox.height;
//
//            if (enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize)
//            {
//                direction = "up";
//            }
//            else if (enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize)
//            {
//                direction = "down";
//            }
//            else if (enTopY >= nextY && enBottomY < nextY + gp.tileSize)
//            {
//                if (enLeftX > nextX)
//                {
//                    direction = "left";
//                }
//                if (enLeftX < nextX)
//                {
//                    direction = "right";
//                }
//            }
//            else if(enTopY > nextY && enLeftX > nextX)
//            {
//                // up or left
//                direction = "up";
//                checkCollision();
//                if (collisionOn)
//                {
//                    direction = "left";
//                }
//            }
//            else if (enTopY > nextY && enLeftX < nextX)
//            {
//                //up or right
//                direction = "up";
//                checkCollision();
//                if (collisionOn)
//                {
//                    direction = "right";
//                }
//            }
//            else if(enTopY < nextY && enLeftX > nextX)
//            {
//                // down or left
//                direction = "down";
//                checkCollision();
//                if (collisionOn)
//                {
//                    direction = "left";
//                }
//            }
//            else if(enTopY < nextY && enLeftX < nextX)
//            {
//                // down or right
//                direction = "down";
//                checkCollision();
//                if (collisionOn)
//                {
//                    direction = "right";
//                }
//            }
//
//            int nextCol = gp.pFinder.pathList.get(0).col;
//            int nextRow = gp.pFinder.pathList.get(0).row;
//            if (nextCol == goalCol && nextRow == goalRow)
//            {
//                onPath = false;
//            }
//        }
//    }

}
