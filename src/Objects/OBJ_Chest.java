package Objects;

import Entity.Entity;
import Main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Chest extends Entity {
    GamePanel gp;
    Entity loot;
    boolean opened = false;

    public OBJ_Chest(GamePanel gp, Entity loot)
    {
        super(gp);
        this.gp = gp;
        type = TYPE_OBSTACLE;
        this.loot = loot;
        setDialogue();
        image = setup("/objects/chest", gp.tileSize, gp.tileSize);
        image2 = setup("/objects/chest_opened", gp.tileSize, gp.tileSize);
        down1 = image;
        name = "Chest";
        collision = true;



        hitbox.x = 4;
        hitbox.y = 16;
        hitbox.width = 40;
        hitbox.height = 32;
        hitboxDefaultX = hitbox.x;
        hitboxDefaultY = hitbox.y;
        setDialogue();
    }

    public void setDialogue()
    {
        dialogues[0][0] = "You open the chest and find a " + loot.name + "!" + "\nYou cannot carry any more!";
        dialogues[1][0] = "You open the chest and find a " + loot.name + "!" + "\nYou obtain the " + loot.name + "!";
        dialogues[2][0] = "The chest is empty";
    }

    public void interact()
    {

        if (opened == false)
        {
            gp.playSE(3);
            if (gp.player.inventory.size() == gp.player.maxInventorySize)
            {
                startDialogue(this, 0);
            }
            else{
                startDialogue(this, 1);
                gp.player.inventory.add(loot);
                down1 = image2;
                opened = true;
            }
        }
        else {
            startDialogue(this, 2);
            gp.gameState = gp.selectLevelState;
            gp.ui.lvl1Done = true;
        }
    }
}
