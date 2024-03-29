package Objects;

import Entity.Entity;
import Main.GamePanel;

public class OBJ_Health_Potion extends Entity {

    GamePanel gp;
    public OBJ_Health_Potion(GamePanel gp)
    {
        super(gp);
        this.gp = gp;
        value = 5;
        name = "Health Potion";
        type = TYPE_CONSUMABLE;
        down1 = setup("/objects/potion_red", gp.tileSize, gp.tileSize);
        description = "Healing potion \n Heals your health by " + value;

        setDialogue();
    }
    public void setDialogue()
    {
        dialogues[0][0] = "You drink the " + name + "!\nYour life has been recovered by " + value;
    }
    public void use(Entity entity)
    {
        startDialogue(this,0);
        entity.life += value;
        gp.playSE(2);
    }
}
