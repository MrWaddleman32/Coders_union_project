package Objects;

import Entity.Entity;
import Main.GamePanel;

public class OBJ_Coin_Bronze extends Entity {

    GamePanel gp;
    public OBJ_Coin_Bronze(GamePanel gp)
    {
        super(gp);
        this.gp = gp;

        type = TYPE_PICKUPONLY;
        value = 1;
        down1 = setup("/objects/coin_bronze", gp.tileSize, gp.tileSize);
    }

    public void use(Entity entity)
    {
        gp.playSE(1);
        gp.ui.addMessage("Coin + " + value);
        gp.player.coin += value;
    }
}
