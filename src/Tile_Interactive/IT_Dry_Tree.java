package Tile_Interactive;

import Entity.Entity;
import Main.GamePanel;

import java.awt.*;

public class IT_Dry_Tree extends InteractiveTile{

    GamePanel gp;
    public IT_Dry_Tree(GamePanel gp, int col, int row)
    {
        super(gp, col, row);
        this.gp = gp;
        this.worldX = gp.tileSize*col;
        this.worldY = gp.tileSize*row;
        life = 1;

        down1 = setup("/tiles_interactive/drytree",gp.tileSize,gp.tileSize);
        destructible = true;
    }
    public boolean isCorrectItem(Entity entity)
    {
        boolean isCorrectItem = false;
        if (entity.currentWeapon.type == TYPE_AXE)
        {
            isCorrectItem = true;
        }
        return isCorrectItem;
    }
    public void playSE()
    {
        gp.playSE(10);
    }
    public InteractiveTile getDestroyedForm()
    {
        InteractiveTile tile = new IT_Trunk(gp,worldX/gp.tileSize, worldY/gp.tileSize);
        return tile;
    }
    public Color getParticleColor()
    {
        Color color = new Color(65,50,30);
        return color;
    }
    public int getParticleSize()
    {
        int size = 6;
        return size;
    }
    public int getParticleSpeed()
    {
        int speed = 1;
        return speed;
    }
    public int getMaxLife()
    {
        int maxLife = 20;
        return maxLife;
    }

}
