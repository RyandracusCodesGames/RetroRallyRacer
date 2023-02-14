
package com.ryancodesgames.retrorally.mathlib;

public class Vec2D 
{
    public float u, v, w;
    
    public Vec2D(float u, float v)
    {
        this.u = u;
        this.v = v;
        this.w = 1;
    }
    
    public Vec2D()
    {
        this.u = 0;
        this.v = 0;
        this.w = 1;
    }
    
    public void scale(float f, boolean multiply)
    {
        if(multiply)
        {
            this.u *= f;
            this.v *= f;
        }
        else
        {
            this.u /= v;
            this.v /= v;
        }
    }
    
    @Override
    public Object clone()
    {
        return new Vec2D(u, v);
    }
}
