package Objects;

import Entity.Projectile;
import Main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Snowball extends Projectile {

    GamePanel gp;
    public OBJ_Snowball(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Snowball";
        speed = 7;
        maxLife = 80;
        life = maxLife;
        attack = 2;
        useCost = 1;
        alive = false;
        getImage();

    }

    public void getImage()
    {
        up1 = setup("/projectiles/snowball",gp.tileSize,gp.tileSize);
        up2 = setup("/projectiles/snowball",gp.tileSize,gp.tileSize);
        down1 = setup("/projectiles/snowball",gp.tileSize,gp.tileSize);
        down2 = setup("/projectiles/snowball",gp.tileSize,gp.tileSize);
        left1 = setup("/projectiles/snowball",gp.tileSize,gp.tileSize);
        right1 = setup("/projectiles/snowball",gp.tileSize,gp.tileSize);
    }
}
