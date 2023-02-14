
package com.ryancodesgames.retrorally.gameobject;

import com.ryancodesgames.retrorally.mathlib.Mesh;
import static com.ryancodesgames.retrorally.mathlib.RallyMath.degToRad;
import com.ryancodesgames.retrorally.mathlib.Triangle;
import com.ryancodesgames.retrorally.mathlib.Vec2D;
import com.ryancodesgames.retrorally.mathlib.Vec3D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;

public class TunnelSide 
{
    private Mesh tunnelSide;
    private float x  = 0, y = 0, z = 0;
    
    public TunnelSide(int i, float width, float height, float depth)
    {
        BufferedImage img = null;
        try 
        {
            switch(i)
            {
                case 1:
                    img = ImageIO.read(new File("./assets/map_data/tunnel/tunnel_side.bmp"));
                break;
                case 2:
                    img = ImageIO.read(new File("./assets/map_data/tunnel/tunnel_top_side.bmp"));
                break;
                case 3:
                    img = ImageIO.read(new File("./assets/map_data/tunnel/tunnel_top_side2.bmp"));
                break;
                default: img = ImageIO.read(new File("./assets/map_data/tunnel/tunnel_side.bmp"));
                break;
            }
        } catch (IOException ex) 
        {
            ex.printStackTrace();
        }
        
        tunnelSide = new Mesh(Arrays.asList(new Triangle[]{
        //SOUTH
        new Triangle(new Vec3D(x, y, z), new Vec3D(x, y + height, z), new Vec3D(x + width, y + height, z), new Vec2D(0,0), new Vec2D(0,1), new Vec2D(1,1)),
        new Triangle(new Vec3D(x, y, z), new Vec3D(x + width, y + height, z), new Vec3D(x + width, y, z), new Vec2D(0,0), new Vec2D(1, 1), new Vec2D(1,0)),
        //EAST
        new Triangle(new Vec3D(x + width, y, z), new Vec3D(x + width, y + height, z), new Vec3D(x + width, y + height, z + depth), new Vec2D(0,0), new Vec2D(0, 1), new Vec2D(1, 1)),
        new Triangle(new Vec3D(x + width, y, z), new Vec3D(x + width, y + height, z + depth), new Vec3D(x + width, y, z + depth),  new Vec2D(0,0), new Vec2D(1, 1), new Vec2D(1, 0)),
        //NORTH
        new Triangle(new Vec3D(x + width, y, z  + depth), new Vec3D(x  + width, y + height, z + depth), new Vec3D(x, y + height, z + depth), new Vec2D(0,0), new Vec2D(0, 1), new Vec2D(1,1)),
        new Triangle(new Vec3D(x + width, y, z + depth), new Vec3D(x, y + height, z + depth), new Vec3D(x, y, z + depth), new Vec2D(0,0), new Vec2D(1, 1), new Vec2D(1, 0)),
        //WEST
        new Triangle(new Vec3D(x, y, z + depth), new Vec3D(x, y + height, z + depth), new Vec3D(x, y + height, z), new Vec2D(0,0), new Vec2D(0,1), new Vec2D(1,1)),
        new Triangle(new Vec3D(x, y, z + depth), new Vec3D(x, y + height, z), new Vec3D(x, y, z),new Vec2D(0,0), new Vec2D(1,1), new Vec2D(1,0)),
        //TOP
        new Triangle(new Vec3D(x, y + height, z), new Vec3D(x, y + height, z + depth), new Vec3D(x + width, y + height, z + depth), new Vec2D(0,0), new Vec2D(0, 1), new Vec2D(1, 1)),
        new Triangle(new Vec3D(x, y + height, z), new Vec3D(x + width, y + height, z + depth), new Vec3D(x + width, y + height, z), new Vec2D(0,0), new Vec2D(1, 1), new Vec2D(1, 0)),
        //BOTTOM
        new Triangle(new Vec3D(x + width, y, z + depth), new Vec3D(x, y, z + depth), new Vec3D(x, y, z), new Vec2D(0,0), new Vec2D(0, 1), new Vec2D(1,1)),
        new Triangle(new Vec3D(x + width, y, z + depth), new Vec3D(x, y, z), new Vec3D(x + width, y, z), new Vec2D(0,0), new Vec2D(1, 1), new Vec2D(1,0))
        }), img);
        
        tunnelSide.transform.setRotAngleY(degToRad(90));
    }
    
    public Mesh getTunnelSide()
    {
        return this.tunnelSide;
    }
}
