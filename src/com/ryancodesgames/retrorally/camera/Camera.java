
package com.ryancodesgames.retrorally.camera;

import com.ryancodesgames.retrorally.mathlib.Mat4x4;
import com.ryancodesgames.retrorally.mathlib.Vec3D;

public class Camera 
{
    private Vec3D cam;
    private Vec3D vLookDir;
    private Vec3D vUp;
    private Vec3D vTarget;  
    private Mat4x4 matCameraRot;
    private Mat4x4 viewMatrix;
    
    public Camera(float x, float y, float z)
    {
        cam = new Vec3D(0,0,0);
        
        cam.x = x;
        cam.y = y;
        cam.z = z;
    }
    
    public Camera()
    {
        cam = new Vec3D(0,0,0);
    }
 
    public Vec3D getCamera()
    {
        return cam;
    }
    
    public void setLookDir(Vec3D lookDir)
    {
        this.vLookDir = lookDir;
    }
    
    public void setUpDir(Vec3D upDir)
    {
        this.vUp = upDir;
    }
    
    public void setTargDir(Vec3D targDir)
    {
        this.vTarget = targDir;
    }
    
    public void setForwardDirection(Vec3D vFoward)
    {
        cam = cam.vec3f_add(cam, vFoward);
    }
    
    public void setMatCameraRot(Mat4x4 matCameraRot)
    {
        this.matCameraRot = matCameraRot;
    }
    
    public void setForwardDirectionBack(Vec3D vFoward)
    {
        cam = cam.vec3f_sub(cam, vFoward);
    }
    
    public void addCameraX(double f)
    {
        cam.x += f;
    }
    
    public void addCameraY(double f)
    {
        cam.y += f;
    }
    
    public void addCameraZ(double f)
    {
        cam.z += f;
    }
    
    public void subtractCameraX(double f)
    {
        cam.x -= f;
    }
    
    public void subtractY(double f)
    {
        cam.y -= f;
    }
    
    public void subtractZ(double f)
    {
        cam.z -= f;
    }
    
    public void setCamera(Vec3D camera)
    {
        cam.x = camera.x;
        cam.y = camera.y;
        cam.z = camera.z;
    }
    
    public void setX(float f)
    {
        cam.x = f;
    }
    
    public void setY(float f)
    {
        cam.y = f;
    }
     
    public void setZ(float f)
    {
        cam.z = f;
    }

    public float getX()
    {
        return cam.x;
    }
    
    public float getY()
    {
        return cam.y;
    }
     
    public float getZ()
    {
        return cam.z;
    }
}
