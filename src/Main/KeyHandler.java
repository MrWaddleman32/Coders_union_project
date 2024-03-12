package Main;


import Entity.Entity;
import Tile.TileManager;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.Key;


public class KeyHandler implements KeyListener {
    GamePanel gp;
    int songsPlayed = 0;

    public int code;
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, shotKeyPressed;
    public KeyHandler(GamePanel gp)
    {
        this.gp = gp;
    }
    @Override
    public void keyTyped(KeyEvent e) {


    }


    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();


        // TITLE STATE
        if (gp.gameState == gp.titleState)
        {
            titleState(code);
        }


        // PLAY STATE
        else if (gp.gameState == gp.playState) {
            playState(code);


        }


        // PAUSE STATE
        else if (gp.gameState == gp.pauseState)
        {
            pauseState(code);
        }
        // DIALOGUE STATE
        else if (gp.gameState == gp.dialogueState || gp.gameState == gp.cutsceneState)
        {
            dialogueState(code);
        }
        // CHARACTER STATE
        else if(gp.gameState == gp.characterState)
        {
            characterState(code);
        }
        // GAME OVER STATE
        else if (gp.gameState == gp.gameOverState)
        {
            gameOverState(code);
        }
        // LEVEL SELECTOR
        else if (gp.gameState == gp.selectLevelState)
        {
            selectLevelState(code);
        }
        else if (gp.gameState == gp.optionsState)
        {
            optionsState(code);
        }






    }
    public void titleState(int code)
    {
        if (gp.ui.titleScreenState == 0) {
            if (code == KeyEvent.VK_UP || code == KeyEvent.VK_W) {
                if (gp.ui.commandNum > 0) {
                    gp.ui.commandNum--;
                }
            }
            if (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S) {
                if (gp.ui.commandNum < 2) {
                    gp.ui.commandNum++;
                }
            }
            if (code == KeyEvent.VK_ENTER) {
                if (gp.ui.commandNum == 0) {
                    gp.ui.titleScreenState = 1;
                    gp.ui.g2.clearRect(0,0, gp.screenWidth2, gp.screenHeight2);
                    gp.gameState = gp.selectLevelState;
                }
                if (gp.ui.commandNum == 1) {
                    // add later
                }
                if (gp.ui.commandNum == 2) {
                    System.exit(0);
                }
            }
        }
    }
    public void playState(int code)
    {
        if (code == KeyEvent.VK_UP || code == KeyEvent.VK_W)
        {
            upPressed = true;
        }
        if (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S)
        {
            downPressed = true;
        }
        if (code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A)
        {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D)
        {
            rightPressed = true;
        }
        if (code == KeyEvent.VK_P)
        {
            gp.gameState = gp.pauseState;
        }
        if (code == KeyEvent.VK_C)
        {
            gp.gameState = gp.characterState;
        }
        if (code == KeyEvent.VK_ENTER)
        {
            enterPressed = true;
        }
        if (code == KeyEvent.VK_SPACE)
        {
            shotKeyPressed = true;
        }
        if (code == KeyEvent.VK_ESCAPE)
        {
            gp.gameState = gp.optionsState;
        }
    }
    public void pauseState(int code)
    {
        if (code == KeyEvent.VK_P)
        {
            gp.gameState = gp.playState;
        }
    }
    public void dialogueState(int code)
    {
        if (code == KeyEvent.VK_ENTER);
        {
           enterPressed = true;


        }
    }
    public void characterState(int code)
    {
        if (code == KeyEvent.VK_C)
        {
            gp.gameState = gp.playState;
        }
        if (code == KeyEvent.VK_W)
        {
            if (gp.ui.slotRow != 0) {
                gp.ui.slotRow--;
                gp.playSE(9);
            }
        }
        if (code == KeyEvent.VK_A)
        {
            if (gp.ui.slotCol != 0) {
                gp.ui.slotCol--;
                gp.playSE(9);
            }
        }
        if (code == KeyEvent.VK_S)
        {
            if (gp.ui.slotRow != 3) {
                gp.ui.slotRow++;
                gp.playSE(9);
            }
        }
        if (code == KeyEvent.VK_D)
        {
            if (gp.ui.slotCol != 4) {
                gp.ui.slotCol++;
                gp.playSE(9);
            }
        }
        if (code == KeyEvent.VK_ENTER) {
            gp.player.selectItem();
        }
    }
    public void gameOverState(int code)
    {
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP)
        {
            gp.ui.commandNum--;
            if (gp.ui.commandNum < 0)
            {
                gp.ui.commandNum = 1;
            }
        }
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN)
        {
            gp.ui.commandNum++;
            if (gp.ui.commandNum > 1)
            {
                gp.ui.commandNum = 0;
            }
        }
        if (code == KeyEvent.VK_ENTER)
        {
            if (gp.ui.commandNum == 0)
            {
                gp.gameState = gp.playState;
                gp.retry();
            }
            else  if (gp.ui.commandNum == 1)
            {


                System.exit(0);
            }
        }
    }
    public void selectLevelState(int code)
    {
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT)
        {
            gp.ui.levelCommandNum++;
            gp.playSE(9);
            if (gp.ui.levelCommandNum > gp.ui.numOfLevels)
            {
                gp.ui.levelCommandNum = 1;
            }
        }
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT)
        {
            gp.ui.levelCommandNum--;
            gp.playSE(9);
            if (gp.ui.levelCommandNum < 1)
            {
                gp.ui.levelCommandNum = gp.ui.numOfLevels;
            }
        }
        if (code == KeyEvent.VK_ENTER && gp.ui.levelCommandNum == 1)
        {
            gp.currentMap = 0;
            gp.tileM.loadMap("/maps/Lvl1CutScene.txt", 0);
            gp.gameState = gp.cutsceneState;
            gp.csManager.sceneNum = gp.csManager.beginningScene;
            gp.ui.timesAssigned = 0;
            gp.csManager.scenePhase = 0;
            gp.player.worldX = gp.tileSize * 23;
            gp.player.worldY = gp.tileSize * 20;
        }
        if (code == KeyEvent.VK_ENTER && gp.ui.levelCommandNum == 2)
        {
            if (gp.ui.lvl1Done) {
                gp.csManager.sceneNum = gp.csManager.lvl2BeginningScene;
                gp.csManager.scenePhase = 0;
                gp.gameState = gp.cutsceneState;
            }
        }
    }
    public void optionsState(int code)
    {
        if (code == KeyEvent.VK_ESCAPE)
        {
            gp.gameState = gp.playState;
        }
        if (code == KeyEvent.VK_ENTER)
        {
            enterPressed = true;
        }
        int maxCommandNum = 0;
        switch (gp.ui.subState){
            case 0: maxCommandNum = 5; break;
        }
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP)
        {
            gp.ui.commandNum--;
            gp.playSE(9);
            if(gp.ui.commandNum < 0)
            {
                gp.ui.commandNum = maxCommandNum;
            }
        }
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN)
        {
            gp.ui.commandNum++;
            gp.playSE(9);
            if (gp.ui.commandNum > maxCommandNum)
            {
                gp.ui.commandNum = 0;
            }
        }
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT){
            if (gp.ui.subState ==0)
            {
                if (gp.ui.commandNum == 1 && gp.sound.volumeScale > 0){
                    gp.sound.volumeScale--;
                    gp.sound.checkVolume();
                    gp.playSE(9);
                }
            }
        }
        if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT){
            if (gp.ui.subState ==0)
            {
                if (gp.ui.commandNum == 1 && gp.sound.volumeScale < 5){
                    gp.sound.volumeScale++;
                    gp.sound.checkVolume();
                    gp.playSE(9);
                }
            }
        }
    }


    @Override
    public void keyReleased(KeyEvent e) {


        int code = e.getKeyCode();
        if (code == KeyEvent.VK_UP || code == KeyEvent.VK_W)
        {
            upPressed = false;
        }
        if (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S)
        {
            downPressed = false;
        }
        if (code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A)
        {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D)
        {
            rightPressed = false;
        }
        if (code == KeyEvent.VK_SPACE)
        {
            shotKeyPressed = false;
        }


    }
}
