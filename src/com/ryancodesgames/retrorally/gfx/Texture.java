
package com.ryancodesgames.retrorally.gfx;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import javax.imageio.ImageIO;

public class Texture
{
    private final int width;
    private final int height;
    private int widthMask;
    private int heightMask;
    private int widthShift;
    private int[] texArray;
    
    public Texture(BufferedImage img)
    {
        this.width = img.getWidth();
        this.height = img.getHeight();
        
        if(img.getType() != BufferedImage.TYPE_INT_RGB)
        {
            BufferedImage newImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
             Graphics2D g = newImage.createGraphics();
                g.drawImage(img, 0, 0, null);
                g.dispose();
                img = newImage;
        }
        
        
        texArray = ((DataBufferInt)img.getRaster().getDataBuffer()).getData();
        widthMask = img.getWidth() - 1;
        heightMask = img.getHeight() - 1;
        widthShift = countbits(getWidth()-1);
    }
    
    public void createTexture(String fileName)
    {
       BufferedImage img = new BufferedImage(0, 0, BufferedImage.TYPE_INT_RGB);
        try
        {
            img = ImageIO.read(getClass().getResource(fileName));
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        if(!isPowerOfTwo(img.getWidth()) || !isPowerOfTwo(img.getHeight()))
        {
            throw new IllegalArgumentException("Current texture width or height is not a power of 2 which slows down performance. Resize to power of two");
        }
        
        if (img.getType() != BufferedImage.TYPE_INT_RGB) 
        {
        BufferedImage newImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
                Graphics2D g = newImage.createGraphics();
                g.drawImage(img, 0, 0, null);
                g.dispose();
                img = newImage;
        }
        texArray = ((DataBufferInt)img.getRaster().getDataBuffer()).getData();
        widthMask = img.getWidth() - 1;
        heightMask = img.getHeight() - 1;
        widthShift = countbits(getWidth()-1);
    }
    
    public int getWidth()
    {
        return width;
    }
    
    public int getHeight()
    {
        return height;
    }
    
    public int[] getTexArray()
    {
        return texArray;
    }
    
    public int getWidthMask()
    {
        return widthMask;
    }
    
    public int getHeightMask()
    {
        return heightMask;
    }
    
    public int getPixel(int x, int y)
    {
        return texArray[x + y * getWidth()];
    }
    
    public int setPixel(int x, int y, int col)
    {
        return texArray[x + y * getWidth()] = col;
    }

    public int getWidthShift()
    {
        return widthShift;
    }
    
    public static boolean isPowerOfTwo(int n) 
    {
        return ((n & (n-1)) == 0);
    }
    /**
        Counts the number of "on" bits in an integer.
    */
    public static int countbits(int n) 
    {
        int count = 0;
        while (n > 0) {
            count+=(n & 1);
            n>>=1;
        }
        return count;
    }
}
