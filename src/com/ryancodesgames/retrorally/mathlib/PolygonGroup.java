
package com.ryancodesgames.retrorally.mathlib;

import java.util.ArrayList;
import java.util.List;

public class PolygonGroup 
{
    private List<Mesh> polygon = new ArrayList<>();
    
    public void setTransform(Transformation t, int i)
    {
        polygon.get(i).transform = t;
    }
    
    public void addMesh(Mesh m)
    {
        polygon.add(m);
    }
    
    public void removeMesh(Mesh m)
    {
        polygon.remove(m);
    }
    
    public List<Mesh> getPolyGroup()
    {
        return this.polygon;
    }
}
