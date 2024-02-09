package Objects;

import Entity.Entity;
import Main.GamePanel;

public class OBJ_Sword_Ice extends Entity {

    public OBJ_Sword_Ice(GamePanel gp)
    {
        super(gp);

        name = "Ice Sword";
        down1 = setup("/objects/Ice_sword", gp.tileSize, gp.tileSize);
        attackValue = 3;

    }
}
