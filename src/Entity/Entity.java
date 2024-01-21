package Entity;

import Main.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {

    GamePanel gp;
    public int worldX,worldY;
    public BufferedImage up1, up2, down1, down2, right, left, snowball;
    public String direction, specialMove;
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
    public Entity(GamePanel gp)
    {
        this.gp = gp;
    }

    public void setAction()
    {

    }
    public void update() {
        setAction();

        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
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

}
