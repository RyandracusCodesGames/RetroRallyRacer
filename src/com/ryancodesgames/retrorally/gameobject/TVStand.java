
package com.ryancodesgames.retrorally.gameobject;

import com.ryancodesgames.retrorally.mathlib.Mesh;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class TVStand 
{
    private Mesh TV_Stand;
    
    public TVStand()
    {
        BufferedImage img = null;
        try
        {
            img = ImageIO.read(new File("./assets/map_data/tv/tv.bmp"));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        TV_Stand = new Mesh("./assets/map_data/tv/tv.obj",img);
    }
    
    public Mesh getTVStand()
    {
        return this.TV_Stand;
    }
}
