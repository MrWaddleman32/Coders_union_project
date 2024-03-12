package Main;

import Entity.Entity;
import Objects.OBJ_Axe;
import Objects.OBJ_Coin_Bronze;
import Objects.OBJ_Heart;
import Objects.OBJ_Snowball;

import java.util.Random;

public class MON_Tutorial_Monster extends Entity {

    GamePanel gp;
    public MON_Tutorial_Monster(GamePanel gp) {
        super(gp);

        this.gp = gp;

        type = TYPE_MONSTER;
        name = "Inflatiosaur";
        speed = 0;
        attack = 5;
        defense = 0;
        maxLife = 8;
        sleep = true;
        life = maxLife;
        alive = true;
        dying = false;
        exp = 3;

        hitbox.x = 12;
        hitbox.y = 3;
        hitbox.width = 27;
        hitbox.height = 42;
        hitboxDefaultX = hitbox.x;
        hitboxDefaultY = hitbox.y;
        getImage();
        projectile = new OBJ_Snowball(gp);
        setDialogues();
    }

    public void getImage()
    {
        up1 = setup("/monster/Inflatiosaur_left", gp.tileSize ,gp.tileSize);
        up2 = setup("/monster/Inflatiosaur_left", gp.tileSize, gp.tileSize);
        down1 = setup("/monster/Inflatiosaur_right", gp.tileSize, gp.tileSize);
        down2 = setup("/monster/Inflatiosaur_right", gp.tileSize, gp.tileSize);
        left1 = setup("/monster/Inflatiosaur_left",gp.tileSize ,gp.tileSize);
        right1 = setup("/monster/Inflatiosaur_right",gp.tileSize,gp.tileSize);
    }
    public void setDialogues(){
        dialogues[0][0] = "Inflatiosaur: Hey";
        dialogues[0][1] = "FBLA Felix: AHH IS THAT DINOSAUR TALKING??";
        dialogues[0][2] = "Inflatiosaur: *sighs* no Im an Inflatiosaur, trying to stop you \nfrom starting your business. Whatever you do" +
                " don't press\nENTER to attack me.";
        dialogues[0][3] = "FBLA Felix: I am not going to hurt you\n I just want to know how to find the first \npiece to the business beacon.";
        dialogues[0][4] = "Inflatiosaur: Good luck with that, you will never \nget passed the Business Bat.";
        dialogues[0][5] = "FBLA Felix: I really need it, my family is in a \nrough spot financially. It would mean the world if you helped me.";
        dialogues[0][6] = "Inflatiosaur: Okay, you seem like you really need it\nSo to get to the first piece, you will need this axe\n to break the dry trees";
        dialogues[0][7] = "Inflatiosaur: Then, you grab it from the chest.\nI am not allowed to tell you any more information or the Business Bat\nmight hurt me.";
        dialogues[0][8] = "FBLA Felix: Oh my god you poor thing, I will save you\njust give me the axe.";
        dialogues[0][9] = "Inflatiosaur: Okay I have given it to you.\nCheck your inventory by pressing 'c', and it should be there";
    }

    public void setAction()
    {
//        actionLockCounter++;
//
//        if(actionLockCounter == 120) {
//
//
//            Random random = new Random();
//            int i = random.nextInt(100) + 1; // PICKS A RANDOM NUM FROM 1 TO 100
//            if (i <= 25) {
//                direction = "up";
//            }
//            if (i > 25 && i <= 50) {
//                direction = "down";
//            }
//            if (i > 50 && i <= 75) {
//                direction = "left";
//            }
//            if (i > 75 && i <= 100) {
//                direction = "right";
//            }
//            actionLockCounter = 0;
//        }


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
        dropItem(new OBJ_Axe(gp));
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

