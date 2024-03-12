package Main;


import Entity.Entity;


public class collisionChecker {


    GamePanel gp;


    public collisionChecker(GamePanel gp)
    {
        this.gp = gp;


    }
    public void checkTile(Entity entity)
    {
        int entityLeftWorldX = entity.worldX + entity.hitbox.x;
        int entityRightWorldX = entity.worldX + entity.hitbox.x + entity.hitbox.width;
        int entityTopWorldY = entity.worldY + entity.hitbox.y;
        int entityBottomWorldY = entity.worldY + entity.hitbox.y + entity.hitbox.height;


        int entityLeftCol = entityLeftWorldX/gp.tileSize;
        int entityRightCol = entityRightWorldX/gp.tileSize;
        int entityTopRow = entityTopWorldY/gp.tileSize;
        int entityBottomRow = entityBottomWorldY/gp.tileSize;


        int tileNum1, tileNum2;


        if (entity.direction.equals("up")) {
            entityTopRow = (entityTopWorldY - entity.speed)/gp.tileSize;
            tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
            tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
            if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision)
            {
                entity.collisionOn = true;
            }
        }
        if (entity.direction.equals("down")) {
            entityBottomRow = (entityBottomWorldY + entity.speed)/gp.tileSize;
            tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
            tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
            if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision)
            {
                entity.collisionOn = true;
            }
        }
        if (entity.direction.equals("left")) {
            entityLeftCol = (entityLeftWorldX - entity.speed)/gp.tileSize;
            tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
            tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
            if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision)
            {
                entity.collisionOn = true;
            }
        }
        if (entity.direction.equals("right")) {
            entityRightCol = (entityRightWorldX + entity.speed)/gp.tileSize;
            tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
            tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
            if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision)
            {
                entity.collisionOn = true;
            }
        }




    }
    public int checkObject(Entity entity, boolean player)
    {
        int  index = 999;


        for (int i = 0; i < gp.obj[1].length; i++)
        {
            if (gp.obj[gp.currentMap][i] != null)
            {
                // GET ENTITIES SOLID AREA POSITION
                entity.hitbox.x = entity.worldX + entity.hitbox.x;
                entity.hitbox.y = entity.worldY + entity.hitbox.y;


                // GET THE OBJECTS SOLID AREA POSITION
                gp.obj[gp.currentMap][i].hitbox.x = gp.obj[gp.currentMap][i].worldX + gp.obj[gp.currentMap][i].hitbox.x;
                gp.obj[gp.currentMap][i].hitbox.y = gp.obj[gp.currentMap][i].worldY + gp.obj[gp.currentMap][i].hitbox.y;


                switch(entity.direction)
                {
                    case("up"): entity.hitbox.y -= entity.speed; break;
                    case("down"): entity.hitbox.y += entity.speed; break;
                    case("left"): entity.hitbox.x -= entity.speed; break;
                    case("right"): entity.hitbox.x += entity.speed; break;
                }


                if (entity.hitbox.intersects(gp.obj[gp.currentMap][i].hitbox))
                {
                    if (gp.obj[gp.currentMap][i].collision)
                    {
                        entity.collisionOn = true;
                    }
                    if (player)
                    {
                        index = i;
                    }
                }




                entity.hitbox.x = entity.hitboxDefaultX;
                entity.hitbox.y = entity.hitboxDefaultY;
                gp.obj[gp.currentMap][i].hitbox.x = gp.obj[gp.currentMap][i].hitboxDefaultX;
                gp.obj[gp.currentMap][i].hitbox.y = gp.obj[gp.currentMap][i].hitboxDefaultY;




            }
        }
        return index;
    }
    public int checkEntity(Entity entity, Entity[][] target)
    {
        int  index = 999;


        for (int i = 0; i < target[1].length; i++)
        {
            if (target[gp.currentMap][i] != null)
            {
                // GET ENTITIES SOLID AREA POSITION
                entity.hitbox.x = entity.worldX + entity.hitbox.x;
                entity.hitbox.y = entity.worldY + entity.hitbox.y;


                // GET THE OBJECTS SOLID AREA POSITION
                target[gp.currentMap][i].hitbox.x = target[gp.currentMap][i].worldX + target[gp.currentMap][i].hitbox.x;
                target[gp.currentMap][i].hitbox.y = target[gp.currentMap][i].worldY + target[gp.currentMap][i].hitbox.y;


                switch(entity.direction)
                {
                    case("up"): entity.hitbox.y -= entity.speed; break;
                    case("down"): entity.hitbox.y += entity.speed; break;
                    case("left"): entity.hitbox.x -= entity.speed; break;
                    case("right"): entity.hitbox.x += entity.speed; break;
                }
                if (entity.hitbox.intersects(target[gp.currentMap][i].hitbox))
                {
                    if (target[gp.currentMap][i] != entity) {
                        entity.collisionOn = true;
                        index = i;
                    }
                }
                entity.hitbox.x = entity.hitboxDefaultX;
                entity.hitbox.y = entity.hitboxDefaultY;
                target[gp.currentMap][i].hitbox.x = target[gp.currentMap][i].hitboxDefaultX;
                target[gp.currentMap][i].hitbox.y = target[gp.currentMap][i].hitboxDefaultY;




            }
        }
        return index;
    }
    public boolean checkPlayer(Entity entity)
    {
        boolean contactPlayer = false;
        // GET ENTITIES SOLID AREA POSITION
        entity.hitbox.x = entity.worldX + entity.hitbox.x;
        entity.hitbox.y = entity.worldY + entity.hitbox.y;


        // GET THE OBJECTS SOLID AREA POSITION
        gp.player.hitbox.x = gp.player.worldX + gp.player.hitbox.x;
        gp.player.hitbox.y = gp.player.worldY + gp.player.hitbox.y;


        switch(entity.direction)
        {
            case("up"): entity.hitbox.y -= entity.speed; break;
            case("down"): entity.hitbox.y += entity.speed; break;
            case("left"): entity.hitbox.x -= entity.speed; break;
            case("right"): entity.hitbox.x += entity.speed; break;
        }


        if (entity.hitbox.intersects(gp.player.hitbox))
        {
            entity.collisionOn = true;
            contactPlayer = true;
        }
        entity.hitbox.x = entity.hitboxDefaultX;
        entity.hitbox.y = entity.hitboxDefaultY;
        gp.player.hitbox.x = gp.player.hitboxDefaultX;
        gp.player.hitbox.y = gp.player.hitboxDefaultY;


        return contactPlayer;
    }
}
