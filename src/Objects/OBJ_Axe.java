package Objects;

import Entity.Entity;
import Main.GamePanel;

public class OBJ_Axe extends Entity {

    public OBJ_Axe(GamePanel gp)
    {
        super(gp);

        name = "Wood Cutters Axe";
        type = TYPE_AXE;
        down1 = setup("/objects/axe", gp.tileSize, gp.tileSize);
        attackValue = 4;
        description = "An ancient axe that\n was used by the KPI Kings.";
        attackHitBox.width = 30;
        attackHitBox.height = 30;

    }
}
