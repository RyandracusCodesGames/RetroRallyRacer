
package com.ryancodesgames.retrorally.gfx;

import com.ryancodesgames.retrorally.GamePanel;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class slWindow 
{
    /**
     * The width of the window.
     */
    private final int WIDTH;
    /**
     * The height of the window.
     */
    private final int HEIGHT;
    /**
     * The title of the display window.
     */
    private final String TITLE;
    /**
     * The window is in full screen mode.
     */
    private final boolean FULLSCREEN;
    /**
     * The screen resolution of the display.
     */
    private TV_RES screenResolution;
     /**
     * The FPS of the display.
     */
    private int FPS;
    
    private JFrame jframe;
    
    /**
     * The varying screen resolutions of different 5th generation consoles.
     */
    public enum TV_RES
    {
        TV_320x224, /*The screen resolution of the Sega Saturn*/
        TV_320x240, /*The screen resolution of the original PlayStation*/
        TV_240x160, /*The screen resolution of the Gameboy Advance*/
        TV_640x480, /*The screen resolution of the Nintendo 64*/
        TV_256x192  /*The screen resolution of the Nintendo DS*/
    }
    
    public slWindow(int width, int height, String title, boolean fullScreen)
    {
        this.WIDTH = width;
        this.HEIGHT = height;
        this.TITLE = title;
        this.FULLSCREEN = fullScreen;
    }
    
    public slWindow()
    {
        this.WIDTH = 800;
        this.HEIGHT = 600;
        this.TITLE = "Retro Rally Racer Tech Demo (Sega Saturn)";
        this.FULLSCREEN = false;
    }
    
    public void slInitSystem(TV_RES screenRes, Image img, int fps)
    {
        this.screenResolution = screenRes;
        this.FPS = fps;
        
        GamePanel gp = new GamePanel();
        
        Image rallyIcon = new ImageIcon("retro_icon.png").getImage();
        
        jframe = new JFrame();
        jframe.setSize(WIDTH, HEIGHT);
        jframe.setResizable(false);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setTitle(TITLE);
        jframe.setLocationRelativeTo(null);
        jframe.setIconImage(rallyIcon);
        jframe.add(gp);
        
        if(FULLSCREEN)
        {
            GraphicsDevice graphics = GraphicsEnvironment.getLocalGraphicsEnvironment()
            .getDefaultScreenDevice();
            
            graphics.setFullScreenWindow(jframe);
            graphics.setDisplayMode(new DisplayMode(WIDTH, HEIGHT, 32, 60));
        }
        
        jframe.setVisible(true);
        
        gp.startGameThread();
    }
    
    public int getScreenWidth()
    {
        int width = 320;
        switch(screenResolution)
        {
            case TV_320x224:
                width = 320;
            break;
            case TV_320x240:
                width = 320;
            break;
            case TV_240x160:
                width = 240;
            break;
            case TV_640x480:
                width = 640;
            break;
            case TV_256x192:
                width = 256;
            break;
        }

        return width;
    }
    
    public int getScreenHeight()
    {
        int height = 224;
        switch(screenResolution)
        {
            case TV_320x224:
                height = 224;
            break;
            case TV_320x240:
                height = 240;
            break;
            case TV_240x160:
                height = 160;
            break;
            case TV_640x480:
                height = 480;
            break;
            case TV_256x192:
                height = 192;
            break;
        }

        return height;
    }
    
    public int getFPS()
    {
        return this.FPS;
    }
}
