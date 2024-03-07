package Main;

import Entity.Entity;
import Objects.OBJ_Heart;
import Objects.OBJ_Key;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class UI {

    public int numOfTabsOpened = 0;

    GamePanel gp;
    Graphics2D g2;
    Font maruMonica;
    BufferedImage heart_full, heart_half, heart_blank;
    BufferedImage keyImage;
    public boolean messageOn = false;
    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();
    public boolean gameFinished = false;
    public String currentDialogue = "";
    public int commandNum = 0;
    public int slotCol = 0;
    public int slotRow = 0;
    public final int numOfLevels = 3;

    public int titleScreenState = 0; //0: first screen ; 1: second screen ect.
    public int levelCommandNum = 1; // 1: first level ; 2: second level ect.

    public UI(GamePanel gp)
    {
        this.gp = gp;

        try {
            InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
        }catch(FontFormatException e)
        {
            e.printStackTrace();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        OBJ_Key key = new OBJ_Key(gp);
        keyImage = key.image;

        // CREATE HUD OBJECT
        Entity heart = new OBJ_Heart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;
    }

    public void addMessage(String text) {
        message.add(text);
        messageCounter.add(0);
    }

    public void draw(Graphics2D g2) {

        this.g2 = g2;
        g2.setFont(maruMonica);
        g2.setColor(Color.BLACK);
        // TITLE STATE
        if (gp.gameState == gp.titleState)
        {
            drawTitleScreen();
        }
        // PLAY STATE
        if (gp.gameState == gp.playState) {
            drawPlayerLife();
            drawMessage();
        }
        // PAUSE STATE
        if (gp.gameState == gp.pauseState)
        {
            drawPauseScreen();
        }
        // DIALOGUE STATE
        if (gp.gameState == gp.dialogueState)
        {
            drawDialogueScreen();
        }
        // CHARACTER STATUS STATE
        if (gp.gameState == gp.characterState)
        {
            drawCharacterScreen();
            drawInventory();
        }
        // GAME OVER STATE
        if (gp.gameState == gp.gameOverState)
        {
            drawGameOverScreen();
        }
        // LEVEL SELECTOR
        if (gp.gameState == gp.selectLevelState)
        {
            drawLevelSelector();
        }
        if (gp.gameState == gp.lvl1PreSceneState)
        {
            gp.tileM.loadMap("/maps/Lvl1CutScene.txt");
        }

    }
    public void drawTitleScreen()
    {
        if (titleScreenState == 0)
        {
            g2.setColor(new Color(0,0,0));
            g2.fillRect(0,0,gp.screenWidth2, gp.screenHeight2);

            //TITLE NAME

            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 45));
            String text = "Frosty Vs. the Fierce Frostnappers";
            int x = getXForCenteredText(text);
            int y = gp.tileSize * 3;

            // SHADOW
            g2.setColor(Color.gray);
            g2.drawString(text, x+5, y+5);

            // MAIN COLOR

            g2.setColor(Color.WHITE);
            g2.drawString(text,x,y);

            // FROSTY IMAGE

            x = gp.screenWidth/2 - gp.tileSize;
            y += gp.tileSize*2;
            g2.drawImage(gp.player.down1, x, y, gp.tileSize*2, gp.tileSize*2, null);

            // MENU

            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48));
            text = "NEW GAME";
            x = getXForCenteredText(text);
            y += gp.tileSize*4;
            g2.drawString(text,x,y);
            if (commandNum == 0)
            {
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "LOAD GAME";
            x = getXForCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text,x,y);
            if (commandNum == 1)
            {
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "QUIT";
            x = getXForCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text,x,y);
            if (commandNum == 2)
            {
                g2.drawString(">", x - gp.tileSize, y);
            }
        }
//        else if (titleScreenState == 1)
//        {
//            Desktop desktop = Desktop.getDesktop();
//            if (numOfTabsOpened < 1) {
//                try {
//                    desktop.browse(java.net.URI.create("https://youtu.be/nJPCQ4OLJQo"));
//                } catch (IOException e) {
//                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
//                }
//            }
//            numOfTabsOpened++;
//            gp.gameState = gp.playState;
//        }

    }
    public void drawPauseScreen()
    {
        String text = "PAUSED";
        int x = getXForCenteredText(text);
        int y = gp.screenHeight/2;
        g2.drawString(text, x, y);
    }
    public int getXForCenteredText(String text)
    {
        int textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 - textLength/2;
        return x;
    }
    public void drawDialogueScreen()
    {
        // WINDOW
        int x = gp.tileSize * 2;
        int y = gp.tileSize / 2;
        int width = gp.screenWidth - (gp.tileSize * 4);
        int height = gp.tileSize * 4;
        drawSubWindow(x,y,width,height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 30));
        x += gp.tileSize;
        y += gp.tileSize;
        for(String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += 40;
        }
    }
    public void drawSubWindow(int x, int y, int width, int height)
    {
        Color c = new Color(0,0,0, 210);
        g2.setColor(c);
        g2.fillRoundRect(x,y,width,height, 35, 35);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width - 10, height - 10, 25, 25);
    }
    public void drawPlayerLife() {
        int x = gp.tileSize/2;
        int y = gp.tileSize/2;
        int i = 0;
        //DRAW MAX LIFE
        while(i < gp.player.maxLife/2)
        {
            g2.drawImage(heart_blank, x, y, null);
            i++;
            x+= gp.tileSize;
        }

        x = gp.tileSize/2;
        y = gp.tileSize/2;
        i = 0;
        // DRAW CURRENT LIFE

        while(i < gp.player.life)
        {
            g2.drawImage(heart_half, x, y, null);
            i++;
            if (i < gp.player.life)
            {
                g2.drawImage(heart_full,x,y,null);
            }
            i++;
            x+=gp.tileSize;
        }

    }
    public void drawMessage() {
        int messageX = gp.tileSize;
        int messageY = gp.tileSize * 4;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32f));

        for (int i = 0; i < message.size(); i++)
        {
            if (message.get(i) != null)
            {
                g2.setColor(Color.BLACK);
                g2.drawString(message.get(i), messageX + 2, messageY + 2);

                g2.setColor(Color.WHITE);
                g2.drawString(message.get(i), messageX, messageY);

                int counter  = messageCounter.get(i) + 1;
                messageCounter.set(i, counter);
                messageY += 50;

                if (messageCounter.get(i) > 180)
                {
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
        }
    }
    public void drawCharacterScreen() {
        // CREATE A FRAME
        final int frameX = gp.tileSize;
        final int frameY = gp.tileSize;
        final int frameHeight = gp.tileSize * 10;
        final int frameWidth = gp.tileSize * 5;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        //TEXT
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32F));
        int textX = frameX + 20;
        int textY = frameY + gp.tileSize;
        final int lineHeight = 35;

        // NAMES
        g2.drawString("Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Life", textX, textY);
        textY += lineHeight;
        g2.drawString("Strength", textX, textY);
        textY += lineHeight;
        g2.drawString("Dexterity", textX, textY);
        textY += lineHeight;
        g2.drawString("Attack", textX, textY);
        textY += lineHeight;
        g2.drawString("Defense", textX, textY);
        textY += lineHeight;
        g2.drawString("EXP", textX, textY);
        textY += lineHeight;
        g2.drawString("Next Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Coin", textX, textY);
        textY += lineHeight;
        g2.drawString("Weapon", textX, textY);
        textY += lineHeight;
        g2.drawString("Shield", textX, textY);

        // Values
        int tailX = (frameX + frameWidth) - 30;
        //Reset textY
        textY = frameY + gp.tileSize;
        String value;

        value = String.valueOf(gp.player.level);
        textX = alignTextXToRight(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.life + " / " + gp.player.maxLife);
        textX = alignTextXToRight(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.strength);
        textX = alignTextXToRight(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.dexterity);
        textX = alignTextXToRight(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.attack);
        textX = alignTextXToRight(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.defense);
        textX = alignTextXToRight(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.exp);
        textX = alignTextXToRight(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.nextLevelExp);
        textX = alignTextXToRight(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.coin);
        textX = alignTextXToRight(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.tileSize, textY - 25, null);
        textY += gp.tileSize;


    }
    public void drawInventory() {

        int frameX = gp.tileSize*9;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize*6;
        int frameHeight = gp.tileSize*5;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        //SLOTS
        final int slotXStart = frameX + 20;
        final int slotYStart = frameY + 20;
        int slotX = slotXStart;
        int slotY = slotYStart;
        int slotSize = gp.tileSize+3;

        //DRAW PLAYERS ITEMS
        for (int i = 0; i < gp.player.inventory.size(); ++i) {
            // EQUIP CURSOR
            if (gp.player.inventory.get(i) == gp.player.currentWeapon)
            {
                g2.setColor(new Color(240,190,0));
                g2.fillRoundRect(slotX,slotY,gp.tileSize, gp.tileSize, 10, 10);
            }
            g2.drawImage(gp.player.inventory.get(i).down1, slotX, slotY, null);
            slotX += slotSize;

            if (i == 4 || i == 9 || i == 14)
            {
                slotX = slotXStart;
                slotY += slotSize;
            }
        }

        // CURSOR

        int cursorX = slotXStart + slotSize*slotCol;
        int cursorY = slotYStart + slotSize*slotRow;
        int cursorWidth = gp.tileSize;
        int cursorHeight = gp.tileSize;
        // DRAW CURSOR
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

        //DESCRIPTION WINDOW
        int dFrameX = frameX;
        int dFrameY = frameY + frameHeight;
        int dFrameWidth = frameWidth;
        int dFrameHeight = gp.tileSize * 3;
        drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);
        // DRAW THE DESCRIPTION TEXT
        int textX = dFrameX + 20;
        int textY = dFrameY + gp.tileSize;
        g2.setFont(g2.getFont().deriveFont(28F));
        int itemIndex = getItemIndex();
        if (itemIndex < gp.player.inventory.size())
        {
            for (String line : gp.player.inventory.get(itemIndex).description.split("\n"))
            {
                g2.drawString(line, textX ,textY);
                textY += 28;
            }
        }


    }
    public void drawLevelSelector()
    {
        // COORDINATES
        int level = 1;
        int levelX = gp.tileSize * 5;
        int levelY = gp.tileSize * 3;
        int levelWidth = gp.tileSize * 2;
        int levelHeight = gp.tileSize * 2;
        int textX = levelX + gp.tileSize - 8;
        int textY = levelY + gp.tileSize + 10;
        int originalCursorX = textX;
        int cursorX = textX;
        int cursorY = textY + gp.tileSize * 2 + 10;
        int gapBetweenBoxes = gp.tileSize * 4;
        String cursor = "^";
        // LEVEL BOXES
        for (int i = 0; i < numOfLevels; ++i) {
            Color c = new Color(255, 255, 255);
            g2.setColor(c);
            g2.setStroke(new BasicStroke(5));
            g2.fillRoundRect(levelX, levelY, levelWidth, levelHeight, 8, 8);
            g2.setColor(Color.BLACK);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32f));
            g2.drawString(Integer.toString(level), textX, textY);
            levelX += gp.tileSize * 4;
            textX = levelX + gp.tileSize - 8;
            level++;
        }
        // CURSOR
        g2.setColor(Color.WHITE);;
        if (levelCommandNum == 1) {
            g2.clearRect(cursorX + gapBetweenBoxes - 10, cursorY - 60, 100, 100);
            g2.clearRect(cursorX + gapBetweenBoxes*2 - 10, cursorY - 60, 100, 100);
            g2.drawString(cursor, cursorX, cursorY);
        }
        if (levelCommandNum == 2)
        {
            g2.clearRect(cursorX - 10, cursorY - 60, 100, 100);
            g2.clearRect(cursorX + gapBetweenBoxes * 2 - 10, cursorY - 60, 100, 100);
            g2.drawString(cursor, cursorX + gapBetweenBoxes, cursorY);
        }
        if (levelCommandNum == 3)
        {
            g2.clearRect(cursorX  + gapBetweenBoxes - 10, cursorY - 60, 100, 100);
            g2.clearRect(cursorX  - 10, cursorY - 60, 100, 100);
            g2.drawString(cursor, cursorX + gapBetweenBoxes * 2, cursorY);
        }

        // LEVEL SELECTOR TEXT

        String levelHeader = "Select a level";
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 64f));
        g2.drawString(levelHeader, getXForCenteredText(levelHeader), gp.tileSize * 2);

        //Draw each level Description; (Since there are only 3 levels, we are going to write each desc seperately)
        String desc = "";
        int descX = 0;
        int descY = gp.tileSize * 5 + levelY;

        if (levelCommandNum == 1)
        {

            deleteLvlDescription(desc, descX, descY - 20);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 28f));
            desc = "FBLA Felix goes through the Fierce FBLA Forest \nin order to find the first piece" +
                    " \nto unlock the Business Beacon";
            descX = getXForCenteredText(desc) + gp.tileSize * 8;
            drawLvlDescription(desc, descX, descY);
        }
        if (levelCommandNum == 2)
        {

            deleteLvlDescription(desc, descX, descY - 20);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 28f));
            desc = "FBLA Felix is now on his way through \nCommission Cave to find the \nsecond piece to the Business Beacon";
            descX = getXForCenteredText(desc) + gp.tileSize * 8;

            drawLvlDescription(desc, descX, descY);
        }
        if (levelCommandNum == 3)
        {

            deleteLvlDescription(desc, descX, descY - 20);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 28f));
            desc = "?????";
            descX = getXForCenteredText(desc);

            drawLvlDescription(desc, descX, descY);
        }

    }
    public void drawGameOverScreen() {
        g2.setColor(new Color(0,0,0,150));
        g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);

        int x;
        int y;
        String text;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110f));

        text = "Game Over";
        g2.setColor(Color.BLACK);
        x = getXForCenteredText(text);
        y = gp.tileSize*4;
        g2.drawString(text, x,y);

        g2.setColor(Color.WHITE);
        g2.drawString(text,x-4,y-4);

        // RETRY

        g2.setFont(g2.getFont().deriveFont(50f));
        text = "Retry";
        x = getXForCenteredText(text);
        y += gp.tileSize * 4;
        g2.drawString(text,x,y);

        if (commandNum == 0)
        {
            g2.drawString(">", x - 40, y);
        }

        //BACK TO TITLE SCREEN
        text = "Quit";
        x = getXForCenteredText(text);
        y += 55;
        g2.drawString(text,x,y);

        if (commandNum == 1)
        {
            g2.drawString(">", x - 40, y);
        }
    }
    public int alignTextXToRight(String text, int tailX) {
        int textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = tailX - textLength;
        return x;
    }

    public int getItemIndex()
    {
        int itemIndex = slotCol + slotRow*5;
        return itemIndex;
    }

    public void drawLvlDescription(String description, int levelX, int levelY)
    {
        for(String line : description.split("\n")) {
            g2.drawString(line, levelX, levelY);
            levelY += 40;
        }
    }

    public void deleteLvlDescription(String description, int descX, int descY)
    {
//        int descWidth = 0;
//        int descHeight = description.split("\n").length * 45;
//        int maxlinelength = 0;
//        for(String line : description.split("\n")) {
//            if (maxlinelength < line.length()) {
//                maxlinelength = line.length();
//            }
//
//        }
//        descWidth = maxlinelength * g2.getFont().getSize() + 10;
        g2.clearRect(descX, descY, 1000, 300);
    }

}
