
package com.ryancodesgames.retrorally.gfx;

import com.ryancodesgames.retrorally.camera.Camera;
import static com.ryancodesgames.retrorally.mathlib.RallyMath.clamp;
import static com.ryancodesgames.retrorally.mathlib.RallyMath.degToRad;
import static com.ryancodesgames.retrorally.mathlib.RallyMath.max;
import com.ryancodesgames.retrorally.mathlib.Vec3D;

public class SpotLight extends PointLight
{
    private Vec3D light_direction;
    private Vec3D pos;
    private int color;
    private boolean hasColor;
    private float constant;
    private float linear;
    private float quadratic;
    private float cutOff;
    private float outerCutOff;
    private float theta;
    private float epsilon;

    public SpotLight(Vec3D light_direction, Vec3D pos, float constant, float linear, float quadratic, int color, boolean hasColor, 
    float cutOff, float outerCutOff, float theta, float epsilon)
    {
        super(light_direction, pos, constant, linear, quadratic, color, hasColor);
        
        this.cutOff = cutOff;
        this.outerCutOff = outerCutOff;
        this.theta = theta;
        this.epsilon = epsilon;
    }
    
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
        // spot light (soft edges)
        theta =  line1.dotProduct(dir, line1.normalize(light_direction));
        epsilon = degToRad(12.5f) - 0.82f;
        
        float intensity = clamp((theta - 0.82f) / epsilon, 0.0f, 1.0f);
        
        diffuse *= intensity;

        return diffuse;
    }
}
