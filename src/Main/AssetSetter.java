package Main;

import Entity.Entity;
import Entity.NPC_OldMan;
import Objects.*;
import Tile_Interactive.IT_Dry_Tree;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp)
    {
        this.gp = gp;
    }
    public void setObject()
    {
        int i = 0;
        gp.obj[i] = new OBJ_Coin_Bronze(gp);
        gp.obj[i].worldX = gp.tileSize * 27;
        gp.obj[i].worldY = gp.tileSize * 21;
        i++;
        gp.obj[i] = new OBJ_Health_Potion(gp);
        gp.obj[i].worldX = gp.tileSize * 22;
        gp.obj[i].worldY = gp.tileSize * 27;
        i++;
        gp.obj[i] = new OBJ_Axe(gp);
        gp.obj[i].worldX = gp.tileSize * 23;
        gp.obj[i].worldY = gp.tileSize * 29;
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
    public void setInteractiveTiles()
    {
        int i = 0;
        gp.iTile[i] = new IT_Dry_Tree(gp,27,12);i++;
        gp.iTile[i] = new IT_Dry_Tree(gp,28,12);i++;
        gp.iTile[i] = new IT_Dry_Tree(gp,29,12);i++;
        gp.iTile[i] = new IT_Dry_Tree(gp,30,12);i++;
        gp.iTile[i] = new IT_Dry_Tree(gp,31,12);i++;
        gp.iTile[i] = new IT_Dry_Tree(gp,32,12);i++;
        gp.iTile[i] = new IT_Dry_Tree(gp,33,12);i++;
    }
}
