package Objects;

import Entity.Entity;
import Main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Scroll extends Entity {
    GamePanel gp;
    public OBJ_Scroll(GamePanel gp)
    {
        super(gp);
        name = "Scroll";
        type = TYPE_CONSUMABLE;
        down1 = setup("/objects/Scroll",gp.tileSize,gp.tileSize);
    }
}
