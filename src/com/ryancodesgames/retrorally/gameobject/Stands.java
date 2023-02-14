
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

public class Stands
{
    private Mesh stands;
    private float x  = 0, y = 0, z = 0;
    
    public Stands(float width, float height, float depth)
    {
        BufferedImage img = null;
        try 
        {
            img = ImageIO.read(new File("./assets/map_data/bleachers/stands.bmp"));
        } catch (IOException ex) 
        {
            ex.printStackTrace();
        }
        
        stands = new Mesh(Arrays.asList(new Triangle[]{
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
        
        stands.transform.setRotAngleY(degToRad(90));
        
        for(Triangle t: stands.triangles)
        {
            t.vec2d.scale(4, true);
            t.vec2d2.scale(4, true);
            t.vec2d3.scale(4, true);
        }
    }
    
    public Mesh getStands()
    {
        return this.stands;
    }
}
