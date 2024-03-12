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
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackRight1, attackRight2, attackLeft1, attackLeft2;
    public Rectangle attackHitBox =  new Rectangle(0,0,0,0);
    //COUNTER




    //STATE


    public String direction = "down";


    public int spriteCounter = 0;
    public int spriteNum = 1;
    public boolean attacking = false;
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
    public Entity currentLight;


    // ITEM ATTRIBUTES

    public boolean stackable = false;
    public int amount = 1;
    public int value;
    public int attackValue;
    public int defenseValue;
    public String description = "";
    public int useCost;
    public Rectangle hitbox = new Rectangle(0, 0 , 48 , 48);
    public int hitboxDefaultX, hitboxDefaultY;
    public boolean collisionOn = false;
    public boolean invincible = false;
    public boolean drawing = true;
    public int lightRadius;
    // COUNTERS
    public int invincibleCounter = 0;
    public int shotAvailableCounter = 0;
    public int dyingCounter = 0;
    public int actionLockCounter;
    public int hpBarCounter = 0;
    public String dialogues[][] = new String[20][20];
    public int dialogueSet = 0;
    public int dialogueIndex = 0;
    public int type_PickUpOnly = 0;
    public BufferedImage image, image2, image3;
    public boolean collision;
    public boolean sleep = false;
    public boolean onPath = false;
    public int type; // 0 is player, 1 is npc, 2 is monster
    public final int TYPE_PLAYER = 0;
    public final int TYPE_NPC = 1;
    public final int TYPE_MONSTER = 2;
    public final int TYPE_SWORD = 3;
    public final int TYPE_AXE = 4;
    public final int TYPE_CONSUMABLE = 5;
    public final int TYPE_PICKUPONLY = 6;
    public final int TYPE_OBSTACLE = 7;
    public final int TYPE_LIGHT = 8;
    public final int TYPE_PICKAXE = 9;
    public Entity(GamePanel gp)
    {
        this.gp = gp;
    }




    public void draw(Graphics2D g2) {
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




            // HEALTH BAR
            if (type == 2 && hpBarOn)
            {
                double oneScale = (double) gp.tileSize/maxLife;
                double hpBarValue = oneScale*life;
                g2.setColor(new Color(35,35,35));
                g2.fillRect(screenX-1, screenY - 16, gp.tileSize+2, 12);
                g2.setColor(new Color(255, 0, 30));
                g2.fillRect(screenX, screenY - 15, (int)hpBarValue, 10);
                hpBarCounter++;
                if (hpBarCounter > 600)
                {
                    hpBarCounter = 0;
                    hpBarOn = false;
                }
            }




            if (invincible)
            {
                hpBarOn = true;
                hpBarCounter = 0;
                changeAlpha(g2, 0.3F);
            }
            g2.drawImage(image,screenX,screenY,null);
            if (dying)
            {
                dyingAnimation(g2);
            }


            changeAlpha(g2,1F);


        }




    }
    public void dyingAnimation(Graphics2D g2) {
        dyingCounter++;
        int i = 10;
        if (dyingCounter <= i) {changeAlpha(g2, 0f);}
        if (dyingCounter > i && dyingCounter <= i*2)  {changeAlpha(g2, 1f);}
        if (dyingCounter > i*2 && dyingCounter <= i*3) {changeAlpha(g2, 0f);}
        if (dyingCounter > i*3&& dyingCounter <= i*4) {changeAlpha(g2, 1f);}
        if (dyingCounter > i*4 && dyingCounter <= i*5) {changeAlpha(g2, 0f);}
        if (dyingCounter > i*5 && dyingCounter <= i*6) {changeAlpha(g2, 1f);}
        if (dyingCounter > i*6 && dyingCounter <= i*7) {changeAlpha(g2, 0f);}
        if (dyingCounter > i*7 && dyingCounter <= i*8) {changeAlpha(g2, 1f);}
        if (dyingCounter > i*8)
        {
            alive = false;
        }
    }
    public void changeAlpha(Graphics2D g2, float alphaValue) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }
    public BufferedImage setup(String imagePath, int width, int height) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;


        try
        {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            image = uTool.scaleImage(image, width, height);
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return image;
    }


    public void setAction() { }
    public void damageReaction(){}
    public void speak() {


    }
    public void interact(){}

    public void facePlayer()
    {
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
    public void startDialogue(Entity entity, int setNum){
        gp.gameState = gp.dialogueState;
        gp.ui.npc = entity;
        dialogueSet = setNum;
    }


    public void checkDrop()
    {


    }
    public void dropItem(Entity droppedItem)
    {
        for (int i = 0; i < gp.obj[1].length; ++i)
        {
            if (gp.obj[gp.currentMap][i] == null){
                gp.obj[gp.currentMap][i] = droppedItem;
                gp.obj[gp.currentMap][i].worldX = worldX; // THE DEAD MONSTERS X
                gp.obj[gp.currentMap][i].worldY = worldY; // THE DEAD MONSTERS Y
                break;
            }
        }
    }


    public void checkCollision() {
        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkEntity(this, gp.npc);
        gp.cChecker.checkEntity(this, gp.monster);
        gp.cChecker.checkEntity(this, gp.iTile);
        boolean contactPlayer = gp.cChecker.checkPlayer(this);


        if (this.type == TYPE_MONSTER && contactPlayer)
        {
            damagePlayer(attack);
        }
    }
    public void update() {

        setAction();
        checkCollision();
        if (sleep == false)
        {

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



    }
    public void damagePlayer(int attack) {
        if (!(gp.player.invincible))
        {
            gp.playSE(6);


            int damage = attack - gp.player.defense;
            if (damage < 0){
                damage = 0;
            }
            gp.player.life -= damage;
            gp.player.invincible = true;
        }
    }
    public Color getParticleColor()
    {
        Color color = null;
        return color;
    }
    public int getParticleSize()
    {
        int size = 0;
        return size;
    }
    public int getParticleSpeed()
    {
        int speed = 0;
        return speed;
    }
    public int getMaxLife()
    {
        int maxLife = 0;
        return maxLife;
    }
    public void generateParticle(Entity generator, Entity target)
    {
        Color color = generator.getParticleColor();
        int size = generator.getParticleSize();
        int speed = generator.getParticleSpeed();
        int maxLife = generator.getMaxLife();


        Particle p1 = new Particle(gp, target, color, size, speed, maxLife, -1, -1);
        gp.particleList.add(p1);
    }
    protected void use(Entity entity) {


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
