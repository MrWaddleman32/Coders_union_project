package Main;

import Entity.Entity;
import Objects.OBJ_Coin_Bronze;
import Objects.OBJ_Heart;

import java.util.Random;

public class MON_BossInflatiosaurGreen extends Entity {
    GamePanel gp;
    public MON_BossInflatiosaurGreen(GamePanel gp)
    {
        super(gp);
        this.gp = gp;
    }
    public void getImage()
    {
        int i = 5;
        up1 = setup("/monster/bat_down_1", gp.tileSize*i ,gp.tileSize*i);
        up2 = setup("/monster/bat_down_2", gp.tileSize*i, gp.tileSize*i);
        down1 = setup("/monster/bat_down_1", gp.tileSize*i, gp.tileSize*i);
        down2 = setup("/monster/bat_down_2", gp.tileSize*i, gp.tileSize*i);
        left1 = setup("/monster/bat_down_1",gp.tileSize*i ,gp.tileSize*i);
        right1 = setup("/monster/bat_down_2",gp.tileSize*i,gp.tileSize*i);
    }


    public void setAction()
    {
        actionLockCounter++;

        if(actionLockCounter == 120) {


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
}
