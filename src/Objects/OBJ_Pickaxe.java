package Objects;

import Entity.Entity;
import Main.GamePanel;

public class OBJ_Pickaxe extends Entity {
    public OBJ_Pickaxe(GamePanel gp){
        super(gp);
        name = "Pickaxe";
        type = TYPE_PICKAXE;
        down1 = setup("/objects/pickaxe", gp.tileSize, gp.tileSize);
        attackValue = 1;
        description = "An ancient pickaxe that can break certain rocks.";
        attackHitBox.width = 30;
        attackHitBox.height = 30;
    }
}
