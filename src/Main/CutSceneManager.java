package Main;

import Entity.Entity;
import Entity.NPC_OldTeacher;
import Entity.PlayerDummy;
import Objects.OBJ_Axe;
import Objects.OBJ_Lantern;
import Tile_Interactive.IT_Dry_Tree;

import java.awt.*;
import java.util.Timer;

public class CutSceneManager{
    GamePanel gp;
    Graphics2D g2;
    public int sceneNum;
    public int scenePhase;
    public int timesLoaded = 0;

    //SCENE NUMBER

    public final int NA = 0;
    public final int beginningScene = 1;
    public final int tutorialScene = 2;
    public final int lvl2BeginningScene = 3;
    public final int evilBatScene = 4;
    public final int lvl3Scene = 5;

    public CutSceneManager(GamePanel gp)
    {
        this.gp = gp;
    }
    int counter = 0;
    int counter2 = 0;
    int counter3 = 0;
    int counter4 = 0;
    public void draw(Graphics2D g2)
    {
        this.g2 = g2;
        switch (sceneNum){
            case beginningScene: scene_Beginning(); break;
            case tutorialScene: sceneTutorial(); break;
            case lvl2BeginningScene: lvl2BeginningScene(); break;
            case evilBatScene: evilBatScene(); break;
            case lvl3Scene: lvl3Scene(); break;

        }
    }
    public void scene_Beginning()
    {
        if(scenePhase == 0)
        {
            gp.player.direction = "up";
            gp.currentMap = 0;
            gp.ui.drawDialogueScreen();
            // USE THE FOLLOWING CODE IF YOU ARE TRYING TO MAKE THE CAMERA MOVE
//            gp.player.drawing = false;
//            for (int i = 0; i < gp.npc.length; i++)
//            {
//                if (gp.npc[i] == null)
//                {
//                    gp.npc[i] = new PlayerDummy(gp);
//                    gp.npc[i].worldX = gp.player.worldX;
//                    gp.npc[i].worldY = gp.player.worldY;
//                    gp.npc[i].direction = gp.player.direction;
//                    break;
//                }
//            }
        }
        if (scenePhase == 1)
        {
            gp.player.direction = "down";
            gp.player.movePlayerdown();
            if (gp.player.worldY > 35*gp.tileSize)
            {
                scenePhase++;
            }

        }
        if (scenePhase == 2)
        {
            gp.ui.npc.dialogueSet = 1;
            gp.ui.drawDialogueScreen();
        }
        if (scenePhase == 3)
        {
            if (timesLoaded < gp.ui.timesGameEnded+1) {
                gp.currentMap = 1;
                gp.player.direction = "right";
                gp.player.worldX = gp.tileSize * 36;
                gp.player.worldY = gp.tileSize * 16;
                gp.aSetter.setNPC();
                gp.gameState = gp.cutsceneState;
                scenePhase++;
                timesLoaded++;
            }

        }
        if (scenePhase == 4)
        {
            gp.ui.drawDialogueScreen();
        }
        if (scenePhase == 5)
        {
            scenePhase = 999;
            sceneNum = NA;
            gp.gameState = gp.playState;
            gp.aSetter.setObject();
            gp.aSetter.setMonster();
        }

    }
    public void sceneTutorial() {
        if (scenePhase == 0) {
//            for (int i = 0; i < gp.npc[1].length; i++)
//            {
//                if (gp.npc[gp.currentMap][i] == null)
//                {
//                    gp.npc[gp.currentMap][i] = new PlayerDummy(gp);
//                    gp.npc[gp.currentMap][i].worldX = gp.player.worldX;
//                    gp.npc[gp.currentMap][i].worldY = gp.player.worldY;
//                    gp.npc[gp.currentMap][i].direction = gp.player.direction;
//                    break;
//                }
//            }
//            gp.player.drawing = false;

            int j = 10;

            for (int i = 0; i < gp.iTile[1].length; ++i) {
                if (gp.iTile[gp.currentMap][i] == null && counter < 3) {
                    gp.iTile[gp.currentMap][i] = new IT_Dry_Tree(gp, j, 36);
                    j++;
                    counter++;
                } else if (gp.iTile[gp.currentMap][i] == null && counter >= 3) {
                    break;
                }


            }
            {
                for (int i = 0; i < gp.monster[1].length; i++) {
                    if (gp.monster[gp.currentMap][i] != null && gp.monster[gp.currentMap][i].name.equals("Inflatiosaur")) {
                        gp.monster[gp.currentMap][i].sleep = false;
                        gp.ui.npc = gp.monster[gp.currentMap][i];
                        scenePhase++;
                    }
                }

            }

        }
        if (scenePhase == 1) {
            gp.ui.drawDialogueScreen();

        }
        if (scenePhase == 2)
        {

            if (counter2 < 1) {
                gp.ui.addMessage("Picked up Axe");
                gp.player.inventory.add(new OBJ_Axe(gp));

                gp.gameState = gp.playState;
                counter2++;
            }
        }
    }
    public void lvl2BeginningScene()
    {
        if (scenePhase == 0) {
            gp.player.attacking = false;
            gp.player.worldX = gp.tileSize * 35;
            gp.player.worldY = gp.tileSize * 16;
            gp.aSetter.setNPC();
            gp.player.direction = "right";
            gp.ui.npc.direction = "left";
            gp.ui.npc.dialogueSet = 1;
            gp.ui.npc.speed = 0;
            gp.ui.drawDialogueScreen();
        }
        if (scenePhase == 1)
        {
                gp.gameState = gp.playState;
                sceneNum = NA;

        }
    }
    public void evilBatScene()
    {
            if (scenePhase == 0) {
                if (gp.currentMap != 2) {
                    gp.gameState = gp.playState;
                    sceneNum = NA;
                } else {
                    if (counter > 1) {
                        for (int i = 0; i < gp.npc[1].length; i++) {
                            if (gp.npc[gp.currentMap][i] == null) {
                                gp.npc[gp.currentMap][i] = new PlayerDummy(gp);
                                gp.npc[gp.currentMap][i].worldX = 15 * gp.tileSize;
                                gp.npc[gp.currentMap][i].worldY = 37 * gp.tileSize;
                                break;
                            }
                        }
                        counter++;
                    }
                    gp.player.drawing = false;
                    if (gp.player.worldX < 18 * gp.tileSize || gp.player.worldY > 10 * gp.tileSize) {
                        if (gp.player.worldX < 18 * gp.tileSize) {
                            gp.player.worldX += 5;
                        }
                        if (gp.player.worldY > 10 * gp.tileSize) {
                            gp.player.worldY -= 5;
                        }
                    } else {
                        if (counter2 < 2) {
                            gp.ui.npc.dialogueSet = 0;
                            gp.ui.npc.dialogueIndex = 0;
                            counter2++;
                        }
                        gp.ui.drawDialogueScreen();
                    }
                }
            }
            if (scenePhase == 1) {
                if (gp.monster[2][1].worldX > 9 * gp.tileSize) {
                    gp.monster[2][1].worldX -= 3;
                    gp.player.worldX = gp.monster[2][1].worldX;
                } else if (gp.monster[2][1].worldY > 7 * gp.tileSize) {
                    gp.monster[2][1].worldY -= 3;
                    gp.player.worldY = gp.monster[2][1].worldY;
                } else if (gp.monster[2][1].worldX <= 9 * gp.tileSize && gp.monster[2][1].worldY <= 7 * gp.tileSize) {
                    gp.monster[2][1].worldX -= 3;
                    gp.monster[2][1] = null;
                    scenePhase++;
                }

            }

        if (counter3 < 1) {
            if (scenePhase == 2) {
                if (counter4 < 1) {
                    for (int i = 0; i < gp.npc[1].length; i++) {
                        if (gp.npc[gp.currentMap][i] != null && gp.npc[gp.currentMap][i].name.equals(PlayerDummy.npcName)) {
                            gp.player.worldX = gp.npc[gp.currentMap][i].worldX + gp.tileSize;
                            gp.player.worldY = gp.npc[gp.currentMap][i].worldY;
                            gp.npc[gp.currentMap][i] = null;
                            break;
                        }
                    }
                    gp.player.drawing = true;
                    counter4++;
                }
                sceneNum = NA;
                scenePhase = 0;
                gp.gameState = gp.playState;
                gp.player.speed = 4;
                counter3++;
            }
        }

    }
    public void lvl3Scene()
    {
        if (scenePhase == 0) {
            if (gp.currentMap != 3) {
                gp.gameState = gp.playState;
                sceneNum = NA;
            }

        }
    }
}
