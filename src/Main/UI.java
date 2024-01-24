package Main;

import Entity.Entity;
import Objects.OBJ_Heart;
import Objects.OBJ_Key;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
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
    public String message = "";

    public int messageCounter = 0;
    public boolean gameFinished = false;
    public String currentDialogue = "";
    public int commandNum = 0;
    public int titleScreenState = 0; //0: first screen ; 1: second screen ect.

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

    public void showMessage(String text)
    {
        message = text;
        messageOn = true;

    }

    public void draw(Graphics2D g2)
    {

        this.g2 = g2;
        g2.setFont(maruMonica);
        g2.setColor(Color.BLACK);
        // TITLE STATE
        if (gp.gameState == gp.titleState)
        {
            drawTitleScreen();
        }
        // PLAY STATE
        if (gp.gameState == gp.playState)
        {

            //stuff
            drawPlayerLife();
//            if (gameFinished)
//            {
//                String text;
//                int textLength;
//                int x;
//                int y;
//
//                text = "You saved Frosty's Family!";
//                textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
//                x = gp.screenWidth/2 - textLength/2;
//                y = gp.screenHeight/2 - gp.tileSize * 3;
//                g2.drawString(text, x, y);
//                gp.gameThread = null;
//            }
//            else {
//                g2.setFont(maruMonica);
//                g2.setColor(Color.BLACK);
//                //g2.drawImage(keyImage, gp.tileSize / 2, gp.tileSize / 2, gp.tileSize, gp.tileSize, null);
//                g2.drawString("x " + gp.player.numOfKeys, gp.tileSize/2 + 5, gp.tileSize/2);
//
//                if (messageOn) {
//                    g2.setColor(Color.BLACK);
//                    g2.drawString(message, gp.tileSize / 2, gp.tileSize * 5);
//                    messageCounter++;
//                    if (messageCounter > 120) {
//                        messageCounter = 0;
//                        messageOn = false;
//                    }
//                }
//            }
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
        // GAME OVER STATE
        if (gp.gameState == gp.gameOverState)
        {
            drawGameOverScreen();
        }

    }
    public void drawTitleScreen()
    {
        if (titleScreenState == 0)
        {
            g2.setColor(new Color(0,0,0));
            g2.fillRect(0,0,gp.screenWidth, gp.screenHeight);

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
        else if (titleScreenState == 1)
        {
            Desktop desktop = Desktop.getDesktop();
            if (numOfTabsOpened < 1) {
                try {
                    desktop.browse(java.net.URI.create("https://youtu.be/nJPCQ4OLJQo"));
                } catch (IOException e) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
                }
            }
            numOfTabsOpened++;
            gp.gameState = gp.playState;
        }

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
    public void drawPlayerLife()
    {
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
    public void drawGameOverScreen()
    {
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

}
