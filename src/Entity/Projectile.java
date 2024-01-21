package Entity;

import Main.GamePanel;

public class Projectile extends Entity{

    Entity user;
    public Projectile(GamePanel gp)
    {
        super(gp);
    }

    public void set(int worldX, int worldY, String direction, boolean alive)
    {
        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direction;
        this.alive = alive;
        this.life = this.maxLife;
    }

    public void update()
    {

        switch(direction)
        {
            case "up": worldY -= speed; break;
            case "down": worldY += speed; break;
            case "left": worldX -= speed; break;
            case "right": worldX += speed; break;
        }

        life--;
        if (life <= 0)
        {
            alive = false;
        }

    }
}
