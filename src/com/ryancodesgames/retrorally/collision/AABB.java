
package com.ryancodesgames.retrorally.collision;

import com.ryancodesgames.retrorally.mathlib.Mat4x4;
import com.ryancodesgames.retrorally.mathlib.Vec3D;
import java.util.ArrayList;
import java.util.List;

public class AABB 
{
     private Vec3D min; 
    private Vec3D max;
    
    private List<Vec3D> points = new ArrayList<>();
    
    public AABB()
    {
        min = new Vec3D();
        max = new Vec3D();
    }
    
    public boolean intersect(AABB o)
    {
        return(getMinX() <= o.getMaxX() && getMaxX() >= o.getMinX()) &&
        (getMinY() <= o.getMaxY() && getMaxY() >= o.getMinY()) &&
        (getMinZ() <= o.getMaxZ() && getMaxZ() >= o.getMinZ());
    }
    
    public void sort()
    {
        for(int i = 0; i < points.size(); i++)
        {
            addPoint(points.get(i));
        }
    }
    
    public void addPoint(Vec3D p)
    {
        if(p.x < min.x) min.x = p.x;
        if(p.x > max.x) max.x = p.x;
        if(p.y < min.x) min.y = p.y;
        if(p.y > max.x) max.y = p.y;
        if(p.z < min.x) min.z = p.z;
        if(p.z > max.x) max.z = p.z;
    }
    
    public void setPoints(List<Vec3D> points)
    {
        this.points = points;
    }
    
    public void updateAABB(Mat4x4 matWorld, Mat4x4 matView)
    {
        Mat4x4 mat = new Mat4x4();
        
        min =  mat.multiplyMatrixVector(min, matWorld);
        max =  mat.multiplyMatrixVector(max, matWorld);
        min =  mat.multiplyMatrixVector(min, matView);
        max =  mat.multiplyMatrixVector(max, matView);
    }
    
    public Vec3D getMinPoint()
    {
        return min;
    }
    
    public Vec3D getMaxPoint()
    {
        return max;
    }
    
    public float getMinX()
    {
        return min.x;
    }
    
    public float getMinY()
    {
        return min.y;
    }
    
    public float getMinZ()
    {
        return min.z;
    }
    
    public float getMaxX()
    {
        return max.x;
    }
    
    public float getMaxY()
    {
        return max.y;
    }
    
    public float getMaxZ()
    {
        return max.z;
    }
}
