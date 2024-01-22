package Main;

import Objects.OBJ_Key;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class UI {

    public int numOfTabsOpened = 0;

    GamePanel gp;
    Graphics2D g2;
    Font arial_40;
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
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        OBJ_Key key = new OBJ_Key(gp);
        keyImage = key.image;
    }

    public void showMessage(String text)
    {
        message = text;
        messageOn = true;

    }

    public void draw(Graphics2D g2)
    {

        this.g2 = g2;
        g2.setFont(arial_40);
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
            if (gameFinished)
            {
                String text;
                int textLength;
                int x;
                int y;

                text = "You saved Frosty's Family!";
                textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
                x = gp.screenWidth/2 - textLength/2;
                y = gp.screenHeight/2 - gp.tileSize * 3;
                g2.drawString(text, x, y);
                gp.gameThread = null;
            }
            else {
                g2.setFont(arial_40);
                g2.setColor(Color.BLACK);
                g2.drawImage(keyImage, gp.tileSize / 2, gp.tileSize / 2, gp.tileSize, gp.tileSize, null);
                g2.drawString("x " + gp.player.numOfKeys, 74, 50);

                if (messageOn) {
                    g2.drawString(message, gp.tileSize / 2, gp.tileSize * 5);
                    messageCounter++;
                    if (messageCounter > 120) {
                        messageCounter = 0;
                        messageOn = false;
                    }
                }
            }
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

            x = gp.screenWidth/2 - gp.tileSize*1;
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

}
