
package com.ryancodesgames.retrorally.mathlib;

import static com.ryancodesgames.retrorally.mathlib.RallyMath.Q_rsqrt;

public class Vec3D
{
    public float x, y, z, w;
    
    public Vec3D(float x, float y, float z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = 1;
    }
    
    public Vec3D()
    {
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.w = 1;
    }
    
    public Vec3D vec3f_add(Vec3D v1, Vec3D v2)
    {
        return new Vec3D(v1.x + v2.x, v1.y + v2.y, v1.z + v2.z);
    }
    
    public Vec3D vec3f_sub(Vec3D v1, Vec3D v2)
    {
        return new Vec3D(v1.x - v2.x, v1.y - v2.y, v1.z - v2.z);
    }
    
    public Vec3D vec3f_mul_by_scalar(Vec3D v1, float f)
    {
        return new Vec3D(v1.x * f, v1.y * f, v1.z * f);
    }
    
    public Vec3D vec3f_div_by_scalar(Vec3D v1, float f)
    {
        return new Vec3D(v1.x / f, v1.y / f, v1.z / f);
    }
    
    public float dotProduct(Vec3D v1, Vec3D v2)
    {
        return (v1.x * v2.x + v1.y * v2.y + v1.z * v2.z);
    }
    
    public float length(Vec3D v1)
    {
        return 1 / Q_rsqrt(dotProduct(v1, v1));
    }
    
    public Vec3D normalize(Vec3D v1)
    {
        float l = length(v1);
        
        return new Vec3D(v1.x / l, v1.y / l, v1.z / l);
    }
    
    public Vec3D crossProduct(Vec3D in, Vec3D in2)
    {
        Vec3D out = new Vec3D();
        
        out.x = in.y * in2.z - in.z * in2.y;
        out.y = in.z * in2.x - in.x * in2.z;
        out.z = in.x * in2.y - in.y * in2.x;
        
        return out;
    }
    
    public Vec3D reflect(Vec3D I, Vec3D N)
    {
        return vec3f_sub(I, vec3f_mul_by_scalar(N, dotProduct(N,I)*2));
    }
    
    public Vec3D distance(Vec3D pos, Vec3D target)
    {
        Vec3D distance = vec3f_sub(target, pos);
        distance = normalize(distance);
        
        return distance;
    }
    
    public float dist(Vec3D pos, Vec3D target)
    {
        Vec3D distance = vec3f_sub(target, pos);
        
        float dist = length(distance);
        
        return dist;
    }
    
    public Vec3D inverseVector(Vec3D in)
    {
        Vec3D out = new Vec3D(-in.x, -in.y, -in.z);
        
        return out;
    }

    public boolean compareVector(Vec3D in, Vec3D in2)
    {
        if(in.x != in2.x)
        {
            return false;
        }
        else if(in.y != in2.y)
        {
            return false;
        }
        else return(in.z == in2.z);
    }
    
    public boolean vectorsAreCloseEnough(Vec3D in, Vec3D in2, double err)
    {
        if(Math.abs(in.x - in2.x) < err)
        {
            return true;
        }
        else if(Math.abs(in.y - in2.y) < err)
        {
            return true;
        }
        else return(Math.abs(in.z - in2.z) < err);
    }
    
    public boolean isFacingTowards(Vec3D v1, Vec3D v2)
    {
        return dotProduct(v1, v2) < 0.0f;
    }
    
    public void scale(Vec3D in, double scale, boolean multiply)
    {
        if(multiply)
        {
            in.x *= scale;
            in.y *= scale;
            in.z *= scale;
        }
        else
        {
            in.x /= scale;
            in.y /= scale;
            in.z /= scale;
        }
    }
    
    public Vec3D vectorIntersectPlane(Vec3D plane_p, Vec3D plane_n, Vec3D lineStart, Vec3D lineEnd, ExtraData tt)
    {
        plane_n = normalize(plane_n);
	float plane_d = -dotProduct(plane_n, plane_p);
	float ad = dotProduct(lineStart, plane_n);
	float bd = dotProduct(lineEnd, plane_n);
	float t = (-plane_d-ad)/(bd-ad);
        tt.t = t;
	Vec3D lineStartToEnd = vec3f_sub(lineEnd, lineStart);
	Vec3D lineToIntersect = vec3f_mul_by_scalar(lineStartToEnd, t);
	return vec3f_add(lineStart, lineToIntersect);
    }
    
    public int triangleClipAgainstPlane(Vec3D plane_p, Vec3D plane_n, Triangle in, Triangle[] out)
    {
        plane_n = normalize(plane_n);
        
        Vec3D[] inside_points = {new Vec3D(0,0,0), new Vec3D(0,0,0), new Vec3D(0,0,0)};
        int nInsidePointCount = 0;
        
        Vec3D[] outside_points = {new Vec3D(0,0,0), new Vec3D(0,0,0), new Vec3D(0,0,0)};
        int nOutsidePointCount = 0;
        
        Vec2D[] inside_tex = {new Vec2D(0,0), new Vec2D(0,0), new Vec2D(0,0)};
        int nInsideTexCount = 0;
        
        Vec2D[] outside_tex = {new Vec2D(0,0), new Vec2D(0,0), new Vec2D(0,0)};
        int nOutsideTexCount = 0;
        
        double d0 = dist(in.vec3d, plane_n, plane_p);
        double d1 = dist(in.vec3d2, plane_n, plane_p);
        double d2 = dist(in.vec3d3, plane_n, plane_p);
        
        if (d0 >= 0) { inside_points[nInsidePointCount++] = in.vec3d; inside_tex[nInsideTexCount++] = in.vec2d;}
	else { outside_points[nOutsidePointCount++] = in.vec3d; outside_tex[nOutsideTexCount++] = in.vec2d;}
	if (d1 >= 0) { inside_points[nInsidePointCount++] = in.vec3d2; inside_tex[nInsideTexCount++] = in.vec2d2;}
	else { outside_points[nOutsidePointCount++] = in.vec3d2; outside_tex[nOutsideTexCount++] = in.vec2d2; }
	if (d2 >= 0) { inside_points[nInsidePointCount++] = in.vec3d3; inside_tex[nInsideTexCount++] = in.vec2d3;}
	else { outside_points[nOutsidePointCount++] = in.vec3d3; outside_tex[nOutsideTexCount++] = in.vec2d3;}

        
        if (nInsidePointCount == 0)
	{
            // All points lie on the outside of plane, so clip whole triangle
            // It ceases to exist

            return 0; // No returned triangles are valid
	}

	if (nInsidePointCount == 3)
	{
	// All points lie on the inside of plane, so do nothing
	// and allow the triangle to simply pass through
            out[0] = in;

            return 1; // Just the one returned original triangle is valid
	}
        
        if(nInsidePointCount == 1 && nOutsidePointCount == 2)
        {
            out[0].col = in.col;
            out[0].tex = in.tex;
            out[0].dp = in.dp;
            out[0].vec3d = inside_points[0];
            out[0].vec2d = inside_tex[0];
            
            ExtraData t = new ExtraData(0);

            out[0].vec3d2 = vectorIntersectPlane(plane_p, plane_n, inside_points[0], outside_points[0], t);
            out[0].vec2d2.u = t.t * (outside_tex[0].u - inside_tex[0].u) + inside_tex[0].u;
            out[0].vec2d2.v = t.t * (outside_tex[0].v - inside_tex[0].v) + inside_tex[0].v;
            out[0].vec2d2.w = t.t * (outside_tex[0].w - inside_tex[0].w) + inside_tex[0].w;
            
            out[0].vec3d3 = vectorIntersectPlane(plane_p, plane_n, inside_points[0], outside_points[1], t);
            out[0].vec2d3.u = t.t * (outside_tex[1].u - inside_tex[0].u) + inside_tex[0].u;
            out[0].vec2d3.v = t.t * (outside_tex[1].v - inside_tex[0].v) + inside_tex[0].v;
            out[0].vec2d3.w = t.t * (outside_tex[1].w - inside_tex[0].w) + inside_tex[0].w;
            return 1;
        }
        
        if(nInsidePointCount == 2 && nOutsidePointCount == 1)
        {
            ExtraData t = new ExtraData(0);
            
            out[0].col = in.col;
            out[0].tex = in.tex;
            out[0].dp = in.dp;
            out[0].vec3d = inside_points[0];
            out[0].vec3d2 = inside_points[1];
            out[0].vec2d = inside_tex[0];
            out[0].vec2d2 = inside_tex[1];
            
            out[0].vec3d3 = vectorIntersectPlane(plane_p, plane_n, inside_points[0], outside_points[0], t);
            out[0].vec2d3.u = t.t * (outside_tex[0].u - inside_tex[0].u) + inside_tex[0].u;
            out[0].vec2d3.v = t.t * (outside_tex[0].v - inside_tex[0].v) + inside_tex[0].v;
            out[0].vec2d3.w = t.t * (outside_tex[0].w - inside_tex[0].w) + inside_tex[0].w;
            
            out[1].col = in.col;
            out[1].tex = in.tex;
            out[1].dp = in.dp;
            out[1].vec3d = inside_points[1];
            out[1].vec2d = inside_tex[1];
            out[1].vec3d2 = out[0].vec3d3; 
            out[1].vec2d2 = out[0].vec2d3;
            out[1].vec3d3 = vectorIntersectPlane(plane_p, plane_n, inside_points[1], outside_points[0], t);
            out[1].vec2d3.u = t.t * (outside_tex[0].u - inside_tex[1].u) + inside_tex[1].u;
            out[1].vec2d3.v = t.t * (outside_tex[0].v - inside_tex[1].v) + inside_tex[1].v;
            out[1].vec2d3.w = t.t * (outside_tex[0].w - inside_tex[1].w) + inside_tex[1].w;
            return 2;
        }
        
        return 0;
    }
    
    public double dist(Vec3D p, Vec3D plane_n, Vec3D plane_p)
    {
        return(plane_n.x * p.x + plane_n.y * p.y + plane_n.z * p.z - dotProduct(plane_n, plane_p));
    }
}
