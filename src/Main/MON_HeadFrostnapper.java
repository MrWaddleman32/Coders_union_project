package Main;

import Entity.Entity;
import Objects.OBJ_Snowball;

import java.util.Random;

public class MON_HeadFrostnapper extends Entity {

    GamePanel gp;

    public MON_HeadFrostnapper(GamePanel gp) {
        super(gp);

        this.gp = gp;

        type = 2;
        name = "Head Frostnapper";
        speed = 4;
        maxLife = 4;
        life = maxLife;

        hitbox.x = 12;
        hitbox.y = 3;
        hitbox.width = 27;
        hitbox.height = 42;
        hitboxDefaultX = hitbox.x;
        hitboxDefaultY = hitbox.y;
        getImage();
        projectile = new OBJ_Snowball(gp);
    }

    public void getImage()
    {
        up1 = setup("/monster/HeadFrostnapper");
        up2 = setup("/monster/HeadFrostnapper");
        down1 = setup("/monster/HeadFrostnapper");
        down2 = setup("/monster/HeadFrostnapper");
        left1 = setup("/monster/HeadFrostnapper");
        right1 = setup("/monster/HeadFrostnapper");
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
        int i = new Random().nextInt(100) + 1;
        if (i > 99 && projectile.alive == false && shotAvailableCounter == 30)
        {
            projectile.set(worldX,worldY,direction, true, this);
            gp.projectileList.add(projectile);
            shotAvailableCounter = 0;
        }
    }
}

