package Entity;

import Main.GamePanel;
import Main.KeyHandler;
import Objects.OBJ_Key;
import Objects.OBJ_Snowball;
import Objects.OBJ_Sword_Normal;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class Player extends Entity{
    Graphics2D snowball;
    KeyHandler keyH;
    public final int screenX;
    public final int screenY;
    public int numOfKeys = 0;
    public int numOfScrolls = 0;
    public boolean attackCancel = false;
    public ArrayList<Entity> inventory = new ArrayList<Entity>();
    public final int maxInventorySize = 20;

    public Player(GamePanel gp, KeyHandler keyH) {
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

//        attackHitBox.width = 36;
//        attackHitBox.height = 36;
        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImages();
        setItems();
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";

        //PLAYER STATUS
        maxLife = 6;
        life = maxLife;
        strength = 1;
        dexterity = 1;
        defense = 1;
        exp = 0;
        nextLevelExp = 5;
        coin = 0;
        level = 1;
        currentWeapon = new OBJ_Sword_Normal(gp);
        attack = getAttack();
//        defense = getDefense();
        projectile = new OBJ_Snowball(gp);
    }
    public void setItems() {
        inventory.add(currentWeapon);
//      inventory.add(currentShield);
        inventory.add(new OBJ_Key(gp));
        inventory.add(new OBJ_Key(gp));
        inventory.add(new OBJ_Key(gp));
    }
    public int getAttack() {
        attackHitBox = currentWeapon.attackHitBox;
        return attack = strength * currentWeapon.attackValue;
    }
//    public int getDefense()
//    {
//    }

    public void setDefaultPositions() {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
//        worldX = gp.tileSize * 12;
//        worldY = gp.tileSize * 13;
        direction = "down";
    }
    public void restoreLife(){
        life = maxLife;
        invincible = false;
    }
    public void getPlayerImage() {
        up1 = setup("/player/boy_up_1", gp.tileSize, gp.tileSize);
        up2 = setup("/player/boy_up_2", gp.tileSize, gp.tileSize);
        down1 = setup("/player/boy_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/player/boy_down_2", gp.tileSize ,gp.tileSize);
        right1 = setup("/player/boy_right_1", gp.tileSize ,gp.tileSize);
        right2 = setup("/player/boy_right_2", gp.tileSize ,gp.tileSize);
        left1 = setup("/player/boy_left_1", gp.tileSize, gp.tileSize);
        left2 = setup("/player/boy_left_2", gp.tileSize, gp.tileSize);
    }
    public void update() {
        if (gp.gameState == gp.lvl1PreSceneState)
        {
            movePlayerdown();
        }
        else if (attacking)
        {
            attacking();
        }
        else if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed || keyH.enterPressed)
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

            // CHECK INTERACTIVE TILE COLLISION
            int iTileIndex = gp.cChecker.checkEntity(this,gp.iTile);

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

                if (keyH.enterPressed && attackCancel == false)
                {
                    gp.playSE(7);
                    attacking = true;
                    spriteCounter = 0;
                }

            }
            attackCancel = false;
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
        if (gp.keyH.shotKeyPressed /* && numOfScrolls > 0 */ && projectile.alive == false && shotAvailableCounter == 30)
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
        if (life > maxLife){
            life = maxLife;
        }
    }
    public void attacking() {
        spriteCounter++;
        if (spriteCounter <= 5)
        {
            spriteNum = 1;
        }
        if (spriteCounter> 5 && spriteCounter <= 50)
        {
            spriteNum = 2;

            // SAVE ORIGINAL X AND Y HITBOX SO WE CAN MOVE HITBOX WHEN HE ATTACKS
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int hitboxWidth = hitbox.width;
            int hitboxHeight = hitbox.height;
            //ADJUST PLAYERS HITBOX TO THE SWORD
            switch(direction)
            {
                case "up": worldY -= attackHitBox.height; break;
                case "down": worldY += attackHitBox.height; break;
                case "left": worldX -= attackHitBox.width; break;
                case "right": worldX += attackHitBox.width; break;
            }
            // Attack hitbox becomes only the sword
            hitbox.width = attackHitBox.width;
            hitbox.height = attackHitBox.height;

            // Check if we are hitting a monster and returning the original hitbox
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            damageMonster(monsterIndex, attack);

            int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
            damageInteractiveTile(iTileIndex);

            worldX = currentWorldX;
            worldY = currentWorldY;
            hitbox.width = hitboxWidth;
            hitbox.height = hitboxHeight;
        }
        if (spriteCounter > 25)
        {
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }
    public void pickUpObject(int i) {
            if (i != 999) {
                // PICK UP ONLY ITEMS
                if (gp.obj[i].type == TYPE_PICKUPONLY)
                {
                    gp.obj[i].use(this);
                    gp.obj[i] = null;
                }
                // INVENTORY ITEMS
                else {
                    String text = "";
                    if (inventory.size() != maxInventorySize) {

                        inventory.add(gp.obj[i]);
                        gp.playSE(1);
                        text = "got a " + gp.obj[i].name;
                    } else {
                        text = "you cannot hold anymore";
                    }
                    gp.ui.addMessage(text);
                    gp.obj[i] = null;
                }
            }

    }
    public void interactNPC(int i) {
        if (gp.keyH.enterPressed){
            if (i != 999)
            {
                attackCancel = true;
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();
            }
            else
            {
                attacking = true;
            }
        }

    }
    public void contactMonster(int index) {
        if (index != 999)
        {
            if (!invincible && !gp.monster[index].dying) {
                gp.playSE(6);

                int damage = gp.monster[index].attack - defense;
                if (damage < 0){
                    damage = 0;
                }
                life -= damage;
                invincible = true;
            }
        }
    }
    public void damageMonster(int i, int attack) {
        if (i != 999)
        {
            if (gp.monster[i].invincible == false)
            {
                gp.playSE(5);

                int damage = attack - gp.monster[i].defense;
                if (damage < 0) {
                    damage = 0;
                }
                gp.monster[i].life -= damage;
                gp.ui.addMessage(damage + " damage!");
                gp.monster[i].invincible = true;
                gp.monster[i].damageReaction();

                if (gp.monster[i].life <= 0)
                {
                    gp.monster[i].dying = true;
                    gp.ui.addMessage("killed the " + gp.monster[i].name + "!");
                    gp.ui.addMessage("exp + " + gp.monster[i].exp + "!");
                    exp += gp.monster[i].exp;
                    checkLevelUp();
                }
            }
        }
    }
    public void damageInteractiveTile(int i)
    {
        if (i != 999 && gp.iTile[i].destructible && gp.iTile[i].isCorrectItem(this) && !gp.iTile[i].invincible)
        {
            gp.iTile[i].playSE();
            gp.iTile[i].life--;
            gp.iTile[i].invincible = true;
            // GENERATES PARTICLES
            generateParticle(gp.iTile[i], gp.iTile[i]);
            if (gp.iTile[i].life == 0) {
                gp.iTile[i] = gp.iTile[i].getDestroyedForm();
            }
        }
    }
    public void checkLevelUp() {
        if (exp >= nextLevelExp)
        {
            level++;
            nextLevelExp += 10;
            maxLife += 2;
            life += 2;
            strength += 2;
            dexterity += 2;
            attack = getAttack();
            gp.playSE(8);
            gp.gameState = gp.dialogueState;
            gp.ui.currentDialogue = "You Leveled up, you are level " + level + " now";
        }
    }
    public void selectItem() {
        int itemIndex = gp.ui.getItemIndex();
        if (itemIndex < inventory.size())
        {
            Entity selectedItem = inventory.get(itemIndex);
            if (selectedItem.type == TYPE_SWORD || selectedItem.type == TYPE_AXE)
            {
                currentWeapon = selectedItem;
                attack = getAttack();
                getPlayerAttackImages();
            }
            if (selectedItem.type == TYPE_CONSUMABLE) {
                selectedItem.use(this);
                inventory.remove(itemIndex);
            }
        }
    }
    public void draw(Graphics2D g2) {

        int tempScreenX = screenX;
        int tempScreenY = screenY;
        BufferedImage image = null;

        switch(direction){
            case "up":
                if (attacking == false) {
                    if (spriteNum == 1) {image = up1;}
                    if (spriteNum == 2) image = up2;
                }
                if (attacking) {
                    tempScreenY = screenY - gp.tileSize;
                    if (spriteNum == 1) {image = attackUp2;}
                    if (spriteNum == 2) image = attackUp1;
                }
                break;
            case "down":
                if (attacking == false) {
                    if (spriteNum == 1) image = down1;
                    if (spriteNum == 2) image = down2;
                }
                if (attacking)
                {
                    if (spriteNum == 1) image = attackDown1;
                    if (spriteNum == 2) image = attackDown2;
                }
                break;
            case "right":
                if (attacking == false) {
                    if (spriteNum == 1) {image = right1;}
                    if (spriteNum == 2) {image = right2;}
                }
                if (attacking){
                    if (spriteNum == 1) {image = attackRight2;}
                    if (spriteNum == 2) {image = attackRight1;}
                }
                break;
            case "left":
                if (attacking == false) {
                    if (spriteNum == 1) {image = left1;}
                    if (spriteNum == 2) {image = left2;}}
                if (attacking){
                    tempScreenX = screenX - gp.tileSize;
                    if (spriteNum == 1) {image = attackLeft2;}
                    if (spriteNum == 2) {image = attackLeft1;}
                }
                break;
        }

        if (invincible)
        {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }
        g2.drawImage(image, tempScreenX, tempScreenY, null);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
    public void getPlayerAttackImages() {
        if (currentWeapon.type == TYPE_SWORD) {
            attackUp1 = setup("/player/boy_attack_up_2", gp.tileSize, gp.tileSize * 2);
            attackUp2 = setup("/player/boy_attack_up_1", gp.tileSize, gp.tileSize * 2);
            attackDown1 = setup("/player/boy_attack_down_1", gp.tileSize, gp.tileSize * 2);
            attackDown2 = setup("/player/boy_attack_down_2", gp.tileSize, gp.tileSize * 2);
            attackLeft2 = setup("/player/boy_attack_left_1", gp.tileSize * 2, gp.tileSize);
            attackLeft1 = setup("/player/boy_attack_left_2", gp.tileSize * 2, gp.tileSize);
            attackRight2 = setup("/player/boy_attack_right_1", gp.tileSize * 2, gp.tileSize);
            attackRight1 = setup("/player/boy_attack_right_2", gp.tileSize * 2, gp.tileSize);
        }
        if (currentWeapon.type == TYPE_AXE) {
            attackUp1 = setup("/player/boy_axe_up_2", gp.tileSize, gp.tileSize * 2);
            attackUp2 = setup("/player/boy_axe_up_1", gp.tileSize, gp.tileSize * 2);
            attackDown1 = setup("/player/boy_axe_down_1", gp.tileSize, gp.tileSize * 2);
            attackDown2 = setup("/player/boy_axe_down_2", gp.tileSize, gp.tileSize * 2);
            attackLeft2 = setup("/player/boy_axe_left_1", gp.tileSize * 2, gp.tileSize);
            attackLeft1 = setup("/player/boy_axe_left_2", gp.tileSize * 2, gp.tileSize);
            attackRight2 = setup("/player/boy_axe_right_1", gp.tileSize * 2, gp.tileSize);
            attackRight1 = setup("/player/boy_axe_right_2", gp.tileSize * 2, gp.tileSize);
        }
    }

    public void movePlayerdown()
    {
        this.worldY+=3;
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
