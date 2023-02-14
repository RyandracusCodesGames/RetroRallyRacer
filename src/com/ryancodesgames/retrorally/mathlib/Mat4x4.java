
package com.ryancodesgames.retrorally.mathlib;

import static com.ryancodesgames.retrorally.mathlib.RallyMath.PI;

public class Mat4x4 
{
    public float mtx11, mtx12, mtx13, mtx14;
    public float mtx21, mtx22, mtx23, mtx24;
    public float mtx31, mtx32, mtx33, mtx34;
    public float mtx41, mtx42, mtx43, mtx44;
    
    public Mat4x4()
    {
        mtx11 = 0.0f; mtx12 = 0.0f; mtx13 = 0.0f; mtx14 = 0.0f;
        mtx21 = 0.0f; mtx22 = 0.0f; mtx23 = 0.0f; mtx24 = 0.0f;
        mtx31 = 0.0f; mtx32 = 0.0f; mtx33 = 0.0f; mtx34 = 0.0f;
        mtx41 = 0.0f; mtx42 = 0.0f; mtx43 = 0.0f; mtx44 = 0.0f;
    }
    
    public Vec3D multiplyMatrixVector(Vec3D in, Mat4x4 m)
    {
        Vec3D out = new Vec3D();

        out.x = in.x * m.mtx11 + in.y * m.mtx21 + in.z * m.mtx31 + in.w * m.mtx41;
        out.y = in.x * m.mtx12 + in.y * m.mtx22 + in.z * m.mtx32 + in.w * m.mtx42;
        out.z = in.x * m.mtx13 + in.y * m.mtx23 + in.z * m.mtx33 + in.w * m.mtx43;
        out.w = in.x * m.mtx14 + in.y * m.mtx24 + in.z * m.mtx34 + in.w * m.mtx44;

        return out;
    }
    
    public Mat4x4 slPerspective(float a, float fov, float fNear, float fFar)
    {
        Mat4x4 m = new Mat4x4();
        
        float fovRad = 1.00f / (float)Math.tan(fov * 0.50f/ PI * 180.00f);
        
        m.mtx11 = a * fovRad;
        m.mtx22 = fovRad;
        m.mtx33 = fFar / (fFar - fNear);
        m.mtx34 = (-fFar * fNear) / (fFar - fNear);
        m.mtx43 = 1.0f;
        m.mtx44 = 0.0f;
        
        return m;
    }
    
    public Mat4x4 slIdentity()
    {
        Mat4x4 m = new Mat4x4();
        
        m.mtx11 = 1.0f; m.mtx12 = 0.0f; m.mtx13 = 0.0f; m.mtx14 = 0.0f;
        m.mtx21 = 0.0f; m.mtx22 = 1.0f; m.mtx23 = 0.0f; m.mtx24 = 0.0f;
        m.mtx31 = 0.0f; m.mtx32 = 0.0f; m.mtx33 = 1.0f; m.mtx34 = 0.0f;
        m.mtx41 = 0.0f; m.mtx42 = 0.0f; m.mtx43 = 0.0f; m.mtx44 = 1.0f;
        
        return m;
    }
    
    public Mat4x4 slTranslation(float tX, float tY, float tZ)
    {
        Mat4x4 m = new Mat4x4();
        
        m.mtx11 = 1.0f; m.mtx12 = 0.0f; m.mtx13 = 0.0f; m.mtx14 = 0.0f;
        m.mtx21 = 0.0f; m.mtx22 = 1.0f; m.mtx23 = 0.0f; m.mtx24 = 0.0f;
        m.mtx31 = 0.0f; m.mtx32 = 0.0f; m.mtx33 = 1.0f; m.mtx34 = 0.0f;
	m.mtx41 =  tX; m.mtx42 =  tY; m.mtx43 =  tZ; m.mtx44 = 1.0f;
        
        return m;
    }
    
    public Mat4x4 slScale(float sX, float sY, float sZ)
    {
        Mat4x4 m = new Mat4x4();
        
        m.mtx11 =  sX; m.mtx12 = 0.0f; m.mtx13 = 0.0f; m.mtx14 = 0.0f;
        m.mtx21 = 0.0f; m.mtx22 =  sY; m.mtx23 = 0.0f; m.mtx24 = 0.0f;
        m.mtx31 = 0.0f; m.mtx32 = 0.0f; m.mtx33 =  sZ; m.mtx34 = 0.0f;
	m.mtx41 = 0.0f; m.mtx42 = 0.0f; m.mtx43 = 0.0f; m.mtx44 = 1.0f;
        
        return m;
    }
    
    public Mat4x4 slRotX(float a)
    {
        Mat4x4 m = new Mat4x4();
        
        float cos = (float)Math.cos(a);
        float sin = (float)Math.sin(a);
        
        m.mtx11 = 1.0f; m.mtx12 = 0.0f; m.mtx13 = 0.0f; m.mtx14 = 0.0f;
        m.mtx21 = 0.0f; m.mtx22 = cos; m.mtx23 = -sin; m.mtx24 = 0.0f;
        m.mtx31 = 0.0f; m.mtx32 = sin; m.mtx33 = cos; m.mtx34 = 0.0f;
	m.mtx41 = 0.0f; m.mtx42 = 0.0f; m.mtx43 = 0.0f; m.mtx44 = 1.0f;
        
        return m;
    }
    
    public Mat4x4 slRotY(float a)
    {
        Mat4x4 m = new Mat4x4();
        
        float cos = (float)Math.cos(a);
        float sin = (float)Math.sin(a);
        
        m.mtx11 = cos; m.mtx12 = 0.0f; m.mtx13 = sin; m.mtx14 = 0.0f;
        m.mtx21 = 0.0f; m.mtx22 = 1.0f; m.mtx23 = 0.0f; m.mtx24 = 0.0f;
        m.mtx31 = -sin; m.mtx32 = 0.0f; m.mtx33 = cos; m.mtx34 = 0.0f;
	m.mtx41 = 0.0f; m.mtx42 = 0.0f; m.mtx43 = 0.0f; m.mtx44 = 1.0f;
        
        return m;
    }
    
    public Mat4x4 slRotZ(float a)
    {
        Mat4x4 m = new Mat4x4();
        
        float cos = (float)Math.cos(a);
        float sin = (float)Math.sin(a);
        
        m.mtx11 = cos; m.mtx12 = -sin; m.mtx13 = 0.0f; m.mtx14 = 0.0f;
        m.mtx21 = sin; m.mtx22 = cos; m.mtx23 = 0.0f; m.mtx24 = 0.0f;
        m.mtx31 = 0.0f; m.mtx32 = 0.0f; m.mtx33 = 1.0f; m.mtx34 = 0.0f;
	m.mtx41 = 0.0f; m.mtx42 = 0.0f; m.mtx43 = 0.0f; m.mtx44 = 1.0f;
        
        return m;
    }
    
    public Mat4x4 matrixMatrixMultiplication(Mat4x4 m1, Mat4x4 m2)
    {
        Mat4x4 m = new Mat4x4();
           
        float[][] out = new float[][]{{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}};
        float[][] mat = new float[][]{{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}};
        float[][] mat2 = new float[][]{{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}};
        
        mat[0][0] = m1.mtx11; mat[0][1] = m1.mtx12; mat[0][2] = m1.mtx13; mat[0][3] = m1.mtx14;
        mat[1][0] = m1.mtx21; mat[1][1] = m1.mtx22; mat[1][2] = m1.mtx23; mat[1][3] = m1.mtx24;
        mat[2][0] = m1.mtx31; mat[2][1] = m1.mtx32; mat[2][2] = m1.mtx33; mat[2][3] = m1.mtx34;
        mat[3][0] = m1.mtx41; mat[3][1] = m1.mtx42; mat[3][2] = m1.mtx43; mat[3][3] = m1.mtx44;
        
        mat2[0][0] = m2.mtx11; mat2[0][1] = m2.mtx12; mat2[0][2] = m2.mtx13; mat2[0][3] = m2.mtx14;
        mat2[1][0] = m2.mtx21; mat2[1][1] = m2.mtx22; mat2[1][2] = m2.mtx23; mat2[1][3] = m2.mtx24;
        mat2[2][0] = m2.mtx31; mat2[2][1] = m2.mtx32; mat2[2][2] = m2.mtx33; mat2[2][3] = m2.mtx34;
        mat2[3][0] = m2.mtx41; mat2[3][1] = m2.mtx42; mat2[3][2] = m2.mtx43; mat2[3][3] = m2.mtx44;
        
         for(int c = 0; c < 4; c++)
        {
            for(int r = 0; r < 4; r++)
            {
                out[r][c] = mat[r][0] * mat2[0][c] + mat[r][1] * mat2[1][c] +
                mat[r][2] * mat2[2][c] + mat[r][3] * mat2[3][c];
            }
        }
         
       m.mtx11 = out[0][0]; m.mtx12 = out[0][1]; m.mtx13 = out[0][2]; m.mtx14 = out[0][3];
       m.mtx21 = out[1][0]; m.mtx22 = out[1][1]; m.mtx23 = out[1][2]; m.mtx24 = out[1][3];
       m.mtx31 = out[2][0]; m.mtx32 = out[2][1]; m.mtx33 = out[2][2]; m.mtx34 = out[2][3];
       m.mtx41 = out[3][0]; m.mtx42 = out[3][1]; m.mtx43 = out[3][2]; m.mtx44 = out[3][3];
        
        
        return m;
    }
    
    public Mat4x4 pointAtMatrix(Vec3D pos, Vec3D target, Vec3D up)
    {
        //CALCULATE THE NEW FOWARD DIRECTION
        Vec3D newForward = new Vec3D(0,0,0);
        
        newForward = newForward.vec3f_sub(target, pos);
        newForward = newForward.normalize(newForward);
        
        //CALCULATE THE NEW UP DIRECTION
        Vec3D a = new Vec3D(0,0,0);
        Vec3D newUp = new Vec3D(0,0,0);
        
        a = a.vec3f_mul_by_scalar(newForward, a.dotProduct(up, newForward));
        newUp = a.vec3f_sub(up, a);
        newUp = a.normalize(newUp);
        
        //NEW RIGHT DIRECTION JUST TAKES THE CROSS PRODUCT
        Vec3D newRight = new Vec3D();
        newRight = a.crossProduct(newUp, newForward);
        
        //MANUALLY CONSTRUCT POINT AT MATRIX, THE DIMENSION AND TRANSITION
        Mat4x4 mat = new Mat4x4();
        
        mat.mtx11 = newRight.x;	  mat.mtx12 = newRight.y;   mat.mtx13 = newRight.z;	mat.mtx14 = 0.0f;
	mat.mtx21 = newUp.x;	  mat.mtx22 = newUp.y;      mat.mtx23 = newUp.z;	mat.mtx24 = 0.0f;
	mat.mtx31 = newForward.x; mat.mtx32 = newForward.y; mat.mtx33 = newForward.z;	mat.mtx34 = 0.0f;
	mat.mtx41 = pos.x;	  mat.mtx42= pos.y;         mat.mtx43 = pos.z;          mat.mtx44 = 1.0f;
        
	return mat;
    }
    
    public Mat4x4 slInverseMatrix(Mat4x4 m)
    {
        Mat4x4 mat = new Mat4x4();
	mat.mtx11 = m.mtx11; mat.mtx12 = m.mtx21; mat.mtx13 = m.mtx31; mat.mtx14 = 0.0f;
	mat.mtx21 = m.mtx12; mat.mtx22 = m.mtx22; mat.mtx23 = m.mtx32; mat.mtx24 = 0.0f;
	mat.mtx31 = m.mtx13; mat.mtx32 = m.mtx23; mat.mtx33 = m.mtx33; mat.mtx34 = 0.0f;
	mat.mtx41 = -(m.mtx41 * mat.mtx11 + m.mtx42 * mat.mtx21 + m.mtx43 * mat.mtx31);
	mat.mtx42 = -(m.mtx41 * mat.mtx12 + m.mtx42 * mat.mtx22 + m.mtx43 * mat.mtx32);
	mat.mtx43 = -(m.mtx41 * mat.mtx13 + m.mtx42 * mat.mtx23 + m.mtx43 * mat.mtx33);
	mat.mtx44 = 1.0f;
        
	return mat;
    }
}
