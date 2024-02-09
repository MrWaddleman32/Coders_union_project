package Main;

import Entity.Entity;
import Entity.NPC_OldMan;
import Objects.OBJ_Chest;
import Objects.OBJ_Door;
import Objects.OBJ_Key;
import Objects.OBJ_Scroll;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp)
    {
        this.gp = gp;
    }
    public void setObject()
    {

    }
    public void setNPC()
    {
        gp.npc[0] = new NPC_OldMan(gp);
        gp.npc[0].worldX = gp.tileSize * 21;
        gp.npc[0].worldY = gp.tileSize * 21;
    }

    public void setMonster()
    {
        int i = 0;
        gp.monster[i] = new MON_Frostnapper(gp);
        gp.monster[i].worldX = gp.tileSize*23;
        gp.monster[i].worldY = gp.tileSize*36;
        i++;

        gp.monster[i] = new MON_Frostnapper(gp);
        gp.monster[i].worldX = gp.tileSize*20;
        gp.monster[i].worldY = gp.tileSize*33;
        i++;

        gp.monster[i] = new MON_Frostnapper(gp);
        gp.monster[i].worldX = gp.tileSize*23;
        gp.monster[i].worldY = gp.tileSize*34;
        i++;


    }
}
