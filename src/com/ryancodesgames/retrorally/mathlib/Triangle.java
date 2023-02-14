
package com.ryancodesgames.retrorally.mathlib;

import com.ryancodesgames.retrorally.gfx.Texture;
import java.awt.Color;

public class Triangle 
{
    public Vec3D vec3d, vec3d2, vec3d3;
    
    public Vec2D vec2d, vec2d2, vec2d3;
    
    public float dp;
    
    public Texture tex;
    
    public Color col;
    
    public int color;
    
    public Triangle(Vec3D vec3d, Vec3D vec3d2, Vec3D vec3d3, Vec2D vec2d, Vec2D vec2d2, Vec2D vec2d3)
    {
        this.vec3d = vec3d;
        this.vec3d2 = vec3d2;
        this.vec3d3 = vec3d3;
        this.vec2d = vec2d;
        this.vec2d2 = vec2d2;
        this.vec2d3 = vec2d3;
    }
    
    public Triangle(Vec3D vec3d, Vec3D vec3d2, Vec3D vec3d3)
    {
        this.vec3d = vec3d;
        this.vec3d2 = vec3d2;
        this.vec3d2 = vec3d3;
    }
    
    public Triangle()
    {
        this.vec3d = new Vec3D();
        this.vec3d2 = new Vec3D();
        this.vec3d3 = new Vec3D();
        this.vec2d = new Vec2D(0,0);
        this.vec2d2 = new Vec2D(0,0);
        this.vec2d3 = new Vec2D(0,0);
    }
    
    public int setColor(int r, int g, int b)
    {
        this.col = new Color(r, g, b);
        this.color = col.getRGB();
        
        return color;
    }
}
