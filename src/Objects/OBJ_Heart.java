package Objects;

import Entity.Entity;
import Main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Heart extends Entity {

    GamePanel gp;
    public OBJ_Heart(GamePanel gp)
    {

        super(gp);
        this.gp = gp;
        type = TYPE_PICKUPONLY;
        value = 2;
        name = "Heart";
        down1 = setup("/objects/heart_full", gp.tileSize, gp.tileSize);
        image = setup("/objects/heart_full", gp.tileSize, gp.tileSize);
        image2 = setup("/objects/heart_half", gp.tileSize ,gp.tileSize);
        image3 = setup("/objects/heart_blank",gp.tileSize,gp.tileSize);
    }

    public void use(Entity entity)
    {
        gp.playSE(1);
        gp.ui.addMessage("Life + " + value);
        entity.life += value;
    }
}
