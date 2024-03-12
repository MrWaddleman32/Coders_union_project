package Entity;

import Main.GamePanel;

import java.util.Random;

public class NPC_OldMan extends Entity{


    public NPC_OldMan(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 1;

        dialogueSet = 0;
        getImage();
        setDialogues();

    }

    public void getImage()
    {
        up1 = setup("/npc/oldman_up_1", gp.tileSize, gp.tileSize);
        up2 = setup("/npc/oldman_up_2", gp.tileSize, gp.tileSize);
        down1 = setup("/npc/oldman_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/npc/oldman_down_2", gp.tileSize ,gp.tileSize);
        right1 = setup("/npc/oldman_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("/npc/oldman_right_2", gp.tileSize, gp.tileSize);
        left1 = setup("/npc/oldman_left_1", gp.tileSize, gp.tileSize);
        left2 = setup("/npc/oldman_left_2", gp.tileSize, gp.tileSize);
    }
    public void setDialogues()
    {
        dialogues[0][0] = "Wise Guru: Hello there chap";
        dialogues[0][1] = "Wise Guru: Oh you look for the first piece to unlocking\nthe business beacon?";
        dialogues[0][2] = "Wise Guru: Well I heard it was very well hidden within this forest\ninside a treasure chest";
        dialogues[0][3] = "Wise Guru: Some of these trees look a little weird, I think they can be \ncut down with an axe";
        dialogues[0][4] = "Wise Guru: If you ever get too tired, try drinking some water \nat my garden";

        dialogues[1][0] = "Wise Guru: Wow you found the first piece to the \nbusiness beacon?";
        dialogues[1][1] ="Wise Guru: Well now you must go into the deep dark\nCommission Cave by walking down those stairs over there.";
        dialogues[1][2] = "Wise Guru: In there you will have to solve a \nRiddle to get the second piece.";
        dialogues[1][3] = "Wise Guru: I cannot join you because I have been banished\nfor trying to get the second piece too many times.";
        dialogues[1][3] = "Wise Guru: Well good luck to you Felix.";

    }

    public void setAction()
    {
//        if (onPath)
//        {
//            int goalCol = (gp.player.worldX + gp.player.hitbox.x)/gp.tileSize;
//            int goalRow = (gp.player.worldY + gp.player.hitbox.y)/gp.tileSize;
//
//            searchPath(goalCol,goalRow);
//        }
//        else {
            actionLockCounter++;
//
            if(actionLockCounter == 120) {
//
//
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
//        }
//
    }
    public void speak() {

        facePlayer();
        startDialogue(this, dialogueSet);
        dialogueSet++;

        if (dialogues[dialogueSet][0] == null){
            dialogueSet = 0;
        }

//        onPath = true;
    }
}
