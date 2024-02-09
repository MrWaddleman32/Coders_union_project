package Objects;

import Entity.Entity;
import Main.GamePanel;

public class OBJ_Key extends Entity {
    GamePanel gp;
    public OBJ_Key(GamePanel gp)
    {
        super(gp);
        this.gp = gp;
        name = "Key";
        down1 = setup("/objects/key",gp.tileSize,gp.tileSize);
        type = type_PickUpOnly;
    }

    public void use(Entity entity)
    {
        gp.player.numOfKeys++;
    }
}
