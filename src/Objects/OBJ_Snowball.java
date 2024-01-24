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
        up1 = setup("/projectiles/snowball");
        up2 = setup("/projectiles/snowball");
        down1 = setup("/projectiles/snowball");
        down2 = setup("/projectiles/snowball");
        left1 = setup("/projectiles/snowball");
        right1 = setup("/projectiles/snowball");
    }
}
