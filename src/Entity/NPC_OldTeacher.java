package Entity;

import Main.GamePanel;

public class NPC_OldTeacher extends Entity{
    public NPC_OldTeacher(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 0;

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
        dialogues[0][0] = "Teacher: Class, what would you guys \nlike to do when you grow up?";
        dialogues[0][1] = "FBLA Felix: Im going to start a \nbusiness and become a billionaire";
        dialogues[0][2] = "Teacher: You really think you can be successful?";
        dialogues[0][3] = "FBLA Felix: Yes I do";
        dialogues[0][4] = "Teacher: That's amazing, I believe in you. Go chase your dreams";
        dialogues[0][5] = "FBLA Felix: You know what I will. Thank you teacher";

        dialogues[1][0] = "FBLA Felix: I will show the world what I am capable of";
        dialogues[1][1] = "FBLA Felix: I will travel far and wide to claim the\nbusiness beacon as mine and I will become successful";
    }

    //    public void setAction()
//    {
//        if (onPath)
//        {
//            int goalCol = (gp.player.worldX + gp.player.hitbox.x)/gp.tileSize;
//            int goalRow = (gp.player.worldY + gp.player.hitbox.y)/gp.tileSize;
//
//            searchPath(goalCol,goalRow);
//        }
//        else {
//            actionLockCounter++;
//
//            if(actionLockCounter == 120) {
//
//
//                Random random = new Random();
//                int i = random.nextInt(100) + 1; // PICKS A RANDOM NUM FROM 1 TO 100
//                if (i <= 25) {
//                    direction = "up";
//                }
//                if (i > 25 && i <= 50) {
//                    direction = "down";
//                }
//                if (i > 50 && i <= 75) {
//                    direction = "left";
//                }
//                if (i > 75 && i <= 100) {
//                    direction = "right";
//                }
//                actionLockCounter = 0;
//            }
//        }
//
//    }
    public void speak() {

        facePlayer();
        startDialogue(this,dialogueSet);
        dialogueSet++;

        if (dialogues[dialogueSet][0] == null){
            dialogueSet = 0;
        }

//        onPath = true;
    }
}
