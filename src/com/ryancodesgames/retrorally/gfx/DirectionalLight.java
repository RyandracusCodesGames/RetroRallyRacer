
package com.ryancodesgames.retrorally.gfx;

import com.ryancodesgames.retrorally.camera.Camera;
import static com.ryancodesgames.retrorally.mathlib.RallyMath.max;
import com.ryancodesgames.retrorally.mathlib.Vec3D;

public class DirectionalLight extends Light
{
    private Vec3D light_direction;
    private int light_color;
    private final boolean hasLight;
    
    public DirectionalLight(Vec3D light_direction, int light_color, boolean hasLight)
    {
        this.light_direction = light_direction;
        this.light_color = light_color;
        this.hasLight = hasLight;
    }
    
    @Override
    public void setLightDirection(Vec3D light_direction)
    {
        this.light_direction = light_direction;
    }
    
    @Override
    public void setLightColor(int col)
    {
        this.light_color = col;
    }
    
    @Override
    public  int getLightColor()
    {
        return this.light_color;
    }
    
    @Override
    public  Vec3D getLightDirection()
    {
        return this.light_direction;
    }
    
    public boolean hasLight()
    {
        return this.hasLight;
    }

    public float calcLightAttenuation(Vec3D normal, float ambient, Camera camera, Vec3D line2)
    {
        Vec3D z = new Vec3D();
        
        light_direction = z.normalize(light_direction);
        
        return max(z.dotProduct(light_direction, normal), ambient);
    }
}
