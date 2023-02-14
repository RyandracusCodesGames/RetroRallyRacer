
package com.ryancodesgames.retrorally.gfx;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Sprite 
{
     private int width;
    private int height;
    private int[] texArray;
    
    public Sprite(BufferedImage img)
    {
        this.width = img.getWidth();
        this.height = img.getHeight();
        
        if(img.getType() != BufferedImage.TYPE_INT_RGB)
        {
            BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = newImage.createGraphics();
            g.drawImage(img, 0, 0, null);
            g.dispose();
            img = newImage;
        }
        
        texArray = ((DataBufferInt)img.getRaster().getDataBuffer()).getData();
    }
    
    public int getWidth()
    {
        return width;
    }
    
    public int getHeight()
    {
        return height;
    }
    
    public int[] getColorArray()
    {
        return texArray;
    }
    
    public int getPixel(int x, int y)
    {
        return texArray[x + y * width];
    }
    
    public void setPixel(int x, int y, int col)
    {
        texArray[x + y * width] = col;
    }
}
