
package com.ryancodesgames.retrorally;

import com.ryancodesgames.retrorally.gfx.slWindow;
import com.ryancodesgames.retrorally.gfx.slWindow.TV_RES;

public class RetroRallyRacer {
    
    private static final int WIDTH = 800;
    
    private static final int HEIGHT = 600;
    
    private static final String TITLE = "Retro Rally Racer Tech Demo";
    
    private static slWindow slWindow;

    public static void main(String[] args) 
    {
        slWindow = new slWindow(WIDTH, HEIGHT, TITLE, false);
        slWindow.slInitSystem(TV_RES.TV_320x224, null, 1);
    }
    
    public static int getFrameWidth()
    {
        return WIDTH;
    }
    
    public static int getFrameHeight()
    {
        return HEIGHT;
    }
    
    public static int getImageWidth()
    {
        return slWindow.getScreenWidth();
    }
    
    public static int getImageHeight()
    {
        return slWindow.getScreenHeight();
    }
    
    public static int getFPS()
    {
        return slWindow.getFPS();
    }
}
