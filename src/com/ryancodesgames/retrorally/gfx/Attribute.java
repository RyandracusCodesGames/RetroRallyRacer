
package com.ryancodesgames.retrorally.gfx;

public class Attribute 
{
    private DrawMode dm;
    private Culling cull;
    private ZSort zSort;
    private LIGHTING light;
    private FOG fog;
    
    public Attribute(DrawMode dm, Culling cull, ZSort zSort, LIGHTING light, FOG fog)
    {
        this.dm = dm;
        this.cull = cull;
        this.zSort = zSort;
        this.light = light;
        this.fog = fog;
    }
    
    public static enum DrawMode
    {
        WIREFRAME,
        SURFACE,
        TEXTURED;
    }
    
    public static enum Culling
    {
        BACK_FACE_CULLING,
        FRONT_FACE_CULLING,
        NO_CULLING;
    }
    
    public static enum ZSort
    {
        ZSORT,
        Z_BUFFER;
    }
    
    public static enum LIGHTING
    {
        DIRECTIONAL_LIGHTING,
        SPOT_LIGHTING,
        POINT_LIGHTING,
        NO_LIGHITNG;
    }
    
    public static enum FOG
    {
        FOG_ON,
        FOG_OFF;
    }
    
    public static enum SHADING
    {
        
    }
    
    public DrawMode getDrawMode()
    {
        return this.dm;
    }
    
    public Culling getCulling()
    {
        return this.cull;
    }
    
    public ZSort getZSort()
    {
        return this.zSort;
    }
    
    public FOG getFog()
    {
        return this.fog;
    }
    
}
