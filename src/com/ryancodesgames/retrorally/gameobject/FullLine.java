
package com.ryancodesgames.retrorally.gameobject;

import com.ryancodesgames.retrorally.mathlib.Mesh;
import static com.ryancodesgames.retrorally.mathlib.RallyMath.degToRad;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class FullLine 
{
    private Mesh fullLine;
    private float scale = 30;
    private float rotY = degToRad(90);
    private float tY = -0.01f;
    
    public FullLine()
    {
        BufferedImage img = null;
        try
        {
            img = ImageIO.read(new File("./assets/map_data/road/road_full.bmp"));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        fullLine = new Mesh("./assets/map_data/road/road_full.obj", img);
        fullLine.transform.setScaleMatrix(30);
        fullLine.transform.setRotAngleY(degToRad(90));
        fullLine.transform.setTranslationMatrix(0, tY, 0);
    }
    
    public void translateGapLine(float x, float y, float z)
    {
        fullLine.transform.setTranslationMatrix(x, y + tY, z);
    }
    
    public Mesh getFullLine()
    {
        return this.fullLine;
    }
}
