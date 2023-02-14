
package com.ryancodesgames.retrorally.gfx;

public class ColorUtils 
{
    //CLASS MEANT TO BE FAST, COMPACT STORAGE UNIT FOR CONTAINING
    //AND MANIPULATING COLOR VALUES OF A PARTICULAR PIXEL IN JAVA.
    public static final int CD_BLACK = 0x000000;
    
    public static final int CD_WHITE = 0xffffff;
    
    public static final int CD_RED = 0xff0000;
    
    public static final int CD_GREEN = 0x00ff00;
    
    public static final int CD_BLUE = 0x0000ff;
    
    public static final int CD_ORANGE = 0xFFA500;
    
    public static final int CD_GRAY = 0xFF808080;
    
    public static final int CD_YELLOW = -256;
    
    public static int mix(int col, int col2)
    {
        int i1 = col;
        int i2 = col2;
        
        int a1 = getA(i1);
        int r1 = getR(i1);
        int g1 = getG(i1);
        int b1 = getB(i1);

        int a2 = getA(i2);
        int r2 = getR(i2);
        int g2 = getG(i2);
        int b2 = getB(i2);

        int a = a1 + a2;
        int r = r1 + r2;
        int g = g1 + g2;
        int b = b1 + b2;

        return a << 24 | r << 16 | g << 8 | b ;
    }
    
    public static int multiply(int col, int col2)
    {
        int i1 = col;
        int i2 = col2;
        
        int a1 = getA(i1);
        int r1 = getR(i1);
        int g1 = getG(i1);
        int b1 = getB(i1);

        int a2 = getA(i2);
        int r2 = getR(i2);
        int g2 = getG(i2);
        int b2 = getB(i2);

        int a = a1 * a2;
        int r = r1 * r2;
        int g = g1 * g2;
        int b = b1 * b2;

        return a << 24 | r << 16 | g << 8 | b ;
    }
    
    public static int dotColor(int col, float factor)
    {
        int i1 = col;//blend(col, blend(col, CD_RED, factor), factor);
        
        int a1 = getA(i1);
        int r1 = getR(i1);
        int g1 = getG(i1);
        int b1 = getB(i1);
        
        int a = (int)(a1 * factor);
        int r = (int)(r1 * factor);
        int g = (int)(g1 * factor);
        int b = (int)(b1 * factor);
        
        return a << 24 | r << 16 | g << 8 | b;
    }
    
    public static int blend(int col, int col2, float ratio)
    {
        if ( ratio > 1f ) ratio = 1f;
        else if ( ratio < 0f ) ratio = 0f;
        float iRatio = 1.0f - ratio;

        int i1 = col;
        int i2 = col2;

        int a1 = getA(i1);
        int r1 = getR(i1);
        int g1 = getG(i1);
        int b1 = getB(i1);

        int a2 = getA(i2);
        int r2 = getR(i2);
        int g2 = getG(i2);
        int b2 = getB(i2);

        int a = (int)((a1 * iRatio) + (a2 * ratio));
        int r = (int)((r1 * iRatio) + (r2 * ratio));
        int g = (int)((g1 * iRatio) + (g2 * ratio));
        int b = (int)((b1 * iRatio) + (b2 * ratio));

        return a << 24 | r << 16 | g << 8 | b ;
    }
    
    public static int getA(int col)
    {
        return (col >> 24 & 0xff);
    }
    
    public static int getR(int col)
    {
        return ((col & 0xff0000) >> 16);
    }
    
    public static int getG(int col)
    {
        return ((col & 0xff00) >> 8);
    }
    
    public static int getB(int col)
    {
        return (col & 0xff);
    }
}
