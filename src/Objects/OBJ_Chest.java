package Objects;

import Entity.Entity;
import Main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Chest extends Entity {
    GamePanel gp;

    public OBJ_Chest(GamePanel gp)
    {
        super(gp);
        down1 = setup("/objects/FrostyFamily", gp.tileSize, gp.tileSize);
        name = "Chest";
        collision = true;
    }
}
