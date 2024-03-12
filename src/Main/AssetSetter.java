package Main;


import Entity.Entity;
import Entity.NPC_OldMan;
import Entity.NPC_OldTeacher;
import Objects.*;
import Tile_Interactive.IT_Destructible_Wall;
import Tile_Interactive.IT_Dry_Tree;
import Tile_Interactive.InteractiveTile;


public class AssetSetter {


    GamePanel gp;
    int iTileIndex = 0;
    int OBJIndex = 0;


    public AssetSetter(GamePanel gp)
    {
        this.gp = gp;
    }
    public void setObject()
    {
        int mapNum = 1;
        gp.obj[mapNum][OBJIndex] = new OBJ_Chest(gp, new OBJ_Key(gp));
        gp.obj[mapNum][OBJIndex].worldX = gp.tileSize * 32;
        gp.obj[mapNum][OBJIndex].worldY = gp.tileSize * 36;
        OBJIndex++;
        gp.obj[mapNum][OBJIndex] = new OBJ_Lantern(gp);
        gp.obj[mapNum][OBJIndex].worldX = gp.tileSize * 30;
        gp.obj[mapNum][OBJIndex].worldY = gp.tileSize * 16;
        mapNum = 2;
        OBJIndex = 0;
        gp.obj[mapNum][OBJIndex] = new OBJ_Chest(gp, new OBJ_Pickaxe(gp));
        gp.obj[mapNum][OBJIndex].worldX = gp.tileSize * 40;
        gp.obj[mapNum][OBJIndex].worldY = gp.tileSize * 41;
    }
    public void setNPC()
    {
        int mapNum = 0;
        int i = 0;
        gp.npc[mapNum][i] = new NPC_OldTeacher(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize * 23;
        gp.npc[mapNum][i].worldY = gp.tileSize * 17;
        i++;

        mapNum++;
        gp.npc[mapNum][i] = new NPC_OldMan(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize * 37;
        gp.npc[mapNum][i].worldY = gp.tileSize * 16;
        gp.npc[mapNum][i].direction = "left";

    }


    public void setMonster()
    {
        int mapNum = 1;
        int i = 0;
        gp.monster[mapNum][i] = new MON_Tutorial_Monster(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 11;
        gp.monster[mapNum][i].worldY = gp.tileSize * 30;
        mapNum++;
        i++;
        gp.monster[mapNum][i] = new MON_Bat(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 18;
        gp.monster[mapNum][i].worldY = gp.tileSize * 10;
        gp.monster[mapNum][i].speed = 0;

        i++;
        gp.monster[mapNum][i] = new MON_Bat(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 15;
        gp.monster[mapNum][i].worldY = gp.tileSize * 26;
        gp.monster[mapNum][i].speed = 5;

        i++;
        gp.monster[mapNum][i] = new MON_Bat(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 19;
        gp.monster[mapNum][i].worldY = gp.tileSize * 26;
        gp.monster[mapNum][i].speed = 5;

        i++;
        gp.monster[mapNum][i] = new MON_Bat(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 18;
        gp.monster[mapNum][i].worldY = gp.tileSize * 27;
        gp.monster[mapNum][i].speed = 5;




    }
    public void setInteractiveTiles()
    {
        int mapNum = 1;
        int i = 0;
        setRowOfITTrees(22,44,8,1);
        setColOfITWalls(18, 31,3,2);
    }

    public void setRowOfITTrees(int x, int y, int width, int mapNum)
    {
        int startX = x;
        while (x < startX + width)
        {
            gp.iTile[mapNum][iTileIndex] = new IT_Dry_Tree(gp,x,y);
            x++;
            iTileIndex++;
        }
    }
    public void setColOfITWalls(int x, int y, int height, int mapNum)
    {
        int startY = y;
        while (y < startY + height)
        {
            gp.iTile[mapNum][iTileIndex] = new IT_Destructible_Wall(gp,x,y);
            y++;
            iTileIndex++;
        }
    }
}
