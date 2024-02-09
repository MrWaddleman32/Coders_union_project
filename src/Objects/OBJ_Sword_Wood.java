package Objects;

import Entity.Entity;
import Main.GamePanel;

public class OBJ_Sword_Wood extends Entity {

    public OBJ_Sword_Wood(GamePanel gp)
    {
        super(gp);

        name = "Wood Sword";
        down1 = setup("/objects/Starter_Wood_Sword", gp.tileSize, gp.tileSize);
        attackValue = 2;

    }
}
