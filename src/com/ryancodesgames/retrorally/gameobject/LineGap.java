
package com.ryancodesgames.retrorally.gameobject;

import com.ryancodesgames.retrorally.mathlib.Mesh;
import static com.ryancodesgames.retrorally.mathlib.RallyMath.degToRad;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class LineGap 
{
    private Mesh lineGap;
    private float scale = 30;
    private float rotY = degToRad(90);
    private float tY = -0.01f;
    
    public LineGap()
    {
        BufferedImage img = null;
        try
        {
            img = ImageIO.read(new File("./assets/map_data/road/road_gap.bmp"));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        lineGap = new Mesh("./assets/map_data/road/road_gap.obj", img);
        lineGap.transform.setScaleMatrix(30);
        lineGap.transform.setRotAngleY(degToRad(90));
        lineGap.transform.setTranslationMatrix(0, tY, 0);
    }
    
    public void translateGapLine(float x, float y, float z)
    {
        lineGap.transform.setTranslationMatrix(x, y + tY, z);
    }
    
    public Mesh getLineGap()
    {
        return this.lineGap;
    }
}
