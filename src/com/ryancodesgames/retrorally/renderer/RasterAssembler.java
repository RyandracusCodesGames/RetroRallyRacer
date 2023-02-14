
package com.ryancodesgames.retrorally.renderer;

import static com.ryancodesgames.retrorally.RetroRallyRacer.getImageHeight;
import static com.ryancodesgames.retrorally.RetroRallyRacer.getImageWidth;
import com.ryancodesgames.retrorally.camera.Camera;
import com.ryancodesgames.retrorally.mathlib.Mat4x4;
import com.ryancodesgames.retrorally.mathlib.Mesh;
import com.ryancodesgames.retrorally.mathlib.Triangle;
import com.ryancodesgames.retrorally.mathlib.Vec2D;
import com.ryancodesgames.retrorally.mathlib.Vec3D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RasterAssembler 
{
    private Mat4x4 m = new Mat4x4();
    
    public void assembleWorldMatrix(Triangle triTrans, Triangle tri, Mat4x4 matWorld)
    {
        triTrans.vec3d = m.multiplyMatrixVector(tri.vec3d, matWorld);
        triTrans.vec3d2 = m.multiplyMatrixVector(tri.vec3d2, matWorld);
        triTrans.vec3d3 = m.multiplyMatrixVector(tri.vec3d3, matWorld);
        triTrans.vec2d = tri.vec2d;
        triTrans.vec2d2 = tri.vec2d2;
        triTrans.vec2d3 = tri.vec2d3;
    }
    
    public Vec3D assembleVertexNormals(Triangle triTrans, Vec3D normal, Vec3D line1, Vec3D line2)
    {
        line1 = line1.vec3f_sub(triTrans.vec3d2, triTrans.vec3d);
        line2 = line1.vec3f_sub(triTrans.vec3d3, triTrans.vec3d);

        normal = line1.crossProduct(line1, line2);
        normal = line1.normalize(normal); 
        
        return normal;
    }
    
    public void assembleViewMatrix(Triangle triViewed, Triangle triTrans, Mat4x4 matView)
    {
        triViewed.vec3d = m.multiplyMatrixVector(triTrans.vec3d, matView);
        triViewed.vec3d2 = m.multiplyMatrixVector(triTrans.vec3d2, matView);
        triViewed.vec3d3 = m.multiplyMatrixVector(triTrans.vec3d3, matView);
        triViewed.vec2d = triTrans.vec2d;
        triViewed.vec2d2 = triTrans.vec2d2;
        triViewed.vec2d3 = triTrans.vec2d3;
    }
    
    public Mat4x4 calculateViewMatrix(Mesh mesh, Camera camera, Vec3D vLookDir)
    {
        Vec3D vUp = new Vec3D(0,1,0);
        Vec3D vTarget = new Vec3D(0,0,1);
        Mat4x4 matCameraRotated = new Mat4x4();
        matCameraRotated = m.slRotY(mesh.transform.getRotAngleY());
        vTarget = vTarget.vec3f_add(camera.getCamera(), vLookDir);

        //USING THE INFORMATION PROVIDED ABOVE TO DEFINE A CAMERA MATRIX
        Mat4x4 matCamera = new Mat4x4();
        matCamera = matCamera.pointAtMatrix(camera.getCamera(), vTarget, vUp);

        Mat4x4 matView = new Mat4x4();
        matView = matView.slInverseMatrix(matCamera);
        
        return matView;
    }
    
    public void assembleProjectionMatrix(Triangle triProjected, Triangle triViewed, Mat4x4 matProj)
    {
        triProjected.vec3d = m.multiplyMatrixVector(triViewed.vec3d, matProj);
        triProjected.vec3d2 = m.multiplyMatrixVector(triViewed.vec3d2, matProj);
        triProjected.vec3d3 = m.multiplyMatrixVector(triViewed.vec3d3, matProj);
    }
    
    public void assembleClipSpace(Triangle triProjected, Triangle[] clipped, int i, Mat4x4 matProj )
    {
        triProjected.vec3d = m.multiplyMatrixVector(clipped[i].vec3d, matProj);
        triProjected.vec3d2 = m.multiplyMatrixVector(clipped[i].vec3d2, matProj);
        triProjected.vec3d3 = m.multiplyMatrixVector(clipped[i].vec3d3, matProj);
        triProjected.vec2d = (Vec2D)clipped[i].vec2d.clone();
        triProjected.vec2d2 = (Vec2D)clipped[i].vec2d2.clone();
        triProjected.vec2d3 = (Vec2D)clipped[i].vec2d3.clone();
    }
    
    public void assemblePerspectiveDivide(Triangle triProjected, Vec3D line1)
    {
        triProjected.vec2d.u = triProjected.vec2d.u/triProjected.vec3d.w;
        triProjected.vec2d2.u = triProjected.vec2d2.u/triProjected.vec3d2.w;
        triProjected.vec2d3.u = triProjected.vec2d3.u/triProjected.vec3d3.w;
        triProjected.vec2d.v = triProjected.vec2d.v/triProjected.vec3d.w;
        triProjected.vec2d2.v = triProjected.vec2d2.v/triProjected.vec3d2.w;
        triProjected.vec2d3.v = triProjected.vec2d3.v/triProjected.vec3d3.w;

        triProjected.vec2d.w = 1.0f/triProjected.vec3d.w;
        triProjected.vec2d2.w = 1.0f/triProjected.vec3d2.w;
        triProjected.vec2d3.w = 1.0f/triProjected.vec3d3.w;

        triProjected.vec3d = line1.vec3f_div_by_scalar(triProjected.vec3d, triProjected.vec3d.w);
        triProjected.vec3d2 = line1.vec3f_div_by_scalar(triProjected.vec3d2, triProjected.vec3d2.w);
        triProjected.vec3d3 = line1.vec3f_div_by_scalar(triProjected.vec3d3, triProjected.vec3d3.w);
    }
    
    public void scaleIntoView(Triangle triProjected)
    {
        triProjected.vec3d.x += 1.0f;
        triProjected.vec3d2.x += 1.0f;
        triProjected.vec3d3.x += 1.0f;
        triProjected.vec3d.y += 1.0f;
        triProjected.vec3d2.y += 1.0f;
        triProjected.vec3d3.y += 1.0f;

        triProjected.vec3d.x *= 0.5f * getImageWidth();
        triProjected.vec3d.y *= 0.5f * getImageHeight();
        triProjected.vec3d2.x *= 0.5f * getImageWidth();
        triProjected.vec3d2.y *= 0.5f * getImageHeight();
        triProjected.vec3d3.x *= 0.5f * getImageWidth();
        triProjected.vec3d3.y *= 0.5f * getImageHeight();
    }
    
    public void ZSort(List<Triangle> vecTrianglesToRaster)
    {
        Collections.sort((ArrayList<Triangle>)vecTrianglesToRaster, new Comparator<Triangle>() {
                    @Override
                    public int compare(Triangle t1, Triangle t2) {
                        float z1=(t1.vec3d.z+t1.vec3d2.z+t1.vec3d3.z)/3.0f;
                        float z2=(t2.vec3d.z+t2.vec3d2.z+t2.vec3d3.z)/3.0f;
                        return (z1<z2)?1:(z1==z2)?0:-1;
                    }
                });
    }
}
