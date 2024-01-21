package Objects;

import Main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Scroll extends SuperObject{
    GamePanel gp;
    public OBJ_Scroll(GamePanel gp)
    {
        name = "Scroll";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/objects/Scroll.png"));
            uTool.scaleImage(image, gp.tileSize , gp.tileSize);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
