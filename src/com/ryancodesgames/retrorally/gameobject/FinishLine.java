
package com.ryancodesgames.retrorally.gameobject;

import com.ryancodesgames.retrorally.mathlib.Mesh;
import static com.ryancodesgames.retrorally.mathlib.RallyMath.degToRad;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class FinishLine 
{
    private Mesh finishLine;
    private float scale = 30;
    private float rotY = degToRad(90);
    private float tY = -0.01f;
    
    public FinishLine()
    {
        BufferedImage img = null;
        try 
        {
            img = ImageIO.read(new File("./assets/map_data/road/road_finish_line.bmp"));
        } catch (IOException ex) 
        {
            ex.printStackTrace();
        }
        
        finishLine = new Mesh("./assets/map_data/road/road_finish_line.obj", img);
        
        finishLine.transform.setScaleMatrix(30);
        finishLine.transform.setRotAngleY(degToRad(90));
        finishLine.transform.setTranslationMatrix(0, tY, 0);
    }
    
    public void translateFinishLine(float x, float y, float z)
    {
        finishLine.transform.setTranslationMatrix(x, y + tY, z);
    }
    
    public Mesh getFinishLine()
    {
        return this.finishLine;
    }
}
