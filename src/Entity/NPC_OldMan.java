package Entity;

import Main.GamePanel;

import java.util.Random;

public class NPC_OldMan extends Entity{


    public NPC_OldMan(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 1;

        getImage();
        setDialogues();
    }

    public void getImage()
    {
        up1 = setup("/npc/oldman_up_1");
        up2 = setup("/npc/oldman_up_2");
        down1 = setup("/npc/oldman_down_1");
        down2 = setup("/npc/oldman_down_2");
        right1 = setup("/npc/oldman_right_1");
        right2 = setup("/npc/oldman_right_2");
        left1 = setup("/npc/oldman_left_1");
        left2 = setup("/npc/oldman_left_2");
    }
    public void setDialogues()
    {
        dialogues[0] = "Hello there chap";
        dialogues[1] = "Our world under the rule of the \nFrostnappers, and you think you \ncan help us?";
        dialogues[2] = "I used to be the hero of our world, \nbut I guess im a little too old \nfor that now aren't I?";
        dialogues[3] = "Well the only way to defeat them is \nby finding the sacred scroll of \n'snowball fighting'.";
        dialogues[4] = "Then you must get the keys and open the \ndoors to finally get rid of \nthe head Frostnapper";
        dialogues[5] = "Good luck chap, may you save \nour land.";
    }

    public void setAction()
    {
        if (onPath)
        {
//            int goalCol = (gp.player.worldX + gp.player.hitbox.x)/gp.tileSize;
//            int goalRow = (gp.player.worldY + gp.player.hitbox.y)/gp.tileSize;
//
//            searchPath(goalCol,goalRow);
        }
        else {
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
        }

    }
    public void speak()
    {
        super.speak();

        onPath = true;
    }
}
