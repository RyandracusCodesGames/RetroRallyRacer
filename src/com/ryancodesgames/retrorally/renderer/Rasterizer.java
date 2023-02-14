
package com.ryancodesgames.retrorally.renderer;

import static com.ryancodesgames.retrorally.RetroRallyRacer.getFrameHeight;
import static com.ryancodesgames.retrorally.RetroRallyRacer.getFrameWidth;
import com.ryancodesgames.retrorally.camera.Camera;
import com.ryancodesgames.retrorally.gfx.Attribute;
import com.ryancodesgames.retrorally.gfx.Attribute.FOG;
import static com.ryancodesgames.retrorally.gfx.ColorUtils.CD_GREEN;
import static com.ryancodesgames.retrorally.gfx.ColorUtils.CD_WHITE;
import com.ryancodesgames.retrorally.gfx.DirectionalLight;
import static com.ryancodesgames.retrorally.gfx.DrawUtils.TexturedTriangle;
import static com.ryancodesgames.retrorally.gfx.DrawUtils.slDrawTriangle;
import static com.ryancodesgames.retrorally.gfx.DrawUtils.slFillTriangle;
import com.ryancodesgames.retrorally.gfx.Light;
import com.ryancodesgames.retrorally.gfx.PointLight;
import com.ryancodesgames.retrorally.gfx.ZBuffer;
import com.ryancodesgames.retrorally.mathlib.Mat4x4;
import com.ryancodesgames.retrorally.mathlib.Mesh;
import com.ryancodesgames.retrorally.mathlib.PolygonGroup;
import static com.ryancodesgames.retrorally.mathlib.RallyMath.clamp;
import static com.ryancodesgames.retrorally.mathlib.RallyMath.max;
import com.ryancodesgames.retrorally.mathlib.Triangle;
import com.ryancodesgames.retrorally.mathlib.Vec2D;
import com.ryancodesgames.retrorally.mathlib.Vec3D;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Rasterizer 
{
    private PolygonGroup poly = new PolygonGroup();
    private Attribute att;
    private RasterAssembler r = new RasterAssembler();
    private Camera camera;
    private Vec3D vLookDir;
    private Mat4x4 matProj;
    private Graphics2D g2;
    private int[] pixels;
    private ZBuffer zBuffer;
    private int triangleCount;
    private float move;
    private float intensity;
    
    public Rasterizer(Attribute att, PolygonGroup poly, Camera camera, Mat4x4 matProj,Vec3D vLookDir, ZBuffer zBuffer, Graphics2D g2, int[] pixels,double intensity, float move)
    {
        this.att = att;
        this.poly = poly;
        this.camera = camera;
        this.matProj = matProj;
        this.vLookDir = vLookDir;
        this.zBuffer = zBuffer;
        this.g2 = g2;
        this.pixels = pixels;
        this.move = move;
    }
 
    public void draw()
    {
       Mat4x4 m = new Mat4x4();
       
       triangleCount = 0;

       List<Triangle> vecTrianglesToRaster = new ArrayList<>();
       
       for(Mesh mesh: poly.getPolyGroup())
       {
           Mat4x4 matView = r.calculateViewMatrix(mesh, camera, vLookDir);

           Mat4x4 matWorld = mesh.transform.getWorldMatrix();
            
           for(Triangle tri: mesh.triangles)
           {
                Triangle triProjected = new Triangle(new Vec3D(0,0,0), new Vec3D(0,0,0), new Vec3D(0,0,0));
                Triangle triTrans = new Triangle(new Vec3D(0,0,0), new Vec3D(0,0,0), new Vec3D(0,0,0));
                Triangle triViewed = new Triangle(new Vec3D(0,0,0), new Vec3D(0,0,0), new Vec3D(0,0,0));

                //ACCUMULATE ALL TRANSFORMATIONS TO ONE MATRIX
                r.assembleWorldMatrix(triTrans, tri, matWorld);

                //DETERMINE SURFACE NORMALS OF THE MESH
                Vec3D normal = new Vec3D(0,0,0);
                Vec3D line1 = new Vec3D(0,0,0);
                Vec3D line2 = new Vec3D(0,0,0);

                line1 = line1.vec3f_sub(triTrans.vec3d2, triTrans.vec3d);
                line2 = line1.vec3f_sub(triTrans.vec3d3, triTrans.vec3d);

                normal = line1.crossProduct(line1, line2);
                normal = line1.normalize(normal); 

                Vec3D vCameraRay = line1.vec3f_sub(triTrans.vec3d, camera.getCamera());

                //TAKES PROJECTION INTO ACCOUNT TO TEST SIMILARITY BETWEEN NORMAL AND CAMERA VECTOR
                if(line1.isFacingTowards(normal, vCameraRay))
                {
                   //DEFINE DIRECTION OF LIGHT SOURCE TO APPLY TO SURFACES
                   float totalLight = 0;
                   
                   Vec3D light_direction = new Vec3D(-0.05f ,-12.749f, -6.104886f); 
                   
                   Light a = new DirectionalLight(new Vec3D(0, 0, -1), 0xff0000, false);
                   
                   float x = (triTrans.vec3d.x + triTrans.vec3d2.x + triTrans.vec3d3.x)/3.0f;
                   float y = (triTrans.vec3d.y + triTrans.vec3d2.y + triTrans.vec3d3.y)/3.0f;
                   float z = (triTrans.vec3d.z + triTrans.vec3d2.z + triTrans.vec3d3.z)/3.0f;
                   
                   Vec3D pos = new Vec3D(x, y, z);
                   
                   Light light = new PointLight(light_direction, pos, 1.0f, 0.01f, 0.002f, CD_GREEN, false);
                   Light light2 = new PointLight(new Vec3D(-0.05f ,-12.749f, -180.104886f), pos, 1.0f, 0.01f, 0.002f, CD_GREEN, false);
                   Light light3 = new PointLight(new Vec3D(-0.31f ,-6.55f, -226.59f), pos, 1.0f, 0.01f, 0.002f, CD_GREEN, false);
                    
                   float dp = light.calcLightAttenuation(normal, 0.1f, camera, line2) + light2.calcLightAttenuation(normal, 0.1f, camera, line2) + light3.calcLightAttenuation(normal, 0.1f, camera, line2);

                    //WORLD SPACE TO VIEW SPACE
                    r.assembleViewMatrix(triViewed, triTrans, matView);

                    //PROJECT 3D GEOMETRICAL DATA TO NORMALIZED 2D SCREEN
                    r.assembleProjectionMatrix(triProjected, triViewed, matProj);

                    //CLIP TRIANGLE AGAINST NEAR PLANE
                    int nClippedTriangles = 0;
                    Triangle[] clipped = new Triangle[]{new Triangle(new Vec3D(0,0,0), new Vec3D(0,0,0), new Vec3D(0,0,0), new Vec2D(0,0), new Vec2D(0,0), new Vec2D(0,0))
                    ,new Triangle(new Vec3D(0,0,0), new Vec3D(0,0,0), new Vec3D(0,0,0), new Vec2D(0,0), new Vec2D(0,0), new Vec2D(0,0))};

                    nClippedTriangles = line1.triangleClipAgainstPlane(new Vec3D(0,0,0.1f), new Vec3D(0,0,1
                    ),triViewed, clipped);

                        for(int i = 0; i < nClippedTriangles; i++)
                        {
                            r.assembleClipSpace(triProjected, clipped, i, matProj);
                            r.assemblePerspectiveDivide(triProjected, line1);
                            //SCALE INTO VIEW
                            r.scaleIntoView(triProjected);

                            triProjected.setColor((int)Math.abs(dp*255),(int)Math.abs(dp*255),(int)Math.abs(dp*255));
                            triProjected.tex = mesh.tex;

                            vecTrianglesToRaster.add(triProjected);
                            triProjected.dp = (float)dp;
                    }
    
                }
           }
           
            //IMPLEMENTATION OF THE PAINTER'S ALGORITHM (SORT TRIANGLES FROM BACK TO FRONT)
            r.ZSort(vecTrianglesToRaster);
            
            zBuffer.resetBuffer();
            
            for(Triangle t: vecTrianglesToRaster)
            {
                Triangle[] clipped = new Triangle[]{new Triangle(new Vec3D(0,0,0), new Vec3D(0,0,0), new Vec3D(0,0,0), new Vec2D(0,0), new Vec2D(0,0), new Vec2D(0,0)),
                    new Triangle(new Vec3D(0,0,0), new Vec3D(0,0,0), new Vec3D(0,0,0), new Vec2D(0,0), new Vec2D(0,0), new Vec2D(0,0))};

                LinkedList<Triangle> listTriangles = new LinkedList<>();
                listTriangles.add(t);
                int nNewTriangles = 1;

                for(int p = 0; p < 4; p++)
                {
                    int trisToAdd = 0;

                    while(nNewTriangles > 0)
                    {
                        clipped = new Triangle[]{new Triangle(new Vec3D(0,0,0), new Vec3D(0,0,0), new Vec3D(0,0,0), new Vec2D(0,0), new Vec2D(0,0), new Vec2D(0,0)),
                    new Triangle(new Vec3D(0,0,0), new Vec3D(0,0,0), new Vec3D(0,0,0), new Vec2D(0,0), new Vec2D(0,0), new Vec2D(0,0))};
                        
                        Triangle test = new Triangle(new Vec3D(0,0,0),new Vec3D(0,0,0), new Vec3D(0,0,0), new Vec2D(0,0), new Vec2D(0,0), new Vec2D(0,0));
                        test = listTriangles.peek();
                        listTriangles.pollFirst();
                        nNewTriangles--;

                        Vec3D vec = new Vec3D(0,0,0);  

                        switch(p)
                        {
                            case 0:{trisToAdd = vec.triangleClipAgainstPlane(new Vec3D(0,0,0),new Vec3D(0,1,0),test,clipped);}break;
                            case 1:{trisToAdd = vec.triangleClipAgainstPlane(new Vec3D(0,getFrameHeight()-1,0),new Vec3D(0,-1,0),test,clipped);}break;
                            case 2:{trisToAdd = vec.triangleClipAgainstPlane(new Vec3D(0,0,0),new Vec3D(1,0,0),test,clipped);}break;
                            case 3:{trisToAdd = vec.triangleClipAgainstPlane(new Vec3D(getFrameWidth()-1,0,0),new Vec3D(-1,0,0),test,clipped);}break;
                        }

                        for (int w = 0; w < trisToAdd; w++)
                        {
                            listTriangles.add(clipped[w]);
                        }
                    }
                     nNewTriangles = listTriangles.size();
                }

                for(Triangle tt: listTriangles)
                {
                  //FORMULA FOR DEPTH CUE FOG IN RELATION TO DEPTH OF PARTICULAR PIXEL
                  //FORMULA FOUND THANKS TO DAVID COLSON
                  //CALCULATE THE DEPTH OF OF THE VERTEX IN VIEW SPACE AND THEN DO AN
                  //INVERSE LERP TO GET BACK A FOG DENSITY VALUE
                    float d = 0;
                    boolean fog = false;
                    if(att.getFog() == FOG.FOG_ON)
                    { 
                        Vec3D z = new Vec3D(0,0,0);

                        float maxFogDepth = 0.05f;
                        float minFogDepth = 0.003f;

                        d = Math.abs(tt.vec3d.z / tt.vec3d.w);
                        d = (float)(d - Math.floor(d) + intensity);
                        d = clamp((maxFogDepth - d)/(minFogDepth - maxFogDepth), 0.0f, 1.0f);
                        
                        fog = true;
                    }

                        switch(att.getDrawMode())
                        {
                            case TEXTURED:
                                TexturedTriangle(g2, (int)tt.vec3d.x,(int)tt.vec3d.y, tt.vec2d.u, tt.vec2d.v,tt.vec2d.w,
                           (int)tt.vec3d2.x,(int)tt.vec3d2.y, tt.vec2d2.u, tt.vec2d2.v, tt.vec2d2.w,
                            (int)tt.vec3d3.x,(int)tt.vec3d3.y, tt.vec2d3.u, tt.vec2d3.v, tt.vec2d3.w,
                        tt.tex,d, fog, false, pixels, zBuffer, tt.tex.getTexArray(), tt.dp);
                            break;
                            case SURFACE:
                                 slFillTriangle(pixels,(int)tt.vec3d.x,(int)tt.vec3d.y,(int)tt.vec3d2.x,(int)tt.vec3d2.y,
                        (int)tt.vec3d3.x,(int)tt.vec3d3.y,tt.col.getRGB());
                            break;
                            case WIREFRAME:
                                slDrawTriangle(pixels,(int)tt.vec3d.x,(int)tt.vec3d.y,(int)tt.vec3d2.x,(int)tt.vec3d2.y,
                        (int)tt.vec3d3.x,(int)tt.vec3d3.y, CD_WHITE);
                            break;
                        }
                      triangleCount++;
                }
                
            }
       }
    }
    
    public int getTriangleCount()
    {
        return triangleCount;
    }
    
    public void setIntensity(float f)
    {
        this.intensity = f;
    }
    
    public double getIntensity()
    {
        return intensity;
    }
}
