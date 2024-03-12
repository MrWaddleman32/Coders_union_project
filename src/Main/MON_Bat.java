package Main;

import Entity.Entity;
import Objects.OBJ_Coin_Bronze;
import Objects.OBJ_Heart;
import Objects.OBJ_Snowball;

import java.util.Random;

public class MON_Bat extends Entity {
    GamePanel gp;
    public MON_Bat(GamePanel gp) {
        super(gp);

        this.gp = gp;

        type = TYPE_MONSTER;
        name = "Bat";
        speed = 4;
        attack = 5;
        defense = 0;
        maxLife = 8;
        life = maxLife;
        alive = true;
        dying = false;
        exp = 3;

        hitbox.x = 12;
        hitbox.y = 3;
        hitbox.width = 42;
        hitbox.height = 21;
        hitboxDefaultX = hitbox.x;
        hitboxDefaultY = hitbox.y;
        projectile = new OBJ_Snowball(gp);
        getImage();
        setDialogues();
    }

    public void getImage()
    {
        up1 = setup("/monster/bat_down_1", gp.tileSize ,gp.tileSize);
        up2 = setup("/monster/bat_down_2", gp.tileSize, gp.tileSize);
        down1 = setup("/monster/bat_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/monster/bat_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/monster/bat_down_1",gp.tileSize ,gp.tileSize);
        right1 = setup("/monster/bat_down_2",gp.tileSize,gp.tileSize);
    }

    public void setDialogues()
    {
        dialogues[0][0] = "Business Bat: The Business Beacon is up ahead.\n I cant wait to eat it! It is going to taste\nSOOO GOOD";
    }


    public void setAction()
    {
        actionLockCounter++;

        if(actionLockCounter == 60) {


            Random random = new Random();
            int i = random.nextInt(100) + 1; // PICKS A RANDOM NUM FROM 1 TO 100
            if (i <= 25) {
                direction = "up";
            }
            if (i > 25 && i <= 50) {
                direction = "down";
            }
            if (i > 50 && i <= 75) {
                direction = "left";
            }
            if (i > 75 && i <= 100) {
                direction = "right";
            }
            actionLockCounter = 0;
        }


        // This next part lets the monsters shoot snowballs

//        int i = new Random().nextInt(100) + 1;
//        if (i > 99 && projectile.alive == false && shotAvailableCounter == 30)
//        {
//            projectile.set(worldX,worldY,direction, true, this);
//            gp.projectileList.add(projectile);
//            shotAvailableCounter = 0;
//        }
    }

    public void damageReaction()
    {
        actionLockCounter = 0;
        direction = gp.player.direction;
    }
    public void checkDrop()
    {
        int i  = new Random().nextInt(100)+1;

        // SET THE MONSTER DROP
        if (i < 50){
            dropItem(new OBJ_Coin_Bronze(gp));
        }
        else{
            dropItem(new OBJ_Heart(gp));
        }
    }
    public void speak()
    {
        facePlayer();
        startDialogue(this,dialogueSet);
        dialogueSet++;

        if (dialogues[dialogueSet][0] == null){
            dialogueSet = 0;
        }
    }
}
