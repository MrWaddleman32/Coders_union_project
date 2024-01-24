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
        gp.obj[0] = new OBJ_Key(gp);
        gp.obj[0].worldX = 23 * gp.tileSize;
        gp.obj[0].worldY = 7 * gp.tileSize;

        gp.obj[1] = new OBJ_Key(gp);
        gp.obj[1].worldX = 33 * gp.tileSize;
        gp.obj[1].worldY = 8 * gp.tileSize;

        gp.obj[2] = new OBJ_Key(gp);
        gp.obj[2].worldX = 8 * gp.tileSize;
        gp.obj[2].worldY = 22 * gp.tileSize;





        gp.obj[3] = new OBJ_Door(gp);
        gp.obj[3].worldX = 10 * gp.tileSize;
        gp.obj[3].worldY = 11 * gp.tileSize;

        gp.obj[4] = new OBJ_Door(gp);
        gp.obj[4].worldX = 19 * gp.tileSize;
        gp.obj[4].worldY = 21 * gp.tileSize;

        gp.obj[5] = new OBJ_Door(gp);
        gp.obj[5].worldX = 23 * gp.tileSize;
        gp.obj[5].worldY = 17 * gp.tileSize;

        gp.obj[6] = new OBJ_Chest(gp);
        gp.obj[6].worldX = 10 * gp.tileSize;
        gp.obj[6].worldY = 7 * gp.tileSize;

        gp.obj[7] = new OBJ_Scroll(gp);
        gp.obj[7].worldX = 37 * gp.tileSize;
        gp.obj[7].worldY = 42 * gp.tileSize;
    }
    public void setNPC()
    {
        gp.npc[0] = new NPC_OldMan(gp);
        gp.npc[0].worldX = gp.tileSize * 21;
        gp.npc[0].worldY = gp.tileSize * 21;
    }

    public void setMonster()
    {
        gp.monster[0] = new MON_Frostnapper(gp);
        gp.monster[0].worldX = gp.tileSize * 36;
        gp.monster[0].worldY = gp.tileSize * 10;

        gp.monster[1] = new MON_Frostnapper(gp);
        gp.monster[1].worldX = gp.tileSize * 36;
        gp.monster[1].worldY = gp.tileSize * 11;

        gp.monster[2] = new MON_Frostnapper(gp);
        gp.monster[2].worldX = gp.tileSize * 35;
        gp.monster[2].worldY = gp.tileSize * 10;

        gp.monster[3] = new MON_HeadFrostnapper(gp);
        gp.monster[3].worldX = gp.tileSize * 10;
        gp.monster[3].worldY = gp.tileSize * 26;

        gp.monster[4] = new MON_Frostnapper(gp);
        gp.monster[4].worldX = gp.tileSize * 11;
        gp.monster[4].worldY = gp.tileSize * 26;

        gp.monster[5] = new MON_Frostnapper(gp);
        gp.monster[5].worldX = gp.tileSize * 10;
        gp.monster[5].worldY = gp.tileSize * 27;

        gp.monster[6] = new MON_Frostnapper(gp);
        gp.monster[6].worldX = gp.tileSize * 11;
        gp.monster[6].worldY = gp.tileSize * 27;



    }
}
