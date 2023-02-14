
package com.ryancodesgames.retrorally.gfx;

import com.ryancodesgames.retrorally.camera.Camera;
import com.ryancodesgames.retrorally.mathlib.Vec3D;

public abstract class Light 
{
    public abstract void setLightDirection(Vec3D light_direction);
    
    public abstract void setLightColor(int col);
    
    public abstract int getLightColor();
    
    public abstract Vec3D getLightDirection();
    
    public abstract float calcLightAttenuation(Vec3D normal, float ambient, Camera camera, Vec3D line2);
}
