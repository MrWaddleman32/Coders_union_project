package Entity;

import Main.GamePanel;
import Main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity{
    GamePanel gp;
    KeyHandler keyH;

    public Player(GamePanel gp, KeyHandler keyH)
    {
        this.gp = gp;
        this.keyH = keyH;
        setDefaultValues();
        getPlayerImage();
        direction = "down";
    }

    public void setDefaultValues()
    {
        x = 100;
        y = 100;
        speed = 4;
    }
    public void getPlayerImage()
    {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/down_2.png"));
            right = ImageIO.read(getClass().getResourceAsStream("/player/right.png"));
            left = ImageIO.read(getClass().getResourceAsStream("/player/left.png"));

        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void update()
    {
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed)
        {
            if(keyH.upPressed)
            {
                direction = "up";
                y -= speed;
            }
            if(keyH.downPressed)
            {
                direction = "down";
                y += speed;
            }
            if(keyH.leftPressed)
            {
                direction = "left";
                x -= speed;
            }
            if(keyH.rightPressed)
            {
                direction = "right";
                x += speed;
            }

            spriteCounter++;
            if(spriteCounter > 12){
                if(spriteNum == 1)
                {
                    spriteNum = 2;
                }
                else if (spriteNum ==2)
                {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }


    }
    public void draw(Graphics2D g2)
    {
        BufferedImage image = null;

        switch(direction){
            case "up":
                if (spriteNum == 1)
                    image = up1;
                if (spriteNum == 2)
                    image = up2;
                break;
            case "down":
                if (spriteNum == 1)
                    image = down1;
                if (spriteNum == 2)
                    image = down2;
                break;
            case "right":
                image = right;
                break;
            case "left":
                image = left;
                break;
        }
        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
    }

}
