
package com.ryancodesgames.retrorally.gameobject;

import com.ryancodesgames.retrorally.mathlib.Mesh;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Dome 
{
    private Mesh dome;
    
    public Dome()
    {
        BufferedImage img = null;
        try 
        {
            img = ImageIO.read(new File("./assets/map_data/dome/dome.bmp"));
        } catch (IOException ex) 
        {
            ex.printStackTrace();
        }
        
        dome = new Mesh("./assets/map_data/dome/dome.obj", img);
    }
    
    public Mesh getDome()
    {
        return this.dome;
    }
}
