
package com.ryancodesgames.retrorally.gfx;

import com.ryancodesgames.retrorally.camera.Camera;
import static com.ryancodesgames.retrorally.mathlib.RallyMath.clamp;
import static com.ryancodesgames.retrorally.mathlib.RallyMath.degToRad;
import static com.ryancodesgames.retrorally.mathlib.RallyMath.max;
import com.ryancodesgames.retrorally.mathlib.Vec3D;

public class PointLight extends Light
{
    private Vec3D light_direction;
    private final Vec3D pos;
    private final float constant;
    private final float linear;
    private final float quadratic;
    private int light_color;
    private final boolean hasColor;
    
    public PointLight(Vec3D light_direction, Vec3D pos, float constant, float linear, float quadratic, int light_color, boolean hasColor)
    {
        this.light_direction = light_direction;
        this.pos = pos;
        this.constant = constant;
        this.linear = linear;
        this.quadratic = quadratic;
        this.light_color = light_color;
        this.hasColor = hasColor;
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
    public int getLightColor()
    {
        return this.light_color;
    }
    
    @Override
    public Vec3D getLightDirection()
    {
        return this.light_direction;
    }
    
    @Override
    public float calcLightAttenuation(Vec3D normal, float ambient, Camera camera, Vec3D line2)
    {
        Vec3D line1 = new Vec3D();
        
        Vec3D dir = line1.vec3f_sub(light_direction, pos);
        float distance = line1.length(dir);
        dir = line1.normalize(dir);

        float diffuse = max(ambient, line1.dotProduct(dir, normal));

        Vec3D reflectDir = line1.reflect(dir, normal);
        Vec3D viewDir = line1.normalize(line1.vec3f_sub(camera.getCamera(), line2));

        float spec = (float)Math.pow(max(line1.dotProduct(viewDir, reflectDir), ambient), 256);

        float attenuation = (1.0f / (constant + (linear * distance) +
                  (quadratic * distance * distance)));

        spec *= attenuation;

        diffuse *= attenuation;

        return diffuse;
    }
}
