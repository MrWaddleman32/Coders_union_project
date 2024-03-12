package Objects;

import Entity.Entity;
import Main.GamePanel;

public class OBJ_Lantern extends Entity {

    public OBJ_Lantern(GamePanel gp)
    {
        super(gp);

        type = TYPE_LIGHT;
        name = "Lantern";
        down1 = setup("/objects/lantern",gp.tileSize,gp.tileSize);
        description = "[Lantern]\n Illuminates your \nsurroundings";
        lightRadius = 250;
    }
}
