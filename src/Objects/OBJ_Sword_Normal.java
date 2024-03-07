package Objects;

import Entity.Entity;
import Main.GamePanel;

public class OBJ_Sword_Normal extends Entity {

    public OBJ_Sword_Normal(GamePanel gp)
    {
        super(gp);

        name = "Starter Sword";
        type = TYPE_SWORD;
        down1 = setup("/objects/sword_normal", gp.tileSize, gp.tileSize);
        attackValue = 2;
        description = "[" + name + "]\nBasic old rusty sword";
        attackHitBox.width = 30;
        attackHitBox.height = 36;
    }
}
